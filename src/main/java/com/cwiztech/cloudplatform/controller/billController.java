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
import com.cwiztech.cloudplatform.model.Bill;
import com.cwiztech.cloudplatform.model.OrganizationResource;
import com.cwiztech.cloudplatform.repository.billRepository;
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
@RequestMapping("/bill")


	
	public class billController{
		
		private static final Logger log = LoggerFactory.getLogger(billController.class);
		
		@Autowired
		private billRepository billrepository;
		
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
			APIRequestDataLog apiRequest = checkToken("GET", "/bill/all", null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Bill> bill = billrepository.findActive();		
			return new ResponseEntity(getAPIResponse(bill, null,null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/all", method = RequestMethod.GET)
		public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/bill/all", null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Bill> bill = billrepository.findAll();
			
			return new ResponseEntity(getAPIResponse(bill, null,null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/bill/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			Bill bill = billrepository.findById(id).get();
			
			return new ResponseEntity(getAPIResponse(null, bill,null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/ids", method = RequestMethod.POST)
		public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/bill/ids", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			List<Integer> ids = new ArrayList<Integer>(); 
			JSONObject jsonObj = new JSONObject(data);
			JSONArray jsonbill = jsonObj.getJSONArray("bill");
			for (int i=0; i<jsonbill.length(); i++) {
				ids.add((Integer) jsonbill.get(i));
			}
			List<Bill> bill_s = new ArrayList<Bill>();
			if (jsonbill.length()>0)
				bill_s = billrepository.findByIDs(ids);
			
			return new ResponseEntity(getAPIResponse(bill_s, null,null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("POST", "/bill", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			return insertupdateAll(null, new JSONObject(data), apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("PUT", "/bill/"+id, data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			JSONObject jsonObj = new JSONObject(data);
			jsonObj.put("id", id);
			
			return insertupdateAll(null, jsonObj, apiRequest);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(method = RequestMethod.PUT)
		public ResponseEntity insertupdate(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
				throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("PUT", "/bill", data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			return insertupdateAll(new JSONArray(data), null, apiRequest);
		}
		
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ResponseEntity insertupdateAll(JSONArray json_bill, JSONObject jsonbill, APIRequestDataLog apiRequest) throws JsonProcessingException, JSONException, ParseException {
		    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();

			List<Bill> bill1 = new ArrayList<Bill>();
			if (jsonbill != null) {
				json_bill = new JSONArray();
				json_bill.put(jsonbill);
			}
			log.info(json_bill.toString());
			
			for (int a=0; a<json_bill.length(); a++) {
				JSONObject jsonObj = json_bill.getJSONObject(a);
				Bill bill = new Bill();
				long id = 0;

				if (jsonObj.has("id")) {
					id = jsonObj.getLong("id");
					if (id != 0) {
						bill =billrepository.findById(id).get();
						
						if (bill == null)
							return new ResponseEntity(getAPIResponse(null, null, null, null,"Invalid bill Data!", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					}
				}

				if (id == 0) {
					if (!jsonObj.has("RESOURCE_ID") || jsonObj.isNull("RESOURCE_ID"))
						return new ResponseEntity(getAPIResponse(null, null, null, null,"RESOURCE_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
					
					
					if (!jsonObj.has("USER_ID") || jsonObj.isNull("USER_ID"))
						return new ResponseEntity(getAPIResponse(null, null, null, null,"USER_ID is missing", apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
				}
				
				if (jsonObj.has("RESOURCE_ID") && !jsonObj.isNull("RESOURCE_ID"))
					bill.setRESOURCE_ID(jsonObj.getLong("RESOURCE_ID"));
				
				if (jsonObj.has("USER_ID") && !jsonObj.isNull("USER_ID"))
					bill.setUSER_ID(jsonObj.getLong("USER_ID"));
				
				if (jsonObj.has("QUANTITY") && !jsonObj.isNull("QUANTITY"))
					bill.setQUANTITY(jsonObj.getString("QUANTITY"));

				if (jsonObj.has("FEE_TYPE") && !jsonObj.isNull("FEE_TYPE")) 
					bill.setFEE_TYPE(jsonObj.getString("FEE_TYPE"));
				
				if (jsonObj.has("TOTAL_AMOUNT") && !jsonObj.isNull("TOTAL_AMOUNT")) 
					bill.setTOTAL_AMOUNT(jsonObj.getLong("TOTAL_AMOUNT"));
				
				if (jsonObj.has("STATUS") && !jsonObj.isNull("STATUS")) 
					bill.setSTATUS(jsonObj.getString("STATUS"));
				
				
				
				if (jsonObj.has("isactive"))
					bill.setISACTIVE(jsonObj.getString("isactive"));
			    else if (id == 0)
			    	bill.setISACTIVE("Y");

				bill.setMODIFIED_BY(apiRequest.getREQUEST_ID());
				bill.setMODIFIED_WORKSTATION(apiRequest.getLOG_WORKSTATION());
				bill.setMODIFIED_WHEN(dateFormat1.format(date));
				bill1.add(bill);
			}
			
			for (int a=0; a<bill1.size(); a++) {
				Bill bill  = bill1.get(a);
				bill =  billrepository.saveAndFlush(bill);
				bill1.get(a).setRESOURCE_ID(bill.getRESOURCE_ID());
			}
			
			ResponseEntity responseentity;
			if (jsonbill != null)
				responseentity = new ResponseEntity(getAPIResponse(null,bill1.get(0), null, null,null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
			else
				responseentity = new ResponseEntity(getAPIResponse(bill1, null,null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
			return responseentity;
		}
								
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/bill/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			Bill bill = billrepository.findById(id).get();
			billrepository.delete(bill);
			
			return new ResponseEntity(getAPIResponse(null, bill,null, null, null, apiRequest, true).getREQUEST_OUTPUT(), HttpStatus.OK);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
		public ResponseEntity remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
			APIRequestDataLog apiRequest = checkToken("GET", "/bill/"+id, null, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
			
			JSONObject bill = new JSONObject();
			bill.put("id", id);
			bill.put("isactive", "N");
			
			return insertupdateAll(null, bill, apiRequest);
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
			APIRequestDataLog apiRequest = checkToken("POST", "/bill/search" + ((active == true) ? "" : "/all"), data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			JSONObject jsonObj = new JSONObject(data);

			List<Bill>bill_s = ((active == true)
					? billrepository.findBySearch("%" + jsonObj.getString("search") + "%")
					: billrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
			
			return new ResponseEntity(getAPIResponse(bill_s, null, null,null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
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
			APIRequestDataLog apiRequest = checkToken("POST", "/bill/advancedsearch" + ((active == true) ? "" : "/all"), data, null, headToken);
			if (apiRequest.getREQUEST_STATUS() != null) return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);

			JSONObject jsonObj = new JSONObject(data);
			long bill_ID = 0;

			if (jsonObj.has("bill_ID"))
				bill_ID = jsonObj.getLong("bill_ID");

			List<Bill> bill = ((active == true)
					? billrepository.findByAdvancedSearch(bill_ID)
					: billrepository.findAllByAdvancedSearch(bill_ID));

			return new ResponseEntity(getAPIResponse(bill,null,null, null, null, apiRequest, false).getREQUEST_OUTPUT(), HttpStatus.OK);
		}



		public APIRequestDataLog checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
			JSONObject checkTokenResponse = AccessToken.checkToken(accessToken);
			DatabaseTables databaseTableID = databasetablesrepository.findById(Bill.getDatabaseTableID()).get();
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
            apiRequest.setREQUEST_OUTPUT(accessToken);
			return apiRequest;
		}
		
		APIRequestDataLog getAPIResponse(List<Bill> bill_s, Bill bill, JSONArray jsonbill_s , JSONObject jsonbill, String message, APIRequestDataLog apiRequest, boolean isTableLog) throws JSONException, JsonProcessingException, ParseException {
			ObjectMapper mapper = new ObjectMapper();
			long bill_ID = 0;
			
			if (message != null) {
				apiRequest = tableDataLogs.errorDataLog(apiRequest, "bill", message);
				apirequestdatalogRepository.saveAndFlush(apiRequest);
			} else {
				if (bill != null) {
			
					if(bill.getRESOURCE_ID() != null) {
					JSONObject resource = new JSONObject(ResourceService.GET("resource/"+bill.getRESOURCE_ID(), apiRequest.getREQUEST_OUTPUT()));
					bill.setRESOURCE_DETAIL(resource.toString());
				}
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(bill));
					bill_ID	 = bill.getBILL_ID();
				} 
			
			else if(bill_s!=null) {
						if (bill_s.size()>0) {
						List<Integer> resourceList = new ArrayList<Integer>();
						for (int i=0; i<bill_s.size(); i++) {
							resourceList.add(Integer.parseInt(bill_s.get(i).getRESOURCE_ID().toString()));
						}
					JSONArray resourceObject = new JSONArray(ResourceService.POST("/resource/ids", "{resources: "+resourceList+"}", apiRequest.getREQUEST_OUTPUT()));
						
						for (int i=0; i<bill_s.size(); i++) {
							for (int j=0; j<resourceObject.length(); j++) {
								JSONObject resource = resourceObject.getJSONObject(j);
								if(bill_s.get(i).getRESOURCE_ID() == resource.getLong("resource_ID") ) {
									bill_s.get(i).setRESOURCE_DETAIL(resource.toString());
								}
							}
					apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(bill_s));
				}
						}
						else if(jsonbill_s!=null) {
							apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(jsonbill_s.toString()));
							
						}
						
						else if(jsonbill!=null) {
							apiRequest.setREQUEST_OUTPUT(mapper.writeValueAsString(jsonbill.toString()));
							
						}
						
				apiRequest.setREQUEST_STATUS("Success");
				apirequestdatalogRepository.saveAndFlush(apiRequest);
			}
			
			if (isTableLog)
				tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(bill_ID, apiRequest.getDATABASETABLE_ID(), apiRequest.getREQUEST_ID(), apiRequest.getREQUEST_OUTPUT()));
			
			log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
			log.info("--------------------------------------------------------");

			
			}
			return apiRequest;
		}}

