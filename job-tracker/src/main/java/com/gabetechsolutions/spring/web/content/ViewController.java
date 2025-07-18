package com.gabetechsolutions.spring.web.content;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.gabetechsolutions.spring.common.Path.BASE_URI;
import static com.gabetechsolutions.spring.common.Path.DASHBOARD_URI;
import static com.gabetechsolutions.spring.common.Path.LOGIN_URI;
import static com.gabetechsolutions.spring.common.Path.SIGNUP_URI;

// @Controller indicates that this class is a Spring MVC controller
@Controller
public class ViewController {

    @GetMapping({BASE_URI, LOGIN_URI})
    public String loginPage() {
        return "index";
    }

    @GetMapping(SIGNUP_URI)
    public String signupPage() {
        return "signup";
    }

    @GetMapping(DASHBOARD_URI)
    public String dashboardPage() {
        return "dashboard";
    }
}
