package me.tahacheji.mafana.command;

import me.tahacheji.mafana.MafanaBankNetwork;
import me.tahacheji.mafana.commandExecutor.Command;
import me.tahacheji.mafana.commandExecutor.paramter.Param;
import me.tahacheji.mafana.data.AccountType;
import me.tahacheji.mafana.data.Bank;
import me.tahacheji.mafana.data.BankCard;
import me.tahacheji.mafana.data.TransactionType;
import me.tahacheji.mafana.item.BankCardItem;
import me.tahacheji.mafana.util.BankUtil;
import me.tahacheji.mafana.util.CurrencyUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BankCommand {


    @Command(names = "bank generate", permission = "", playerOnly = true)
    public void generateBank(Player player, @Param(name = "pin") int pin) {
        MafanaBankNetwork.getInstance().getBankDatabase().createBank(player.getUniqueId(), pin).thenAcceptAsync(created -> {
            if (created) {
                player.sendMessage(ChatColor.GREEN + "Generated Bank!");
                MafanaBankNetwork.getInstance().getBankDatabase().getBankCard(player.getUniqueId()).thenAcceptAsync(bankCard -> {
                    new BankCardItem(player.getUniqueId(), bankCard).getCard(3).thenAcceptAsync(itemStack -> {
                        Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> {
                            player.getInventory().addItem(itemStack);
                            player.sendMessage(ChatColor.GREEN + "Received Bank Card (DON'T LOSE)!");
                            player.updateInventory();
                        });
                    });
                });
            } else {
                player.sendMessage(ChatColor.RED + "Bank already exists!");
            }
        });
    }

    @Command(names = "bank newCard", permission = "", playerOnly = true)
    public void generateCard(Player player) {
        if(new CurrencyUtil().getTotalCurrencyInPlayersInventory(player) > 100) {
            MafanaBankNetwork.getInstance().getBankDatabase().setBankCard(player.getUniqueId(), new BankCard(player.getUniqueId().toString())).thenRunAsync(() -> {
                MafanaBankNetwork.getInstance().getBankDatabase().getBankCard(player.getUniqueId()).thenAcceptAsync(bankCard -> {
                    new BankCardItem(player.getUniqueId(), bankCard).getCard(3).thenAcceptAsync(itemStack -> {
                        Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> {
                            player.getInventory().addItem(itemStack);
                            player.sendMessage(ChatColor.GREEN + "Received Bank Card (DON'T LOSE)!");
                            player.updateInventory();
                        });
                    });
                });
            });
        }
    }

    @Command(names = "bank newPin", permission = "", playerOnly = true)
    public void generateCard(Player player, @Param(name = "pin") int pin) {
        MafanaBankNetwork.getInstance().getBankDatabase().getBank(player.getUniqueId()).thenAcceptAsync(bank -> {
            Bank b = bank;
            b.setPin(pin + "");
            MafanaBankNetwork.getInstance().getBankDatabase().setBank(player.getUniqueId(), b).thenRunAsync(() -> {
                player.sendMessage(ChatColor.GREEN + "New Pin!");
            });
        });
    }

    @Command(names = "bank newBankRoutingNumbers", permission = "", playerOnly = true)
    public void generateRoutingNumbers(Player player) {
        MafanaBankNetwork.getInstance().getBankDatabase().getBank(player.getUniqueId()).thenAcceptAsync(bank -> {
            Bank b = bank;
            b.setCheckingRoutingNumber("CHECKING_" + new BankUtil().generateRoutingNumber());
            b.setSavingRoutingNumber("SAVING_" + new BankUtil().generateSavingsNumber());
            MafanaBankNetwork.getInstance().getBankDatabase().setBank(player.getUniqueId(), b).thenRunAsync(() -> {
                player.sendMessage(ChatColor.GREEN + "New Bank Routing Numbers!");
            });
        });
    }

}
