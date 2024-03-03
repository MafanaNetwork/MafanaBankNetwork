package me.tahacheji.mafana.display;

import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import me.tahacheji.mafana.MafanaBankNetwork;
import me.tahacheji.mafana.MafanaNetworkCommunicator;
import me.tahacheji.mafana.data.AccountType;
import me.tahacheji.mafana.data.Bank;
import me.tahacheji.mafana.data.BankTransaction;
import me.tahacheji.mafana.data.TransactionType;
import me.tahacheji.mafana.util.BankUtil;
import me.tahacheji.mafana.util.CurrencyUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BankAccountDisplay {


    public CompletableFuture<Gui> getCheckingAccountDisplay(UUID uuid, Player open) {
        return MafanaNetworkCommunicator.getInstance().getPlayerDatabase().getOfflineProxyPlayerAsync(uuid).thenApplyAsync(offlineProxyPlayer -> {
            Gui gui = Gui.gui()
                    .title(Component.text(ChatColor.GOLD + offlineProxyPlayer.getPlayerName() + "'s Checking Account"))
                    .rows(3)
                    .disableAllInteractions()
                    .create();
            CurrencyUtil currencyUtil = new CurrencyUtil();
            gui.getFiller().fill(ItemBuilder.from(Material.BROWN_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());

            gui.setItem(11, ItemBuilder.from(Material.GREEN_DYE).name(Component.text(ChatColor.GREEN + "Deposit")).asGuiItem(event -> {
                Gui deposit = Gui.gui()
                        .title(Component.text(ChatColor.GOLD + "MafanaBank Deposit Menu"))
                        .rows(3)
                        .disableAllInteractions()
                        .create();
                deposit.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
                deposit.setItem(4, ItemBuilder.from(Material.GREEN_DYE).name(Component.text(ChatColor.DARK_GREEN + "Deposit All")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    int c = currencyUtil.getTotalCurrencyInPlayersInventory(player);
                    if(c > 0) {
                        currencyUtil.removeCurrencyFromInventory((Player) e.getWhoClicked(), c);
                        MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                            bank.setCheckingBalance(bank.getCheckingBalance() + c);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), "" + c);
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        });
                    }

                }));

                deposit.setItem(10, ItemBuilder.from(Material.LIME_DYE).name(Component.text(ChatColor.GREEN + "Deposit 100")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    if(currencyUtil.getTotalCurrencyInPlayersInventory(player) >= 100) {
                        currencyUtil.removeCurrencyFromInventory(player, 100);
                        MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                            bank.setCheckingBalance(bank.getCheckingBalance() + 100);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), "100");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        });
                    }
                }));

                deposit.setItem(12, ItemBuilder.from(Material.LIME_DYE).name(Component.text(ChatColor.GREEN + "Deposit 500")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    if(currencyUtil.getTotalCurrencyInPlayersInventory(player) >= 500) {
                        currencyUtil.removeCurrencyFromInventory(player, 500);
                        MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                            bank.setCheckingBalance(bank.getCheckingBalance() + 500);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), "500");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        });
                    }
                }));

                deposit.setItem(14, ItemBuilder.from(Material.LIME_DYE).name(Component.text(ChatColor.GREEN + "Deposit 1000")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    if(currencyUtil.getTotalCurrencyInPlayersInventory(player) >= 1000) {
                        currencyUtil.removeCurrencyFromInventory(player, 1000);
                        MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                            bank.setCheckingBalance(bank.getCheckingBalance() + 1000);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), "1000");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        });
                    }
                }));

                deposit.setItem(16, ItemBuilder.from(Material.NAME_TAG).name(Component.text(ChatColor.GREEN + "Deposit Custom Amount")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    SignGUI.builder()
                            .setLines(null, "---------------", "Enter Amount", "MafanaBankNetwork") // set lines
                            .setType(Material.DARK_OAK_SIGN)
                            .setHandler((p, result) -> {
                                String x = result.getLineWithoutColor(0);
                                if (x.isEmpty() || currencyUtil.getTotalCurrencyInPlayersInventory(player) < Integer.parseInt(x)) {
                                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenApplyAsync(bank -> List.of(SignGUIAction.run(() -> getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open))))));
                                    }
                                return List.of(SignGUIAction.run(() -> {
                                    currencyUtil.removeCurrencyFromInventory(player, Integer.parseInt(x));
                                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                                        bank.setCheckingBalance(bank.getCheckingBalance() + Integer.parseInt(x));
                                        MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                            BankTransaction bankTransaction =
                                                    new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), x);
                                            MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                            player.closeInventory();
                                            getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                                        });
                                    });
                                }));
                            }).callHandlerSynchronously(MafanaBankNetwork.getInstance()).build().open(open);
                }));
                deposit.setItem(22, ItemBuilder.from(Material.BARRIER).setName(ChatColor.RED + "Back").asGuiItem(e -> {
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        new BankAccountDisplay().getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                    });
                    }));
                event.getWhoClicked().closeInventory();
                deposit.open(event.getWhoClicked());
            }));

            gui.setItem(15, ItemBuilder.from(Material.RED_DYE).name(Component.text(ChatColor.RED + "Withdraw")).asGuiItem(event -> {
                Gui withdraw = Gui.gui()
                        .title(Component.text(ChatColor.GOLD + "MafanaBank Withdraw Menu"))
                        .rows(3)
                        .disableAllInteractions()
                        .create();
                withdraw.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
                withdraw.setItem(4, ItemBuilder.from(Material.RED_DYE).name(Component.text(ChatColor.DARK_RED + "Withdraw All")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        int c = bank.getCheckingBalance();
                        if (c > 0) {
                            currencyUtil.addCurrency((Player) e.getWhoClicked(), c);
                            bank.setCheckingBalance(bank.getCheckingBalance() - c);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), "" + c);
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        }
                    });

                }));

                withdraw.setItem(10, ItemBuilder.from(Material.PINK_DYE).name(Component.text(ChatColor.RED + "Withdraw 100")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        if (bank.getCheckingBalance() >= 100) {
                            currencyUtil.addCurrency(player, 100);
                            bank.setCheckingBalance(bank.getCheckingBalance() - 100);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), "100");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        }
                    });
                }));

                withdraw.setItem(12, ItemBuilder.from(Material.PINK_DYE).name(Component.text(ChatColor.RED + "Withdraw 500")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        if (bank.getCheckingBalance() >= 500) {
                            currencyUtil.addCurrency(player, 500);
                            bank.setCheckingBalance(bank.getCheckingBalance() - 500);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), "500");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        }
                    });
                }));

                withdraw.setItem(14, ItemBuilder.from(Material.PINK_DYE).name(Component.text(ChatColor.RED + "Withdraw 1000")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        if (bank.getCheckingBalance() >= 1000) {
                            currencyUtil.addCurrency(player, 1000);
                            bank.setCheckingBalance(bank.getCheckingBalance() - 1000);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), "1000");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        }
                    });
                }));

                withdraw.setItem(16, ItemBuilder.from(Material.NAME_TAG).name(Component.text(ChatColor.RED + "Withdraw Custom Amount")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    SignGUI.builder()
                            .setLines(null, "---------------", "Enter Amount", "MafanaBankNetwork") // set lines
                            .setType(Material.DARK_OAK_SIGN)
                            .setHandler((p, result) -> {
                                String x = result.getLineWithoutColor(0);
                                MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenApplyAsync(bank -> {
                                    if (x.isEmpty() || bank.getCheckingBalance() < Integer.parseInt(x)) {
                                        return List.of(SignGUIAction.run(() -> getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)))));
                                    }
                                    return null;
                                });
                                return List.of(SignGUIAction.run(() -> {
                                    currencyUtil.addCurrency(player, Integer.parseInt(x));
                                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                                        bank.setCheckingBalance(bank.getCheckingBalance() - Integer.parseInt(x));
                                        MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                            BankTransaction bankTransaction =
                                                    new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getCheckingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.CHECKING.getLore(), new BankUtil().getDate(), x);
                                            MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                            player.closeInventory();
                                            getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                                        });
                                    });
                                }));
                            }).callHandlerSynchronously(MafanaBankNetwork.getInstance()).build().open(open);
                }));
                withdraw.setItem(22, ItemBuilder.from(Material.BARRIER).setName(ChatColor.RED + "Back").asGuiItem(e -> {
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        new BankAccountDisplay().getCheckingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                    });
                    }));
                event.getWhoClicked().closeInventory();
                withdraw.open(event.getWhoClicked());
            }));

            gui.setItem(22, ItemBuilder.from(Material.BARRIER).setName(ChatColor.RED + "Back").asGuiItem(event -> {
                MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                    new BankSelectionDisplay().getBankSelectionDisplay(bank, open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                });
                }));
            return gui;
        });
    }


    public CompletableFuture<Gui> getSavingAccountDisplay(UUID uuid, Player open) {
        return MafanaNetworkCommunicator.getInstance().getPlayerDatabase().getOfflineProxyPlayerAsync(uuid).thenApplyAsync(offlineProxyPlayer -> {
            Gui gui = Gui.gui()
                    .title(Component.text(ChatColor.GOLD + offlineProxyPlayer.getPlayerName() + "'s Savings Account"))
                    .rows(3)
                    .disableAllInteractions()
                    .create();
            CurrencyUtil currencyUtil = new CurrencyUtil();
            gui.getFiller().fill(ItemBuilder.from(Material.BLUE_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());

            gui.setItem(11, ItemBuilder.from(Material.GREEN_DYE).name(Component.text(ChatColor.GREEN + "Deposit")).asGuiItem(event -> {
                Gui deposit = Gui.gui()
                        .title(Component.text(ChatColor.GOLD + "MafanaBank Deposit Menu"))
                        .rows(3)
                        .disableAllInteractions()
                        .create();
                deposit.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
                deposit.setItem(4, ItemBuilder.from(Material.GREEN_DYE).name(Component.text(ChatColor.DARK_GREEN + "Deposit All")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    int c = currencyUtil.getTotalCurrencyInPlayersInventory(player);
                    if(c > 0) {
                        currencyUtil.removeCurrencyFromInventory((Player) e.getWhoClicked(), c);
                        MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                            bank.setSavingBalance(bank.getCheckingBalance() + c);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), "" + c);
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        });
                    }

                }));

                deposit.setItem(10, ItemBuilder.from(Material.LIME_DYE).name(Component.text(ChatColor.GREEN + "Deposit 100")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    if(currencyUtil.getTotalCurrencyInPlayersInventory(player) >= 100) {
                        currencyUtil.removeCurrencyFromInventory(player, 100);
                        MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        bank.setSavingBalance(bank.getCheckingBalance() + 100);
                        MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                            BankTransaction bankTransaction =
                                    new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), "100");
                            MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                            player.closeInventory();
                            getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                        });
                        });
                    }
                }));

                deposit.setItem(12, ItemBuilder.from(Material.LIME_DYE).name(Component.text(ChatColor.GREEN + "Deposit 500")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    if(currencyUtil.getTotalCurrencyInPlayersInventory(player) >= 500) {
                        currencyUtil.removeCurrencyFromInventory(player, 500);
                        MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                            bank.setSavingBalance(bank.getCheckingBalance() + 500);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), "500");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        });
                    }
                }));

                deposit.setItem(14, ItemBuilder.from(Material.LIME_DYE).name(Component.text(ChatColor.GREEN + "Deposit 1000")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    if(currencyUtil.getTotalCurrencyInPlayersInventory(player) >= 1000) {
                        currencyUtil.removeCurrencyFromInventory(player, 1000);
                        MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                            bank.setSavingBalance(bank.getCheckingBalance() + 1000);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), "1000");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        });
                    }
                }));

                deposit.setItem(16, ItemBuilder.from(Material.NAME_TAG).name(Component.text(ChatColor.GREEN + "Deposit Custom Amount")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    SignGUI.builder()
                            .setLines(null, "---------------", "Enter Amount", "MafanaBankNetwork") // set lines
                            .setType(Material.DARK_OAK_SIGN)
                            .setHandler((p, result) -> {
                                String x = result.getLineWithoutColor(0);

                                if (x.isEmpty() || currencyUtil.getTotalCurrencyInPlayersInventory(player) < Integer.parseInt(x)) {
                                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenApplyAsync(bank -> {
                                        return List.of(SignGUIAction.run(() -> getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)))));
                                    });
                                    }
                                return List.of(SignGUIAction.run(() -> {
                                    currencyUtil.removeCurrencyFromInventory(player, Integer.parseInt(x));
                                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                                        bank.setSavingBalance(bank.getCheckingBalance() + Integer.parseInt(x));
                                        MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                            BankTransaction bankTransaction =
                                                    new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.DEPOSIT.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), x);
                                            MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                            player.closeInventory();
                                            getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                                        });
                                    });
                                }));
                            }).callHandlerSynchronously(MafanaBankNetwork.getInstance()).build().open(open);
                }));
                deposit.setItem(22, ItemBuilder.from(Material.BARRIER).setName(ChatColor.RED + "Back").asGuiItem(e -> {
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        new BankAccountDisplay().getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                    });
                    }));
                event.getWhoClicked().closeInventory();
                deposit.open(event.getWhoClicked());
            }));

            gui.setItem(15, ItemBuilder.from(Material.RED_DYE).name(Component.text(ChatColor.RED + "Withdraw")).asGuiItem(event -> {
                Gui withdraw = Gui.gui()
                        .title(Component.text(ChatColor.GOLD + "MafanaBank Withdraw Menu"))
                        .rows(3)
                        .disableAllInteractions()
                        .create();
                withdraw.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").setLore(" ").asGuiItem());
                withdraw.setItem(4, ItemBuilder.from(Material.RED_DYE).name(Component.text(ChatColor.DARK_RED + "Withdraw All")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        int c = bank.getSavingBalance();
                        if (c > 0) {
                            currencyUtil.addCurrency((Player) e.getWhoClicked(), c);
                            bank.setSavingBalance(bank.getSavingBalance() - c);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), "" + c);
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        }
                    });

                }));

                withdraw.setItem(10, ItemBuilder.from(Material.PINK_DYE).name(Component.text(ChatColor.RED + "Withdraw 100")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        if (bank.getSavingBalance() >= 100) {
                            currencyUtil.addCurrency(player, 100);
                            bank.setSavingBalance(bank.getCheckingBalance() - 100);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), "100");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        }
                    });
                }));

                withdraw.setItem(12, ItemBuilder.from(Material.PINK_DYE).name(Component.text(ChatColor.RED + "Withdraw 500")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        if (bank.getSavingBalance() >= 500) {
                            currencyUtil.addCurrency(player, 500);
                            bank.setSavingBalance(bank.getCheckingBalance() - 500);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), "500");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        }
                    });
                }));

                withdraw.setItem(14, ItemBuilder.from(Material.PINK_DYE).name(Component.text(ChatColor.RED + "Withdraw 1000")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        if (bank.getSavingBalance() >= 1000) {
                            currencyUtil.addCurrency(player, 1000);
                            bank.setSavingBalance(bank.getCheckingBalance() - 1000);
                            MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                BankTransaction bankTransaction =
                                        new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), TransactionType.WITHDRAW.getLore(), AccountType.SAVINGS.getLore(), new BankUtil().getDate(), "1000");
                                MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                player.closeInventory();
                                getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                            });
                        }
                    });
                }));

                withdraw.setItem(16, ItemBuilder.from(Material.NAME_TAG).name(Component.text(ChatColor.RED + "Withdraw Custom Amount")).asGuiItem(e -> {
                    Player player = (Player) e.getWhoClicked();
                    SignGUI.builder()
                            .setLines(null, "---------------", "Enter Amount", "MafanaBankNetwork") // set lines
                            .setType(Material.DARK_OAK_SIGN)
                            .setHandler((p, result) -> {
                                String x = result.getLineWithoutColor(0);
                                MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenApplyAsync(bank -> {
                                    if (x.isEmpty() || bank.getSavingBalance() < Integer.parseInt(x)) {
                                        return List.of(SignGUIAction.run(() -> getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)))));
                                    }
                                    return null;
                                });
                                return List.of(SignGUIAction.run(() -> {
                                    currencyUtil.addCurrency(player, Integer.parseInt(x));
                                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                                        bank.setSavingBalance(bank.getCheckingBalance() - Integer.parseInt(x));
                                        MafanaBankNetwork.getInstance().getBankDatabase().setBank(bank.getUuid(), bank).thenRunAsync(() -> {
                                            BankTransaction bankTransaction =
                                                    new BankTransaction(player.getUniqueId().toString(), UUID.randomUUID().toString(), bank.getSavingRoutingNumber(), AccountType.SAVINGS.getLore(), TransactionType.WITHDRAW.getLore(), new BankUtil().getDate(), x);
                                            MafanaBankNetwork.getInstance().getBankDatabase().addBankTransaction(bank.getUuid(), bankTransaction);
                                            player.closeInventory();
                                            getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                                        });
                                    });
                                }));
                            }).callHandlerSynchronously(MafanaBankNetwork.getInstance()).build().open(open);
                }));
                withdraw.setItem(22, ItemBuilder.from(Material.BARRIER).setName(ChatColor.RED + "Back").asGuiItem(e -> {
                    MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                        new BankAccountDisplay().getSavingAccountDisplay(bank.getUuid(), open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                    });
                    }));
                event.getWhoClicked().closeInventory();
                withdraw.open(event.getWhoClicked());
            }));

            gui.setItem(22, ItemBuilder.from(Material.BARRIER).setName(ChatColor.RED + "Back").asGuiItem(event -> {
                MafanaBankNetwork.getInstance().getBankDatabase().getBank(uuid).thenAcceptAsync(bank -> {
                    new BankSelectionDisplay().getBankSelectionDisplay(bank, open).thenAcceptAsync(g -> Bukkit.getScheduler().runTask(MafanaBankNetwork.getInstance(), () -> g.open(open)));
                });
                }));
            return gui;
        });
    }


}
