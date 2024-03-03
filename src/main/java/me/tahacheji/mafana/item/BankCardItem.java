package me.tahacheji.mafana.item;

import de.tr7zw.nbtapi.NBTItem;
import me.tahacheji.mafana.MafanaBankNetwork;
import me.tahacheji.mafana.MafanaNetwork;
import me.tahacheji.mafana.MafanaNetworkCommunicator;
import me.tahacheji.mafana.commandExecutor.paramter.impl.ChatColorProcessor;
import me.tahacheji.mafana.data.BankCard;
import me.tahacheji.mafana.display.BankSelectionDisplay;
import me.tahacheji.mafana.itemData.GameItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BankCardItem extends GameItem {

    private final UUID uuid;
    private final BankCard bankCard;

    public BankCardItem(UUID uuid, BankCard bankCard) {
        super("", Material.PAPER, true, "");
        this.uuid = uuid;
        this.bankCard = bankCard;
        MafanaNetwork.getInstance().getGameItems().add(this);
    }

    public CompletableFuture<ItemStack> getCard(int usesLeft) {
        return MafanaNetworkCommunicator.getInstance().getPlayerDatabase().getOfflineProxyPlayerAsync(uuid).thenApplyAsync(offlineProxyPlayer -> {
            ItemStack itemStack = super.getItem();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + offlineProxyPlayer.getPlayerName() + "'s Bank Card" + ChatColor.DARK_GRAY + "]");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GOLD + "Card Number: " + ChatColor.DARK_GRAY + bankCard.getCardNumber());
            lore.add(ChatColor.GOLD + "CVV: " + ChatColor.DARK_GRAY + bankCard.getCardCVV());
            lore.add(ChatColor.GOLD + "Loan Score: " + ChatColor.DARK_GRAY + bankCard.getLoanScore());
            lore.add(ChatColor.GOLD + "DOC: " + ChatColor.DARK_GRAY + bankCard.getDateOfCreation());
            lore.add(ChatColor.GOLD + "Card Holder: " + ChatColor.DARK_GRAY + offlineProxyPlayer.getPlayerName());
            lore.add("");
            lore.add(ChatColor.RED + "Uses: " + usesLeft);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            NBTItem nbtItem = new NBTItem(itemStack);
            nbtItem.setString("CARD_NUMBER", bankCard.getCardNumber());
            nbtItem.setString("CVV", bankCard.getCardCVV());
            nbtItem.setUUID("CARD_HOLDER", uuid);
            nbtItem.setInteger("USES", usesLeft);
            return nbtItem.getItem();
        });
    }


    @Override
    public boolean rightClickAirAction(Player var1, ItemStack var2) {
        NBTItem nbtItem = new NBTItem(var2);
        if(nbtItem.hasTag("CARD_HOLDER")) {
            String cardNumber = nbtItem.getString("CARD_NUMBER");
            String cVV = nbtItem.getString("CVV");
            MafanaBankNetwork.getInstance().getBankDatabase().getBankCard(nbtItem.getUUID("CARD_HOLDER")).thenAcceptAsync(b -> {
               if(cardNumber.equalsIgnoreCase(b.getCardNumber())) {
                   if(cVV.equalsIgnoreCase(b.getCardCVV())) {
                       MafanaBankNetwork.getInstance().getBankDatabase().getBank(nbtItem.getUUID("CARD_HOLDER")).thenAcceptAsync(bank -> {
                           new BankSelectionDisplay().getBankSelectionDisplay(bank, var1).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(var1)));
                       });
                   }
               }
            });
        }
        return true;
    }
}
