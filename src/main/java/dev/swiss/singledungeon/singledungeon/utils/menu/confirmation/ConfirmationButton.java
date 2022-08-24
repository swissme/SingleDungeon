package dev.swiss.singledungeon.singledungeon.utils.menu.confirmation;

import dev.swiss.singledungeon.singledungeon.utils.CC;
import dev.swiss.singledungeon.singledungeon.utils.menu.Button;
import dev.swiss.singledungeon.singledungeon.utils.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

/**
 * @author Swiss (swiss@swissdev.com)
 */
@AllArgsConstructor
public class ConfirmationButton extends Button {

    private boolean confirmation, isCloseAfterResponse;
    private Consumer<Boolean> consumer;

    @Override
    public ItemStack getItem(Player player) {
        ItemStack itemStack = new ItemStack(
                confirmation ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE
        );
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(CC.translate(confirmation ? "&aConfirm" : "&cCancel"));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public void clicked(Player player) {
        if(isCloseAfterResponse) {
            Menu currentMenu = Menu.getCurrentlyOpenedMenus().get(player.getUniqueId());
            if(currentMenu != null)
                player.closeInventory();
            else currentMenu.setClosedByMenu(true);
        }

        this.consumer.accept(this.confirmation);
    }
}
