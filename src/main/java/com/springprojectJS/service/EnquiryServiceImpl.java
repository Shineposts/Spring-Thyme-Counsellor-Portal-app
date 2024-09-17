package com.springprojectJS.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.springprojectJS.dto.ViewEnqsFilterRequest;
import com.springprojectJS.entities.Counsellor;
import com.springprojectJS.entities.Enquiry;
import com.springprojectJS.repos.CounsellorRepo;
import com.springprojectJS.repos.EnquiryRepo;

import io.micrometer.common.util.StringUtils;
@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	private EnquiryRepo enqRepo;
	
	private CounsellorRepo counsellorRepo;
	
	public EnquiryServiceImpl(EnquiryRepo enqRepo, CounsellorRepo counsellorRepo) {
		this.enqRepo=enqRepo;
		this.counsellorRepo=counsellorRepo;
	}
 
	
	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		// TODO Auto-generated method stub
		
		Counsellor counsellor=counsellorRepo.findById(counsellorId).orElse(null);
		if(counsellor==null) {
			throw new Exception("No Counsellor found");
		}
		//associating counsellor to enquiry
		enq.setCounsellor(counsellor);		
		Enquiry save=enqRepo.save(enq);
		
		if(save.getEnqId() !=null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		// TODO Auto-generated method stub
		return enqRepo.getEnquriesByCounsellorId(counsellorId);
	}
	
	@Override
	public Enquiry getEnquiryById(Integer engId) {
		// TODO Auto-generated method stub
		return enqRepo.findById(engId).orElse(null);
		}

	@Override
	public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId) {
		// TODO Auto-generated method stub
		//QBE(QueyBy Example) implementation(Dynamic Query Preparation)
		
		Enquiry enq=new Enquiry();
		
		
		if (StringUtils.isNotEmpty(filterReq.getClassMode())) {
		enq.setClassMode(filterReq.getClassMode());
		
	    }
		if (StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
			
		    }
		if (StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}
		Counsellor c=counsellorRepo.findById(counsellorId).orElse(null);
		enq.setCounsellor(c);
		
		Example<Enquiry> of=Example.of(enq);
		List<Enquiry> enqList=enqRepo.findAll(of);
		
		return enqList;
	}

}
