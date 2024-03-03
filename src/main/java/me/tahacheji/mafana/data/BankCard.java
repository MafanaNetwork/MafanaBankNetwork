package me.tahacheji.mafana.data;

import me.tahacheji.mafana.util.BankUtil;

import java.util.UUID;

public class BankCard {

    private final String uuid;

    private final String cardNumber;
    private final String cardCVV;
    private final String loanScore;
    private final String dateOfCreation;

    public BankCard(String uuid, String cardNumber, String cardCVV, String loanScore, String dateOfCreation) {
        this.uuid = uuid;
        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
        this.loanScore = loanScore;
        this.dateOfCreation = dateOfCreation;
    }

    public BankCard(String uuid) {
        this.uuid = uuid;
        this.cardNumber = new BankUtil().generateCardNumber();
        this.cardCVV = new BankUtil().generateCVV();
        this.loanScore = String.valueOf(600);
        this.dateOfCreation = new BankUtil().generateDate();
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public String getLoanScore() {
        return loanScore;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }
}
