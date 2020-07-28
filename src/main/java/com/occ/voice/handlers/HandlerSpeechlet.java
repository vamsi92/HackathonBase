package com.occ.voice.handlers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.occ.voice.model.RootCategory;
import com.occ.voice.services.FabIndiaApiServices;
import com.occ.voice.utils.AlexaUtils;
import com.occ.voice.utils.State;



@Service
public class HandlerSpeechlet implements SpeechletV2 {
 protected Logger logger = LoggerFactory.getLogger(HandlerSpeechlet.class);

 @Autowired
 FabIndiaApiServices fabIndiaApiServices;
 
 @Autowired 
 private BeanFactory beanFactory;
 
 @Value("${username}")
 private String username;
 
 @Value("${password}")
 private String password;
 
 //static LoginResponse auth;
 
 RootCategory rootCategory; 
	
	
 public HandlerSpeechlet() {}

 @Override
 public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		
		// This is invoked when a new Alexa session is started.  Any initialization logic would go here. 
		// You can store stuff in the Alexa session, for example, by calling:
		// 		Session session = requestEnvelope.getSession();
 }
	
 @Override
 public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		
		// This is called when the skill is started with no specific intent.  
		// Such as "Alexa, ask MyDemoApp."
		// When this happens, we should provide a welcome message and prompt 
		// the user to ask a question.
		
		logger.info("onLaunch Called");
		logger.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());
		logger.info("timestamp={}", requestEnvelope.getRequest().getTimestamp());
		logger.info("locale={}", requestEnvelope.getRequest().getLocale());
		logger.info("AppId={}", requestEnvelope.getSession().getApplication().getApplicationId());
		logger.info("UserId={}", requestEnvelope.getSession().getUser().getUserId());

		
		 logger.info("logging in user {}", username);
		 State.currentUser = fabIndiaApiServices.userLogin(username, password);

		Session session = requestEnvelope.getSession();
		String speechText = "Hello. " + AlexaUtils.NonUserLinkHelpText;
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText, false);
		Card card = AlexaUtils.newCard("Welcome!", speechText);
		return AlexaUtils.newSpeechletResponse(card, speech, session, false);
		
	}

	@Override	
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		
		// This is invoked whenever an intent is invoked for our application.  We need
		// to figure out when intent it is, then delegate to a handler for that specific
		// intent.
		logger.info("onIntent Called");
		logger.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());
		logger.info("timestamp={}", requestEnvelope.getRequest().getTimestamp());
		logger.info("locale={}", requestEnvelope.getRequest().getLocale());
		logger.info("AppId={}", requestEnvelope.getSession().getApplication().getApplicationId());
		logger.info("UserId={}", requestEnvelope.getSession().getUser().getUserId());
		
		IntentRequest request = requestEnvelope.getRequest();
		Session session = requestEnvelope.getSession();
		logger.info("request session:" + session.getSessionId() + " "
				+ session.getAttributes());
		
		// Get the intent
		Intent intent = request.getIntent();
		if ( intent != null ) {
			// Derive the handler's bean name
			String intentName = intent.getName();
			String handlerBeanName = intentName + "Handler";
			logger.info("intentName={}", intentName);
			// If this is an Amazon Intent, change the handler name to better
			// match up to a Spring bean name.  For example, the intent AMAZON.HelpIntent should
			// be changed to AmazonHelpIntent.
			handlerBeanName = StringUtils.replace(handlerBeanName, "AMAZON.", "Amazon");
			handlerBeanName = handlerBeanName.substring(0, 1).toLowerCase() + handlerBeanName.substring(1);
			
			if ( logger.isInfoEnabled() )
				logger.info("About to invoke handler '" + handlerBeanName + "' for intent '" + intentName + "'.");
			
			// Handle the intent by delegating to the designated handler.
			try {
				Object handlerBean = beanFactory.getBean(handlerBeanName);
			
				if ( handlerBean != null ) {
					
					if ( handlerBean instanceof IntentHandler ) {
						IntentHandler intentHandler = (IntentHandler) handlerBean;
						return intentHandler.handleIntent(intent, request, session);
					}
				}
			}
			catch (Exception e) {
				logger.error("Error handling intent " + intentName, e);
			}
		}
		
		
		// Handle unknown intents.  Ask the user for more info.
		// Start a conversation (if not started already) and say that we did not understand the intent
		AlexaUtils.setConversationMode(session, true);
		
		String errorText = "Sorry, I could not understand your request. " + "Say help for some suggestions.";
		
		Card card = AlexaUtils.newCard("Dazed and Confused", errorText);
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(errorText, false);
		
		return AlexaUtils.newSpeechletResponse(card, speech, session, false);				
	}

	@Override
	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		logger.info("onSessionEnded Called");
		logger.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());
		logger.info("timestamp={}", requestEnvelope.getRequest().getTimestamp());
		logger.info("locale={}", requestEnvelope.getRequest().getLocale());
		logger.info("AppId={}", requestEnvelope.getSession().getApplication().getApplicationId());
		logger.info("AccessToken={}", requestEnvelope.getSession().getUser().getAccessToken());
		logger.info("UserId={}", requestEnvelope.getSession().getUser().getUserId());
		logger.info("Permissions={}", requestEnvelope.getSession().getUser().getPermissions());
	}


	private SpeechletResponse sendMessage(String message, Session session) {
		Card card = AlexaUtils.newCard("OCC", message);
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(message, AlexaUtils.inConversationMode(session));
		return AlexaUtils.newSpeechletResponse( card, speech, session, false);
	}
}
