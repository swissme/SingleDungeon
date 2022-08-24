package dev.swiss.singledungeon.singledungeon.utils.menu.tasks;

import dev.swiss.singledungeon.singledungeon.utils.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Swiss (swiss@swissdev.com)
 */
public class MenuUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        Menu.getCurrentlyOpenedMenus().forEach((key, value) -> {
            if(!value.isAutoUpdate())
                return;
            Player player = Bukkit.getPlayer(key);
            value.getButtons().forEach(((integer, button) -> {
                player.getOpenInventory().getTopInventory().setItem(integer, button.getItem(player));
            }));
            player.updateInventory();
        });
    }

}
