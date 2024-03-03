package me.tahacheji.mafana.data;

import me.tahacheji.mafana.util.BankUtil;

import java.util.UUID;

public class Bank {

    private final String uuid;

    private int checkingBalance;
    private String checkingRoutingNumber;

    private int savingBalance;
    private String savingRoutingNumber;

    private String pin;

    public Bank(String uuid, int checkingBalance, String checkingRoutingNumber, int savingBalance, String savingRoutingNumber, String pin) {
        this.uuid = uuid;
        this.checkingBalance = checkingBalance;
        this.checkingRoutingNumber = checkingRoutingNumber;
        this.savingBalance = savingBalance;
        this.savingRoutingNumber = savingRoutingNumber;
        this.pin = pin;
    }

    public Bank(String uuid, int pin) {
        this.uuid = uuid;
        this.checkingBalance = 0;
        this.checkingRoutingNumber = "CHECKING_" + new BankUtil().generateRoutingNumber();
        this.savingBalance = 0;
        this.savingRoutingNumber = "SAVING_" + new BankUtil().generateSavingsNumber();
        this.pin = String.valueOf(pin);
    }

    public void setCheckingRoutingNumber(String checkingRoutingNumber) {
        this.checkingRoutingNumber = checkingRoutingNumber;
    }

    public void setSavingRoutingNumber(String savingRoutingNumber) {
        this.savingRoutingNumber = savingRoutingNumber;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public int getCheckingBalance() {
        return checkingBalance;
    }

    public String getCheckingRoutingNumber() {
        return checkingRoutingNumber;
    }

    public int getSavingBalance() {
        return savingBalance;
    }

    public String getSavingRoutingNumber() {
        return savingRoutingNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setCheckingBalance(int checkingBalance) {
        this.checkingBalance = checkingBalance;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setSavingBalance(int savingBalance) {
        this.savingBalance = savingBalance;
    }
}
