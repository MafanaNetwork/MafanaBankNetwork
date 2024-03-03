package me.tahacheji.mafana.item;

import org.bukkit.inventory.ItemStack;

public enum CurrencyType {
    SILVER,
    GOLD,
    DIAMOND,
    COPPER;

    public ItemStack createItemStack(CurrencyType currencyType, int amount) {
        ItemStack currencyItem = null;
        switch (currencyType) {
            case SILVER:
                ItemStack s = new Silver().getItem();
                s.setAmount(amount);
                currencyItem = s;
                break;
            case GOLD:
                ItemStack g = new Gold().getItem();
                g.setAmount(amount);
                currencyItem = g;
                break;
            case DIAMOND:
                ItemStack d = new Diamond().getItem();
                d.setAmount(amount);
                currencyItem = d;
                break;
            case COPPER:
                ItemStack c = new Copper().getItem();
                c.setAmount(amount);
                currencyItem = c;
                break;
        }
        return currencyItem;
    }
}
