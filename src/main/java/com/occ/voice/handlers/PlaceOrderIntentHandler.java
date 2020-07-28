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
import com.occ.voice.services.FabIndiaApiServices;
import com.occ.voice.utils.AlexaUtils;
import com.occ.voice.utils.State;

@Component
public class PlaceOrderIntentHandler implements IntentHandler {
	
 protected Logger logger = LoggerFactory.getLogger(PlaceOrderIntentHandler.class);
 
 @Autowired
 FabIndiaApiServices fabIndiaApiServices;

@Override
public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
 logger.info("<<< PlaceOrderIntentHandler >>>");
 try {
 fabIndiaApiServices.placeHolder(State.cartItems, State.currentUser.getAccess_token());
 } catch(Exception e) {
	 
 }
 
 //String speechText = "Sorry! Place order is not supported yet, Please place order using fabindia.com. Thank You !";
 String speechText = "Your Order has been placed. Thank You! for shopping on fabindia.com. Have a Good day!";
 Card card = AlexaUtils.newCard("FabIndia", speechText);
 PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText, AlexaUtils.inConversationMode(session));
 return AlexaUtils.newSpeechletResponse( card, speech, session, false);
}
 
}
