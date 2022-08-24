package dev.swiss.singledungeon.singledungeon.database;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import dev.swiss.singledungeon.singledungeon.SingleDungeon;
import dev.swiss.singledungeon.singledungeon.playersession.PlayerSession;
import dev.swiss.singledungeon.singledungeon.profile.Profile;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class MySQL {

    @Getter
    private final DataSource dataSource;

    public MySQL(ConnectionSettings connectionSettings) {
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setServerName(connectionSettings.getHost());
        dataSource.setPortNumber(connectionSettings.getPort());
        dataSource.setDatabaseName(connectionSettings.getDatabase());
        dataSource.setUser(connectionSettings.getUsername());
        dataSource.setPassword(connectionSettings.getPassword());

        this.dataSource = dataSource;

        try {
            testDataSource(dataSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void testDataSource(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            if (!conn.isValid(1)) {
                throw new SQLException("Could not establish database connection.");
            }
        }
    }

//    public static void insertPlayerSession(PlayerSession playerSession) {
//        try (Connection conn = SingleDungeon.getInstance().getMySQL().getDataSource().getConnection()) {
//            conn.createStatement().executeUpdate(
//                    "INSERT INTO playersessions (uuid, playerUUID, base64Inventory, kills, isDead) VALUES " +
//                            "('" + playerSession.getUuid().toString() + "', " +
//                            "'" + playerSession.getPlayerUUID().toString() + "', " +
//                            "'{" + playerSession.getBase64Inventory() + "}', " +
//                            "'" + playerSession.getKills() + "', " +
//                            "'" + playerSession.isDead() + "')"
//            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void updateProfile(Profile profile) {
//        try (Connection conn = SingleDungeon.getInstance().getMySQL().getDataSource().getConnection()) {
//            conn.createStatement().executeUpdate(
//                    "UPDATE profiles SET playerSessions = '" + profile.getPlayerSessions() + "' WHERE uuid = '" + profile.getUuid() + "'"
//            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void deleteProfile(Profile profile) {
//        try (Connection conn = SingleDungeon.getInstance().getMySQL().getDataSource().getConnection()) {
//            conn.createStatement().executeUpdate(
//                    "DELETE FROM profiles WHERE uuid = '" + profile.getUuid() + "'"
//            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public static boolean containsProfile(Profile profile) {
//        try (Connection conn = SingleDungeon.getInstance().getMySQL().getDataSource().getConnection()) {
//            int i = conn.createStatement().executeUpdate(
//                    "SELECT 1 FROM profiles WHERE uuid= '" + profile.getUuid() + "' LIMIT 1"
//            );
//            if(i == 1)
//                return true;
//            else
//                return false;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

//    public static Profile getProfile(UUID uuid) {
//        try (Connection conn = SingleDungeon.getInstance().getMySQL().getDataSource().getConnection()) {
//            String playerSessions = conn.createStatement().executeQuery(
//                    "SELECT playerSessions FROM profiles WHERE uuid= '" + uuid + "'"
//            ).getString("playerSessions");
//            Profile newProfile = new Profile(uuid);
//            newProfile.setPlayerSessions(playerSessions);
//            return newProfile;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
