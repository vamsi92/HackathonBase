package com.occ.voice.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;

public interface IntentHandler {
	SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session);
}
