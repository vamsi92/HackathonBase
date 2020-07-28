package com.occ.voice.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.occ.voice.model.Category;
import com.occ.voice.model.CategoryDetails;
import com.occ.voice.model.ProductDetails;
import com.occ.voice.services.FabIndiaApiServices;
import com.occ.voice.utils.AlexaUtils;
import com.occ.voice.utils.Constants;
import com.occ.voice.utils.State;

@Component
public class ProductIntentHandler implements IntentHandler {
	
protected Logger logger = LoggerFactory.getLogger(ProductIntentHandler.class);
	
 @Autowired
 FabIndiaApiServices fabIndiaApiServices;

 @Override
 public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
  logger.info("<<< CategoryIntentHandler >>>");
  String productSlot = intent.getSlot("product").getValue();
  logger.info("product slot : {}", productSlot);
  int productSlotInt = Integer.parseInt(productSlot);
  
  CategoryDetails category = State.selectedCategory;
  Category selectedCategory = fabIndiaApiServices.getProducts(category.getId());
  ProductDetails product = selectedCategory.getItems().get(productSlotInt - 1);
  State.selectedProduct = product;
  StringBuilder prodDetails = new StringBuilder();
  prodDetails.append("Here are the Product details. \n ")
      .append( product.getDisplayName() + " made by " + product.getBrand() +". \n ")
      .append(" Color is :" + product.getX_colorName() +". \n ")
      .append(" Listed price is : " + product.getListPrice() +" Rupees. \n ")
      .append("Say add to card to add this item to cart. ");
      
  String speachText = prodDetails.toString();
  Card card = AlexaUtils.newCard("FabIndia", speachText, Constants.BASE_URL +product.getPrimaryFullImageURL());
  PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speachText, AlexaUtils.inConversationMode(session));
  return AlexaUtils.newSpeechletResponse(card, speech, session, false);
 
  }

}
