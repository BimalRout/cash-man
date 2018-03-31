package com.bkr.atm.form;

public class WithdrawalForm {

    private String withdrawalAmount;

    public String getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(String withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WithdrawalForm that = (WithdrawalForm) o;

        if (withdrawalAmount != null ? !withdrawalAmount.equals(that.withdrawalAmount) : that.withdrawalAmount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return withdrawalAmount != null ? withdrawalAmount.hashCode() : 0;
    }
}
