package com.occ.voice.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.occ.voice.model.CategoryDetails;
import com.occ.voice.model.RootCategory;
import com.occ.voice.services.FabIndiaApiServices;
import com.occ.voice.utils.AlexaUtils;

@Component
public class RootCategoryIntentHandler implements IntentHandler {

@Autowired
FabIndiaApiServices fabIndiaApiServices;
	
@Override
public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
  RootCategory rootCategory = fabIndiaApiServices.getRootCategory();
  StringBuilder categoryStr = new StringBuilder();
  int counter = 0;
  if(null != rootCategory) {
   List<CategoryDetails> items = rootCategory.getItems();
   for(CategoryDetails item : items) {
	   if(item.isActive()) {
		   counter ++;
	 categoryStr.append(counter);
	 categoryStr.append(" ");
	 
	 categoryStr.append(item.getDisplayName());
	 categoryStr.append(".");
	 categoryStr.append("\n ");
	 
	   }
   }
  }
  String speach = "I have total " + counter + " collections. \n " + " " + categoryStr.toString();
  speach = speach +". \n Say, Show Collection followed by the collection number to view the products under the collection.";
  
  Card card = AlexaUtils.newCard("FabIndia", speach);
  PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speach, AlexaUtils.inConversationMode(session));
  return AlexaUtils.newSpeechletResponse( card, speech, session, false);
 }

}
