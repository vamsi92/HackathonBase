package com.occ.voice.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.occ.voice.model.Category;
import com.occ.voice.model.LoginResponse;
import com.occ.voice.model.ProductDetails;
import com.occ.voice.model.RootCategory;
import com.occ.voice.utils.Constants;


@Service
public class FabIndiaApiServices {
  	
protected Logger logger = LoggerFactory.getLogger(FabIndiaApiServices.class);
 
@Value("${catalogId}")
String catalogId;

@Value("${storePriceListGroupId}")
String storePriceListGroupId;

private RootCategory rootCategory = null;
private Map<String, Category> getProductsMap = new HashMap();

public RootCategory getRootCategory() {
  String url = new StringBuilder()
   .append(Constants.BASE_URL)
   .append("/ccstoreui/v1/collections/rootCategory")
   .append("?catalogId=cloudCatalog&fields=childCategories(items)")
   .toString();
	  
  RestTemplate restTemplate = new RestTemplate();
  RootCategory response = restTemplate.getForObject(url, RootCategory.class);
  if(response != null) {
	  rootCategory = response;
  }
  return rootCategory;
  }

public LoginResponse userLogin(String email, String password) {
 String url = new StringBuilder()
	.append(Constants.BASE_URL)
	.append("/ccstoreui/v1/login/")
    .toString();

 RestTemplate restTemplate = new RestTemplate();
 HttpHeaders headers = new HttpHeaders();
  headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
  
 MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
  map.add("grant_type", "password");
  map.add("username", email);
  map.add("password", password);
  
  HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers); 
	
  ResponseEntity<LoginResponse> response = restTemplate.postForEntity( url, request , LoginResponse.class);
  return response.getBody();
 

}

//Category
public Category getProducts(String categoryId) {

 if(getProductsMap.get(categoryId) != null) {
	 return getProductsMap.get(categoryId);
 }
 else {
	 String url = new StringBuilder()
				.append(Constants.BASE_URL)
				.append("/ccstoreui/v1/products")
				.append("?totalResults=true&totalExpandedResults=true")
				.append("&catalogId="+catalogId)
				.append("&offset=0&limit=13&sort=creationDate:desc")
				.append("&categoryId="+categoryId)
				.append("&includeChildren=true&storePriceListGroupId="+storePriceListGroupId)
				.toString();
			 
			 RestTemplate restTemplate = new RestTemplate();
			 Category response = restTemplate.getForObject(url, Category.class);
			 getProductsMap.put(categoryId, response);
			 return response;
 }
}

