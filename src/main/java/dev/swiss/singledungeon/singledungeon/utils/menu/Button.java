package dev.swiss.singledungeon.singledungeon.utils.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public abstract class Button {

    /**
     * Get the item for this button.
     * @param player an instance of the player that would click this button
     * @return the item to display
     */
    public abstract ItemStack getItem(Player player);

    /**
     * Establish a click against this player.
     * @param player the player that clicked
     */
    public void clicked(Player player) {}

    /**
     * Establish a click against this player.
     * @param player the player that clicked
     * @param clickType the mouse button they used
     */
    public void clicked(Player player, ClickType clickType) {}

    /**
     * Determine if an action should cause a button is cancel.
     * @param player the player that would click this button
     * @param clickType the mouse button they used
     * @return if clicking this button should cancel the action.
     */
    public boolean shouldCancel(Player player, ClickType clickType) {
        return true;
    }

    /**
     * Determine if an action should cause a button update.
     * @param player the player that would click this button
     * @param clickType the mouse button they used
     * @return if clicking this button should update.
     */
    public boolean shouldUpdate(Player player, ClickType clickType) {
        return false;
    }

}
