package dev.swiss.singledungeon.singledungeon.commands;

import dev.swiss.singledungeon.singledungeon.SingleDungeon;
import dev.swiss.singledungeon.singledungeon.playersession.PlayerSession;
import dev.swiss.singledungeon.singledungeon.utils.BukkitSerialization;
import dev.swiss.singledungeon.singledungeon.utils.Config;
import dev.swiss.singledungeon.singledungeon.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SingleDungeon plugin = SingleDungeon.getInstance();

        Player player = (Player) sender;

        if(plugin.getPlayerSessionHandler().getPlayerSessions().containsKey(player.getUniqueId())) {
            new Message("&cYou already have a dungeon session!").send(player);
            return true;
        }

        Config config = plugin.getConfig();

        Location playerLoc = player.getLocation();

        Location dungeonLoc = new Location(
                Bukkit.getWorld(Objects.requireNonNull(config.getString("dungeon.world-name"))),
                config.getInt("dungeon.x"),
                config.getInt("dungeon.y"),
                config.getInt("dungeon.z"));

        List<String> base64Inventory = Arrays.asList(BukkitSerialization.playerInventoryToBase64(player.getInventory()));

        plugin.getPlayerSessionHandler().addPlayerSession(
                player.getUniqueId(),
                new PlayerSession(player.getUniqueId(), base64Inventory, playerLoc)
        );

        player.teleport(dungeonLoc);

        HashMap<Integer, ItemStack> items = new HashMap<>();

        ItemStack goldenApples = new ItemStack(Material.GOLDEN_APPLE);
        goldenApples.setAmount(64);

        items.put(0, new ItemStack(Material.DIAMOND_SWORD));
        items.put(1, goldenApples);
        items.put(103, new ItemStack(Material.DIAMOND_HELMET));
        items.put(102, new ItemStack(Material.DIAMOND_CHESTPLATE));
        items.put(101, new ItemStack(Material.DIAMOND_LEGGINGS));
        items.put(100, new ItemStack(Material.DIAMOND_BOOTS));

        player.getInventory().clear();
        player.updateInventory();

        items.forEach((key, value) -> {
            player.getInventory().setItem(key, value);
        });

        player.updateInventory();

        new BukkitRunnable() {

            @Override
            public void run() {
                for (int i = 0; i < config.getInt("dungeon.mobcount"); i++) {
                    int newX = (int) (player.getLocation().getX() + new Random().nextInt(2)+1);
                    int newZ = (int) (player.getLocation().getZ() + new Random().nextInt(2)+1);

                    Location spawnLoc = new Location(player.getWorld(), newX, player.getLocation().getY(), newZ);

                    Entity entity = player.getWorld().spawnEntity(spawnLoc, EntityType.ZOMBIE);
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if(onlinePlayer.getUniqueId().equals(player.getUniqueId()))
                            continue;

                        onlinePlayer.hideEntity(plugin, entity);
                        onlinePlayer.hidePlayer(plugin, player);
                    }
                }
            }

        }.runTaskLater(plugin, 100L);

        return true;
    }

}
