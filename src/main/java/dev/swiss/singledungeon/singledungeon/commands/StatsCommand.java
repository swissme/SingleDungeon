package dev.swiss.singledungeon.singledungeon.commands;

import dev.swiss.singledungeon.singledungeon.menus.StatsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return true;

        Player player = (Player) sender;
        StatsMenu menu = new StatsMenu();
        menu.render(player);
        return true;
    }

}
