package net.iamlupo.teleport.command;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import net.iamlupo.teleport.Main;
import net.iamlupo.teleport.Position;
import net.iamlupo.teleport.Request;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class TeleportRequest implements ICommand {

	private List aliases;
	
	public TeleportRequest()
	{
		this.aliases = new ArrayList();
		this.aliases.add("teleportrequest");
	}

	@Override
	public String getCommandName() {
		return "teleportrequest";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "teleportrequest <player>";
	}

	@Override
	public List getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		if(astring.length != 1) {
			icommandsender.addChatMessage(new ChatComponentText("How to use teleportrequest:"));
			icommandsender.addChatMessage(new ChatComponentText("/teleportrequest <player>"));
		}
		else
		{
			ArrayList<EntityPlayerMP> allPlayers = this.getPlayers(astring[0]);
			if(allPlayers.size() == 1)
			{
				EntityPlayerMP player = (EntityPlayerMP)icommandsender;
				EntityPlayerMP target = allPlayers.get(0);
				
				UUID playerUuid = player.getPersistentID();
				UUID targetUuid = target.getPersistentID();
				
				String targetName = target.getCommandSenderName();
				
				if(targetName.equals(astring[0]) && !playerUuid.equals(targetUuid)) {
					Main.requests.add(new Request(playerUuid, targetUuid));
					
					player.addChatMessage(new ChatComponentText("Teleport Request send to " + targetName));
					target.addChatMessage(new ChatComponentText(player.getCommandSenderName() + " send you a teleport Request."));
					target.addChatMessage(new ChatComponentText("Type: /teleportaccept or /teleportdeny"));
				}
			}
		}
	}
	
	private ArrayList<EntityPlayerMP> getPlayers(String name)
	{
		ArrayList<EntityPlayerMP> allPlayers = new ArrayList<EntityPlayerMP>();
		ListIterator itl;

		for(int i = 0; i<MinecraftServer.getServer().worldServers.length; i++) {
			itl = MinecraftServer.getServer().worldServers[i].playerEntities.listIterator();
			
			while(itl.hasNext()) {
				EntityPlayerMP player = (EntityPlayerMP)itl.next();
				if(player.getCommandSenderName().contains(name))
					allPlayers.add(player);
			}
		}
		
		return allPlayers;
	}
 
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
	{
		if(astring.length == 1) {
			ArrayList<String> messages = new ArrayList<String>();
			ArrayList<EntityPlayerMP> allPlayers = this.getPlayers(astring[0]);
			
			for (EntityPlayerMP player : allPlayers) {
				//filter own user
				messages.add(player.getCommandSenderName());
			}
			
			return messages;
		}
		
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}
	
	@Override
	public int compareTo(Object o)
	{
		return 0;
	}
	
}
