package net.iamlupo.teleport.command;

import java.util.ArrayList;
import java.util.List;

import net.iamlupo.teleport.Main;
import net.iamlupo.teleport.Position;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;


public class Teleport implements ICommand
{
	private List aliases;
	public Teleport()
	{
		this.aliases = new ArrayList();
		this.aliases.add("teleport");
	}

	@Override
	public String getCommandName()
	{
		return "teleport";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "teleport <place>";
	}

	@Override
	public List getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		ChatComponentText chat = new ChatComponentText("Unknown parameters");
		
		if(astring.length == 0) {
			icommandsender.addChatMessage(new ChatComponentText("How to use teleport:"));
			icommandsender.addChatMessage(new ChatComponentText("/teleport <place>"));
			icommandsender.addChatMessage(new ChatComponentText("/teleport set <place>"));
			icommandsender.addChatMessage(new ChatComponentText("/teleport remove <place>"));
		}
		else if(astring.length == 1) {
			if(!astring[0].contentEquals("set") && !astring[0].contentEquals("remove")) {
				EntityPlayerMP player = ((EntityPlayerMP) icommandsender);
				
				int idPosition = Main.positions.indexOf(new Position(player.getPersistentID(), astring[0]));
				if(idPosition != -1) {
					Position pos = Main.positions.get(idPosition);
					player.setPositionAndUpdate(pos.posX, pos.posY, pos.posZ);
					
					//Output
					icommandsender.addChatMessage(new ChatComponentText("teleport to " + astring[0]));
				}
			}
			else if(astring[0].contentEquals("set") || astring[0].contentEquals("remove")) {
				//Output
				icommandsender.addChatMessage(new ChatComponentText("Can not teleport to " + astring[0] + "!"));
				icommandsender.addChatMessage(new ChatComponentText("type: /teleport " + astring[0] + " <place>"));
			}
		}
		else if(astring.length == 2) {
			if(astring[0].contentEquals("set")) {
				EntityPlayerMP player = ((EntityPlayerMP) icommandsender);
				Position pos = new Position(player.getPersistentID(), astring[1]);
				
				//Add
				pos.setPosition(player.posX, player.posY, player.posZ);
				Main.positions.add(pos);
				
				//Output
				icommandsender.addChatMessage(new ChatComponentText("Set new teleport location to " + astring[1]));
			}
			else if(astring[0].contentEquals("remove")) {
				EntityPlayerMP player = ((EntityPlayerMP) icommandsender);
				
				Position pos = new Position(player.getPersistentID(), astring[1]);
				int idPosition = Main.positions.indexOf(pos);
				if(idPosition != -1) {
					
					Main.positions.remove(pos);
					icommandsender.addChatMessage(new ChatComponentText("Removed " + astring[1] + " location!"));
				}
				else
				{
					icommandsender.addChatMessage(new ChatComponentText("Can not remove " + astring[1] + ". Name do not exist!"));
				}
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
	{
		if(astring.length == 1) { //First parameter
			List messages = new ArrayList<String>();
			
			String _set = new String("set");
			if(_set.contains(astring[0]))
				messages.add(_set);
			
			String _remove = new String("remove");
			if(_remove.contains(astring[0]))
				messages.add(_remove);
			
			for (Position pos : Main.positions) {
				if(pos.name.contains(astring[0]))
					messages.add(new String(pos.name));
			}
			
			return messages;
		}
		else if(astring.length == 2) {
			List messages = new ArrayList<String>();
			
			for (Position pos : Main.positions) {
				if(pos.name.contains(astring[1]))
					messages.add(new String(pos.name));
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
