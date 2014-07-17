package com.spinalcraft.nachohorse;

import java.util.Hashtable;

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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.spinalcraft.spinalpack.Co;
import com.spinalcraft.spinalpack.Spinalpack;

class PlayerFlags{
	private static Hashtable<String, PlayerFlags> playerFlags = new Hashtable<String, PlayerFlags>();
	public boolean willClaim;
	
	public PlayerFlags(){
		willClaim = false;
	}
	
	public static void add(Player player){
		playerFlags.put(player.getName(), new PlayerFlags());
	}
	
	public static void remove(Player player){
		playerFlags.remove(player.getName());
	}
	
	public static PlayerFlags fromPlayer(Player player){
		return playerFlags.get(player.getName());
	}
}

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
		if(cmd.getName().equalsIgnoreCase("claimhorse")){
			if(sender instanceof Player){
				Player player = (Player)sender;
				PlayerFlags.fromPlayer(player).willClaim = true;
			}
			return true;
		}
		return false;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
		if(!(event.getRightClicked() instanceof Horse))
			return;
		if(!PlayerFlags.fromPlayer(event.getPlayer()).willClaim)
			return;
		console.sendMessage("Clicked horse.");
		
		Player player = event.getPlayer();
		
		PlayerFlags.fromPlayer(player).willClaim = false;
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
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		PlayerFlags.add(player);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		PlayerFlags.remove(player);
	}
	
	@Override
	public void onDisable(){
		
	}
}
