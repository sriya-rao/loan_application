package com.loan.app.service;

import com.loan.app.bind.Login;
import com.loan.app.bind.Register;
import com.loan.app.bind.Unlock;

public interface UserService {

	
	public String login(Login login);
	
	public Boolean register(Register reg);
	
	public Boolean unlock(Unlock unlock);
	
	public Boolean forgotPwd(String email);
}
