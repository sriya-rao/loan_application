package com.loan.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.app.entity.LoanType;

public interface LoanRepository extends JpaRepository<LoanType, Integer>{

}
