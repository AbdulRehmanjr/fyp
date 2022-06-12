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
@Table(name="TBLJOBS")
public class Jobs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long JOB_ID;
	
	@Column(name = "JOB_NAME")
	private String JOB_NAME ;
	
	@Column(name = "RESOURCE_ID")
	private Long RESOURCE_ID ;
	
    @Transient
	private String RESOURCE_DETAIL;
	
	
	private  String SOFTWARE_ID;
	
	@Column(name = "NO_OF_NODES")
	private Long NO_OF_NODES ; 
	
 
	
	@Column(name = "INSTANCE_TYPE")
	private String INSTANCE_TYPE ; 
	
	
	private  String EXECUTABLES ;
	
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

	public Long getJOB_ID() {
		return JOB_ID;
	}

	public void setJOB_ID(Long jOB_ID) {
		JOB_ID = jOB_ID;
	}

	public String getJOB_NAME() {
		return JOB_NAME;
	}

	public void setJOB_NAME(String jOB_NAME) {
		JOB_NAME = jOB_NAME;
	}

	public Long getRESOURCE_ID() {
		return RESOURCE_ID;
	}

	public void setRESOURCE_ID(Long rESOURCE_ID) {
		RESOURCE_ID = rESOURCE_ID;
	}

	public String getRESOURCE_DETAIL() {
		return RESOURCE_DETAIL;
	}

	public void setRESOURCE_DETAIL(String rESOURCE_DETAIL) {
		RESOURCE_DETAIL = rESOURCE_DETAIL;
	}

	public String getSOFTWARE_ID() {
		return SOFTWARE_ID;
	}

	public void setSOFTWARE_ID(String sOFTWARE_ID) {
		SOFTWARE_ID = sOFTWARE_ID;
	}

	public Long getNO_OF_NODES() {
		return NO_OF_NODES;
	}

	public void setNO_OF_NODES(Long nO_OF_NODES) {
		NO_OF_NODES = nO_OF_NODES;
	}

	public String getINSTANCE_TYPE() {
		return INSTANCE_TYPE;
	}

	public void setINSTANCE_TYPE(String iNSTANCE_TYPE) {
		INSTANCE_TYPE = iNSTANCE_TYPE;
	}

	public String getEXECUTABLES() {
		return EXECUTABLES;
	}

	public void setEXECUTABLES(String eXECUTABLES) {
		EXECUTABLES = eXECUTABLES;
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
