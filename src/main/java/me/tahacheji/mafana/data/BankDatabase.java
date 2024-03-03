package me.tahacheji.mafana.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.tahacheji.mafana.MafanaNetworkCommunicator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BankDatabase extends MySQL {
    SQLGetter sqlGetter = new SQLGetter(this);

    public BankDatabase() {
        super("localhost", "3306", "51251", "51251", "d5f02bb941");
    }

    public CompletableFuture<Boolean> createBank(UUID uuid, int pin) {
        return sqlGetter.existsAsync(uuid).thenComposeAsync(exists -> {
            if (!exists) {
                return MafanaNetworkCommunicator.getInstance().getPlayerDatabase().getOfflineProxyPlayerAsync(uuid).thenComposeAsync(player -> {
                    Gson gson = new Gson();
                    CompletableFuture<Void> setName = sqlGetter.setStringAsync(new DatabaseValue("NAME", uuid, player.getPlayerName()));
                    CompletableFuture<Void> setBank = sqlGetter.setStringAsync(new DatabaseValue("BANK", uuid, gson.toJson(new Bank(uuid.toString(), pin))));
                    CompletableFuture<Void> setBankCard = sqlGetter.setStringAsync(new DatabaseValue("BANK_CARD", uuid, gson.toJson(new BankCard(uuid.toString()))));
                    CompletableFuture<Void> setBankLoan = sqlGetter.setStringAsync(new DatabaseValue("BANK_LOAN", uuid, ""));
                    CompletableFuture<Void> setBankTransactions = sqlGetter.setStringAsync(new DatabaseValue("BANK_TRANSACTIONS", uuid, ""));
                    return CompletableFuture.allOf(setName, setBank, setBankCard, setBankLoan, setBankTransactions)
                            .thenApplyAsync(ignored -> true);
                });
            } else {
                return CompletableFuture.completedFuture(false);
            }
        });
    }



    public CompletableFuture<List<BankTransaction>> getBankTransactions(UUID uuid) {
        return sqlGetter.getStringAsync(uuid, new DatabaseValue("BANK_TRANSACTIONS")).thenApplyAsync(string -> {
            Gson gson = new Gson();
            return gson.fromJson(string, new TypeToken<List<BankTransaction>>() {
            }.getType());
        });
    }

    public CompletableFuture<Void> setBankTransactions(UUID uuid, List<BankTransaction> bankTransactions) {
        Gson gson = new Gson();
        return sqlGetter.setStringAsync(new DatabaseValue("BANK_TRANSACTIONS", uuid, gson.toJson(bankTransactions)));
    }

    public CompletableFuture<Void> addBankTransaction(UUID uuid, BankTransaction bankTransaction) {
        return getBankTransactions(uuid).thenComposeAsync(bankTransactions -> {
            List<BankTransaction> b;
            if(bankTransactions == null) {
                b = new ArrayList<>();
            } else {
                b = new ArrayList<>(bankTransactions);
            }
            b.add(bankTransaction);
            return setBankTransactions(uuid, b);
        });
    }

    public CompletableFuture<List<BankLoan>> getBankLoans(UUID uuid) {
        return sqlGetter.getStringAsync(uuid, new DatabaseValue("BANK_LOAN")).thenApplyAsync(string -> {
            Gson gson = new Gson();
            return gson.fromJson(string, new TypeToken<List<BankLoan>>() {
            }.getType());
        });
    }

    public CompletableFuture<Void> setBankLoans(UUID uuid, List<BankLoan> bankLoans) {
        Gson gson = new Gson();
        return sqlGetter.setStringAsync(new DatabaseValue("BANK_LOAN", uuid, gson.toJson(bankLoans)));
    }

    public CompletableFuture<Void> addBankLoan(UUID uuid, BankLoan bankLoan) {
        return getBankLoans(uuid).thenComposeAsync(bankLoans -> {
            List<BankLoan> b;
            if(bankLoans == null) {
                b = new ArrayList<>();
            } else {
                b = new ArrayList<>(bankLoans);
            }
            b.add(bankLoan);
            return setBankLoans(uuid, b);
        });
    }

    public CompletableFuture<Void> updateBankLoan(UUID uuid, BankLoan bankLoan) {
        return getBankLoans(uuid).thenComposeAsync(bankLoans -> {
            List<BankLoan> x = new ArrayList<>();
            for(BankLoan z : bankLoans) {
                if(z.getUuid().equals(bankLoan.getUuid())) {
                    x.add(bankLoan);
                } else {
                    x.add(z);
                }
            }
            return setBankLoans(uuid, x);
        });
    }

    public CompletableFuture<Void> removeBankLoan(UUID uuid, BankLoan bankLoan) {
        return getBankLoans(uuid).thenComposeAsync(bankLoans -> {
            BankLoan x = null;
            for(BankLoan z : bankLoans) {
                if(z.getUuid().equals(bankLoan.getUuid())) {
                    x = z;
                    break;
                }
            }
            if(x != null) {
                bankLoans.remove(x);
            }
            return setBankLoans(uuid, bankLoans);
        });
    }

    public CompletableFuture<BankCard> getBankCard(UUID uuid) {
        return getAllBankCards().thenApplyAsync(bankCards -> {
            for (BankCard card : bankCards) {
                if (uuid.toString().equalsIgnoreCase(card.getUuid().toString())) {
                    return card;
                }
            }
            return null;
        });
    }

    public CompletableFuture<Void> setBankCard(UUID uuid, BankCard bankCard) {
        Gson gson = new Gson();
        return sqlGetter.setStringAsync(new DatabaseValue("BANK_CARD", uuid, gson.toJson(bankCard)));
    }

    public CompletableFuture<Bank> getBank(String routingNumber) {
        return getAllBanks().thenApplyAsync(banks -> {
            for (Bank bank : banks) {
                if (routingNumber.equalsIgnoreCase(bank.getSavingRoutingNumber()) || routingNumber.equalsIgnoreCase(bank.getCheckingRoutingNumber())) {
                    return bank;
                }
            }
            return null;
        });
    }

    public CompletableFuture<Bank> getBank(UUID uuid) {
        return getAllBanks().thenApplyAsync(banks -> {
            for (Bank bank : banks) {
                if (uuid.toString().equalsIgnoreCase(bank.getUuid().toString())) {
                    return bank;
                }
            }
            return null;
        });
    }

    public CompletableFuture<Void> setBank(UUID uuid, Bank bank) {
        Gson gson = new Gson();
        return sqlGetter.setStringAsync(new DatabaseValue("BANK", uuid, gson.toJson(bank)));
    }

    public CompletableFuture<List<Bank>> getAllBanks() {
        return sqlGetter.getAllStringAsync(new DatabaseValue("BANK"))
                .thenApplyAsync(list -> {
                    Gson gson = new Gson();
                    List<Bank> banks = new ArrayList<>();
                    for (String s : list) {
                        banks.add(gson.fromJson(s, new TypeToken<Bank>() {
                        }.getType()));
                    }
                    return banks;
                });
    }

    public CompletableFuture<List<BankCard>> getAllBankCards() {
        return sqlGetter.getAllStringAsync(new DatabaseValue("BANK_CARD"))
                .thenApplyAsync(list -> {
                    Gson gson = new Gson();
                    List<BankCard> x = new ArrayList<>();
                    for (String s : list) {
                        x.add(gson.fromJson(s, new TypeToken<BankCard>() {
                        }.getType()));
                    }
                    return x;
                });
    }

    @Override
    public SQLGetter getSqlGetter() {
        return sqlGetter;
    }

    public void connect() {
        sqlGetter.createTable("player_banking",
                new DatabaseValue("NAME", ""),
                new DatabaseValue("BANK", ""),
                new DatabaseValue("BANK_CARD", ""),
                new DatabaseValue("BANK_LOAN", ""),
                new DatabaseValue("BANK_TRANSACTIONS", ""));
    }
}
