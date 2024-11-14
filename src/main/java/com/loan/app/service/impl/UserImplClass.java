package com.loan.app.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.app.bind.Login;
import com.loan.app.bind.Register;
import com.loan.app.bind.Unlock;
import com.loan.app.entity.User;
import com.loan.app.repo.UserRepository;
import com.loan.app.service.UserService;
import com.loan.app.util.Constants;
import com.loan.app.util.EmailUtils;
import com.loan.app.util.PasswordUtil;

import jakarta.servlet.http.HttpSession;


@Service
public class UserImplClass implements UserService{

	@Autowired
	UserRepository repository;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public String login(Login login) {
       User user=repository.findByEmailAndPassword(login.getEmail(), login.getPassword());
       if(user==null) {
    	   return "Invalid Credentials";
       }
	
       else if(user!=null && user.getStatus().equals("LOCKED")) {
    	   return "Unlock your account to login";
       }
       else {
    	   session.setAttribute("userObj", user);
           return "success";
           
	}
	
	}

	@Override
	public Boolean register(Register reg) {
		User user1=repository.findByEmail(reg.getEmail());
		if(user1!=null) {
			return false;
		}
		
		User user=new User();
		BeanUtils.copyProperties(reg, user);
		String pwdString=PasswordUtil.generatePwd();
		user.setStatus(Constants.LOCKED.toString());
		user.setPassword(pwdString);
		repository.save(user);
		String to=reg.getEmail();
		String subject="Unlock your account here";
		StringBuilder body=new StringBuilder("");
		body.append("<h3>Use below temp passsword to unlock your account</h3><br>");
		body.append("Your temp password is "+pwdString);
		body.append("<br>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Click here to unlock account</a>");
				
		emailUtils.sendEmail(to, subject, body.toString());
		return true;
	}

	@Override
	public Boolean unlock(Unlock unlock) {
        User user=repository.findByEmail(unlock.getEmail());
        if(user.getPassword().equals(unlock.getTempPwd()))
        {
		user.setPassword(unlock.getNewPwd());
		user.setStatus(Constants.UNLOCKED.toString());
		repository.save(user);
		return true;
        }
        else {
			return false;
		}
	}

	@Override
	public Boolean forgotPwd(String email) {
       User user=repository.findByEmail(email);
       
       if(user!=null) {
    	   String pwd=user.getPassword();
    	   String to=user.getEmail();
    	   String subject="Login Password";
    	   String body="<h4>Your password is ::</h4>"+pwd;
    	   emailUtils.sendEmail(to, subject, body);
    	   return true;
       }
       else {
   		return false;
	    }
	}

}
