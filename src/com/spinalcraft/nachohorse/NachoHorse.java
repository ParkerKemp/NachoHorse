package com.spinalcraft.nachohorse;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.spinalcraft.spinalpack.Co;
import com.spinalcraft.spinalpack.Spinalpack;


public class NachoHorse extends JavaPlugin implements Listener{

	static ConsoleCommandSender console;
	
	@Override
	public void onEnable(){
		console = Bukkit.getConsoleSender();
		
		console.sendMessage(Spinalpack.code(Co.BLUE) + "NachoHorse online!");
		getServer().getPluginManager().registerEvents((Listener)this,  this);
		Spinalpack.createPetTable();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		return false;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
		if(!(event.getRightClicked() instanceof Horse))
			return;
		
		Horse horse = (Horse)event.getRightClicked();
		String ownerName = Spinalpack.petOwner(horse.getUniqueId());
		
		if(ownerName == null)
			return;
		
		Player player = event.getPlayer();
		
		if(!player.getName().equals(ownerName)){
			event.setCancelled(true);
			player.sendMessage(Spinalpack.code(Co.RED) + "That's not your horse!");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityTame(EntityTameEvent event){
		if(!(event.getEntityType() == EntityType.HORSE))
			return;
		if(!(event.getOwner() instanceof Player))
			return;
		Horse horse = (Horse)event.getEntity();
		Player player = (Player)event.getOwner();
		Spinalpack.insertPet(player, horse);
	}
	
	@Override
	public void onDisable(){
		
	}
}
