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

import com.cwiztech.cloudplatform.model.Jobs;
import com.cwiztech.cloudplatform.repository.jobsRepository;
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
	@RequestMapping("/jobs")
	public class jobsController {
		
		private static final Logger log = LoggerFactory.getLogger(jobsController.class);
		
		//@Autowired
		@Autowired(required = false) 
		private jobsRepository jobsrepository;
		
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
			APIRequestDataLog apiRequest = checkToken("GET", "/jobs/all", null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Jobs> jobs = jobsrepository.findActive();		
			return new ResponseEntity(getAPIResponse(jobs, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/all", method = RequestMethod.GET)
		public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/jobs/all", null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Jobs> jobs = jobsrepository.findAll();
			
			return new ResponseEntity(getAPIResponse(jobs, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/jobs/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			Jobs jobs = jobsrepository.findById(id).get();
			
			return new ResponseEntity(getAPIResponse(null, jobs, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/ids", method = RequestMethod.POST)
		public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/jobs/ids", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Integer> ids = new ArrayList<Integer>(); 
			JSONObject jsonObj = new JSONObject(data);
			JSONArray josonjobs = jsonObj.getJSONArray("jobs");
			for (int i=0; i<josonjobs.length(); i++) {
				ids.add((Integer) josonjobs.get(i));
			}
			List<Jobs> jobs = new ArrayList<Jobs>();
			if (josonjobs.length()>0)
				jobs = jobsrepository.findByIDs(ids);
			
			return new ResponseEntity(getAPIResponse(jobs, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/jobs", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			return insertupdateAll(null, new JSONObject(data), apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("PUT", "/jobs/"+id, data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			JSONObject jsonObj = new JSONObject(data);
			jsonObj.put("id", id);
			
			return insertupdateAll(null, jsonObj, apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.PUT)
		public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("PUT", "/jobs", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			return insertupdateAll(new JSONArray(data), null, apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity insertupdateAll(JSONArray josonjobs, JSONObject jsonjob, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
		    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();

			List<Jobs> jobs = new ArrayList<Jobs>();
			if (jsonjob != null) {
				josonjobs = new JSONArray();
				josonjobs.put(jsonjob);
			}
			log.info(josonjobs.toString());
			
			for (int a=0; a<josonjobs.length(); a++) {
				JSONObject jsonObj = josonjobs.getJSONObject(a);
				Jobs jobs1 = new Jobs();
				long id = 0;

				if (jsonObj.has("id")) {
					id = jsonObj.getLong("id");
					if (id != 0) {
						jobs1 = jobsrepository.findById(id).get();
						
						if (jobs1 == null)
							return new ResponseEntity(getAPIResponse(null, null, "Invalid jobs Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					}
				}

				if (id == 0) {
					if (!jsonObj.has("JOB_ID") || jsonObj.isNull("JOB_ID"))
						return new ResponseEntity(getAPIResponse(null, null, "JOB_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
					
					if (!jsonObj.has("JOB_NAME") || jsonObj.isNull("JOB_NAME"))
						return new ResponseEntity(getAPIResponse(null, null, "JOB_NAME is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
				}
				
				if (jsonObj.has("JOB_ID") && !jsonObj.isNull("JOB_ID"))
					jobs1.setJOB_ID(jsonObj.getLong("JOB_ID"));
				
				if (jsonObj.has("JOB_NAME") && !jsonObj.isNull("JOB_NAME"))
					jobs1.setJOB_NAME(jsonObj.getString("JOB_NAME"));
				
				if (jsonObj.has("NO_OF_NODES") && !jsonObj.isNull("NO_OF_NODES"))
					jobs1.setNO_OF_NODES(jsonObj.getLong("NO_OF_NODES"));

				if (jsonObj.has("INSTANCE_TYPE") && !jsonObj.isNull("INSTANCE_TYPE")) 
					jobs1.setINSTANCE_TYPE(jsonObj.getString("INSTANCE_TYPE"));
				
				
				
				if (jsonObj.has("isactive"))
				jobs1.setISACTIVE(jsonObj.getString("isactive"));
			    else if (id == 0)
				jobs1.setISACTIVE("Y");

				jobs1.setMODIFIED_BY(apiRequest.getREQUEST_ID());
				jobs1.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
				jobs1.setMODIFIED_WHEN(dateFormat1.format(date));
				jobs.add(jobs1);
			}
		
			
			for (int a=0; a<jobs.size(); a++) {
				Jobs job = jobs.get(a);
				job = jobsrepository.saveAndFlush(job);
				jobs.get(a).setJOB_ID(job.getJOB_ID());
			}
			
			ResponseEntity responseentity;
			if (jsonjob != null)
				responseentity = new ResponseEntity(getAPIResponse(null, jobs.get(0), null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
			else
				responseentity = new ResponseEntity(getAPIResponse(jobs, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
			return responseentity;
		}
								
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/jobs/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			Jobs jobs = jobsrepository.findById(id).get();
			jobsrepository.delete(jobs);
			
			return new ResponseEntity(getAPIResponse(null, jobs, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
		public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/jobs/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			JSONObject jobs = new JSONObject();
			jobs.put("id", id);
			jobs.put("isactive", "N");
			
			return insertupdateAll(null, jobs, apiRequest);
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
			APIRequestDataLog apiRequest = checkToken("POST", "/jobs/search" + ((active == true) ? "" : "/all"), data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			JSONObject jsonObj = new JSONObject(data);

			List<		Jobs> jobs = ((active == true)
					? jobsrepository.findBySearch("%" + jsonObj.getString("search") + "%")
					: jobsrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
			
			return new ResponseEntity(getAPIResponse(jobs, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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
			APIRequestDataLog apiRequest = checkToken("POST", "/jobs/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			JSONObject jsonObj = new JSONObject(data);
			long organizationresource_ID = 0;

			if (jsonObj.has("organizationresource_ID"))
				organizationresource_ID = jsonObj.getLong("organizationresource_ID");

			List<Jobs> jobs = ((active == true)
					? jobsrepository.findByAdvancedSearch(organizationresource_ID)
					: jobsrepository.findAllByAdvancedSearch(organizationresource_ID));
			

			return new ResponseEntity(getAPIResponse(jobs, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}



		public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
			JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
			DatabaseTables databaseTableID = databasetablesrepository.findById(Jobs.getDatabaseTableID()).get();
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
		
		APIRequestDataLog getAPIResponse(List<Jobs> jobs, Jobs job, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
			ObjectMapper mapper = new ObjectMapper();
			long JobID = 0;
			
			if (message != null) {
				apiRequest = tableDataLogs.errorDataLog(apiRequest, "jobs", message);
				apirequestdatalogRepository.saveAndFlush(apiRequest);
			} else {
				if (job != null) {
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(job));
					JobID = job.getJOB_ID();
				} else {
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(job));
				}
				apiRequest.setREQUEST_STATUS("Success");
				apirequestdatalogRepository.saveAndFlush(apiRequest);
			}
			
			if (isTableLog)
				tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(JobID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
			
			log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
			log.info("--------------------------------------------------------");

			return apiRequest;
		}
}
