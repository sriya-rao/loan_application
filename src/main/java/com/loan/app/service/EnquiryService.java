package com.loan.app.service;

import java.util.List;

import com.loan.app.bind.Dashboard;
import com.loan.app.bind.EnquiryDForm;
import com.loan.app.bind.Search;
import com.loan.app.entity.Enquiry;
import com.loan.app.entity.LoanType;
import com.loan.app.entity.User;

public interface EnquiryService {

	
	public Dashboard dashBoard(User user);
	
	public Boolean saveEnquiry(EnquiryDForm form);
	
	public Boolean updateEnquiry(EnquiryDForm form);

	
	public List<Enquiry> getAllData(User user,Search search);
	
	public List<Enquiry> getFilteredData(User user,Search search);

	
	public List<LoanType> getLoanType();
	
	public EnquiryDForm editCustomer(Integer id);
	
	public void deleteCustomer(Integer id);
}
