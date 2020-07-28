package com.xavidop.alexa.servlet;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;
import com.xavidop.alexa.handlers.*;
import com.xavidop.alexa.interceptors.request.LocalizationRequestInterceptor;
import com.xavidop.alexa.interceptors.request.LogRequestInterceptor;
import com.xavidop.alexa.interceptors.response.LogResponseInterceptor;

public class AlexaServlet extends SkillServlet {

    public AlexaServlet() {
        super(getSkill());
    }

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new HelloWorldIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new FallbackIntentHandler(),
                        new ErrorHandler())
                .addExceptionHandler(new MyExceptionHandler())
                .addRequestInterceptors(
                        new LogRequestInterceptor(),
                        new LocalizationRequestInterceptor())
                .addResponseInterceptors(new LogResponseInterceptor())
                // Add your skill id below
                .withSkillId("amzn1.ask.skill.4e539b94-8d8a-45ad-ad92-6e08207ef332")
                .build();
    }

}