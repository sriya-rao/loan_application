package com.loan.app.bind;

import lombok.Data;

@Data
public class Unlock {

	private String email;
	
	private String tempPwd;
	
	private String newPwd;
	
	private String confirmPwd;
}
