package me.tahacheji.mafana.data;

import java.util.UUID;

public class BankTransaction {

    private final String uuid;
    private final String transactionUUID;

    private final String routingNumber;
    private final String transactionType;
    private final String accountType;
    private final String date;

    private final String amount;

    public BankTransaction(String uuid, String transactionUUID, String routingNumber, String transactionType, String accountType, String date, String amount) {
        this.uuid = uuid;
        this.transactionUUID = transactionUUID;
        this.routingNumber = routingNumber;
        this.transactionType = transactionType;
        this.accountType = accountType;
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }



    public String getAmount() {
        return amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public UUID getTransactionUUID() {
        return UUID.fromString(transactionUUID);
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
