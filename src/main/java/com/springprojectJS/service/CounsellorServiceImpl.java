package com.springprojectJS.service;

import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springprojectJS.dto.DashboardResponse;

import com.springprojectJS.entities.Counsellor;
import com.springprojectJS.entities.Enquiry;
import com.springprojectJS.repos.CounsellorRepo;
import com.springprojectJS.repos.EnquiryRepo;
@Service
public class CounsellorServiceImpl implements CounsellorService {
	
	//@Autowired Since we used constructor here. this @Autowired  commented here
	private CounsellorRepo counsellorRepo;
	/*
	@Autowired
	private EnquiryRepo enqRepo;
	*/
	
	private EnquiryRepo enqRepo;
	
	public CounsellorServiceImpl(CounsellorRepo counsellorRepo, EnquiryRepo enqRepo) {
		this.counsellorRepo=counsellorRepo;
		this.enqRepo=enqRepo;
	}
		
	@Override
	public Counsellor findByEmail(String email) {
		return counsellorRepo.findByEmail(email);		
	}
	@Override
	public boolean register(Counsellor counsellor) {
		Counsellor savedCounsellor=counsellorRepo.save(counsellor);
		
		if(null!=savedCounsellor.getCounsellorId()) {
			return true;
		}
	
		return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		Counsellor counsellor=counsellorRepo.findByEmailAndPwd(email, pwd);
		return counsellor;
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {
		// TODO Auto-generated method stub
		DashboardResponse response=new DashboardResponse();
		
		List<Enquiry> enqList=enqRepo.getEnquriesByCounsellorId(counsellorId);
		
		int totalEnq=enqList.size();
		
		int enrolledEnq=enqList.stream()
				.filter(e ->e.getEnqStatus().equals("Enrolled"))
				.collect(Collectors.toList())
				.size();
		int lostEnq=enqList.stream()
				.filter(e ->e.getEnqStatus().equals("Lost"))
				.collect(Collectors.toList())
				.size();
		int openEnq=enqList.stream()
				.filter(e ->e.getEnqStatus().equals("Open"))
				.collect(Collectors.toList())
				.size();
		
		
		
		response.setTotalEnqs(totalEnq);
		response.setEnrolledEnqs(enrolledEnq);
		response.setLostEnqs(lostEnq);
		response.setOpenEnqs(openEnq);
		
		return response;
	}

}
