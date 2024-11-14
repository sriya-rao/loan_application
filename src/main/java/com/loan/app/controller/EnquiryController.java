package com.loan.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loan.app.bind.Dashboard;
import com.loan.app.bind.EnquiryDForm;
import com.loan.app.bind.Search;
import com.loan.app.constants.AppConstants;
import com.loan.app.entity.Enquiry;
import com.loan.app.entity.User;
import com.loan.app.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	EnquiryService service;
	
	
	
	@Autowired
	private HttpSession session;

	@GetMapping("/dash")
	public String getDashboard(Model model) {
		User user=(User)session.getAttribute(AppConstants.USER_OBJ);
		Dashboard dashboard=service.dashBoard(user);
		model.addAttribute("dashboard", dashboard);
		return "dashboard";
	}
	
	@GetMapping("/add")
	public String addCustomer(Model model) {
		List<String> loans=service.getLoanType().stream().map(e->e.getLoan()).collect(Collectors.toList());		
		model.addAttribute(AppConstants.LOANS, loans);
		model.addAttribute(AppConstants.CUSTOMER, new EnquiryDForm());
		return "add";
	}
	
	
	@PostMapping("/customer")
	public String saveCustomer(EnquiryDForm form,Model model) {
		boolean isSaved=service.saveEnquiry(form);
		if(isSaved) {
			model.addAttribute("success", "Added Successfully");
		}
		else {
			model.addAttribute("error", "Something went wrong");
		}
		List<String> loans=service.getLoanType().stream().map(e->e.getLoan()).collect(Collectors.toList());		
		model.addAttribute(AppConstants.LOANS, loans);
		model.addAttribute(AppConstants.CUSTOMER, new EnquiryDForm());
		return "add";
	}
	
	
	@GetMapping("/view")
	public String viewAllData(Search search,Model model) {
		User user=(User)session.getAttribute(AppConstants.USER_OBJ);		
		List<Enquiry> list=service.getAllData(user, search);
		List<String> loans=service.getLoanType().stream().map(e->e.getLoan()).collect(Collectors.toList());		
		model.addAttribute(AppConstants.LOANS, loans);
		model.addAttribute("search",  new Search());
		model.addAttribute("list", list);
		return "view";
	}
	
	
	@GetMapping("/filter")
	public String filterData(@RequestParam String type,@RequestParam String status,@RequestParam String bank,Model model) {
		Search search =new Search();
		search.setBankName(bank);
		search.setLoanType(type);
		search.setLoanStatus(status);
		User user=(User)session.getAttribute(AppConstants.USER_OBJ);		

		
		List<Enquiry> list=service.getFilteredData(user, search);
		if(list==null) {
			model.addAttribute("msg", "No Records Available");
		}
		model.addAttribute("list", list);
		return "filter";
	}
	
	
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam Integer id,Model model) {
		service.deleteCustomer(id);
		model.addAttribute("msg", "Deleted Succesfully");
		return "redirect:view";
	}
	
	
	@GetMapping("/edit")
	public String editCustomer(@RequestParam Integer id,Model model) {
		EnquiryDForm form= service.editCustomer(id);
		model.addAttribute(AppConstants.CUSTOMER, form);
		return "add";
	}
	
	
	@PostMapping("/update")
	public String updateCustomer(EnquiryDForm form,Model model) {
		
		boolean isSaved=service.updateEnquiry(form);
		if(isSaved) {
			model.addAttribute("success", "Updated Successfully");
		}
		else {
			model.addAttribute("error", "Something went wrong");
		}
		return "redirect:view";
	}

	
	
	
	
	
}
