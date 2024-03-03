package me.tahacheji.mafana.item;

import de.tr7zw.nbtapi.NBTItem;
import me.tahacheji.mafana.itemData.GameItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class Diamond extends GameItem {

    public Diamond() {
        super(ChatColor.DARK_GRAY + "[" + net.md_5.bungee.api.ChatColor.of(new Color(185, 242, 255)) + "Diamond" + ChatColor.DARK_GRAY + "]", Material.DIAMOND, true, ChatColor.GOLD + "Currency Value: 150");
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = super.getItem();
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("VALUE", 150);
        nbtItem.setString("GameItemUUID", "DIAMOND");
        return nbtItem.getItem();
    }
}
