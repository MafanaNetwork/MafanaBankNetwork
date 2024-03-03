package me.tahacheji.mafana.command;

import me.tahacheji.mafana.MafanaBankNetwork;
import me.tahacheji.mafana.commandExecutor.Command;
import me.tahacheji.mafana.commandExecutor.paramter.Param;
import me.tahacheji.mafana.data.AccountType;
import me.tahacheji.mafana.data.Bank;
import me.tahacheji.mafana.data.BankTransaction;
import me.tahacheji.mafana.data.TransactionType;
import me.tahacheji.mafana.util.BankUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;


public class BankTransfer {

    @Command(names = "bank transfer", permission = "", playerOnly = true)
    public void transfer(Player player, @Param(name = "routingNumberFrom") String routingNumberFrom, @Param(name = "routingNumberTo") String routingNumberTo, @Param(name = "amount") int amount) {
        MafanaBankNetwork.getInstance().getBankDatabase().getBank(routingNumberFrom).thenAcceptAsync(bank1 -> {
            MafanaBankNetwork.getInstance().getBankDatabase().getBank(routingNumberTo).thenAcceptAsync(bank2 -> {
                if (bank1 == null || bank2 == null) {
                    player.sendMessage(ChatColor.RED + "One or both of the specified banks do not exist.");
                    return;
                }
                int bankOneAmount = 0;
                if(routingNumberFrom.contains("CHECKING")) {
                    bankOneAmount = bank1.getCheckingBalance();
                } else {
                    bankOneAmount = bank1.getSavingBalance();
                }

                if(bankOneAmount < amount) {
                    player.sendMessage(ChatColor.RED + "Not enough in account.");
                    return;
                }
                if(routingNumberFrom.contains("CHECKING")) {
                    bank1.setCheckingBalance(bank1.getCheckingBalance() - amount);
                    MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank1.getUuid(), new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(),
                            routingNumberFrom, TransactionType.TRANSFER_WITHDRAW.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), amount + ""));
                } else {
                    bank1.setSavingBalance(bank1.getSavingBalance() - amount);
                    MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank1.getUuid(), new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(),
                            routingNumberFrom, TransactionType.TRANSFER_WITHDRAW.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), amount + ""));
                }

                if(routingNumberTo.contains("CHECKING")) {
                    bank2.setCheckingBalance(bank2.getCheckingBalance() + amount);
                    MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank2.getUuid(), new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(),
                            routingNumberTo, TransactionType.TRANSFER_DEPOSIT.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), amount + ""));
                } else {
                    bank2.setSavingBalance(bank2.getSavingBalance() + amount);
                    MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank2.getUuid(), new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(),
                            routingNumberTo, TransactionType.TRANSFER_DEPOSIT.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), amount + ""));

                }

                Bank b1 = bank1;
                Bank b2 = bank2;

                MafanaBankNetwork.getInstance().getBankDatabase().setBank(b1.getUuid(), b1);
                MafanaBankNetwork.getInstance().getBankDatabase().setBank(b2.getUuid(), b2);
                player.sendMessage(ChatColor.GREEN + "Transfer Complete.");

            });
        });
    }


}
