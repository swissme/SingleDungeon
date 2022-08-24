package dev.swiss.singledungeon.singledungeon.playersession;

import dev.swiss.singledungeon.singledungeon.profile.Profile;
import dev.swiss.singledungeon.singledungeon.profile.ProfileHandler;
import dev.swiss.singledungeon.singledungeon.utils.BukkitSerialization;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerSessionHandler {

    @Getter
    private HashMap<UUID, PlayerSession> playerSessions;

    public PlayerSessionHandler() {
        playerSessions = new HashMap<>();
    }

    public void addPlayerSession(UUID uuid, PlayerSession playerSession) {
        playerSessions.put(uuid, playerSession);
    }

    public PlayerSession getPlayerSession(UUID uuid) {
        return playerSessions.get(uuid);
    }

    public void removePlayerSession(UUID uuid) {
        playerSessions.remove(uuid);
    }

    public void endSession(PlayerSession playerSession) {
        Player player = Bukkit.getPlayer(playerSession.getPlayerUUID());
        if(player != null)
            player.teleport(playerSession.getOldLocation());

        BukkitSerialization.base64ToPlayerInventory(playerSession.getBase64Inventory(), player);

        assert player != null;
        Profile profile = ProfileHandler.getProfileByUUID(player.getUniqueId());
        profile.addPlayerSession(playerSession);
        profile.save();

        removePlayerSession(playerSession.getPlayerUUID());
    }

}
