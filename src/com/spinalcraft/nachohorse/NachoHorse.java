package com.spinalcraft.nachohorse;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
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
	}
	
	
	
	@Override
	public void onDisable(){
		
	}
}
