package com.loan.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.app.bind.Dashboard;
import com.loan.app.bind.EnquiryDForm;
import com.loan.app.bind.Search;
import com.loan.app.constants.AppConstants;
import com.loan.app.entity.Enquiry;
import com.loan.app.entity.LoanType;
import com.loan.app.entity.User;
import com.loan.app.repo.EnquiryRepository;
import com.loan.app.repo.LoanRepository;
import com.loan.app.repo.UserRepository;
import com.loan.app.service.EnquiryService;

import jakarta.servlet.http.HttpSession;


@Service
public class EnquiryImplClass implements EnquiryService{
	
	@Autowired
	EnquiryRepository repository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoanRepository loan;
	
	@Autowired
     private HttpSession session;

	@Override
	public Dashboard dashBoard(User user) {		
		List<Enquiry> list=repository.findByUser(user);
		Integer total=list.size();	
	    Integer enrolled=list.stream().filter(e->e.getLoanStatus().equals("ENROLLED")).toList().size();
	    Integer lost=list.stream().filter(e->e.getLoanStatus().equals("LOST")).toList().size();
	    Dashboard dashboard=new Dashboard();
	    
	    dashboard.setTotal(total);
	    dashboard.setEnrolled(enrolled);
	    dashboard.setLost(lost);
		
	    return dashboard;
	}
	

	@Override
	public Boolean saveEnquiry(EnquiryDForm form) {
       Enquiry enquiry=new Enquiry();
       User user=(User)session.getAttribute(AppConstants.USER_OBJ);
       BeanUtils.copyProperties(form, enquiry);
       enquiry.setUser(user);
       repository.save(enquiry);
		List<Enquiry> list=user.getCustomers();
		if(list==null) {
			list=new ArrayList<>();
			list.add(enquiry);
		}
		else {
			list.add(enquiry);
		}
		return true;
	}

	@Override
	public List<Enquiry> getAllData(User user, Search search) {
		
		
		return repository.findByUser(user);
	}

	@Override
	public List<LoanType> getLoanType() {
        
		return loan.findAll();
	}

	@Override
	public EnquiryDForm editCustomer(Integer id) {
		Optional<Enquiry> enquiry=repository.findById(id);
		EnquiryDForm form=new EnquiryDForm();

		if(enquiry.isPresent()) {
		BeanUtils.copyProperties(enquiry.get(), form);
		}
		return form;
	}

	@Override
	public void deleteCustomer(Integer id) {
		Optional<Enquiry> enquiry=repository.findById(id);
		if(enquiry.isPresent()) {
		repository.deleteById(id);
		}
	}


	@Override
	public Boolean updateEnquiry(EnquiryDForm form) {
		return true;
	}


	@Override
	public List<Enquiry> getFilteredData(User user, Search search) {
     List<Enquiry> list=repository.findByUser(user);
     if(null!=search.getLoanType()&& !"".equals(search.getLoanType())) {
    	 list=list.stream().filter(e->e.getLoanType().equals(search.getLoanType())).toList();
     }
     
     if(null!=search.getLoanStatus()&& !"".equals(search.getLoanStatus())) {
    	 list=list.stream().filter(e->e.getLoanStatus().equals(search.getLoanStatus())).toList();
     }
     
     if(null!=search.getBankName()&& !"".equals(search.getBankName())) {
    	 list=list.stream().filter(e->e.getBankName().equals(search.getBankName())).toList();
     }
				
		return list;
	}

}
