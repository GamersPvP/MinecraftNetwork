package me.riguron.bukkit.shop;

import me.riguron.bukkit.gui.GUI;
import me.riguron.bukkit.gui.GUIFactory;
import me.riguron.bukkit.gui.multipage.MockItemFactory;
import me.riguron.system.shop.Purchasable;
import org.bukkit.Bukkit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;

import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Bukkit.class)
public class ShopViewsTest {

    @Test
    public void createConfirmationWindow() {
        MockItemFactory.mockItemFactory();
        GUIFactory guiFactory = mock(GUIFactory.class);
        ShopInventoryButtons buttons = mock(ShopInventoryButtons.class);
        ShopViews shopViews = new ShopViews(
                guiFactory,
                buttons
        );
        when(guiFactory.newSinglePageGui(any())).thenReturn(mock(GUI.class));
        UUID uuid = UUID.randomUUID();
        shopViews.openShopView(uuid, mock(Purchasable.class));
        assertTrue(shopViews.isInShop(uuid));
    }


}