package com.bkr.atm.web;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bkr.atm.domain.Accounts;
import com.bkr.atm.exception.WithdrawNotEnoughMoneyException;
import com.bkr.atm.exception.WithdrawZeroAmountException;
import com.bkr.atm.form.AddForm;
import com.bkr.atm.form.WithdrawalForm;
import com.bkr.atm.service.AccountService;
import com.bkr.atm.service.OperationHistoryService;

@Controller
public class OperationsController extends BaseSecurityController {

    private static final Logger logger = LoggerFactory.getLogger(OperationsController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private OperationHistoryService operationHistoryService;

    @RequestMapping("/operations")
    public String operations() {
        logger.info("Operations page request");
        return "operations";
    }

    @RequestMapping("/balance")
    public ModelAndView balance() {
        logger.info("Balance operation request");
        String number = getCardNumber();
        validateCard(number);
        Accounts creditCard = accountService.checkBalance(number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("number", creditCard.getNumber());
        modelAndView.addObject("twentyDenom", creditCard.getTwentyDenomNo());
        modelAndView.addObject("fiftyDenom", creditCard.getFiftyDenomNo());
        modelAndView.addObject("amount", creditCard.getAmount());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        modelAndView.addObject("currentDate", dateFormat.format(new Date()));

        modelAndView.setViewName("balance");
        return modelAndView;
    }

    @RequestMapping("/withdrawal")
    public ModelAndView withdrawal(
            @RequestParam(value = "notEnoughMoney", required = false) String notEnoughMoney,
            @RequestParam(value = "emptyWithdrawalAmount", required = false) String emptyWithdrawalAmount) {
        logger.info("Withdrawal page request");

        String number = getCardNumber();
        validateCard(number);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("number", number);
        if (emptyWithdrawalAmount != null) {
            modelAndView.addObject("error", "Withdrawal amount can not be empty");
        } else if (notEnoughMoney != null) {
            modelAndView.addObject("error", "Not a valid amount to retrive or Not enough money on card");
        }
        modelAndView.setViewName("withdrawal");
        return modelAndView;
    }
    
    @RequestMapping("/addBalance")
    public ModelAndView addBalance() {
        logger.info("Add Balance page request");
        String number = getCardNumber();
        validateCard(number);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("number", number);
        modelAndView.setViewName("addBalance");
        return modelAndView;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public ModelAndView withdraw(@ModelAttribute WithdrawalForm form) {
        logger.info(String.format("Withdraw operation request, withdrawal amount = %s$", form.getWithdrawalAmount()));

        String number = getCardNumber();
        validateCard(number);
        // TODO back-end validation
        BigDecimal withdrawalAmount = new BigDecimal(form.getWithdrawalAmount());
        Accounts saveAccount = accountService.withdraw(number, withdrawalAmount);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("number", saveAccount.getNumber());
        modelAndView.addObject("balanceAmount", saveAccount.getAmount());
        modelAndView.addObject("withdrawalAmount", withdrawalAmount);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        modelAndView.addObject("currentDate", dateFormat.format(new Date()));
        modelAndView.addObject("twentyNotes", saveAccount.getWithdrawDetails().getTwentyNotes());
        modelAndView.addObject("fiftyNotes", saveAccount.getWithdrawDetails().getFiftyNotes());

        modelAndView.setViewName("report");
        return modelAndView;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute AddForm addForm) {
        logger.info("Add operation request");

        String number = getCardNumber();
        validateCard(number);
        Accounts accounts = accountService.addBalance(number,addForm);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("number", accounts.getNumber());
        modelAndView.addObject("twentyDenom", accounts.getTwentyDenomNo());
        modelAndView.addObject("fiftyDenom", accounts.getFiftyDenomNo());
        modelAndView.addObject("amount", accounts.getAmount());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        modelAndView.addObject("currentDate", dateFormat.format(new Date()));

        modelAndView.setViewName("addReport");
        return modelAndView;
    }

    /**
     * In case of empty withdrawal amount redirect back to withdrawal page and show error msg
     */
    @ExceptionHandler(WithdrawZeroAmountException.class)
    public String handleWithdrawZeroAmount(Exception ex) {
        return "redirect:/withdrawal?emptyWithdrawalAmount";
    }

    /**
     * In case of not enough money on card to withdraw redirect back to withdrawal page and show error msg
     */
    @ExceptionHandler(WithdrawNotEnoughMoneyException.class)
    public String handleWithdrawNotEnoughMoney(Exception ex) {
        return "redirect:/withdrawal?notEnoughMoney";
    }

}
