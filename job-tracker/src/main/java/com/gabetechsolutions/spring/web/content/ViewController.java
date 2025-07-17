package com.gabetechsolutions.spring.web.content;

import com.gabetechsolutions.spring.common.Path;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// @Controller indicates that this class is a Spring MVC controller
@Controller
public class ViewController {

    @GetMapping(Path.LOGIN_URI)
    public String loginPage() {
        return "login";
    }
}
