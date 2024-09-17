package com.springprojectJS.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springprojectJS.entities.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer>{
	//select * from counsellor_tbl where email=:email
	public Counsellor findByEmail(String email); //for dublicate email check
	//select * from counsellor_tb1 where email=:email and pwd=:pwd
	public Counsellor findByEmailAndPwd(String email, String pwd);
}
