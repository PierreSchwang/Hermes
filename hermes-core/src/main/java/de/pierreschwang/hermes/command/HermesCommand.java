package de.pierreschwang.hermes.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class HermesCommand extends Command {

    public HermesCommand(String command, String description, String permission, String... aliases) {
        super(command, description, "/" + command, Arrays.asList(aliases));
        setPermission(permission);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }

    public void onConsoleExecute() {
    }

    public void onPlayerExecute() {
    }

    public boolean hasPermission(CommandSender commandSender) {
        return testPermissionSilent(commandSender);
    }

}
