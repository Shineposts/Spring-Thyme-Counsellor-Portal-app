package com.springprojectJS.service;

import com.springprojectJS.dto.DashboardResponse;
import com.springprojectJS.entities.Counsellor;

public interface CounsellorService {
	
	public Counsellor findByEmail(String email);
	
	public boolean register(Counsellor counsellor);
	
	public Counsellor login(String email,  String pwd);
	
	public DashboardResponse getDashboardInfo(Integer CounsellorId);

}
