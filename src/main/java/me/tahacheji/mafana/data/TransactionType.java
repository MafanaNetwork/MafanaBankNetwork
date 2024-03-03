package me.tahacheji.mafana.data;

import java.util.List;

public enum TransactionType {

    LOAN("Loan"),
    DEPOSIT("Deposit"),
    RESET("Reset"),
    PAY_LOAN("Payed Loan"),
    WITHDRAW("Withdraw"),
    TRANSFER_DEPOSIT("Transfer Deposit"),
    TRANSFER_WITHDRAW("Transfer Withdraw"),
    ALL("All");


    private final String lore;

    TransactionType(String lore) {
        this.lore = lore;
    }

    public String getLore() {
        return lore;
    }

    public static List<TransactionType> getAllTransactionType() {
        return List.of(DEPOSIT, WITHDRAW);
    }

    public static TransactionType getNextTransactionType(TransactionType currentTransactionType) {
        switch (currentTransactionType) {
            case LOAN:
                return TransactionType.DEPOSIT;
            case DEPOSIT:
                return TransactionType.RESET;
            case RESET:
                return TransactionType.PAY_LOAN;
            case PAY_LOAN:
                return TransactionType.WITHDRAW;
            case WITHDRAW:
                return TransactionType.ALL;
            case ALL:
            default:
                return TransactionType.LOAN;
        }
    }

}
