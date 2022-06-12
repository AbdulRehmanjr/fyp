package com.cwiztech.cloudplatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TBLORGANIZATIONRESOURCE")
public class OrganizationResource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ORGANIZATIONRESOURCE_ID;
	@Column(name = "ORGANIZATION_ID")
	private Long ORGANIZATION_ID  ;
	
	@Column(name = "RESOURCETYPE_ID")
	private Long RESOURCETYPE_ID ;
	
	@Column(name = "ORGANIZATIONRESOURCE_DESCRIPTION")
	private  String ORGANIZATIONRESOURCE_DESCRIPTION;
	
	@Column(name = "MAXIMUM_NODES")
	private Long MAXIMUM_NODES ; 
	
	@Column(name = "WEBSITE")
	private String WEBSITE ; 
	
	@Column(name = "VISIBILITY_ID")
	private Long VISIBILITY_ID ; 
	
	@Column(name = "RESOURCEBEHAVIOURTYPE_ID ")
	private  Long RESOURCEBEHAVIOURTYPE_ID ;
	
	@Column(name = "USEGLOBALSECURITYGROUP")
	private  String USEGLOBALSECURITYGROUP ; 
	
	@Column(name = "CHECKRUNWAYINSTANCES")
	private  String CHECKRUNWAYINSTANCES ;
	
	@Column(name = "ACCESSINTERNET")
	private char ACCESSINTERNET ; 
	
	@Column(name = "USEPROXY")
	private String USEPROXY ;
	
	@Column(name = "PROXY_HOSTNAME")
	private String PROXY_HOSTNAME ;
	
	@Column(name = "PROXY_USERNAME")
	private String  PROXY_USERNAME  ;
	
	@Column(name = "PROXY_PASSWORD")
	private  String PROXY_PASSWORD ;
	
	@Column(name = "PROXY_PRIVATEKEY")
	private String PROXY_PRIVATEKEY ;
	
	@Column(name = "PROXY_PUBLICEKEY")
	private String PROXY_PUBLICEKEY ;
	
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

	
	public Long getORGANIZATIONRESOURCE_ID() {
		return ORGANIZATIONRESOURCE_ID;
	}
	public void setORGANIZATIONRESOURCE_ID(Long oRGANIZATIONRESOURCE_ID) {
		ORGANIZATIONRESOURCE_ID = oRGANIZATIONRESOURCE_ID;
	}
	public Long getORGANIZATION_ID() {
		return ORGANIZATION_ID;
	}
	public void setORGANIZATION_ID(Long oRGANIZATION_ID) {
		ORGANIZATION_ID = oRGANIZATION_ID;
	}
	public Long getRESOURCETYPE_ID() {
		return RESOURCETYPE_ID;
	}
	public void setRESOURCETYPE_ID(Long rESOURCETYPE_ID) {
		RESOURCETYPE_ID = rESOURCETYPE_ID;
	}
	public String  getORGANIZATIONRESOURCE_DESCRIPTION() {
		return ORGANIZATIONRESOURCE_DESCRIPTION;
	}
	public void setORGANIZATIONRESOURCE_DESCRIPTION(String  oRGANIZATIONRESOURCE_DESCRIPTION) {
		ORGANIZATIONRESOURCE_DESCRIPTION = oRGANIZATIONRESOURCE_DESCRIPTION;
	}
	public Long getMAXIMUM_NODES() {
		return MAXIMUM_NODES;
	}
	public void setMAXIMUM_NODES(Long mAXIMUM_NODES) {
		MAXIMUM_NODES = mAXIMUM_NODES;
	}
	public String getWEBSITE() {
		return WEBSITE;
	}
	public void setWEBSITE(String wEBSITE) {
		WEBSITE = wEBSITE;
	}
	public Long getVISIBILITY_ID() {
		return VISIBILITY_ID;
	}
	public void setVISIBILITY_ID(Long vISIBILITY_ID) {
		VISIBILITY_ID = vISIBILITY_ID;
	}
	public Long getRESOURCEBEHAVIOURTYPE_ID() {
		return RESOURCEBEHAVIOURTYPE_ID;
	}
	public void setRESOURCEBEHAVIOURTYPE_ID(Long rESOURCEBEHAVIOURTYPE_ID) {
		RESOURCEBEHAVIOURTYPE_ID = rESOURCEBEHAVIOURTYPE_ID;
	}
	public String getUSEGLOBALSECURITYGROUP() {
		return USEGLOBALSECURITYGROUP;
	}
	public void setUSEGLOBALSECURITYGROUP(String string) {
		USEGLOBALSECURITYGROUP = string;
	}
	public String getCHECKRUNWAYINSTANCES() {
		return CHECKRUNWAYINSTANCES;
	}
	public void setCHECKRUNWAYINSTANCES(String string) {
		CHECKRUNWAYINSTANCES = string;
	}
	public char getACCESSINTERNET() {
		return ACCESSINTERNET;
	}
	public void setACCESSINTERNET(String string) {
		string = string;
	}
	public String getUSEPROXY() {
		return USEPROXY;
	}
	public void setUSEPROXY(String string) {
		USEPROXY = string;
	}
	public String getPROXY_HOSTNAME() {
		return PROXY_HOSTNAME;
	}
	public void setPROXY_HOSTNAME(String pROXY_HOSTNAME) {
		PROXY_HOSTNAME = pROXY_HOSTNAME;
	}
	public String getPROXY_USERNAME() {
		return PROXY_USERNAME;
	}
	public void setPROXY_USERNAME(String pROXY_USERNAME) {
		PROXY_USERNAME = pROXY_USERNAME;
	}
	public String getPROXY_PASSWORD() {
		return PROXY_PASSWORD;
	}
	public void setPROXY_PASSWORD(String pROXY_PASSWORD) {
		PROXY_PASSWORD = pROXY_PASSWORD;
	}
	public String getPROXY_PRIVATEKEY() {
		return PROXY_PRIVATEKEY;
	}
	public void setPROXY_PRIVATEKEY(String pROXY_PRIVATEKEY) {
		PROXY_PRIVATEKEY = pROXY_PRIVATEKEY;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}
    @JsonIgnore
	public Long getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(Long mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}
	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}
	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}
	public String getPROXY_PUBLICEKEY() {
		return PROXY_PUBLICEKEY;
	}
	public void setPROXY_PUBLICEKEY(String pROXY_PUBLICEKEY) {
		PROXY_PUBLICEKEY = pROXY_PUBLICEKEY;
	}
	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}
	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	public static long getDatabaseTableID() {
		return (long) 1;
	}

	}
	


