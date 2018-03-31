package com.bkr.atm.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bkr.atm.form.AccountNumberForm;
import com.bkr.atm.service.AccountService;

@Controller
public class HomeController extends BaseSecurityController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    AccountService accountService;

    @Autowired
    public HomeController(AccountService creditCardService) {
        this.accountService = creditCardService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@RequestParam(value = "logout", required = false) String logout) {
        logger.info(String.format("Home page request"));

        ModelAndView modelAndView = new ModelAndView();
        if (logout != null) {
            modelAndView.addObject("msg", "You've exited successfully");
        }
        modelAndView.setViewName("home");

        return modelAndView;
    }

    @RequestMapping(value = "/enter_card", method = RequestMethod.POST)
    public String submitCreditCardNumber(
            @ModelAttribute AccountNumberForm form,
            HttpSession httpSession) {
        String number = form.getNumber();
        validateCard(number);

        logger.info(String.format("Enter card request: Card %s found", number));

        // once we already know the number of credit card entered on first page of 2-page login, we should remember it
        // in session. otherwise if we enter login incorrectly for the first time, the number will be lost.
        httpSession.setAttribute("LAST_NUMBER", number);
        return "redirect:/login";

    }

}
