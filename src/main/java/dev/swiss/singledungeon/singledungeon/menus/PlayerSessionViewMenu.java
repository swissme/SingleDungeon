package dev.swiss.singledungeon.singledungeon.menus;

import dev.swiss.singledungeon.singledungeon.playersession.PlayerSession;
import dev.swiss.singledungeon.singledungeon.profile.Profile;
import dev.swiss.singledungeon.singledungeon.profile.ProfileHandler;
import dev.swiss.singledungeon.singledungeon.utils.CC;
import dev.swiss.singledungeon.singledungeon.utils.ItemBuilder;
import dev.swiss.singledungeon.singledungeon.utils.menu.Button;
import dev.swiss.singledungeon.singledungeon.utils.menu.page.PagedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerSessionViewMenu extends PagedMenu {

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&6Player Session Viewer";
    }

    @Override
    public Map<Integer, Button> getAllButtons(Player player) {
        Profile profile = ProfileHandler.getProfileByUUID(player.getUniqueId());

        return new HashMap<Integer, Button>() {{

            for (int i = 0; i < profile.getPlayerSessions().size(); i++) {

                PlayerSession playerSession = profile.getPlayerSessions().get(i);

                List<String> lore = new ArrayList<>();
                lore.add(CC.MENU_BAR);
                lore.add("&7Mob Kills: &f" + playerSession.getKills());
                lore.add("&7Died? &f" + playerSession.isDead());
                lore.add(CC.MENU_BAR);

                int finalI = i;
                put(i, new Button() {
                    @Override
                    public ItemStack getItem(Player player) {
                        return new ItemBuilder(Material.PAPER)
                                .setDisplayName("&6Session #" + (finalI + 1))
                                .setLore(lore)
                                .build();
                    }
                });
            }

        }};
    }

    @Override
    public int getSize() {
        return 54;
    }
}
