package dev.swiss.singledungeon.singledungeon.utils.menu;


import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;


@Getter
@Setter
public abstract class Menu {

    @Getter
    private static final Map<UUID, Menu> currentlyOpenedMenus = Maps.newHashMap();

    private Map<Integer, Button> buttons = Maps.newHashMap();
    private boolean autoUpdate = false, updateAfterClick = true, closedByMenu = false;

    /**
     * Render a menu for a player.
     * @param player the player to render the menu for
     */
    public void render(Player player) {
        this.buttons = this.getButtons(player);

        Menu previous = currentlyOpenedMenus.get(player.getUniqueId());
        Inventory inventory = null;

        String title = ChatColor.translateAlternateColorCodes('&', this.getTitle(player));
        int size = this.getSize() == -1 ? this.size(this.buttons) : this.getSize();
        boolean shouldUpdate = false;

        Inventory openInventory = player.getOpenInventory().getTopInventory();
        if(openInventory != null) {
            if(previous == null)
                player.closeInventory();
            else {
                int previousSize = openInventory.getSize();
                if(previousSize == size && player.getOpenInventory().getTitle().equals(title)) {
                    inventory = player.getOpenInventory().getTopInventory();
                    shouldUpdate = true;
                } else {
                    previous.setClosedByMenu(true);
                    player.closeInventory();
                }
            }
        }

        if(inventory == null)
            inventory = Bukkit.createInventory(player, size, title);

        inventory.setContents(new ItemStack[inventory.getSize()]);
        currentlyOpenedMenus.put(player.getUniqueId(), this);

        for(Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet())
            inventory.setItem(buttonEntry.getKey(), buttonEntry.getValue().getItem(player));

        if(shouldUpdate)
            player.updateInventory();
        else player.openInventory(inventory);

        this.setClosedByMenu(false);
    }

    public int size(Map<Integer, Button> buttons) {
        int highest = 0;

        for(int indx : buttons.keySet()) {
            if(indx > highest)
                highest = indx;
        }

        return (int) (Math.ceil((highest + 1) / 9D) * 9D);
    }

    public int getSlot(int x, int y) {
        return (9 * y) + x;
    }

    /**
     * Get the size of this menu.
     * @return the size of this menu
     */
    public int getSize() {
        return -1;
    }


    /**
     * Get the string of this menu.
     * @param player the player to use
     * @return the title of this menu
     */
    public abstract String getTitle(Player player);

    /**
     * Get the buttons that a player would be displayed.
     * @param player the player to use
     * @return a map of buttons
     */
    public abstract Map<Integer, Button> getButtons(Player player);
}
