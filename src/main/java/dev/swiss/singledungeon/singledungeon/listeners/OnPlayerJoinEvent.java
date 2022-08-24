package dev.swiss.singledungeon.singledungeon.listeners;

import dev.swiss.singledungeon.singledungeon.profile.Profile;
import dev.swiss.singledungeon.singledungeon.profile.ProfileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(ProfileHandler.getProfiles().containsKey(player.getUniqueId()))
            return;

        ProfileHandler.getProfiles().put(player.getUniqueId(), new Profile(player.getUniqueId()));
    }
}
