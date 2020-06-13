package com.github.jolice.gui.multipage;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import com.github.jolice.gui.multipage.player.PlayerPages;

import java.util.UUID;

import static com.github.jolice.gui.multipage.MultiPageButtons.NEXT_PAGE;
import static com.github.jolice.gui.multipage.MultiPageButtons.PREVIOUS_PAGE;

@RequiredArgsConstructor
public class MultiPageListener implements Listener {

    private final PageSwitcher pageSwitcher;
    private final SlotActions slotActions;
    private final PlayerPages playerPages;

    @EventHandler
    public void inventoryClicked(InventoryClickEvent e) {
        if (playerPages.hasSession(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem != null) {
                handleItemClick(e, currentItem);
            }
        }
    }

    @EventHandler
    public void inventoryClosed(InventoryCloseEvent e) {
        playerPages.removePlayer(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        playerPages.removePlayer(event.getPlayer().getUniqueId());
    }

    private void handleItemClick(InventoryClickEvent event, ItemStack currentItem) {
        UUID uuid = event.getWhoClicked().getUniqueId();
        if (NEXT_PAGE.getItemStack().equals(currentItem)) {
            this.pageSwitcher.switchPage(uuid, operand -> operand + 1);
        } else if (PREVIOUS_PAGE.getItemStack().equals(currentItem)) {
            this.pageSwitcher.switchPage(uuid, operand -> operand - 1);
        } else {
            slotActions.executeSlotAction(uuid, event.getSlot());
        }
    }




}
