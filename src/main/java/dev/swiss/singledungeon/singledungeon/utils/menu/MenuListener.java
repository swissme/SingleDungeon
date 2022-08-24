package dev.swiss.singledungeon.singledungeon.utils.menu;

import dev.swiss.singledungeon.singledungeon.SingleDungeon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class MenuListener implements Listener {

    private JavaPlugin instance;

    public MenuListener(JavaPlugin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu openedMenu = Menu.getCurrentlyOpenedMenus().get(player.getUniqueId());

        if(openedMenu == null) return;

        if(event.getSlot() != event.getRawSlot()) {
            if(event.getAction().name().contains("SHIFT_"))
                event.setCancelled(true);

            return;
        }

        if(!openedMenu.getButtons().containsKey(event.getSlot())) {
            if(event.getCurrentItem() != null || event.getClick().name().contains("SHIFT_"))
                event.setCancelled(true);

            return;
        }

        Button button = openedMenu.getButtons().get(event.getSlot());
        boolean shouldCancel = button.shouldCancel(player, event.getClick());

        if(!shouldCancel && event.getClick().name().contains("SHIFT_")) {
            event.setCancelled(true);

            if(event.getCurrentItem() != null)
                player.getInventory().addItem(event.getCurrentItem());
        } else event.setCancelled(shouldCancel);

        button.clicked(player);
        button.clicked(player, event.getClick());

        if(Menu.getCurrentlyOpenedMenus().containsKey(player.getUniqueId())) {
            Menu newMenu = Menu.getCurrentlyOpenedMenus().get(player.getUniqueId());
            if(newMenu == openedMenu) {
                boolean shouldUpdate = button.shouldUpdate(player, event.getClick());

                if(shouldUpdate) {
                    openedMenu.setClosedByMenu(true);
                    newMenu.render(player);
                }
            }
        } else if(button.shouldUpdate(player, event.getClick())) {
            openedMenu.setClosedByMenu(true);
            openedMenu.render(player);
        }

        if(event.isCancelled())
            Bukkit.getScheduler().runTaskLater(SingleDungeon.getInstance(), player::updateInventory, 1L);
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu openMenu = Menu.getCurrentlyOpenedMenus().get(player.getUniqueId());
        if(openMenu == null) return;

        Menu.getCurrentlyOpenedMenus().remove(player.getUniqueId());
    }

}
