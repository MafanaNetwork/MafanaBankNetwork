package me.tahacheji.mafana.item;

import de.tr7zw.nbtapi.NBTItem;
import me.tahacheji.mafana.itemData.GameItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;

public class Copper extends GameItem {

    public Copper() {
        super(ChatColor.DARK_GRAY + "[" + net.md_5.bungee.api.ChatColor.of(new Color(184, 115, 51)) + "Copper" + ChatColor.DARK_GRAY + "]", Material.COPPER_INGOT, false, ChatColor.GOLD + "Currency Value: 1");
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = super.getItem();
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("VALUE", 1);
        nbtItem.setString("GameItemUUID", "COPPER");
        return nbtItem.getItem();
    }
}
