package com.occ.voice.services;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductCategoryServices {
//00000004
	protected Logger logger = LoggerFactory.getLogger(ProductCategoryServices.class);
	
	String baseCategoryStr = "sdg-category-";
	int digits = 8;
	int startCategory = 1001;
	int endCategory = 1380;
	 static final String baseUrl = "http://localhost:9080/ccadminui/v1/collections/";
	 String token = "eyJhbGciOiJSUzI1NiIsImprdSI6IjVkYmQyZTRhIiwia2lkIjpudWxsLCJ4NWMiOm51bGwsIng1dSI6Imh0dHA6Ly9BQklTQU1BTi1JTjo5MDgwL2NjYWRtaW4vdjEvdGVuYW50Q2VydENoYWluIn0=.eyJpYXQiOjE1NTU1NzQ0ODEsImV4cCI6MTU1NTU3NTM4MSwic3ViIjoiYWRtaW4iLCJhdWQiOiJhZG1pblVJIiwiY29tLm9yYWNsZS5hdGcuY2xvdWQuY29tbWVyY2Uucm9sZXMiOlsiYWRtaW5Sb2xlIl0sIm9jY3MuYWRtaW4ucm9sZXMiOlsiYWRtaW5Sb2xlIl0sImlzcyI6Imh0dHA6Ly9BQklTQU1BTi1JTjo5MDgwL29jY3MtYWRtaW4iLCJvY2NzLmFkbWluLmxvY2FsZSI6ImVuIiwib2Njcy5hZG1pbi50eiI6bnVsbCwib2Njcy5hZG1pbi50ZW5hbnRUeiI6IkV0Yy9VVEMiLCJvY2NzLmFkbWluLmtlZXBBbGl2ZVVSTCI6Imh0dHA6Ly9BQklTQU1BTi1JTjo5MDgwL2tlZXBhbGl2ZSIsIm9jY3MuYWRtaW4udG9rZW5SZWZyZXNoVVJMIjoiaHR0cDovL0FCSVNBTUFOLUlOOjkwODAvY2NhZG1pbi92MS9zc29Ub2tlbnMvcmVmcmVzaCIsIm9jY3MuYWRtaW4udmVyc2lvbiI6IjE5LjIuNC1TTkFQU0hPVCIsIm9jY3MuYWRtaW4uYnVpbGQiOiJMT0NBTCIsIm9jY3MuYWRtaW4uZW1haWwiOm51bGwsIm9jY3MuYWRtaW4ucHJvZmlsZUlkIjoiYWRtaW4iLCJvY2NzLmFnZW50Lm9ibyI6bnVsbCwib2Njcy5hZG1pbi5maXJzdE5hbWUiOiJBZG1pbiIsIm9jY3MuYWRtaW4ubGFzdE5hbWUiOiJVc2VyIiwib2Njcy5hZG1pbi5wdW5jaG91dFVzZXIiOmZhbHNlLCJzdWJfdHlwZSI6bnVsbCwic2NvcGUiOm51bGx9.NoGpYv+UpaY3mjYQvoZvLdU9OE1Udmu4FVEZxvbT1mjPXluGX9JNt5wr1PQd1HsSkgY79k9sHHkvysYwEYQDpdQMWgBit4aVeIloggvty3Ldn6U2l0wwakMqHIm8QocjF6LHJ6e3PGePGzX16BvSQkO567H9JtcgtQXY8O6x2FMsQif7hYF1U8qpV8LjoapmAuipQkDXKCI0hplsRd4H2AUycH/2yYB8P+MFvEtBq4/PvyjzpSTP5DwrBcmX+skbWNj1nsYO3zurF2sFf/4nXNaXhvKSvh6oY12y0WH/J28Yt0K5mZaOPIb7QDgs2G2Fkn9DHH0POSug6EQJJR4zYQ==";
	String productId="Product_27Fxyzi";
  public void assignParents() throws JsonProcessingException {
	
	  for (int i=startCategory; i< endCategory; i++) {
		  String categoryId = baseCategoryStr + "0000" + String.valueOf(i);
		  
		  AssignProducts product = new AssignProducts();
		  product.setProducts(Collections.singletonList(productId));
		  product.setCollection(categoryId);
		  
		  ObjectMapper mapper = new ObjectMapper();
		  String json = mapper.writeValueAsString(product);
		  System.out.println(json);
		  
		  fireRestCalls(json, productId, categoryId);
	  }
  }
	
	
  public void fireRestCalls(String json, String productId, String categoryId) {
	try {  
	  String url = new StringBuilder()
			  .append(baseUrl).append(categoryId).toString();
	  
	  HttpHeaders headers =new HttpHeaders();
	  headers.setContentType(MediaType.APPLICATION_JSON);
	  headers.set("Authorization","Bearer "+token);
	  headers.set("x-ccasset-language", "en");
		
	  HttpEntity<String> entity = new HttpEntity<String>(json, headers);
	  
	  RestTemplate template = new RestTemplate();
	  ResponseEntity<String> response = template
	            .exchange(url, HttpMethod.PUT, entity, String.class);
		
      System.out.println(response.getStatusCodeValue() + " " + response);
      if(response.getStatusCodeValue() != 200) {
    	  System.out.println("xxxxxERROR"+categoryId+"xxxxxxxxx");
      }
      
      
  }
	catch(Exception e) {
  	  e.printStackTrace();
    }
  }
}
