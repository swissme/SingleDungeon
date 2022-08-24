package dev.swiss.singledungeon.singledungeon.profile;

import dev.swiss.singledungeon.singledungeon.database.MySQL;
import dev.swiss.singledungeon.singledungeon.playersession.PlayerSession;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Profile {

    private UUID uuid;
    private List<PlayerSession> playerSessions;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.playerSessions = new ArrayList<>();
    }

    public int getTotalMobKills() {
        int total = 0;
        for (PlayerSession session : playerSessions) {
            total += session.getKills();
        }
        return total;
    }

    public double getAverageMobKills() {
        return (double) getTotalMobKills() / playerSessions.size();
    }

    public int getTotalDungeonFails() {
        int total = 0;
        for (PlayerSession session : playerSessions) {
            if(session.isDead())
                total += 1;
        }
        return total;
    }

    public void addPlayerSession(PlayerSession playerSession) {
        playerSessions.add(playerSession);
    }

    public void save() {
//        if(MySQL.containsProfile(this))
//            MySQL.updateProfile(this);
//        else
//            MySQL.insertProfile(this);
    }

    public void delete() {
//        MySQL.deleteProfile(this);
    }

}
