package me.tahacheji.mafana.data;

import java.util.List;

public enum AccountType {

    CHECKING("Checking"),
    SAVINGS("Saving"),
    ALL("All");


    private final String lore;

    AccountType(String lore) {
        this.lore = lore;
    }

    public String getLore() {
        return lore;
    }

    public static List<AccountType> getAllAccountType() {
        return List.of(CHECKING, SAVINGS, ALL);
    }

    public static AccountType getNextAccountType(AccountType currentAccountType) {
        switch (currentAccountType) {
            case CHECKING:
                return AccountType.SAVINGS;
            case SAVINGS:
                return AccountType.ALL;
            case ALL:
            default:
                return AccountType.CHECKING;
        }
    }
}
