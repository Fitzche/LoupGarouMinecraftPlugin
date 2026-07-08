package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;

import fr.fitzche.lgmore.RolesLg.Checkers.VoleurChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import net.md_5.bungee.api.ChatColor;

public class VOLEUR implements RoleInstance{
	
	public PlayerData playerWithRole;
	public String name ="Voleur";
	public Camp camp = Camp.Other;
	
	public VOLEUR(PlayerData player) {
		this.playerWithRole = player;
		GameLg game = (GameLg) player.game;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		
		game.resCheckers.add(new VoleurChecker(this, game, null));
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (camp.getColor() + name);
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Main.info +ChatColor.BLUE+"Vous devez gagner tout seul"+ "\n"+ "Pour cela vous posséder force de manière permanente, et vous prendrez l'identité et le role du premier joueur que vous tuerez.";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		playerWithRole.boostS5 += 4;
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		giveNightEffect();
		
	}

	@Override
	public void giveNightEffect() {
		
		
	}

	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void episodeEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEpisodeTrue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}
	
	public void steal(PlayerData stealed) {
		if (stealed.isOnline) {
			for (PotionEffect effect: stealed.player.getActivePotionEffects()) {
				this.playerWithRole.addPotionEffect(effect);
			}
		}
		
		if (stealed.role.getCampOfRole().equals(Camp.Wolf)) {
			
			
			for (PlayerData p: ((GameLg)stealed.game).getFalseWolfAlive()) {
				p.sendMessage(ChatColor.RED+"Le Joueur "+ playerWithRole.getName()+ " a rejoint votre camp");
			}
		}
		playerWithRole.sendMessage(ChatColor.GOLD+"Vous avez volé le role de "+ stealed.getName() + " qui était "+ stealed.role.getName());
		((GameLg)playerWithRole.game).setRole(playerWithRole, stealed.role);
		
		
		
        
		


		
	}

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur n'est pas un rôle à info");
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
