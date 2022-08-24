package dev.swiss.singledungeon.singledungeon.utils.menu.confirmation;

import com.google.common.collect.Maps;
import dev.swiss.singledungeon.singledungeon.utils.ItemBuilder;
import dev.swiss.singledungeon.singledungeon.utils.menu.Button;
import dev.swiss.singledungeon.singledungeon.utils.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Swiss (swiss@swissdev.com)
 */
@AllArgsConstructor
public class ConfirmationMenu extends Menu {

    private String title;
    private Consumer<Boolean> consumer;
    private boolean closeAfterResponse;
    private Button[] centerButtons;

    @Override
    public String getTitle(Player player) {
        return title;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        for (int i = 0; i < getSize(); i++) {
            buttons.put(i, new Button() {
                @Override
                public ItemStack getItem(Player player) {
                    return new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
                }
            });
        }
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                buttons.put(this.getSlot(x, y), new ConfirmationButton(true, closeAfterResponse, consumer));
                buttons.put(this.getSlot(8 - x, y), new ConfirmationButton(false, closeAfterResponse, consumer));
            }
        }

        if(centerButtons != null) {
            for(int i = 0; i < centerButtons.length; i++) {
                Button centerButton = centerButtons[i];
                if(centerButton == null)
                    continue;

                buttons.put(this.getSlot(4, i), centerButton);
            }
        }

        return buttons;
    }
}
