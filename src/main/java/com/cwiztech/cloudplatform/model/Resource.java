package com.cwiztech.cloudplatform.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TBLRESOURCE")
public class Resource  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long RESOURCE_ID;
	
	@Column(name = "RESOURCE_SPECIFIER")
	private String  RESOURCE_SPECIFIER ;
	


	
	

	@Column(name = "AWS_ACCOUNT_ID")
	private  String AWS_ACCOUNT_ID;
	
	@Column(name = "ACCESS_KEY_ID")
	private String ACCESS_KEY_ID ; 
	
	
	
	@Column(name = "SECRET_ACCESS_KEY")
	private String SECRET_ACCESS_KEY ; 
	

	
	@Column(name = "ISACTIVE")
	private String ISACTIVE ;
	
	@JsonIgnore
	@Column(name = " MODIFIED_BY")
	private Long MODIFIED_BY ;
	
	@JsonIgnore
	@Column(name = " MODIFIED_WHEN ")
	private String MODIFIED_WHEN  ;
	
	@JsonIgnore
	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION  ;

	public Long getRESOURCE_ID() {
		return RESOURCE_ID;
	}

	public void setRESOURCE_ID(Long rESOURCE_ID) {
		RESOURCE_ID = rESOURCE_ID;
	}

	public String getRESOURCE_SPECIFIER() {
		return RESOURCE_SPECIFIER;
	}

	public void setRESOURCE_SPECIFIER(String rESOURCE_SPECIFIER) {
		RESOURCE_SPECIFIER = rESOURCE_SPECIFIER;
	}
	

	public String getAWS_ACCOUNT_ID() {
		return AWS_ACCOUNT_ID;
	}

	public void setAWS_ACCOUNT_ID(String aWS_ACCOUNT_ID) {
		AWS_ACCOUNT_ID = aWS_ACCOUNT_ID;
	}

	public String getACCESS_KEY_ID() {
		return ACCESS_KEY_ID;
	}

	public void setACCESS_KEY_ID(String aCCESS_KEY_ID) {
		ACCESS_KEY_ID = aCCESS_KEY_ID;
	}

	public String getSECRET_ACCESS_KEY() {
		return SECRET_ACCESS_KEY;
	}

	public void setSECRET_ACCESS_KEY(String sECRET_ACCESS_KEY) {
		SECRET_ACCESS_KEY = sECRET_ACCESS_KEY;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	public Long getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(Long mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}

	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}

	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}

	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}
	public static long getDatabaseTableID() {
		return (long) 1;
	}
}
