package com.occ.voice.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.occ.voice.services.FabIndiaApiServices;
import com.occ.voice.utils.AlexaUtils;
import com.occ.voice.utils.Constants;
import com.occ.voice.utils.State;

@Component
public class AddToCartIntentHandler implements IntentHandler {

@Autowired
FabIndiaApiServices fabIndiaApiServices;

@Value("${username}")
private String username;

@Value("${password}")
private String password;
	
protected Logger logger = LoggerFactory.getLogger(AddToCartIntentHandler.class);

@Override
public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
  logger.info("<<< AddToCartIntentHandler >>>");
  Object response = null; 
  if(State.currentUser == null) {
	  State.currentUser = fabIndiaApiServices.userLogin(username, password);
  }
  
  String quantity = intent.getSlot("quantity").getValue();
 if(quantity != null) {
	 response = fabIndiaApiServices.addToCart(State.selectedProduct, Integer.parseInt(quantity), State.currentUser.getAccess_token());
  }
 else
   response = fabIndiaApiServices.addToCart(State.selectedProduct, 1, State.currentUser.getAccess_token());
 
  State.cartItems = response;
  
  String speachText = "";
  
  if(response != null) {
	  speachText = "your product " + State.selectedProduct.getDisplayName()+" has been added to the cart. say checkout to place an order"; 
  }
  
  Card card = AlexaUtils.newCard("FabIndia", speachText, Constants.BASE_URL + State.selectedProduct.getPrimaryFullImageURL());
  PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speachText, AlexaUtils.inConversationMode(session));
  return AlexaUtils.newSpeechletResponse(card, speech, session, false);
}

}
