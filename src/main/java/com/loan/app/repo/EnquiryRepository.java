package com.loan.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.app.entity.Enquiry;
import com.loan.app.entity.User;

public interface EnquiryRepository extends JpaRepository<Enquiry, Integer>{

	public List<Enquiry> findByUser(User user);
	
	
}
