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
@Table(name="TBLBILL")
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long BILL_ID;
	
	@Column(name = "USER_ID")
	private Long  USER_ID ;
	
	@Column(name = "ORGANIZATION_ID")
	private Long  ORGANIZATION_ID ;
	
	@Transient
	private String RESOURCE_DETAIL;
	
	
	@Column(name = "QUANTITY")
	private  String QUANTITY;
	
	@Column(name = "FEE_TYPE")
	private String FEE_TYPE ; 
	
	@Column(name = "RESOURCE_ID")
	private Long RESOURCE_ID ; 
	
	
	private Long TOTAL_AMOUNT ; 
	
	@Column(name = "STATUS ")
	private  String STATUS ;
	
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

	public Long getBILL_ID() {
		return BILL_ID;
	}

	public void setBILL_ID(Long bILL_ID) {
		BILL_ID = bILL_ID;
	}

	public Long getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(Long uSER_ID) {
		USER_ID = uSER_ID;
	}

	public Long getORGANIZATION_ID() {
		return ORGANIZATION_ID;
	}

	public void setORGANIZATION_ID(Long oRGANIZATION_ID) {
		ORGANIZATION_ID = oRGANIZATION_ID;
	}

	public String getQUANTITY() {
		return QUANTITY;
	}

	public void setQUANTITY(String qUANTITY) {
		QUANTITY = qUANTITY;
	}

	public String getFEE_TYPE() {
		return FEE_TYPE;
	}

	public void setFEE_TYPE(String fEE_TYPE) {
		FEE_TYPE = fEE_TYPE;
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
	public Long getTOTAL_AMOUNT() {
		return TOTAL_AMOUNT;
	}

	public void setTOTAL_AMOUNT(Long tOTAL_AMOUNT) {
		TOTAL_AMOUNT = tOTAL_AMOUNT;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
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
	
	