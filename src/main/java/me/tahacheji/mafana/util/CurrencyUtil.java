package me.tahacheji.mafana.util;

import de.tr7zw.nbtapi.NBTItem;
import me.tahacheji.mafana.item.currency.CurrencyType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyUtil {


    public int getTotalCurrencyInPlayersInventory(Player player) {
        int x = 0;
        for(ItemStack itemStack : player.getInventory()) {
            if(itemStack != null) {
                if(itemStack.getItemMeta() != null) {
                    NBTItem nbtItem = new NBTItem(itemStack);
                    if(nbtItem.hasTag("VALUE")) {
                        int v = nbtItem.getInteger("VALUE") * itemStack.getAmount();
                        x = x + v;
                    }
                }
            }
        }
        return x;
    }

    public void removeCurrencyFromInventory(Player player, int amount) {
        int remainingAmountToRemove = amount;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                if (itemStack.hasItemMeta()) {
                    NBTItem nbtItem = new NBTItem(itemStack);
                    if (nbtItem.hasKey("VALUE")) {
                        int value = nbtItem.getInteger("VALUE");
                        int currencyToRemoveFromStack = Math.min(value * itemStack.getAmount(), remainingAmountToRemove);
                        remainingAmountToRemove -= currencyToRemoveFromStack;
                        int newAmount = (value * itemStack.getAmount() - currencyToRemoveFromStack) / value;
                        if (newAmount <= 0) {
                            player.getInventory().remove(itemStack);
                        } else {
                            itemStack.setAmount(newAmount);
                        }
                        if (remainingAmountToRemove <= 0) {
                            break;
                        }
                    }
                }
            }
        }
    }


    public void addCurrency(Player player, int totalAmount) {
        Map<CurrencyType, Integer> currencyValues = new HashMap<>();
        currencyValues.put(CurrencyType.DIAMOND, 150);
        currencyValues.put(CurrencyType.GOLD, 50);
        currencyValues.put(CurrencyType.SILVER, 10);
        currencyValues.put(CurrencyType.COPPER, 1);

        List<CurrencyType> sortedTypes = new ArrayList<>(currencyValues.keySet());
        sortedTypes.sort((type1, type2) -> currencyValues.get(type2) - currencyValues.get(type1));

        Map<CurrencyType, Integer> currencyDistribution = new HashMap<>();

        for (CurrencyType type : sortedTypes) {
            int value = currencyValues.get(type);
            int count = totalAmount / value;
            if (count > 0) {
                currencyDistribution.put(type, count);
                totalAmount %= value;
            }
        }

        for (Map.Entry<CurrencyType, Integer> entry : currencyDistribution.entrySet()) {
            CurrencyType currencyType = entry.getKey();
            int amount = entry.getValue();
            if (amount > 0) {
                ItemStack currencyItem = currencyType.createItemStack(amount);
                player.getInventory().addItem(currencyItem);
            }
        }
    }


}