public Object addToCart(ProductDetails productDetails,int quantity, String token ){
	String url = new StringBuilder().append(Constants.BASE_URL).append("/ccstoreui/v1/orders/current").toString();
	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers =new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.set("Authorization","Bearer "+token);
	Map<String, Object> requestMap = new HashMap<String,Object>();
    Map<String, Object> shoppingCartInfo= new HashMap<>();
    Map<String, Object> cartItem= new HashMap<>();
   
   //building cart item 
    cartItem.put("catRefId", productDetails.getChildSKUs().get(0).get("repositoryId"));
    cartItem.put("productId",productDetails.getId());
    cartItem.put("quantity", quantity);
    cartItem.put("commerceItemQuantity", 1);
    cartItem.put("priceListGroup", storePriceListGroupId);
    cartItem.put("invalid", "false");
    
    Map<String, Object> selectedOptions= new HashMap<>();
    if(productDetails.getProductVariantOptions() != null &&  productDetails.getProductVariantOptions().get(0) !=null){
       
        selectedOptions.put("optionName", productDetails.getProductVariantOptions().get(0).get("optionName"));
        selectedOptions.put("optionId", productDetails.getProductVariantOptions().get(0).get("optionId"));
        Map<String, Object> optionMap= new HashMap<>();
        optionMap = (Map<String, Object>) productDetails.getProductVariantOptions().get(0).get("optionValueMap");
        selectedOptions.put("optionValue",optionMap.keySet().toArray()[0]);
        selectedOptions.put("optionValueId",optionMap.values().toArray()[0]);//
    }

    cartItem.put("selectedOptions",Collections.singletonList(selectedOptions));
    
    
   
    shoppingCartInfo.put("items",Collections.singletonList(cartItem));
    requestMap.put(Constants.SHOPPING_CART,shoppingCartInfo);
    requestMap.put(Constants.COMBINE_LINE_ITEM, "yes");
    requestMap.put(Constants.OP_TYPE, Constants.OP_TYPE_CREATE);
    
    HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(requestMap, headers);
    String objmap = null;
	try {
		objmap = new ObjectMapper().writeValueAsString(requestMap);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  
    Object currentResponse = restTemplate.postForObject(url, request, Object.class);
    try {
		objmap = new ObjectMapper().writeValueAsString(currentResponse);
		 System.out.println("addToCartResponse:" +objmap);
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
   
	return currentResponse;	
	
}

public void placeHolder(Object pricingObject ,String token){
    
    Map<String, Object> requestMap= new HashMap<>();
    requestMap = (HashMap<String, Object>)pricingObject;
    
    Map<String, Object> shoppingCart= new HashMap<>();
    Map<String, Object> priceInfo= new HashMap<>();
    shoppingCart = (Map<String, Object>)requestMap.get("shoppingCart");
    priceInfo = (Map<String, Object>)requestMap.get("priceInfo");
    String total = priceInfo.get("total").toString();
    System.out.println("TOTAL:" +total);
    shoppingCart.put("orderTotal", total);
    requestMap.remove("shoppingCart");
    requestMap.put("shoppingCart", shoppingCart);
    String orderId= requestMap.get("orderId").toString();
    
    String url = new StringBuilder().append(Constants.BASE_URL).append("/ccstoreui/v1/orders/").append(orderId).toString();
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers =new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    headers.set("Authorization","Bearer "+token);
    
    
    
    Map<String, Object> paymentsMap= new HashMap<>();
    paymentsMap.put("type","cash");
    requestMap.put("payments",Collections.singletonList(paymentsMap) );
    
    requestMap.put("id",orderId);
    
    Map<String, Object> shippingMethodMap= new HashMap<>();
    shippingMethodMap.put("value","100001");
    requestMap.put("shippingMethod",shippingMethodMap);
    
    
    
    Map<String, Object> billingAddress = new HashMap<String, Object>();
   billingAddress.put("alias", "Address");
    billingAddress.put("prefix","" );
    billingAddress.put("firstName","Rishabh" );
    billingAddress.put("middleName","" );
    billingAddress.put("lastName","arora" );
    billingAddress.put("suffix","" );
    billingAddress.put("country","India" );
    billingAddress.put("postalCode","121006" );
    billingAddress.put("address1","Faridabad" );
    billingAddress.put("address2","" );
    billingAddress.put("address3","" );
    billingAddress.put("city","Faridabad" );
    billingAddress.put("state","Haryana" );
    billingAddress.put("county","" );
    billingAddress.put("phoneNumber","9898989898" );
    billingAddress.put("email","abinsam193@gmail.com" );
    billingAddress.put("jobTitle","" );
    billingAddress.put("companyName","" );
    billingAddress.put("faxNumber","" );
    billingAddress.put("repositoryId","3314435068" );
    billingAddress.put("isDefaultBillingAddress",false );
    billingAddress.put("isDefaultShippingAddress",false );
    billingAddress.put("selectedCountry","IN" );
    billingAddress.put("selectedState","HR" );
    billingAddress.put("state_ISOCode","IN-HR" );
    billingAddress.put("isDefaultAddress",false );
    billingAddress.put("x_loyaltyPointsRedeemed",null );
    requestMap.put("billingAddress",billingAddress);
    
    requestMap.put("shippingAddress", billingAddress);
    requestMap.put("op","complete");
    requestMap.put("combineLineItems", "yes");
    
    String objmap = null;
	try {
		objmap = new ObjectMapper().writeValueAsString(requestMap);
		System.out.println("placeORDER:"+objmap);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(requestMap, headers);
    restTemplate.put(url, request);
    
    /*try {
        String objmap = new ObjectMapper().writeValueAsString(currentResponse);
        System.out.println(objmap);
    } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }*/
    
    //return currentResponse;
    
}

}
