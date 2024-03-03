package me.tahacheji.mafana.item;

import de.tr7zw.nbtapi.NBTItem;
import me.tahacheji.mafana.itemData.GameItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class Gold extends GameItem {

    public Gold() {
        super(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Gold" + ChatColor.DARK_GRAY + "]", Material.GOLD_INGOT, false, ChatColor.GOLD + "Currency Value: 50");
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = super.getItem();
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("VALUE", 50);
        nbtItem.setString("GameItemUUID", "GOLD");
        return nbtItem.getItem();
    }
}
