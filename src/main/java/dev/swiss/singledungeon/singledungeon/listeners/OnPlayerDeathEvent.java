package dev.swiss.singledungeon.singledungeon.listeners;

import dev.swiss.singledungeon.singledungeon.SingleDungeon;
import dev.swiss.singledungeon.singledungeon.playersession.PlayerSession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        SingleDungeon plugin = SingleDungeon.getInstance();

        Player player = event.getEntity();

        if(!plugin.getPlayerSessionHandler().getPlayerSessions().containsKey(player.getUniqueId()))
            return;

        event.getDrops().clear();

        player.getInventory().clear();

        PlayerSession playerSession = plugin.getPlayerSessionHandler().getPlayerSession(player.getUniqueId());

        playerSession.setDead(true);

        plugin.getPlayerSessionHandler().endSession(playerSession);
    }

}
