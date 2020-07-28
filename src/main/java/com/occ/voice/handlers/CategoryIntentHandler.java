package com.occ.voice.handlers;

import java.util.List;

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
import com.occ.voice.model.RootCategory;
import com.occ.voice.services.FabIndiaApiServices;
import com.occ.voice.utils.AlexaUtils;
import com.occ.voice.utils.State;

@Component
public class CategoryIntentHandler  implements IntentHandler {

 protected Logger logger = LoggerFactory.getLogger(CategoryIntentHandler.class);
			
 @Autowired
 FabIndiaApiServices fabIndiaApiServices;
 
 @Override
 public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
  logger.info("<<< CategoryIntentHandler >>>");	 
  Category category = null;
  int counter = 0;
  CategoryDetails selectedCategory = null;
  StringBuilder prodStr = null;
  String categorySlot = intent.getSlot("category").getValue();
  logger.info("category slot {}", categorySlot);
  int categoryKey = Integer.parseInt(categorySlot);
 
  RootCategory rootCategory = fabIndiaApiServices.getRootCategory();
  if(rootCategory != null) {
    selectedCategory = rootCategory.getItems().get(categoryKey - 1);
    State.selectedCategory = selectedCategory;
    logger.info("selected category :" +selectedCategory);
    category = fabIndiaApiServices.getProducts(selectedCategory.getId());
  }
  if(selectedCategory != null && category != null) {
	   prodStr = new StringBuilder();
	  
	   List<ProductDetails> items = category.getItems();
	   for(ProductDetails item : items) {
		   if(item.isActive()) {
				 counter ++;
			   prodStr.append(counter);
			   prodStr.append(". ");
		 
			   prodStr.append(item.getDisplayName());
			   prodStr.append(".");
			   prodStr.append(" \n ");
		   }
	   }
	    
  }
  String speechText = "I have total " + counter + " products under " + selectedCategory.getDisplayName() +" collection. \n " + " " + prodStr.toString();
  speechText = speechText + ". \n Say Show Product followed by Product number to view the Product.";
  Card card = AlexaUtils.newCard("FabIndia", speechText);
  PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText, AlexaUtils.inConversationMode(session));
  return AlexaUtils.newSpeechletResponse(card, speech, session, false);
 }

}
