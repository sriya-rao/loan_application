package com.loan.app.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.loan.app.entity.LoanType;
import com.loan.app.repo.LoanRepository;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	LoanRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		repository.deleteAll();
		
		LoanType loan1=new LoanType();
		loan1.setLoan("Personal Loan");
		
		LoanType loan2=new LoanType();
		loan2.setLoan("Home Loan");
		
		LoanType loan3=new LoanType();
		loan3.setLoan("Business Loan");
		
		LoanType loan4=new LoanType();
		loan4.setLoan("Mortgage Loan");
		
		LoanType loan5=new LoanType();
		loan5.setLoan("Commercial Loan");
		
		
		List<LoanType> list=Arrays.asList(loan1,loan2,loan3,loan4,loan5);
		repository.saveAll(list);
		
		
	}

}
