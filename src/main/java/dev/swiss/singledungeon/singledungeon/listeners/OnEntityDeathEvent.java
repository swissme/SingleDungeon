package dev.swiss.singledungeon.singledungeon.listeners;

import dev.swiss.singledungeon.singledungeon.SingleDungeon;
import dev.swiss.singledungeon.singledungeon.playersession.PlayerSession;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class OnEntityDeathEvent implements Listener {

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if(event.getEntity().getKiller() == null)
            return;

        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();
        if(!entityType.equals(EntityType.ZOMBIE))
            return;

        if(!Objects.requireNonNull(event.getEntity().getKiller()).getType().equals(EntityType.PLAYER))
            return;

        Player player = event.getEntity().getKiller();

        SingleDungeon plugin = SingleDungeon.getInstance();

        if(!plugin.getPlayerSessionHandler().getPlayerSessions().containsKey(player.getUniqueId()))
            return;

        PlayerSession playerSession = plugin.getPlayerSessionHandler().getPlayerSession(player.getUniqueId());
        playerSession.setKills(playerSession.getKills() + 1);

        if(playerSession.getKills() >= plugin.getConfig().getInt("dungeon.mobcount")) {

            new BukkitRunnable() {

                @Override
                public void run() {
                    plugin.getPlayerSessionHandler().endSession(playerSession);
                }

            }.runTaskLater(plugin, 20L);

        }

    }

}
