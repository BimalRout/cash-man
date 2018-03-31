package com.bkr.atm.domain;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bkr.atm.exception.WithdrawNotEnoughMoneyException;
import com.bkr.atm.exception.WithdrawRulesConflictException;
import com.bkr.atm.exception.WithdrawZeroAmountException;

@Entity
@Table(name = "Accounts")
public class Accounts implements UserDetails {

    public static final Integer FAILED_LOGIN_ATTEMPTS_TO_BLOCK = 4;
    private static final Logger logger = LoggerFactory.getLogger(Accounts.class);

    @Id
    @Column(name = "cnumber", length = 19, nullable = false, updatable = false)
    private String number;

    @Column(name = "pin", length = 100, nullable = false)
    private String pin;

    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;
    
    @Column(name = "twentyDenom", precision = 15, scale = 2, nullable = false)
    private Integer twentyDenomNo;
    
    @Column(name = "fiftyDenom", precision = 15, scale = 2, nullable = false)
    private Integer fiftyDenomNo;

    @Column(name = "failed_login_attempts", nullable = false)
    private Integer failedLoginAttempts = 0;
    
    @Transient
    private  WithdrawDetails withdrawDetails;

    public Accounts() {}

    public Accounts(String number, String pin,Integer twentyDenomNo,Integer fiftyDenomNo, BigDecimal amount) {
        this.number = number;
        this.pin = pin;;
        this.twentyDenomNo=twentyDenomNo;
        this.fiftyDenomNo=fiftyDenomNo;
        this.amount = amount;
    }

    public WithdrawDetails getWithdrawDetails() {
		return withdrawDetails;
	}

	public void setWithdrawDetails(WithdrawDetails withdrawDetails) {
		this.withdrawDetails = withdrawDetails;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    /**
     * IMPORTANT! This method should be run in @Transactional context!
     */
    public void incrementFailedLoginAttempts() {
        failedLoginAttempts++;
    }

    /**
     * IMPORTANT! This method should be run in @Transactional context!
     */
    public void resetFailedLoginAttempts() {
        failedLoginAttempts = 0;
    }

   

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return pin;
    }

    @Override
    public String getUsername() {
        return number;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // credit card will be blocked as soon as failed login attempts reach FAILED_LOGIN_ATTEMPTS_TO_BLOCK
        return failedLoginAttempts < FAILED_LOGIN_ATTEMPTS_TO_BLOCK;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	public Integer getTwentyDenomNo() {
		return twentyDenomNo;
	}

	public void setTwentyDenomNo(Integer twentyDenomNo) {
		this.twentyDenomNo = twentyDenomNo;
	}

	public Integer getFiftyDenomNo() {
		return fiftyDenomNo;
	}

	public void setFiftyDenomNo(Integer fiftyDenomNo) {
		this.fiftyDenomNo = fiftyDenomNo;
	}
	
	 /**
     * Withdraws money from the account with checking of business rules
     * IMPORTANT! This method should be run in @Transactional context!
     */
    public BigDecimal withdraw(BigDecimal withdrawalAmount) {
    	
    	 withdrawDetails = new WithdrawDetails();
    	
        if (withdrawalAmount.compareTo(new BigDecimal(0)) < 0) {
            logger.info(String.format("Withdrawal amount is negative: %s", withdrawalAmount));
            throw new WithdrawRulesConflictException("Withdrawal amount is negative");
        } else if (withdrawalAmount.compareTo(new BigDecimal(0)) == 0) {
            logger.info(String.format("Withdrawal amount is zero: %s", withdrawalAmount));
            throw new WithdrawZeroAmountException("Withdrawal amount is zero");
        }
        if (amount.compareTo(withdrawalAmount) < 0) {
            logger.info(String.format("Not enough money on card %s to withdraw %s", number, withdrawalAmount));
            throw new WithdrawNotEnoughMoneyException("Not enough money to withdraw");
        }
        
        Integer fiftyNotes=0;
        Integer twentyNotes=0;
        
       if(withdrawalAmount.compareTo(new BigDecimal(50))==0){
       	 fiftyNotes = withdrawalAmount.divide(new BigDecimal(50)).intValue();
       	 
       }else if(withdrawalAmount.compareTo(new BigDecimal(50))>0){
    	   
         fiftyNotes = withdrawalAmount.divide(new BigDecimal(50)).intValue();
          if(withdrawalAmount.remainder(new BigDecimal(50)).compareTo(new BigDecimal(0))>0){
        	  
        	 BigDecimal fiftyReminder =  withdrawalAmount.remainder(new BigDecimal(50));
        	 
        		  if(fiftyReminder.remainder(new BigDecimal(20)).compareTo(new BigDecimal(0))==0){
        			  twentyNotes =fiftyReminder.divide(new BigDecimal(20)).intValue();
        		  }else{
        			  throw new WithdrawNotEnoughMoneyException("Not a proper amount to retrive..Please try some different amount");
        		  }
              }
        }else if(withdrawalAmount.compareTo(new BigDecimal(50))==0){
        	 fiftyNotes = withdrawalAmount.divide(new BigDecimal(50)).intValue();
        }else if(withdrawalAmount.compareTo(new BigDecimal(50))<0){
        	 if(withdrawalAmount.remainder(new BigDecimal(20)).compareTo(new BigDecimal(0))==0){
   			  twentyNotes =withdrawalAmount.divide(new BigDecimal(20)).intValue();
   		  }else{
   			  throw new WithdrawNotEnoughMoneyException("Not a proper amount to retrive..Please try some different amount");
   		  }
        }
       if( fiftyNotes <= fiftyDenomNo && twentyNotes<= twentyDenomNo){
    	   this.fiftyDenomNo=this.fiftyDenomNo-fiftyNotes;
    	   this.twentyDenomNo=this.twentyDenomNo-twentyNotes;
    	   this.amount = this.amount.subtract(new BigDecimal(fiftyNotes*50).add(new BigDecimal(twentyNotes*20)));
    	   withdrawDetails.setFiftyNotes(fiftyNotes);
    	   withdrawDetails.setTwentyNotes(twentyNotes);
       }
        logger.info(String.format("%s$ were withdrawn from card %s", withdrawalAmount, number));
        return this.amount;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Accounts that = (Accounts) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (failedLoginAttempts != null ? !failedLoginAttempts.equals(that.failedLoginAttempts) : that.failedLoginAttempts != null)
            return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (pin != null ? !pin.equals(that.pin) : that.pin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (failedLoginAttempts != null ? failedLoginAttempts.hashCode() : 0);
        return result;
    }
}
