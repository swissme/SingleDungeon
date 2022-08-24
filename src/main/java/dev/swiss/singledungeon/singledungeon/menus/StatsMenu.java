package dev.swiss.singledungeon.singledungeon.menus;

import dev.swiss.singledungeon.singledungeon.SingleDungeon;
import dev.swiss.singledungeon.singledungeon.profile.Profile;
import dev.swiss.singledungeon.singledungeon.profile.ProfileHandler;
import dev.swiss.singledungeon.singledungeon.utils.CC;
import dev.swiss.singledungeon.singledungeon.utils.ItemBuilder;
import dev.swiss.singledungeon.singledungeon.utils.menu.Button;
import dev.swiss.singledungeon.singledungeon.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return "&6Stats";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        List<String> lore = new ArrayList<>();

        Profile profile = ProfileHandler.getProfileByUUID(player.getUniqueId());

        lore.add(CC.MENU_BAR);
        lore.add("&7Total Mob Kills: &f" + profile.getTotalMobKills());
        lore.add("&7Total Dungeon Sessions: &f" + profile.getPlayerSessions().size());
        lore.add("&7Average Mob Kills: &f" + profile.getAverageMobKills());
        lore.add("&7Total Dungeon Fails: &f" + profile.getTotalDungeonFails());
        lore.add(CC.MENU_BAR);
        lore.add("&7&oClick to view more");
        lore.add(CC.MENU_BAR);

        return new HashMap<Integer, Button>() {{

            put(13, new Button() {
                @Override
                public ItemStack getItem(Player player) {
                    return new ItemBuilder(Material.PAPER)
                            .setDisplayName("&6Personal Stats")
                            .setLore(lore)
                            .build();
                }

                @Override
                public void clicked(Player player) {
                    PlayerSessionViewMenu menu = new PlayerSessionViewMenu();
                    menu.render(player);
                }
            });

        }};
    }

    @Override
    public int getSize() {
        return 27;
    }
}
