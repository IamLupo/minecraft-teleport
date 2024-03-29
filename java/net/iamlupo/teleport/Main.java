package net.iamlupo.teleport;

import java.util.UUID;

import net.iamlupo.teleport.command.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;


@Mod(modid = Main.MODID, version = Main.VERSION)

public class Main {
	public static final String MODID = "teleport";
    public static final String VERSION = "0.2.2";
    
    public static Positions positions = new Positions();
    public static Requests requests = new Requests();
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    	event.registerServerCommand(new Teleport());
    	event.registerServerCommand(new TeleportRequest());
    	event.registerServerCommand(new TeleportAccept());
    }
    
    @EventHandler
    public void PreInit(FMLInitializationEvent event)
    {
    	
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
    	
    }
}
