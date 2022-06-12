package com.cwiztech.cloudplatform.model;


	
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.Table;

	import com.fasterxml.jackson.annotation.JsonIgnore;
	@Entity
	@Table(name="TBLJOBS")
	public class SoftwareDeployment {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long SOFTWARE_ID;
		
		@Column(name = "VISIBILTY")
		private String VISIBILTY ;
		
	
		@Column(name = "WEBSITE")
		private String WEBSITE ; 
		
	 
		
		@Column(name = "DOCUMENTATIN_LINK")
		private String DOCUMENTATIN_LINK ; 
		
		@Column(name = "USE_NFS")
		private Long USE_NFS ; 
		
		
		@Column(name = "LIMITED_INSTANCES_TYPES")
		private String LIMITED_INSTANCES_TYPES ; 
		
		
		@Column(name = "DESCRIPTION")
		private String DESCRIPTION ; 
		
		@Column(name = "EXECUTABLES")
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
		
		
		public Long getSOFTWARE_ID() {
			return SOFTWARE_ID;
		}

		public void setSOFTWARE_ID(Long sOFTWARE_ID) {
			SOFTWARE_ID = sOFTWARE_ID;
		}

		public String getVISIBILTY() {
			return VISIBILTY;
		}

		public void setVISIBILTY(String vISIBILTY) {
			VISIBILTY = vISIBILTY;
		}

		public String getWEBSITE() {
			return WEBSITE;
		}

		public void setWEBSITE(String wEBSITE) {
			WEBSITE = wEBSITE;
		}

		public String getDOCUMENTATIN_LINK() {
			return DOCUMENTATIN_LINK;
		}

		public void setDOCUMENTATIN_LINK(String dOCUMENTATIN_LINK) {
			DOCUMENTATIN_LINK = dOCUMENTATIN_LINK;
		}

		public Long getUSE_NFS() {
			return USE_NFS;
		}

		public void setUSE_NFS(Long uSE_NFS) {
			USE_NFS = uSE_NFS;
		}

		public String getLIMITED_INSTANCES_TYPES() {
			return LIMITED_INSTANCES_TYPES;
		}

		public void setLIMITED_INSTANCES_TYPES(String lIMITED_INSTANCES_TYPES) {
			LIMITED_INSTANCES_TYPES = lIMITED_INSTANCES_TYPES;
		}

		public String getDESCRIPTION() {
			return DESCRIPTION;
		}

		public void setDESCRIPTION(String dESCRIPTION) {
			DESCRIPTION = dESCRIPTION;
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
