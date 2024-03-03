package me.tahacheji.mafana.display;

import de.rapha149.signgui.SignGUI;
import de.tr7zw.nbtapi.NBTItem;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.tahacheji.mafana.MafanaBankNetwork;
import me.tahacheji.mafana.MafanaNetworkCommunicator;
import me.tahacheji.mafana.data.AccountType;
import me.tahacheji.mafana.data.Bank;
import me.tahacheji.mafana.data.BankCard;
import me.tahacheji.mafana.data.TransactionType;
import me.tahacheji.mafana.event.PlayerJoin;
import me.tahacheji.mafana.item.BankCardItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BankSelectionDisplay {


    public CompletableFuture<Gui> getBankSelectionDisplay(Bank bank, Player open) {
        return MafanaNetworkCommunicator.getInstance().getPlayerDatabase().getOfflineProxyPlayerAsync(bank.getUuid()).thenApplyAsync(offlineProxyPlayer -> {
            Gui gui = Gui.gui()
                    .title(Component.text(ChatColor.GOLD + offlineProxyPlayer.getPlayerName() + "'s Bank Account"))
                    .rows(3)
                    .disableAllInteractions()
                    .create();
            gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(11, ItemBuilder.from(Material.IRON_INGOT).setName(ChatColor.GRAY + "Checkings Account").setLore(ChatColor.GOLD + "Balance: " + ChatColor.GRAY + bank.getCheckingBalance()).asGuiItem(event -> {
                new BankAccountDisplay().getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
            }));
            gui.setItem(13, ItemBuilder.from(Material.PAPER).setName(ChatColor.AQUA + "Bank Loan").setLore(ChatColor.GRAY + "Click click to view loans").asGuiItem(event -> {
                //bank loan
            }));

            gui.setItem(4, ItemBuilder.from(Material.NAME_TAG).setName(ChatColor.AQUA + "Bank Transactions").setLore(ChatColor.GRAY + "Click to view transactions").asGuiItem(event -> {
                MafanaBankNetwork.getInstance().getBankDatabase().getBankTransactions(bank.getUuid()).thenAcceptAsync(bankTransactions -> {
                    new BankTransactionDisplay().getBankTransactionDisplay(bank, bankTransactions, TransactionType.ALL, AccountType.ALL, "", true, false, open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                });
            }));

            if (open.hasPermission("mafana.admin") || open.getUniqueId().equals(bank.getUuid())) {
                gui.setItem(15, ItemBuilder.from(Material.DIAMOND).setName(ChatColor.GRAY + "Savings Account").setLore(ChatColor.GOLD + "Balance: " + ChatColor.GRAY + bank.getSavingBalance()).asGuiItem(event -> {
                    new BankAccountDisplay().getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                }));
            } else {
                gui.setItem(15, ItemBuilder.from(Material.DIAMOND).setName(ChatColor.GRAY + "Savings Account").setLore(ChatColor.GOLD + "Balance: " + ChatColor.GRAY + "#########").asGuiItem(event -> {
                    event.getWhoClicked().closeInventory();
                    SignGUI.builder()
                            .setLines(null, "PIN", "", "MafanaBankNetwork")
                            .setType(Material.OAK_SIGN)
                            .setHandler((p, result) -> {
                                String pin = result.getLineWithoutColor(0);
                                open.closeInventory();
                                if (bank.getPin().equalsIgnoreCase(pin)) {
                                    new BankAccountDisplay().getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                                } else {
                                    Player player = (Player) event.getWhoClicked();
                                    ItemStack itemStack = player.getItemInHand();
                                    NBTItem nbtItem = new NBTItem(itemStack);
                                    if (nbtItem.hasTag("USES")) {
                                        int u = nbtItem.getInteger("USES");
                                        if (u > 0) {
                                            MafanaBankNetwork.getInstance().getBankDatabase().getBankCard(bank.getUuid()).thenAcceptAsync(bankCard -> {
                                                new BankCardItem(bank.getUuid(), bankCard).getCard(u - 1).thenAcceptAsync(i -> {
                                                    Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> {
                                                        player.closeInventory();
                                                        player.setItemInHand(i);
                                                    });
                                                });
                                            });
                                        }
                                    }
                                }
                                return null;
                            }).callHandlerSynchronously(MafanaBankNetwork.getInstance()).build().open(open);
                }));
            }
            gui.setItem(22, ItemBuilder.from(Material.BARRIER).setName(ChatColor.RED + "Close").asGuiItem(event -> {
                event.getWhoClicked().closeInventory();
            }));
            return gui;
        });
    }



}
