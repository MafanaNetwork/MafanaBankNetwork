package me.tahacheji.mafana.event;

import de.tr7zw.nbtapi.NBTItem;
import me.tahacheji.mafana.MafanaBankNetwork;
import me.tahacheji.mafana.item.BankCardItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerJoin implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null && itemStack.getItemMeta() != null) {
                NBTItem nbtItem = new NBTItem(itemStack);
                if (nbtItem.hasTag("CARD_HOLDER")) {
                    UUID uuid = nbtItem.getUUID("CARD_HOLDER");
                    String cardNumber = nbtItem.getString("CARD_NUMBER");
                    String cVV = nbtItem.getString("CVV");
                    int usesLeft = nbtItem.getInteger("USES");
                    int finalI = i;
                    MafanaBankNetwork.getInstance().getBankDatabase().getBankCard(uuid).thenAcceptAsync(bankCard -> {
                        if(bankCard.getCardNumber().equalsIgnoreCase(cardNumber)) {
                            if(bankCard.getCardCVV().equalsIgnoreCase(cVV)) {
                                new BankCardItem(uuid, bankCard).getCard(usesLeft).thenAcceptAsync(newItem -> {
                                    Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> {
                                        inventory.setItem(finalI, newItem);
                                        player.updateInventory();
                                    });
                                });
                            }
                        }
                    });
                }
            }
        }
    }

}
