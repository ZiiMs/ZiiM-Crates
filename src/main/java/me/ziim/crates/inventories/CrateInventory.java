package me.ziim.crates.inventories;

import me.ziim.crates.Config;
import me.ziim.crates.Item;
import me.ziim.crates.RandomSelector;
import me.ziim.crates.ZiiMCrates;
import me.ziim.crates.inventoryHelper.IHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class CrateInventory implements IHelper {
    private final Inventory inv;
    Plugin plugin = ZiiMCrates.getPlugin(ZiiMCrates.class);
    private BukkitTask running;

    public CrateInventory(String title) {
        inv = plugin.getServer().createInventory(this, 27, title);

        initItems();
    }

    public void initItems() {
        for (int j = 0; j <= 26; j++) {
            if ((j >= 9 && j <= 17) || j == 21 || j == 23 || j == 26) {
                continue;
            }
            inv.setItem(j, newItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
        inv.setItem(21, newItem(Material.GREEN_STAINED_GLASS_PANE, "Open"));
        inv.setItem(23, newItem(Material.RED_STAINED_GLASS_PANE, "Cancel"));
        inv.setItem(26, newItem(Material.YELLOW_STAINED_GLASS_PANE, "Edit"));
    }

    protected ItemStack newItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openInv(Player player) {
        player.openInventory(inv);
    }


    @Override
    public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, InventoryView view, Inventory inventory, ItemStack cursor) {
        if (clickedItem == null) {
            return;
        }
        for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
            String title = Config.get().getString(keys + ".Title");
            if (view.getTitle().equals(title)) {

                ItemStack key = Config.get().getItemStack(keys + ".Key");
                boolean hasKey = whoClicked.getInventory().containsAtLeast(key, 1);
                boolean hasSlot = Arrays.toString(whoClicked.getInventory().getStorageContents()).contains("null");
                if (clickedItem.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                    if (!hasKey) {
                        whoClicked.sendMessage("You don't have the key for this crate");
                        return;
                    }
                    if (!hasSlot) {
                        whoClicked.sendMessage("You have no inventory space!");
                        view.close();
                        return;
                    }
                    if (running != null) {
                        whoClicked.sendMessage("You are already rolling");
                        return;
                    }
                    ArrayList<ItemStack> itemsList = (ArrayList<ItemStack>) Config.get().getList(keys + ".Items");
                    if (itemsList == null) {
                        whoClicked.sendMessage(ChatColor.RED + "ERROR: " + ChatColor.WHITE + "please tell the administrator to set the items inside the crate.");
                        return;
                    }
                    Map<ItemStack, Integer> dupeCount = new HashMap<>();
                    itemsList.forEach(a -> dupeCount.put(a, dupeCount.getOrDefault(a, 0) + 1));
                    List<Item> rngItemList = new ArrayList<>();
                    dupeCount.forEach((k, v) -> {
                        double prob = (v / 27.0) * 100;
                        rngItemList.add(new Item((int) prob, k));
                    });
                    RandomSelector rngItem = new RandomSelector(rngItemList);
                    ArrayList<ItemStack> SpinningItemList = new ArrayList<>();
                    for (int i = 0; i < 27; i++) {
                        SpinningItemList.add(rngItem.getRandom().item);
                    }

                    Random r = new Random();
                    double seconds = 7.0 + (12.0 - 7.0) * r.nextDouble();

                    running = new BukkitRunnable() {
                        double delay = 0;
                        int ticks = 0;
                        boolean delayDone = false;
                        int index = 0;

                        @Override
                        public void run() {
                            if (delayDone) return;
                            ticks++;
                            delay += 1 / (20 * seconds);
                            if (ticks > delay * 10) {
                                ticks = 0;
                                if (index >= 27) {
                                    index = 0;
                                } else {
                                    index++;
                                }
                                for (int i = 0; i < 9; i++) {
                                    inv.setItem(i + 9, SpinningItemList.get((i + index) % SpinningItemList.size()));
                                }
                                if (delay >= .5) {
                                    delayDone = true;
                                    whoClicked.getInventory().addItem(inv.getItem(13));
                                    whoClicked.sendMessage(ChatColor.LIGHT_PURPLE + "Congrats " + ChatColor.WHITE + "you have won " + ChatColor.AQUA + inv.getItem(13).getItemMeta().getDisplayName());
                                    cancel();
                                    running = null;
                                }
                            }
                        }
                    }.runTaskTimer(plugin, 0, 1);


                    whoClicked.getInventory().removeItem(key);
                } else if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                    view.close();
                } else if (clickedItem.getType().equals(Material.YELLOW_STAINED_GLASS_PANE) && whoClicked.hasPermission("zc.createcrate")) {
                    EditorInventory editInv = new EditorInventory(view.getTitle() + ChatColor.BLUE + " editor");
                    editInv.openInv(whoClicked);
                }
                break;
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
