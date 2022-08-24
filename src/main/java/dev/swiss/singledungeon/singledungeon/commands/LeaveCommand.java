package dev.swiss.singledungeon.singledungeon.commands;

import dev.swiss.singledungeon.singledungeon.SingleDungeon;
import dev.swiss.singledungeon.singledungeon.playersession.PlayerSession;
import dev.swiss.singledungeon.singledungeon.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SingleDungeon plugin = SingleDungeon.getInstance();

        Player player = (Player) sender;

        if(!plugin.getPlayerSessionHandler().getPlayerSessions().containsKey(player.getUniqueId())) {
            new Message("You don't have a dungeon session!").send(player);
            return true;
        }

        PlayerSession playerSession = plugin.getPlayerSessionHandler().getPlayerSession(player.getUniqueId());

        playerSession.setDead(true);

        plugin.getPlayerSessionHandler().endSession(playerSession);


        return true;
    }

}
