package com.springprojectJS.controllers;

import org.springframework.stereotype.Controller;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springprojectJS.entities.Enquiry;
import com.springprojectJS.dto.ViewEnqsFilterRequest;
import com.springprojectJS.dto.DashboardResponse;

import com.springprojectJS.service.EnquiryService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	private EnquiryService enqService;
	
	public EnquiryController(EnquiryService enqService) {
		this.enqService=enqService;
	}
	@PostMapping("/filter-enqs")
	public String filterEnquries(ViewEnqsFilterRequest viewEnqsfilterRequest, HttpServletRequest req, Model model) {
		
		//get existing session obj
		HttpSession session=req.getSession(false);
		Integer counsellorId=(Integer) session.getAttribute("counsellorId");
		
		List<Enquiry>enqsList=enqService.getEnquiriesWithFilter(viewEnqsfilterRequest, counsellorId);
				
		model.addAttribute("enquiries", enqsList);
		
		//come back to this sneha 41:35 aug 23
		return "viewEnqsPage";		
			}
	@GetMapping("/view-enquiries")
	public String  getEnquries(HttpServletRequest request, Model model) {
		//get existing session obj
		HttpSession session=request.getSession(false);
		Integer counsellorId=(Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqsList=enqService.getAllEnquiries(counsellorId);
		model.addAttribute("enquiries", enqsList);
		
		//search form binding object
		
		ViewEnqsFilterRequest filterReq=new ViewEnqsFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", filterReq);
		return "viewEnqsPage";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		Enquiry enquiry=new Enquiry();
		//send data to ui
		model.addAttribute("enquiry", enquiry);		
		return "enquiryForm";
	}
	
	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model) {
		
		Enquiry enquiry=enqService.getEnquiryById(enqId);
		model.addAttribute("enquiry", enquiry);
		
		
		return "enquiryForm";
	}
	
	@PostMapping("/addEnq")
	public String handleAddEnquiry(Enquiry enquiry, HttpServletRequest req, Model model) throws Exception{
		
		//passing true result new session, for existing session false		
		//to get existing session obj
		
		HttpSession session=req.getSession(false);
		Integer counsellorId=(Integer) session.getAttribute("counsellorId");
		
		boolean isSaved=enqService.addEnquiry(enquiry, counsellorId);
		if (isSaved) {
			model.addAttribute("smsg", "Enquiry Added");
		}		
		else {
			model.addAttribute("emsg", "Failed to Add Enquiry");			
		}	
		
	    enquiry= new Enquiry();
		model.addAttribute("enquiry", enquiry);
	
		return "enquiryForm";
	}
	
}
