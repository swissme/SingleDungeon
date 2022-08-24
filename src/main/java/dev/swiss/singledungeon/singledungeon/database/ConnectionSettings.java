package dev.swiss.singledungeon.singledungeon.database;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectionSettings {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

}
