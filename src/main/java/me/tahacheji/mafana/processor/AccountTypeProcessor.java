package me.tahacheji.mafana.processor;

import me.tahacheji.mafana.commandExecutor.paramter.Processor;
import me.tahacheji.mafana.data.AccountType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class AccountTypeProcessor extends Processor<AccountType> {

    public AccountType process(CommandSender sender, String supplied) {
        try {
            return AccountType.valueOf(supplied.toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "An Account Type by the name of '" + supplied + "' cannot be located.");
            return null;
        }
    }

    public List<String> tabComplete(CommandSender sender, String supplied) {
        return AccountType.getAllAccountType()
                .stream()
                .map(AccountType::name)
                .filter(name -> name.toLowerCase().startsWith(supplied.toLowerCase()))
                .limit(100)
                .collect(Collectors.toList());
    }
}

