package me.tahacheji.mafana.processor;

import me.tahacheji.mafana.commandExecutor.paramter.Processor;
import me.tahacheji.mafana.data.TransactionType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionTypeProcessor extends Processor<TransactionType> {

    public TransactionType process(CommandSender sender, String supplied) {
        TransactionType transactionType = TransactionType.valueOf(supplied.toUpperCase());

        if (transactionType == null) {
            sender.sendMessage(ChatColor.RED + "A Transaction Type by the name of '" + supplied + "' cannot be located.");
            return null;
        }

        return transactionType;
    }

    public List<String> tabComplete(CommandSender sender, String supplied) {
        return TransactionType.getAllTransactionType()
                .stream()
                .map(TransactionType::name)
                .filter(name -> name.toLowerCase().startsWith(supplied.toLowerCase()))
                .limit(100)
                .collect(Collectors.toList());
    }
}
