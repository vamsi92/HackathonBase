package com.occ.voice.handlers;

import org.springframework.stereotype.Component;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.occ.voice.utils.AlexaUtils;

@Component
public class AmazonHelpIntentHandler implements IntentHandler {

@Override
public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
  Card card = AlexaUtils.newCard("OCC Help", AlexaUtils.SamplesHelpText);
  PlainTextOutputSpeech speech = AlexaUtils.newSpeech(AlexaUtils.SamplesHelpText, false);
  return AlexaUtils.newSpeechletResponse(card, speech, session, false);
}
}
