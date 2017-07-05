package br.com.playdreamcraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class RaioMestre extends JavaPlugin implements CommandExecutor, Listener{

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("raiomestre").setExecutor(this);
        info("&bPlugin RaioMestre ativado!");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

    private void info(String msg){
        getServer().getConsoleSender().sendMessage(msg.replaceAll("&", "§"));
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(!cmd.getName().equalsIgnoreCase("raiomestre")) return true;

        if(!(cs instanceof Player)){
            if(args.length == 0){
                this.info("&Especifique um player!");
                return true;
            }
            if(args.length ==1){
                if(Bukkit.getPlayerExact(args[0])!=null){
                    Player p = Bukkit.getPlayerExact(args[0]);
                    if(p.getInventory().firstEmpty() == -1){
                        info("&cEste player está com o inventário cheio!");
                        return true;
                    }
                    p.getInventory().setItem(p.getInventory().firstEmpty(), getItem("&6Raio Mestre", Arrays.asList("&9Uso:", "&7: Ao usar este item você soltará um raio onde estiver olhando")));
                    return true;
                }
            }
        }
        if(args.length == 0){
            Player p = (Player) cs;
            p.getInventory().setItem(p.getInventory().firstEmpty(), getItem("&6Raio Mestre", Arrays.asList("&9Uso:", "&7: Ao usar este item você soltará um raio onde estiver olhando")));
            return true;
        }
        if(args.length ==1){
            if(Bukkit.getPlayerExact(args[0])!=null){
                Player p = Bukkit.getPlayerExact(args[0]);
                if(p.getInventory().firstEmpty() == -1){
                   cs.sendMessage("§cEste player está com o inventário cheio!");
                    return true;
                }
                p.getInventory().setItem(p.getInventory().firstEmpty(), getItem("&6Raio Mestre", Arrays.asList("&9Uso:", "&7: Ao usar este item você soltará um raio onde estiver olhando")));
                return true;
            }
        }
       return false;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() != null && event.getItem().hasItemMeta()){
            if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Raio Mestre")){
                event.getPlayer().getWorld().strikeLightning(event.getPlayer().getTargetBlock((Set<Material>) null, 300).getLocation());
            }
        }
    }

    private ItemStack getItem(String nome, List<String> lore){
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
        ItemMeta meta = item.getItemMeta();
        for(int i = 0; i < lore.size(); i++){
            String fxl = lore.get(i).replaceAll("&", "§");
            lore.set(i, fxl);
        }
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(nome.replaceAll("&", "§"));
        item.setItemMeta(meta);
        return item;
    }
}
