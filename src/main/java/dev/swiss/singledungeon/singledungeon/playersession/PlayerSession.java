package dev.swiss.singledungeon.singledungeon.playersession;

import lombok.Data;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

@Data
public class PlayerSession {

    private UUID uuid;
    private UUID playerUUID;
    private List<String> base64Inventory;
    private Location oldLocation;
    private int kills;
    private boolean isDead;

    public PlayerSession(UUID playerUUID, List<String> base64Inventory, Location oldLocation) {
        this.uuid = UUID.randomUUID();
        this.playerUUID = playerUUID;
        this.base64Inventory = base64Inventory;
        this.oldLocation = oldLocation;
        this.kills = 0;
        this.isDead = false;
    }

}
