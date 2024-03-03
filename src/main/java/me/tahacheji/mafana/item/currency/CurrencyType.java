package me.tahacheji.mafana.item.currency;

import org.bukkit.inventory.ItemStack;

public enum CurrencyType {
    SILVER,
    GOLD,
    DIAMOND,
    COPPER;

    public ItemStack createItemStack(int amount) {
        ItemStack currencyItem = null;
        switch (this) {
            case SILVER:
                currencyItem = new Silver().getItem();
                break;
            case GOLD:
                currencyItem = new Gold().getItem();
                break;
            case DIAMOND:
                currencyItem = new Diamond().getItem();
                break;
            case COPPER:
                currencyItem = new Copper().getItem();
                break;
        }
        currencyItem.setAmount(amount);
        return currencyItem;
    }

}
