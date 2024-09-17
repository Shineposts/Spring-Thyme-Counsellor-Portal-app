package com.springprojectJS.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="counsellor_tbl") //different table name in sql we need @Table

public class Counsellor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //GenerationType.SEQUENCE for ORACLE
	private Integer counsellorId; //Entity  & primerykey. use only wrapper type
	private String name;
	
	@Column(unique=true) //email has to be unique
	private String email;
	private String pwd;
	private String phno;
	
	@CreationTimestamp //no need set value , it gets auto populate while created
	private LocalDate createdDate;
	
	@UpdateTimestamp //no need set value , it gets auto populate while updating
	private LocalDate updateDate;

	public Integer getCounsellorId() {
		return counsellorId;
	}

	public void setCounsellorId(Integer counsellorId) {
		this.counsellorId = counsellorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	

	

}
