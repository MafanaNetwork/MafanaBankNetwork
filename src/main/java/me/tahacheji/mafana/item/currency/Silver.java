package me.tahacheji.mafana.item.currency;

import de.tr7zw.nbtapi.NBTItem;
import me.tahacheji.mafana.itemData.GameItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class Silver extends GameItem {

    public Silver() {
        super(ChatColor.DARK_GRAY + "[" + net.md_5.bungee.api.ChatColor.of(new Color(192, 192, 192)) + "Silver" + ChatColor.DARK_GRAY + "]", Material.IRON_INGOT, false, ChatColor.GOLD + "Currency Value: 10");
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = super.getItem();
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("VALUE", 10);
        nbtItem.setString("GameItemUUID", "SILVER");
        return nbtItem.getItem();
    }
}
