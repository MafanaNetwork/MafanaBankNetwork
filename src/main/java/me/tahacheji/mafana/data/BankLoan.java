package me.tahacheji.mafana.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankLoan {

    private final String uuid;

    private final String loadUUID;

    private final int loanAmount;
    private final int loanDays;
    private final List<String> collateral;

    public BankLoan(String uuid, String loadUUID, int loanAmount, int loanDays, List<String> collateral) {
        this.uuid = uuid;
        this.loadUUID = loadUUID;
        this.loanAmount = loanAmount;
        this.loanDays = loanDays;
        this.collateral = collateral;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public String getLoadUUID() {
        return loadUUID;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public int getLoanDays() {
        return loanDays;
    }

    public List<String> getCollateral() {
        return collateral;
    }
}
