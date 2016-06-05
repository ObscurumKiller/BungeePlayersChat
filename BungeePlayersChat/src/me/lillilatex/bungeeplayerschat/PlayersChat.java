package me.lillilatex.bungeeplayerschat;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.TabExecutor;

public class PlayersChat extends Command implements Listener, TabExecutor
{
	public PlayersChat() 
	{
		super("pchat", "bungeecord.command.playerschat", "playerschat");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) 
	{
		ProxiedPlayer player = (ProxiedPlayer) sender;
		
		if(args.length == 0)
		{
			player.sendMessage("§8[§e§lPlayersChat§8] §cProper Usage: §6/pchat <PlayerName> <message>");
			return;
		}
		
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
			
			if (target == sender)
		    {
				player.sendMessage("§8[§e§lPlayersChat§8] §cYou can not send message yourself!");
				return;
		    }
			if(target == null)
			{
				player.sendMessage("§8[§e§lPlayersChat§8] §cPlayer not found!");
			    return;
			}
			
			StringBuilder message = new StringBuilder();
		    for (int i = 1; i < args.length; i++) 
		    {
		      message.append(args[i]).append(" ");
		    }
		    String msg = "";
			
		    /*
			if(msg == " ")
			{
				player.sendMessage("§8[§e§lPlayersChat§8] §cType a Message!");
			}
			*/
		    
		    if(player.hasPermission("bungeecord.playerschat.colors"))
		    {
		    	msg = ChatColor.translateAlternateColorCodes('&', message.toString().trim());
		    }
		    else
		    {
		    	msg = message.toString().trim();
		    }
		    
		    if ((sender instanceof ProxiedPlayer))
		    {
		    	 target.sendMessage("§8[§e§lPlayersChat§8] §6" + player.getName() + " §f§l-> §6" + target.getName() + "§f§l: §8" + msg);
		    	 player.sendMessage("§8[§e§lPlayersChat§8] §6You §f§l-> §6" + target.getName() + "§f§l: §8" + msg);
		    }
		    
	    	ProxyServer.getInstance().getLogger().info("[PlayersChat] : " + player.getName() + " -> " + target.getName() + ": " + msg);
	    	ProxyServer.getInstance().getLogger().info("[PlayersChat] : From Server -> " + player.getServer().getInfo().getName().toUpperCase() + " To Server -> " + target.getServer().getInfo().getName().toUpperCase());
	}
	
	@Override
	public Iterable<String> onTabComplete(CommandSender cs, String[] args)
	{
		if((args.length > 2)) return ImmutableSet.of();
		
		Set<String> match = new HashSet<String>();
		
		if(args.length == 1)
		{
			String search = args[0].toLowerCase();
			
			for(ProxiedPlayer player : BungeeCord.getInstance().getPlayers())
			{
				if(player.getName().toLowerCase().startsWith(search))
				{
					match.add(player.getName());
				}
			}
		}
		return match;
	}
}
