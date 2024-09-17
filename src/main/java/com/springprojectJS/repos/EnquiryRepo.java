package com.springprojectJS.repos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springprojectJS.entities.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {
	
	@Query(value = "SELECT * FROM enquiry_tbl WHERE counsellor_id=@counsellorId", nativeQuery=true)
	public List<Enquiry> getEnquriesByCounsellorId(Integer counsellorId);

}