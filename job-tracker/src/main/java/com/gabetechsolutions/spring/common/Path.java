package com.gabetechsolutions.spring.common;

public class Path {

    public static final String BASE_URI = "/";
    public static final String API_V1 = BASE_URI + "api/v1/";

    // Onboarding and View Endpoints
    public static final String SIGNUP_URI = BASE_URI + "signup";
    public static final String CONFIRMATION_URI = SIGNUP_URI + "/confirm";
    public static final String LOGIN_URI = BASE_URI + "login";
    public static final String DASHBOARD_URI = BASE_URI + "dashboard";

    // Job Application Endpoints
    public static final String JOB_APPLICATION_URI = API_V1 + "application";


}
