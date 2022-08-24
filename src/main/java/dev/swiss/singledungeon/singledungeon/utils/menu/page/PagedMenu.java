package dev.swiss.singledungeon.singledungeon.utils.menu.page;


import com.google.common.collect.Maps;
import dev.swiss.singledungeon.singledungeon.utils.menu.Button;
import dev.swiss.singledungeon.singledungeon.utils.menu.Menu;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Map;


@Getter
public abstract class PagedMenu extends Menu {

    private int page = 1;

    @Override
    public String getTitle(Player player) {
        return getPrePaginatedTitle(player);
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        int min = (int) ((double) (page - 1) * getMaxItemsPerItem(player));
        int max = (int) ((double) (page) * getMaxItemsPerItem(player));
        int topIndx = 0;

        for(Map.Entry<Integer, Button> entry : getAllButtons(player).entrySet()) {
            int indx = entry.getKey();
            if(indx >= min && indx < max) {
                indx -= (int) ((double) (getMaxItemsPerItem(player)) * (page - 1)) - 9;
                buttons.put(indx, entry.getValue());

                if(indx > topIndx)
                    topIndx = indx;
            }
        }

        buttons.put(0, new PageButton(-1, this));
        buttons.put(8, new PageButton(1, this));

        Map<Integer, Button> globalButtons = getGlobalButtons(player);
        if(globalButtons != null)
            buttons.putAll(globalButtons);

        return buttons;
    }

    public void mod(Player player, int mod) {
        page += mod;

        getButtons().clear();
        render(player);
    }

    public int getPages(Player player) {
        int amount = getAllButtons(player).size();
        if(amount == 0)
            return amount;

        return (int) Math.ceil(amount / (double) getMaxItemsPerItem(player));
    }

    public int getMaxItemsPerItem(Player player) {
        return 18;
    }

    public Map<Integer, Button> getGlobalButtons(Player player) {
        return null;
    }

    public abstract String getPrePaginatedTitle(Player player);
    public abstract Map<Integer, Button> getAllButtons(Player player);

}
