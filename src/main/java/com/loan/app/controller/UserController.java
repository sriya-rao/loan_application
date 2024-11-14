package com.loan.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loan.app.bind.Login;
import com.loan.app.bind.Register;
import com.loan.app.bind.Unlock;
import com.loan.app.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new Register());
		return "register";
	}
	
	
	@PostMapping("/save")
	public String saveUser(Register register,Model model) {
	 boolean isSaved=	service.register(register);		
	 if(isSaved) {
		 model.addAttribute("msg", "Saved Success");
	 }
	 else {
		 model.addAttribute("msg", "Email already exists");
	}
		model.addAttribute("user", new Register());
		return "register";
	}

	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email,Model model) {
        Unlock unlock=new Unlock();
        unlock.setEmail(email);
		model.addAttribute("unlock", unlock);
		return "unlock";
	}
	
	
	@PostMapping("/unlock")
	public String unlockAccount(Unlock unlock,Model model) {
		if(unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
		   Boolean  isUnlocked=service.unlock(unlock);
		   if(isUnlocked) {
				model.addAttribute("success", "Account is unlocked!!Login here");
			}
			else {
				model.addAttribute("error", "Unable to unlock account");
			}
		}
		else {
		      model.addAttribute("msg", "Passwords must match");
		}
	
		return "unlock";
	}
		
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("login", new Login());
		return "Login";
	}
	
	
	@PostMapping("/validate")
	public String validateUser(Login login,Model model) {
		String response=service.login(login);
		if(response.contains("success")) {
			model.addAttribute("msg", "Login Success");			
			return "redirect:dash";
		}
		else {
			model.addAttribute("error", response);
			return "Login";
		}
	}
	
	@GetMapping("/forgot")
	public String password() {
		return "forgotPwd";
	}
	
	
	@PostMapping("/pwd")
	public String forgotPassword(@RequestParam String email,Model model) {
		boolean isPresent=service.forgotPwd(email);
		if(isPresent) {
			model.addAttribute("msg", "Password sent to your mail");
		}
		else {
			model.addAttribute("error", "Invalid email");
		}
		return "forgotPwd";
	}
	
	
}
