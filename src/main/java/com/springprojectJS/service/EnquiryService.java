package com.springprojectJS.service;


import com.springprojectJS.entities.Enquiry;
import com.springprojectJS.dto.ViewEnqsFilterRequest;
import java.util.List;

//import com.springprojectJS.entities.Counsellor;


public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry eq, Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer engId);
}
