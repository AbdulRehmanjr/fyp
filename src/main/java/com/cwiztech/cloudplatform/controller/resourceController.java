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

import com.cwiztech.cloudplatform.model.Resource;
import com.cwiztech.cloudplatform.repository.resourceRepository;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.services.ResourceService;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


	@RestController
	@CrossOrigin
	@RequestMapping("/resource")
	public class resourceController{
		
		private static final Logger log = LoggerFactory.getLogger(resourceController.class);
		
		//@Autowired
		@Autowired(required = false) 
		private resourceRepository resourcerepository;
		
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
			APIRequestDataLog apiRequest = checkToken("GET", "/resource/all", null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Resource> resource = resourcerepository.findActive();		
			return new ResponseEntity(getAPIResponse(resource, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/all", method = RequestMethod.GET)
		public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/resource/all", null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Resource> resource = resourcerepository.findAll();
			
			return new ResponseEntity(getAPIResponse(resource, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/resource/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			Resource resource = resourcerepository.findById(id).get();
			
			return new ResponseEntity(getAPIResponse(null, resource, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/ids", method = RequestMethod.POST)
		public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/resource/ids", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Integer> ids = new ArrayList<Integer>(); 
			JSONObject jsonObj = new JSONObject(data);
			JSONArray jsonresources = jsonObj.getJSONArray("resources");
			for (int i=0; i<jsonresources.length(); i++) {
				ids.add((Integer) jsonresources.get(i));
			}
			List<Resource> resources = new ArrayList<Resource>();
			if (jsonresources.length()>0)
				resources = resourcerepository.findByIDs(ids);
			
			return new ResponseEntity(getAPIResponse(resources, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/resource", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			return insertupdateAll(null, new JSONObject(data), apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("PUT", "/resource/"+id, data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			JSONObject jsonObj = new JSONObject(data);
			jsonObj.put("id", id);
			
			return insertupdateAll(null, jsonObj, apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.PUT)
		public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("PUT", "/resource", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			return insertupdateAll(new JSONArray(data), null, apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity insertupdateAll(JSONArray jsonresources, JSONObject jsonresource, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
		    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();

			List<Resource> resources = new ArrayList<Resource>();
			if (jsonresource != null) {
				jsonresources = new JSONArray();
				jsonresources.put(jsonresource);
			}
			log.info(jsonresources.toString());
			
			for (int a=0; a<jsonresources.length(); a++) {
				JSONObject jsonObj = jsonresources.getJSONObject(a);
				Resource resource = new Resource();
				long id = 0;

				if (jsonObj.has("id")) {
					id = jsonObj.getLong("id");
					if (id != 0) {
						resource = resourcerepository.findById(id).get();
						
						if (resource == null)
							return new ResponseEntity(getAPIResponse(null, null, "Invalid resource Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					}
				}

				if (id == 0) {
					if (!jsonObj.has("RESOURCE_ID") || jsonObj.isNull("RESOURCE_ID"))
						return new ResponseEntity(getAPIResponse(null, null, "RESOURCE_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
					
				}
				
				if (jsonObj.has("RESOURCE_ID") && !jsonObj.isNull("RESOURCE_ID"))
					resource.setRESOURCE_ID(jsonObj.getLong("RESOURCE_ID"));
				
				if (jsonObj.has("RESOURCE_SPECIFIER") && !jsonObj.isNull("RESOURCE_SPECIFIER"))
					resource.setRESOURCE_SPECIFIER(jsonObj.getString("RESOURCE_SPECIFIER"));
				
				if (jsonObj.has("AWS_ACCOUNT_ID") && !jsonObj.isNull("AWS_ACCOUNT_ID"))
					resource.setAWS_ACCOUNT_ID(jsonObj.getString("AWS_ACCOUNT_ID"));

				if (jsonObj.has("ACCESS_KEY_ID") && !jsonObj.isNull("ACCESS_KEY_ID")) 
					resource.setACCESS_KEY_ID(jsonObj.getString("ACCESS_KEY_ID"));
				
				if (jsonObj.has("SECRET_ACCESS_KEY") && !jsonObj.isNull("SECRET_ACCESS_KEY")) 
					resource.setSECRET_ACCESS_KEY(jsonObj.getString("SECRET_ACCESS_KEY"));
				
				
				
				if (jsonObj.has("isactive"))
				resource.setISACTIVE(jsonObj.getString("isactive"));
			    else if (id == 0)
				resource.setISACTIVE("Y");

				resource.setMODIFIED_BY(apiRequest.getREQUEST_ID());
				resource.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
				resource.setMODIFIED_WHEN(dateFormat1.format(date));
				resources.add(resource);
			}
		
			
			for (int a=0; a<resources.size(); a++) {
				Resource resource = resources.get(a);
				resource = resourcerepository.saveAndFlush(resource);
				resources.get(a).setRESOURCE_ID(resource.getRESOURCE_ID());
			}
			
			ResponseEntity responseentity;
			if (jsonresource != null)
				responseentity = new ResponseEntity(getAPIResponse(null, resources.get(0), null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
			else
				responseentity = new ResponseEntity(getAPIResponse(resources, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
			return responseentity;
		}
								
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/resource/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			Resource resource = resourcerepository.findById(id).get();
			resourcerepository.delete(resource);
			
			return new ResponseEntity(getAPIResponse(null, resource, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
		public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/resource/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			JSONObject resource = new JSONObject();
			resource.put("id", id);
			resource.put("isactive", "N");
			
			return insertupdateAll(null, resource, apiRequest);
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
			APIRequestDataLog apiRequest = checkToken("POST", "/resource/search" + ((active == true) ? "" : "/all"), data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			JSONObject jsonObj = new JSONObject(data);

			List<Resource> resources = ((active == true)
					? resourcerepository.findBySearch("%" + jsonObj.getString("search") + "%")
					: resourcerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
			
			return new ResponseEntity(getAPIResponse(resources, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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
			APIRequestDataLog apiRequest = checkToken("POST", "/resource/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			JSONObject jsonObj = new JSONObject(data);
			long resource_ID = 0;

			if (jsonObj.has("\resource_ID"))
				resource_ID = jsonObj.getLong("resource_ID");

			List<Resource> resource = ((active == true)
					? resourcerepository.findByAdvancedSearch(resource_ID)
					: resourcerepository.findAllByAdvancedSearch(resource_ID));

			return new ResponseEntity(getAPIResponse(resource, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}



		public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
			JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
			DatabaseTables databaseTableID = databasetablesrepository.findById(Resource.getDatabaseTableID()).get();
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
		
		APIRequestDataLog getAPIResponse(List<Resource> resources, Resource resource, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
			ObjectMapper mapper = new ObjectMapper();
			long resourceID = 0;
			
			if (message != null) {
				apiRequest = tableDataLogs.errorDataLog(apiRequest, "resource", message);
				apirequestdatalogRepository.saveAndFlush(apiRequest);
			} else {
				if (resource != null) {
					
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(resource));
					resourceID = resource.getRESOURCE_ID();
				} else {
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(resource));
				}
				apiRequest.setREQUEST_STATUS("Success");
				apirequestdatalogRepository.saveAndFlush(apiRequest);
			}
			
			if (isTableLog)
				tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(resourceID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
			
			log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
			log.info("--------------------------------------------------------");

		
				
			return apiRequest;
		}
	}
