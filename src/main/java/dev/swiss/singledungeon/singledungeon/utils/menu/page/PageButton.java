package dev.swiss.singledungeon.singledungeon.utils.menu.page;

import dev.swiss.singledungeon.singledungeon.utils.CC;
import dev.swiss.singledungeon.singledungeon.utils.menu.Button;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


@AllArgsConstructor
public class PageButton extends Button {

    private int mod;
    private PagedMenu menu;

    @Override
    public ItemStack getItem(Player player) {
        String displayName;

        if(this.mod > 0) {
            if(hasPreviousPage(player)) {
                displayName = "&6Left-Click &eto go foward a page";
            } else {
                displayName = "&eLast Page";
            }
            ItemStack itemStack1 = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) itemStack1.getItemMeta();
            skullMeta.setOwner("MHF_ArrowRight");
            skullMeta.setDisplayName(CC.translate(displayName));
            itemStack1.setItemMeta(skullMeta);
            return itemStack1;
        } else {
            if(hasPreviousPage(player)) {
                displayName = "&6Left-Click &eto go back a page";
            } else {
                displayName = "&eFirst Page";
            }
            ItemStack itemStack1 = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) itemStack1.getItemMeta();
            skullMeta.setOwner("MHF_ArrowLeft");
            skullMeta.setDisplayName(CC.translate(displayName));
            itemStack1.setItemMeta(skullMeta);
            return itemStack1;
        }
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if(this.mod > 0 && hasNextPage(player)) {
            if(hasNextPage(player) || hasPreviousPage(player))
                this.menu.mod(player, this.mod);
        }
    }

    private boolean hasNextPage(Player player) {
        int page = this.menu.getPage() + this.mod;
        return this.menu.getPages(player) >= page;
    }

    private boolean hasPreviousPage(Player player) {
        int page = this.menu.getPage() + this.mod;
        return page > 0;
    }
}
