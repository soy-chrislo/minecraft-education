package com.soychristian.minecraftpoo.helpers;

import com.soychristian.minecraftpoo.MinecraftPOO;
import com.soychristian.minecraftpoo.exceptions.InvalidEncodedInventoryFormat;
import com.soychristian.minecraftpoo.utils.InventoryUtils;
import com.soychristian.minecraftpoo.utils.PlayerFileFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class AdminInventory implements Listener {
    private Inventory adminInventory;
    PlayerFileFactory playerFileFactory;

    MinecraftPOO plugin;

    public AdminInventory(MinecraftPOO plugin){
        this.plugin = plugin;
    }

    public void setInventory(Inventory inventory){
        this.adminInventory = inventory;
        playerFileFactory.setPlayerData("adminInventory", InventoryUtils.encodeInventory(inventory));
    }
    public Inventory getInventory(String playerName) {
        playerFileFactory = new PlayerFileFactory(this.plugin, playerName);
        String encodedInventory = playerFileFactory.getPlayerData("adminInventory");
        if (encodedInventory == null) return Bukkit.createInventory(null, 9 * 6, playerName + " Admin Inventory");
        Inventory finalInventory = Bukkit.createInventory(null, 9 * 6, playerName + " Admin Inventory");
        Inventory decodeInventory;
        try {
            decodeInventory = InventoryUtils.decodeInventory(encodedInventory);
        } catch (InvalidEncodedInventoryFormat e) {
            throw new RuntimeException(e);
        }
        finalInventory.setContents(decodeInventory.getContents());
        return finalInventory;
    }


    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event){
        if (!event.getView().getTitle().contains("Admin Inventory")) return;
        String playerName = event.getView().getTitle().split(" ")[0];
        playerFileFactory = new PlayerFileFactory(this.plugin, playerName);
        Player player = (Player) event.getPlayer();
        if (player.hasPermission("minecraftpoo.admin.inventory")){
            String playerInventory = event.getView().getTopInventory().getTitle().split(" ")[0];
            player.sendMessage("Guardando inventario del jugador " + playerInventory + "...");
        }
        playerFileFactory.setPlayerData("adminInventory", InventoryUtils.encodeInventory(event.getView().getTopInventory()));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Inventory clickedInventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;

        if (!view.getTitle().contains("Admin Inventory")) return;

        if (!player.hasPermission("minecraftpoo.admin.inventory")) {
            if (clickedInventory.equals(view.getBottomInventory())){
                if (event.isShiftClick()){
                    if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
                        event.setCancelled(true);
                    }
                }
            } else if (clickedInventory.equals(view.getTopInventory())){
                if (event.getCursor() != null){
                    switch (event.getAction()){
                        case PLACE_ALL:
                        case PLACE_ONE:
                        case PLACE_SOME:
                        case HOTBAR_SWAP:
                            event.setCancelled(true);
                            break;
                        default:
                            break;
                    }
                } else if (event.getAction() == InventoryAction.HOTBAR_SWAP){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDragInventory(InventoryDragEvent event){
        if (!event.getView().getTitle().contains("Admin Inventory")) return;
        Player player = (Player) event.getWhoClicked();
        if (!player.hasPermission("minecraftpoo.admin.inventory")) {
            if (event.getOldCursor() != null){
                if (event.getInventory().equals(event.getView().getTopInventory())){
                    event.setCancelled(true);
                }
            }
        }
    }
}
