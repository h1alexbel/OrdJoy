package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static com.ordjoy.util.JspPageConst.*;

public class SwitchLangCommand implements FrontCommand {

    private static final String LANG = "lang";
    private static final String LANGUAGE_ATTRIBUTE = "language";
    private static final Locale US_LOCALE = new Locale("en", "US");
    private static final Locale RU_LOCALE = new Locale("ru", "RU");
    public static final String RU = "ru";

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        String lang = httpServletRequest.getParameter(LANG);
        if (RU.equalsIgnoreCase(lang)) {
            httpServletRequest.getSession().setAttribute(LANGUAGE_ATTRIBUTE, RU_LOCALE);
        } else {
            httpServletRequest.getSession().setAttribute(LANGUAGE_ATTRIBUTE, US_LOCALE);
        }
        page = httpServletRequest.getHeader(REFERER_HEADER);
        frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
        return frontCommandResult;
    }
}