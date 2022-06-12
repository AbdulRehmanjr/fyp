package com.cwiztech.cloudplatform.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwiztech.cloudplatform.model.OrganizationResource;
import com.cwiztech.cloudplatform.repository.*;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.cloudplatform.model.*;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/organizationresource")
public class organizationresourceController{
	
	private static final Logger log = LoggerFactory.getLogger(organizationresourceController.class);
	
	//@Autowired
	@Autowired(required = false) 
	private organizationresourceRepository organizationresourcerepository;
	
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
		APIRequestDataLog apiRequest = checkToken("GET", "/organizationresource/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<OrganizationResource> organizationresource = organizationresourcerepository.findActive();		
		return new ResponseEntity(getAPIResponse(organizationresource, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/organizationresource/all", null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<OrganizationResource> organizationresource = organizationresourcerepository.findAll();
		
		return new ResponseEntity(getAPIResponse(organizationresource, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/organizationresource/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		OrganizationResource organizationresource = organizationresourcerepository.findById(id).get();
		
		return new ResponseEntity(getAPIResponse(null, organizationresource, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/organizationresource/ids", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		List<Integer> ids = new ArrayList<Integer>(); 
		JSONObject jsonObj = new JSONObject(data);
		JSONArray jsonorganizationresources = jsonObj.getJSONArray("organizationresources");
		for (int i=0; i<jsonorganizationresources.length(); i++) {
			ids.add((Integer) jsonorganizationresources.get(i));
		}
		List<OrganizationResource> organizationresources = new ArrayList<OrganizationResource>();
		if (jsonorganizationresources.length()>0)
			organizationresources = organizationresourcerepository.findByIDs(ids);
		
		return new ResponseEntity(getAPIResponse(organizationresources, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("POST", "/organizationresource", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(null, new JSONObject(data), apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/organizationresource/"+id, data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject jsonObj = new JSONObject(data);
		jsonObj.put("id", id);
		
		return insertupdateAll(null, jsonObj, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("PUT", "/organizationresource", data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		return insertupdateAll(new JSONArray(data), null, apiRequest);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity insertupdateAll(JSONArray jsonorganizationresources, JSONObject jsonorganizationresource, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
	    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		List<OrganizationResource> organizationresources = new ArrayList<OrganizationResource>();
		if (jsonorganizationresource != null) {
			jsonorganizationresources = new JSONArray();
			jsonorganizationresources.put(jsonorganizationresource);
		}
		log.info(jsonorganizationresources.toString());
		
		for (int a=0; a<jsonorganizationresources.length(); a++) {
			JSONObject jsonObj = jsonorganizationresources.getJSONObject(a);
			OrganizationResource organizationresource = new OrganizationResource();
			long id = 0;

			if (jsonObj.has("id")) {
				id = jsonObj.getLong("id");
				if (id != 0) {
					organizationresource = organizationresourcerepository.findById(id).get();
					
					if (organizationresource == null)
						return new ResponseEntity(getAPIResponse(null, null, "Invalid organizationresource Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
			}

			if (id == 0) {
				if (!jsonObj.has("ORGANIZATIONRESOURCE_ID") || jsonObj.isNull("ORGANIZATIONRESOURCE_ID"))
					return new ResponseEntity(getAPIResponse(null, null, "ORGANIZATIONRESOURCE_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				
				if (!jsonObj.has("RESOURCETYPE_ID") || jsonObj.isNull("RESOURCETYPE_ID"))
					return new ResponseEntity(getAPIResponse(null, null, "RESOURCETYPE_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				
				if (!jsonObj.has("ORGANIZATIONRESOURCE_DESCRIPTION") || jsonObj.isNull("ORGANIZATIONRESOURCE_DESCRIPTION"))
					return new ResponseEntity(getAPIResponse(null, null, "ORGANIZATIONRESOURCE_DESCRIPTION is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			}
			
			if (jsonObj.has("ORGANIZATIONRESOURCE_ID") && !jsonObj.isNull("ORGANIZATIONRESOURCE_ID"))
				organizationresource.setORGANIZATIONRESOURCE_ID(jsonObj.getLong("ORGANIZATIONRESOURCE_ID"));
			
			if (jsonObj.has("ORGANIZATION_ID") && !jsonObj.isNull("ORGANIZATION_ID"))
				organizationresource.setORGANIZATION_ID(jsonObj.getLong("ORGANIZATION_ID"));
			
			if (jsonObj.has("RESOURCETYPE_ID") && !jsonObj.isNull("RESOURCETYPE_ID"))
				organizationresource.setRESOURCETYPE_ID(jsonObj.getLong("RESOURCETYPE_ID"));

			if (jsonObj.has("ORGANIZATIONRESOURCE_DESCRIPTION") && !jsonObj.isNull("ORGANIZATIONRESOURCE_DESCRIPTION")) 
				organizationresource.setORGANIZATIONRESOURCE_DESCRIPTION(jsonObj.getString("ORGANIZATIONRESOURCE_DESCRIPTION"));
			
			if (jsonObj.has("MAXIMUM_NODES") && !jsonObj.isNull("MAXIMUM_NODES")) 
				organizationresource.setMAXIMUM_NODES(jsonObj.getLong("MAXIMUM_NODES"));
			
			if (jsonObj.has("WEBSITE") && !jsonObj.isNull("WEBSITE")) 
				organizationresource.setWEBSITE(jsonObj.getString("WEBSITE"));
			
			if (jsonObj.has("VISIBILITY_ID") && !jsonObj.isNull("VISIBILITY_ID")) 
				organizationresource.setVISIBILITY_ID(jsonObj.getLong("VISIBILITY_ID"));
			
			if (jsonObj.has("RESOURCEBEHAVIOURTYPE_ID") && !jsonObj.isNull("RESOURCEBEHAVIOURTYPE_ID")) 
				organizationresource.setRESOURCEBEHAVIOURTYPE_ID(jsonObj.getLong("RESOURCEBEHAVIOURTYPE_ID"));
			
			if (jsonObj.has("USEGLOBALSECURITYGROUP") && !jsonObj.isNull("USEGLOBALSECURITYGROUP")) 
				organizationresource.setUSEGLOBALSECURITYGROUP(jsonObj.getString("USEGLOBALSECURITYGROUP"));
			
			if (jsonObj.has("CHECKRUNWAYINSTANCES") && !jsonObj.isNull("CHECKRUNWAYINSTANCES")) 
				organizationresource.setCHECKRUNWAYINSTANCES(jsonObj.getString("CHECKRUNWAYINSTANCES"));
			
			if (jsonObj.has("ACCESSINTERNET") && !jsonObj.isNull("ACCESSINTERNET")) 
				organizationresource.setACCESSINTERNET(jsonObj.getString("ACCESSINTERNET"));
			
			if (jsonObj.has("USEPROXY") && !jsonObj.isNull("USEPROXY")) 
				organizationresource.setUSEPROXY(jsonObj.getString("USEPROXY"));
			
			if (jsonObj.has("PROXY_HOSTNAME") && !jsonObj.isNull("PROXY_HOSTNAME")) 
				organizationresource.setPROXY_HOSTNAME(jsonObj.getString("PROXY_HOSTNAME"));
			
			if (jsonObj.has("PROXY_USERNAME") && !jsonObj.isNull("PROXY_USERNAME")) 
				organizationresource.setPROXY_USERNAME(jsonObj.getString("PROXY_USERNAME"));
			
			if (jsonObj.has("PROXY_PASSWORD") && !jsonObj.isNull("PROXY_PASSWORD")) 
				organizationresource.setPROXY_PASSWORD(jsonObj.getString("PROXY_PASSWORD"));
			
			if (jsonObj.has("PROXY_PRIVATEKEY") && !jsonObj.isNull("PROXY_PRIVATEKEY")) 
				organizationresource.setPROXY_PRIVATEKEY(jsonObj.getString("PROXY_PRIVATEKEY"));
			
			if (jsonObj.has("PROXY_PUBLICEKEY") && !jsonObj.isNull("PROXY_PUBLICEKEY")) 
				organizationresource.setPROXY_PUBLICEKEY(jsonObj.getString("PROXY_PUBLICEKEY"));
			
			if (jsonObj.has("isactive"))
			organizationresource.setISACTIVE(jsonObj.getString("isactive"));
		    else if (id == 0)
			organizationresource.setISACTIVE("Y");

			organizationresource.setMODIFIED_BY(apiRequest.getREQUEST_ID());
			organizationresource.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
			organizationresource.setMODIFIED_WHEN(dateFormat1.format(date));
			organizationresources.add(organizationresource);
		}
	
		
		for (int a=0; a<organizationresources.size(); a++) {
			OrganizationResource organizationresource = organizationresources.get(a);
			organizationresource = organizationresourcerepository.saveAndFlush(organizationresource);
			organizationresources.get(a).setORGANIZATIONRESOURCE_ID(organizationresource.getORGANIZATIONRESOURCE_ID());
		}
		
		ResponseEntity responseentity;
		if (jsonorganizationresource != null)
			responseentity = new ResponseEntity(getAPIResponse(null, organizationresources.get(0), null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		else
			responseentity = new ResponseEntity(getAPIResponse(organizationresources, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		return responseentity;
	}
							
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/organizationresource/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		OrganizationResource organizationresource = organizationresourcerepository.findById(id).get();
		organizationresourcerepository.delete(organizationresource);
		
		return new ResponseEntity(getAPIResponse(null, organizationresource, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		APIRequestDataLog apiRequest = checkToken("GET", "/organizationresource/"+id, null, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		
		JSONObject organizationresource = new JSONObject();
		organizationresource.put("id", id);
		organizationresource.put("isactive", "N");
		
		return insertupdateAll(null, organizationresource, apiRequest);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/organizationresource/search" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);

		List<		OrganizationResource> organizationresources = ((active == true)
				? organizationresourcerepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: organizationresourcerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		return new ResponseEntity(getAPIResponse(organizationresources, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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
		APIRequestDataLog apiRequest = checkToken("POST", "/organizationresource/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
		if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

		JSONObject jsonObj = new JSONObject(data);
		long organizationresource_ID = 0;

		if (jsonObj.has("organizationresource_ID"))
			organizationresource_ID = jsonObj.getLong("organizationresource_ID");

		List<OrganizationResource> organizationresource = ((active == true)
				? organizationresourcerepository.findByAdvancedSearch(organizationresource_ID)
				: organizationresourcerepository.findAllByAdvancedSearch(organizationresource_ID));

		return new ResponseEntity(getAPIResponse(organizationresource, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
	}



	public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
		DatabaseTables databaseTableID = databasetablesrepository.findById(OrganizationResource.getDatabaseTableID()).get();
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
	
	APIRequestDataLog getAPIResponse(List<OrganizationResource> organizationresources, OrganizationResource organizationresource, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		long organizationresourceID = 0;
		
		if (message != null) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "organizationresource", message);
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		} else {
			if (organizationresource != null) {
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(organizationresource));
				organizationresourceID = organizationresource.getORGANIZATIONRESOURCE_ID();
			} else {
				apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(organizationresource));
			}
			apiRequest.setREQUEST_STATUS("Success");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
		}
		
		if (isTableLog)
			tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(organizationresourceID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
		
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");

		return apiRequest;
	}
}





