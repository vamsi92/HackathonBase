package com.occ.voice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.occ.voice.model.Category;
import com.occ.voice.model.LoginResponse;
import com.occ.voice.services.FabIndiaApiServices;
import com.occ.voice.services.ProductCategoryServices;

@Controller
public class HomeController {
	
	@Autowired
	FabIndiaApiServices fabIndiaApiServices;
	
	@Autowired
	ProductCategoryServices productCategoryServices;
	
  @RequestMapping(value={"/", "/home"})
    public String home() {
	  try {
		productCategoryServices.assignParents();
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  /*LoginResponse response = fabIndiaApiServices.userLogin("abinsam193@gmail.com", "Qwerty@123");
	  
	  System.out.println(response);
	  System.out.println(fabIndiaApiServices.getRootCategory());
	  Category products = fabIndiaApiServices.getProducts("justin");
	  System.out.println(products.getItems().get(0));
	  Object obj = fabIndiaApiServices.addToCart(products.getItems().get(0), 2, response.getAccess_token());
	  System.out.println(obj);
	  fabIndiaApiServices.placeHolder(obj, response.getAccess_token());*/
	  
	 // System.out.println(fabIndiaApiServices.getProducts("justin"));
	  // System.out.println(fabIndiaApiServices.getProducts("cat100298"));
	  // System.out.println(fabIndiaApiServices.getProducts("cat100269"));
	  //System.out.println(fabIndiaApiServices.getProducts("cat100235"));
	  
	  return "home";
  }
}
