package dev.swiss.singledungeon.singledungeon;

import dev.swiss.singledungeon.singledungeon.commands.LeaveCommand;
import dev.swiss.singledungeon.singledungeon.commands.StartCommand;
import dev.swiss.singledungeon.singledungeon.commands.StatsCommand;
import dev.swiss.singledungeon.singledungeon.database.ConnectionSettings;
import dev.swiss.singledungeon.singledungeon.database.MySQL;
import dev.swiss.singledungeon.singledungeon.listeners.OnEntityDeathEvent;
import dev.swiss.singledungeon.singledungeon.listeners.OnPlayerDeathEvent;
import dev.swiss.singledungeon.singledungeon.listeners.OnPlayerJoinEvent;
import dev.swiss.singledungeon.singledungeon.playersession.PlayerSessionHandler;
import dev.swiss.singledungeon.singledungeon.profile.ProfileHandler;
import dev.swiss.singledungeon.singledungeon.utils.Config;
import dev.swiss.singledungeon.singledungeon.utils.menu.MenuListener;
import dev.swiss.singledungeon.singledungeon.utils.menu.tasks.MenuUpdateTask;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SingleDungeon extends JavaPlugin {

    @Getter
    private static SingleDungeon instance;

    @Getter
    private Config config;

    @Getter
    private PlayerSessionHandler playerSessionHandler;

    @Getter
    private ProfileHandler profileHandler;

    @Getter
    private MySQL mySQL;

    @Override
    public void onEnable() {
        instance = this;
        config = new Config(this, "config");

        playerSessionHandler = new PlayerSessionHandler();
        profileHandler = new ProfileHandler();

        registerCommands();
        registerListeners();

        new MenuUpdateTask().runTaskTimerAsynchronously(this, 0, 1);

        mySQL = new MySQL(
                new ConnectionSettings(
                    config.getString("mysql.host"),
                    config.getInt("mysql.port"),
                    config.getString("mysql.database"),
                    config.getString("mysql.username"),
                    config.getString("mysql.password")
                )
        );
    }

    public void registerCommands() {
        Objects.requireNonNull(getServer().getPluginCommand("start")).setExecutor(new StartCommand());
        Objects.requireNonNull(getServer().getPluginCommand("leave")).setExecutor(new LeaveCommand());
        Objects.requireNonNull(getServer().getPluginCommand("stats")).setExecutor(new StatsCommand());
    }

    public void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new MenuListener(this), this);
        this.getServer().getPluginManager().registerEvents(new OnEntityDeathEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerDeathEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), this);
    }

    @Override
    public void onDisable() {

    }
}
