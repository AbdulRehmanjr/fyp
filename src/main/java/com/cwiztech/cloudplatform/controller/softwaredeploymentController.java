package com.cwiztech.cloudplatform.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cwiztech.cloudplatform.model.SoftwareDeployment;
import com.cwiztech.cloudplatform.repository.softwaredeploymentRepository;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
	
	@RestController
	@CrossOrigin
	@RequestMapping("/softwaredeployment")
	public class softwaredeploymentController {
		
		private static final Logger log = LoggerFactory.getLogger(softwaredeploymentController.class);
		
		//@Autowired
		@Autowired(required = false) 
		private softwaredeploymentRepository softwaredeploymentrepository;
		
		//@Autowired
		@Autowired(required = false) 
		private apiRequestDataLogRepository apirequestdatalogRepository;
		
		//@Autowired
		@Autowired(required = false) 
		private tableDataLogRepository tbldatalogrepository;
		
		//@Autowired
		@Autowired(required = false) 
		private databaseTablesRepository databasetablesrepository;
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException  {
			APIRequestDataLog apiRequest = checkToken("GET", "/softwaredeployment/all", null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<SoftwareDeployment> softwaredeployment = softwaredeploymentrepository.findActive();		
			return new ResponseEntity(getAPIResponse(softwaredeployment, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/all", method = RequestMethod.GET)
		public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/softwaredeployment/all", null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<SoftwareDeployment> softwaredeployment = softwaredeploymentrepository.findAll();
			
			return new ResponseEntity(getAPIResponse(softwaredeployment, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/softwaredeployment/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			SoftwareDeployment softwaredeployment = softwaredeploymentrepository.findById(id).get();
			
			return new ResponseEntity(getAPIResponse(null, softwaredeployment, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/ids", method = RequestMethod.POST)
		public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/softwaredeployment/ids", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Integer> ids = new ArrayList<Integer>(); 
			JSONObject jsonObj = new JSONObject(data);
			JSONArray jsonsoftware = jsonObj.getJSONArray("softwaredeployments");
			for (int i=0; i<jsonsoftware.length(); i++) {
				ids.add((Integer) jsonsoftware.get(i));
			}
			List<SoftwareDeployment> softwaredeployments = new ArrayList<SoftwareDeployment>();
			if (jsonsoftware.length()>0)
				softwaredeployments = softwaredeploymentrepository.findByIDs(ids);
			
			return new ResponseEntity(getAPIResponse(softwaredeployments, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/softwaredeployment", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			return insertupdateAll(null, new JSONObject(data), apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("PUT", "/softwaredeployment/"+id, data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			JSONObject jsonObj = new JSONObject(data);
			jsonObj.put("id", id);
			
			return insertupdateAll(null, jsonObj, apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.PUT)
		public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("PUT", "/softwaredeployment", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			return insertupdateAll(new JSONArray(data), null, apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity insertupdateAll(JSONArray jsonsoftware, JSONObject jsonsoftwares, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
		    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();

			List<SoftwareDeployment> softwaredeployments = new ArrayList<SoftwareDeployment>();
			if (jsonsoftwares != null) {
				jsonsoftware = new JSONArray();
				jsonsoftware.put(jsonsoftwares);
			}
			log.info(jsonsoftware.toString());
			
			for (int a=0; a<jsonsoftware.length(); a++) {
				JSONObject jsonObj = jsonsoftware.getJSONObject(a);
				SoftwareDeployment softwaredeployment = new SoftwareDeployment();
				long id = 0;

				if (jsonObj.has("id")) {
					id = jsonObj.getLong("id");
					if (id != 0) {
						softwaredeployment = softwaredeploymentrepository.findById(id).get();
						
						if (softwaredeployment == null)
							return new ResponseEntity(getAPIResponse(null, null, "Invalid softwaredeployment Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					}
				}

				if (id == 0) {
					if (!jsonObj.has("SOFTWARE_ID") || jsonObj.isNull("SOFTWARE_ID"))
						return new ResponseEntity(getAPIResponse(null, null, "SOFTWARE_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
					
					if (!jsonObj.has("RESOURCETYPE_ID") || jsonObj.isNull("RESOURCETYPE_ID"))
						return new ResponseEntity(getAPIResponse(null, null, "RESOURCETYPE_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
					if (!jsonObj.has("ORGANIZATIONRESOURCE_DESCRIPTION") || jsonObj.isNull("ORGANIZATIONRESOURCE_DESCRIPTION"))
						return new ResponseEntity(getAPIResponse(null, null, "ORGANIZATIONRESOURCE_DESCRIPTION is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
				
				if (jsonObj.has("SOFTWARE_ID") && !jsonObj.isNull("SOFTWARE_ID"))
					softwaredeployment.setSOFTWARE_ID(jsonObj.getLong("SOFTWARE_ID"));
				
				if (jsonObj.has("VISIBILTY") && !jsonObj.isNull("VISIBILTY"))
					softwaredeployment.setVISIBILTY(jsonObj.getString("VISIBILTY"));
				
				if (jsonObj.has("WEBSITE") && !jsonObj.isNull("WEBSITE"))
					softwaredeployment.setWEBSITE(jsonObj.getString("WEBSITE"));

				if (jsonObj.has("DOCUMENTATIN_LINK") && !jsonObj.isNull("DOCUMENTATIN_LINK")) 
					softwaredeployment.setDOCUMENTATIN_LINK(jsonObj.getString("DOCUMENTATIN_LINK"));
				
				if (jsonObj.has("USE_NFS") && !jsonObj.isNull("USE_NFS")) 
					softwaredeployment.setUSE_NFS(jsonObj.getLong("USE_NFS"));
			
				
				
				if (jsonObj.has("LIMITED_INSTANCES_TYPES") && !jsonObj.isNull("LIMITED_INSTANCES_TYPES")) 
					softwaredeployment.setLIMITED_INSTANCES_TYPES(jsonObj.getString("LIMITED_INSTANCES_TYPES"));
				
				
				
				if (jsonObj.has("DESCRIPTION") && !jsonObj.isNull("DESCRIPTION")) 
					softwaredeployment.setDESCRIPTION(jsonObj.getString("DESCRIPTION"));
				
				
				if (jsonObj.has("EXECUTABLES") && !jsonObj.isNull("EXECUTABLES")) 
					softwaredeployment.setEXECUTABLES(jsonObj.getString("EXECUTABLES"));
				
				if (jsonObj.has("isactive"))
				softwaredeployment.setISACTIVE(jsonObj.getString("isactive"));
			    else if (id == 0)
				softwaredeployment.setISACTIVE("Y");

				softwaredeployment.setMODIFIED_BY(apiRequest.getREQUEST_ID());
				softwaredeployment.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
				softwaredeployment.setMODIFIED_WHEN(dateFormat1.format(date));
				softwaredeployments.add(softwaredeployment);
			}
		
			
			for (int a=0; a<softwaredeployments.size(); a++) {
				SoftwareDeployment softwaredeployment = softwaredeployments.get(a);
				softwaredeployment = softwaredeploymentrepository.saveAndFlush(softwaredeployment);
				softwaredeployments.get(a).setSOFTWARE_ID(softwaredeployment.getSOFTWARE_ID());
			}
			
			ResponseEntity responseentity;
			if (jsonsoftwares != null)
				responseentity = new ResponseEntity(getAPIResponse(null, softwaredeployments.get(0), null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
			else
				responseentity = new ResponseEntity(getAPIResponse(softwaredeployments, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
			return responseentity;
		}
								
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/softwaredeployment/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			SoftwareDeployment softwaredeployment = softwaredeploymentrepository.findById(id).get();
			softwaredeploymentrepository.delete(softwaredeployment);
			
			return new ResponseEntity(getAPIResponse(null, softwaredeployment, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
		public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/softwaredeployment/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			JSONObject softwaredeployment = new JSONObject();
			softwaredeployment.put("id", id);
			softwaredeployment.put("isactive", "N");
			
			return insertupdateAll(null, softwaredeployment, apiRequest);
		}

		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/search", method = RequestMethod.POST)
		public ResponseEntity getBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			return BySearch(data, true, headToken);
		}

		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/search/all", method = RequestMethod.POST)
		public ResponseEntity getAllBySearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			return BySearch(data, false, headToken);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity BySearch(String data, boolean active, String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/softwaredeployment/search" + ((active == true) ? "" : "/all"), data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			JSONObject jsonObj = new JSONObject(data);

			List<		SoftwareDeployment> softwaredeployments = ((active == true)
					? softwaredeploymentrepository.findBySearch("%" + jsonObj.getString("search") + "%")
					: softwaredeploymentrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
			
			return new ResponseEntity(getAPIResponse(softwaredeployments, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/advancedsearch", method = RequestMethod.POST)
		public ResponseEntity getByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			return ByAdvancedSearch(data, true, headToken);
		}

		@SuppressWarnings({ "rawtypes" })
		@RequestMapping(value = "/advancedsearch/all", method = RequestMethod.POST)
		public ResponseEntity getAllByAdvancedSearch(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			return ByAdvancedSearch(data, false, headToken);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity ByAdvancedSearch(String data, boolean active, String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/softwaredeployment/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			JSONObject jsonObj = new JSONObject(data);
			long organizationresource_ID = 0;

			if (jsonObj.has("organizationresource_ID"))
				organizationresource_ID = jsonObj.getLong("organizationresource_ID");

			List<SoftwareDeployment> softwaredeployment = ((active == true)
					? softwaredeploymentrepository.findByAdvancedSearch(organizationresource_ID)
					: softwaredeploymentrepository.findAllByAdvancedSearch(organizationresource_ID));

			return new ResponseEntity(getAPIResponse(softwaredeployment, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}



		public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
			JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
			DatabaseTables databaseTableID = databasetablesrepository.findById(SoftwareDeployment.getDatabaseTableID()).get();
			APIRequestDataLog apiRequest;
			
			log.info(requestType + ": " + requestURI);
			if (requestBody != null)
				log.info("Input: " + requestBody);

			if (checkTokenResponse.has("error")) {
				apiRequest = tableDataLogs.apiRequestDataLog(requestType, databaseTableID, (long) 0, requestURI, requestBody, workstation);
				apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
				apirequestdatalogRepository.saveAndFlush(apiRequest);
				return apiRequest;
			}
			
			Long requestUser = checkTokenResponse.getLong("user_ID");
			apiRequest = tableDataLogs.apiRequestDataLog(requestType, databaseTableID, requestUser, requestURI, requestBody, workstation);

			return apiRequest;
		}
		
		APIRequestDataLog getAPIResponse(List<SoftwareDeployment> softwaredeployments, SoftwareDeployment softwaredeployment, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
			ObjectMapper mapper = new ObjectMapper();
			long softwareID = 0;
			
			if (message != null) {
				apiRequest = tableDataLogs.errorDataLog(apiRequest, "softwaredeployment", message);
				apirequestdatalogRepository.saveAndFlush(apiRequest);
			} else {
				if (softwaredeployment!= null) {
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(softwaredeployment));
					softwareID = softwaredeployment.getSOFTWARE_ID();
				} else {
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(softwaredeployment));
				}
				apiRequest.setREQUEST_STATUS("Success");
				apirequestdatalogRepository.saveAndFlush(apiRequest);
			}
			
			if (isTableLog)
				tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(softwareID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
			
			log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
			log.info("--------------------------------------------------------");

			return apiRequest;
		}
}
