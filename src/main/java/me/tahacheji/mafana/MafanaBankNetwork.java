package me.tahacheji.mafana;

import me.tahacheji.mafana.command.BankCommand;
import me.tahacheji.mafana.command.BankCommandAdmin;
import me.tahacheji.mafana.command.BankTransfer;
import me.tahacheji.mafana.commandExecutor.CommandHandler;
import me.tahacheji.mafana.data.BankDatabase;
import me.tahacheji.mafana.data.BankTransaction;
import me.tahacheji.mafana.event.PlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MafanaBankNetwork extends JavaPlugin {

    private static MafanaBankNetwork instance;
    private BankDatabase bankDatabase;

    @Override
    public void onEnable() {
        instance = this;
        bankDatabase = new BankDatabase();
        bankDatabase.connect();
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        CommandHandler.registerCommands(BankCommand.class, this);
        CommandHandler.registerCommands(BankCommandAdmin.class, this);
        CommandHandler.registerProcessors("me.tahacheji.mafana.processor", this);
        CommandHandler.registerCommands(BankTransfer.class, this);
    }

    @Override
    public void onDisable() {
        bankDatabase.close();
    }

    public BankDatabase getBankDatabase() {
        return bankDatabase;
    }

    public static MafanaBankNetwork getInstance() {
        return instance;
    }
}
