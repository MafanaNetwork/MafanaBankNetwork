package me.tahacheji.mafana.display;

import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import de.tr7zw.nbtapi.NBTItem;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.tahacheji.mafana.MafanaBankNetwork;
import me.tahacheji.mafana.MafanaNetworkCommunicator;
import me.tahacheji.mafana.data.AccountType;
import me.tahacheji.mafana.data.Bank;
import me.tahacheji.mafana.data.BankTransaction;
import me.tahacheji.mafana.data.TransactionType;
import me.tahacheji.mafana.item.BankCardItem;
import me.tahacheji.mafana.util.BankUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BankTransactionDisplay {


    public CompletableFuture<PaginatedGui> getBankTransactionDisplay(Bank bank, List<BankTransaction> bankTransaction, TransactionType transactionType, AccountType accountType, String playerFilter, boolean sortNewestToOldest, boolean showRoutingNumber, Player open) {
        CompletableFuture<PaginatedGui> guiCompletableFuture = new CompletableFuture<>();
        MafanaNetworkCommunicator.getInstance().getPlayerDatabase().getOfflineProxyPlayerAsync(bank.getUuid()).thenAcceptAsync(offlineProxyPlayer -> {
            PaginatedGui gui = Gui.paginated()
                    .title(Component.text(ChatColor.GOLD + offlineProxyPlayer.getPlayerName() + "'s Bank Transactions"))
                    .rows(6)
                    .pageSize(28)
                    .disableAllInteractions()
                    .create();

            gui.setItem(0, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(1, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(2, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(3, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(4, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(5, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(6, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(7, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(8, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(17, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(26, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(35, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(45, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(53, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(52, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(51, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(50, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(48, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(47, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(46, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(44, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(36, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(27, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(18, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(9, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
            gui.setItem(6, 3, ItemBuilder.from(Material.PAPER).setName(ChatColor.DARK_GRAY + "Previous")
                    .asGuiItem(event -> gui.previous()));
            gui.setItem(6, 7, ItemBuilder.from(Material.PAPER).setName(ChatColor.DARK_GRAY + "Next")
                    .asGuiItem(event -> gui.next()));
            gui.setItem(49, ItemBuilder.from(Material.BARRIER).setName(ChatColor.RED + "Back").setLore(ChatColor.DARK_RED + "Click to go back").asGuiItem(event -> {
                if (event.getClick() == ClickType.RIGHT) {
                    new BankSelectionDisplay().getBankSelectionDisplay(bank, open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                }
            }));

            ItemStack playerFilterButton = new ItemStack(Material.WITHER_SKELETON_SKULL);
            ItemMeta playerFilterButtonMeta = playerFilterButton.getItemMeta();
            playerFilterButtonMeta.setDisplayName(ChatColor.YELLOW + "Player Filter: " + playerFilter);
            playerFilterButton.setItemMeta(playerFilterButtonMeta);

            ItemStack sortButton = new ItemStack(Material.COMPARATOR);
            ItemMeta sortButtonMeta = sortButton.getItemMeta();
            sortButtonMeta.setDisplayName(ChatColor.YELLOW + "Sort: " + (sortNewestToOldest ? "Newest to Oldest" : "Oldest to Newest"));
            sortButton.setItemMeta(sortButtonMeta);

            ItemStack nextAccountTypeButton = new ItemStack(Material.CHEST);
            ItemMeta nextAccountTypeButtonMeta = nextAccountTypeButton.getItemMeta();
            nextAccountTypeButtonMeta.setDisplayName(ChatColor.YELLOW + "Next Account Type");
            nextAccountTypeButtonMeta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Current Type: " + accountType.getLore(),
                    ChatColor.GRAY + "Next Type: " + AccountType.getNextAccountType(accountType).getLore()
            ));
            nextAccountTypeButton.setItemMeta(nextAccountTypeButtonMeta);

            ItemStack nextTransactionTypeButton = new ItemStack(Material.PAPER);
            ItemMeta nextTransactionTypeButtonMeta = nextTransactionTypeButton.getItemMeta();
            nextTransactionTypeButtonMeta.setDisplayName(ChatColor.YELLOW + "Next Transaction Type");
            nextTransactionTypeButtonMeta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Current Type: " + transactionType.getLore(),
                    ChatColor.GRAY + "Next Type: " + TransactionType.getNextTransactionType(transactionType).getLore()
            ));
            nextTransactionTypeButton.setItemMeta(nextTransactionTypeButtonMeta);

            gui.setItem(48, new GuiItem(playerFilterButton, event -> {
                SignGUI.builder()
                        .setLines(null, "---------------", "Enter Name", "MafanaBankNetwork")
                        .setType(Material.DARK_OAK_SIGN)
                        .setHandler((p, result) -> {
                            String x = result.getLineWithoutColor(0);
                            if (x.isEmpty()) {
                                return List.of(SignGUIAction.run(() -> getBankTransactionDisplay(bank, bankTransaction, transactionType, accountType, playerFilter, sortNewestToOldest, showRoutingNumber, open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)))));
                            }
                            return List.of(SignGUIAction.run(() -> {
                                getBankTransactionDisplay(bank, bankTransaction, transactionType, accountType, x, sortNewestToOldest, showRoutingNumber, open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            }));
                        }).callHandlerSynchronously(MafanaBankNetwork.getInstance()).build().open(open);
            }));
            gui.setItem(49, new GuiItem(sortButton, event -> {
                boolean newSortNewestToOldest = !sortNewestToOldest;
                CompletableFuture<PaginatedGui> updatedGuiFuture = getBankTransactionDisplay(bank, bankTransaction, transactionType, accountType, playerFilter, newSortNewestToOldest, showRoutingNumber, open);
                updatedGuiFuture.thenAcceptAsync(updatedGui -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> updatedGui.open(open)));
            }));
            gui.setItem(50, new GuiItem(nextAccountTypeButton, event -> {
                AccountType nextAccountType = AccountType.getNextAccountType(accountType);
                CompletableFuture<PaginatedGui> updatedGuiFuture = getBankTransactionDisplay(bank, bankTransaction, transactionType, nextAccountType, playerFilter, sortNewestToOldest, showRoutingNumber, open);
                updatedGuiFuture.thenAcceptAsync(updatedGui -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> updatedGui.open(open)));
            }));
            gui.setItem(52, new GuiItem(nextTransactionTypeButton, event -> {
                TransactionType nextTransactionType = TransactionType.getNextTransactionType(transactionType);
                CompletableFuture<PaginatedGui> updatedGuiFuture = getBankTransactionDisplay(bank, bankTransaction, nextTransactionType, accountType, playerFilter, sortNewestToOldest, showRoutingNumber, open);
                updatedGuiFuture.thenAcceptAsync(updatedGui -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> updatedGui.open(open)));
            }));
            gui.setItem(53, ItemBuilder.from(Material.IRON_INGOT).setName(ChatColor.RED + "Show Routing Number").setLore(ChatColor.DARK_RED + "PIN REQUIRED").asGuiItem(event -> {
                boolean show = !showRoutingNumber;
                if(open.hasPermission("mafana.admin") || show) {
                    SignGUI.builder()
                            .setLines(null, "PIN", "", "MafanaBankNetwork")
                            .setType(Material.OAK_SIGN)
                            .setHandler((p, result) -> {
                                String pin = result.getLineWithoutColor(0);
                                open.closeInventory();
                                if (bank.getPin().equalsIgnoreCase(pin)) {
                                    CompletableFuture<PaginatedGui> updatedGuiFuture = getBankTransactionDisplay(bank, bankTransaction, transactionType, accountType, playerFilter, sortNewestToOldest, show, open);
                                    updatedGuiFuture.thenAcceptAsync(updatedGui -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> updatedGui.open(open))); }
                                else {
                                    open.closeInventory();
                                }
                                return null;
                            }).callHandlerSynchronously(MafanaBankNetwork.getInstance()).build().open(open);
                } else {
                    CompletableFuture<PaginatedGui> updatedGuiFuture = getBankTransactionDisplay(bank, bankTransaction, transactionType, accountType, playerFilter, sortNewestToOldest, show, open);
                    updatedGuiFuture.thenAcceptAsync(updatedGui -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> updatedGui.open(open)));
                }
            }));

            List<BankTransaction> filtered = new ArrayList<>();
            CompletableFuture.allOf(
                            bankTransaction.stream().map(bt ->
                                    MafanaNetworkCommunicator.getInstance().getPlayerDatabase().getOfflineProxyPlayerAsync(bt.getUuid())
                                            .thenAcceptAsync(offlinePlayer -> {
                                                if ((accountType == AccountType.ALL || bt.getAccountType().equalsIgnoreCase(accountType.getLore()))
                                                        && (transactionType == TransactionType.ALL || bt.getTransactionType().equalsIgnoreCase(transactionType.getLore()))
                                                        && (playerFilter.isEmpty() || offlinePlayer.getPlayerName().equalsIgnoreCase(playerFilter))) {
                                                    filtered.add(bt);
                                                }
                                            })
                            ).toArray(CompletableFuture[]::new))
                    .thenRun(() -> {
                        List<GuiItem> guiItems = new ArrayList<>();
                        CompletableFuture<Void> guiItem = CompletableFuture.allOf(filtered.stream().map(bt -> getItemStackBT(bt, showRoutingNumber).thenAcceptAsync(itemStack -> guiItems.add(new GuiItem(itemStack)))).toArray(CompletableFuture[]::new));
                        guiItem.thenRun(() -> {
                            if (sortNewestToOldest) {
                                guiItems.sort((item1, item2) -> {
                                    NBTItem nbtItem1 = new NBTItem(item1.getItemStack());
                                    NBTItem nbtItem2 = new NBTItem(item2.getItemStack());
                                    int time1 = nbtItem1.getInteger("TIME");
                                    int time2 = nbtItem2.getInteger("TIME");
                                    return Integer.compare(time2, time1);
                                });
                            } else {
                                guiItems.sort((item1, item2) -> {
                                    NBTItem nbtItem1 = new NBTItem(item1.getItemStack());
                                    NBTItem nbtItem2 = new NBTItem(item2.getItemStack());
                                    int time1 = nbtItem1.getInteger("TIME");
                                    int time2 = nbtItem2.getInteger("TIME");
                                    return Integer.compare(time1, time2);
                                });
                            }
                            for (GuiItem g : guiItems) {
                                gui.addItem(g);
                            }
                            gui.update();
                            guiCompletableFuture.complete(gui);
                        });
                    });
        });
        return guiCompletableFuture;
    }

    public CompletableFuture<ItemStack> getItemStackBT(BankTransaction bankTransaction, boolean show) {
        return MafanaNetworkCommunicator.getInstance().getPlayerDatabase().getOfflineProxyPlayerAsync(bankTransaction.getUuid()).thenApplyAsync(offlineProxyPlayer -> {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(net.md_5.bungee.api.ChatColor.of(new Color(245, 245, 220)) + offlineProxyPlayer.getPlayerName() + " " + bankTransaction.getDate());
            List<String> itemLore = new ArrayList<>();
            itemLore.add("------------------------");
            itemLore.add(ChatColor.DARK_GRAY + "User: " + offlineProxyPlayer.getPlayerName());
            itemLore.add(ChatColor.DARK_GRAY + "");
            itemLore.add(ChatColor.DARK_GRAY + "Transaction Type: " + bankTransaction.getTransactionType());
            if(show) {
                itemLore.add(ChatColor.DARK_GRAY + "Account Type: " + bankTransaction.getAccountType());
            } else {
                itemLore.add(ChatColor.DARK_GRAY + "Account Type: " + "###############");
            }
            if(show) {
                itemLore.add(ChatColor.DARK_GRAY + "Routing Number: " + bankTransaction.getRoutingNumber());
            } else {
                itemLore.add(ChatColor.DARK_GRAY + "Routing Number: " + "###############");
                }
            itemLore.add(ChatColor.DARK_GRAY + "Amount: " + bankTransaction.getAmount());
            itemLore.add(ChatColor.DARK_GRAY + "Date: " + bankTransaction.getDate());
            itemLore.add(ChatColor.DARK_GRAY + "Transaction UUID: " + bankTransaction.getTransactionUUID());
            itemLore.add("------------------------");
            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
            NBTItem nbtItem = new NBTItem(item);
            nbtItem.setInteger("TIME", new BankUtil().getTime(bankTransaction.getDate()));
            item = nbtItem.getItem();
            return item;
        });
    }


}
