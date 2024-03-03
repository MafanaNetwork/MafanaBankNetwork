package me.tahacheji.mafana.command;

import me.tahacheji.mafana.MafanaBankNetwork;
import me.tahacheji.mafana.commandExecutor.Command;
import me.tahacheji.mafana.commandExecutor.paramter.Param;
import me.tahacheji.mafana.data.OfflineProxyPlayer;
import me.tahacheji.mafana.event.PlayerJoin;
import me.tahacheji.mafana.item.BankCardItem;
import me.tahacheji.mafana.util.CurrencyUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BankCommandAdmin {


    @Command(names = "bank admin generate currency", permission = "mafana.admin", playerOnly = true)
    public void generateCurrency(Player player, @Param(name = "amount") int amount) {
        new CurrencyUtil().addCurrency(player, amount);
    }

    @Command(names = "bank admin generate card", permission = "mafana.admin", playerOnly = true)
    public void generateCard(Player player, @Param(name = "target") OfflineProxyPlayer proxyPlayer) {
        MafanaBankNetwork.getInstance().getBankDatabase().getBankCard(UUID.fromString(proxyPlayer.getPlayerUUID())).thenAcceptAsync(bankCard -> {
            if(bankCard != null) {
                new BankCardItem(UUID.fromString(proxyPlayer.getPlayerUUID()), bankCard).getCard(3).thenAcceptAsync(itemStack -> {
                    Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> {
                        player.getInventory().addItem(itemStack);
                        player.sendMessage(ChatColor.GREEN + "Received Bank Card (DON'T LOSE)!");
                        player.updateInventory();
                    });
                });
            }
        });
    }
}
