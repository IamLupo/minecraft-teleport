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

public class TeleportAccept  implements ICommand {
	private List aliases;
	
	public TeleportAccept()
	{
		this.aliases = new ArrayList();
		this.aliases.add("teleportaccept");
	}

	@Override
	public String getCommandName() {
		return "teleportaccept";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "teleportaccept";
	}

	@Override
	public List getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		if(astring.length != 0) {
			icommandsender.addChatMessage(new ChatComponentText("How to use teleportaccept:"));
			icommandsender.addChatMessage(new ChatComponentText("/teleportaccept"));
		}
		else
		{
			EntityPlayerMP target = (EntityPlayerMP)icommandsender;
			
			Request request = new Request(UUID.fromString("00000000-0000-0000-0000-000000000000"), target.getPersistentID());
			int idPosition = Main.requests.indexOf(request);
			if(idPosition != -1) {
				request = Main.requests.get(idPosition);
				
				ArrayList<EntityPlayerMP> players = getPlayers(request.player);
				
				if(players.size() == 1)
				{
					EntityPlayerMP player = players.get(0);
					
					if(player.dimension != target.dimension)
						player.mcServer.getConfigurationManager().transferPlayerToDimension(player, target.dimension);
					player.setPositionAndUpdate(target.posX, target.posY, target.posZ);
					
					icommandsender.addChatMessage(new ChatComponentText("You have been teleported!"));
				}
				else
				{
					icommandsender.addChatMessage(new ChatComponentText("Can not find player how send you a request."));
				}
				
				Main.requests.remove(request);
			}
			else
			{
				icommandsender.addChatMessage(new ChatComponentText("No request found to accept."));
			}
		}
	}
	
	private ArrayList<EntityPlayerMP> getPlayers(UUID _uuid)
	{
		ArrayList<EntityPlayerMP> allPlayers = new ArrayList<EntityPlayerMP>();
		ListIterator itl;

		for(int i = 0; i<MinecraftServer.getServer().worldServers.length; i++) {
			itl = MinecraftServer.getServer().worldServers[i].playerEntities.listIterator();
			
			while(itl.hasNext()) {
				EntityPlayerMP player = (EntityPlayerMP)itl.next();
				if(player.getPersistentID().equals(_uuid))
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
