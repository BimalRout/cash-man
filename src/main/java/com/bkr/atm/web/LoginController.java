package com.bkr.atm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bkr.atm.service.AccountService;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController extends BaseSecurityController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            HttpSession httpSession) {

        ModelAndView modelAndView = new ModelAndView();

        // try to read the credit card number entered in the first page of 2-page login
        String number = (String) httpSession.getAttribute("LAST_NUMBER");
        if (number == null) {
            // redirect to home page if credit card number is empty in the session
            logger.info("Login Page Request: Redirecting to Home Page to enter card number");
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        validateCard(number);

        logger.info(String.format("Login Page Request: Card Number: %s)", number));
        modelAndView.addObject("number", number);

        if (error != null) {
            // show message if there was invalid pin entering for the last login
            modelAndView.addObject("error", String.format("Invalid PIN (attempts left: %d)",
                            accountService.getLoginAttemptsLeft(number)));
        }

        modelAndView.setViewName("login");
        return modelAndView;
    }

}
