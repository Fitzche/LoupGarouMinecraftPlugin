package fr.fitzche.lgmore.RolesLg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.NecroChecker;
import net.md_5.bungee.api.ChatColor;

public class NECROMANCIEN implements RoleInstance {

	
	public PlayerData playerWithRole;
	public PlayerData target;
	public boolean powerUsed = false;
	public GameLg game;
	
	public NECROMANCIEN(PlayerData player) {
		this.playerWithRole = player;
		((GameLg)player.game).resCheckers.add(new NecroChecker(this));
		game = (GameLg) player.game;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Nécromancien";
	}

	@Override
	public String getDescription() {
		if (game.necrom) {
			return ChatColor.GOLD + "Vous êtes "+ ChatColor.GREEN + "Nécromancien" + "\n"+ChatColor.GOLD + "NECROMANCIE activée"+ "\n"+ "Vous devez gagner tout seul avec 12 coeurs, les joueurs que vous tuerez réssuciteront avec 7 coeurs et devront gagner avec vous (vous perdrez 1 coeur).";
		}
		return ChatColor.GOLD + "Vous êtes "+ ChatColor.GREEN + "Nécromancien" + "\n"+ ChatColor.GOLD + ", "
				+ "Vous devez gagner tout seul, pour cela vous pouvez ensorceler un joueur à chaque épisode avec la commande /lg necro [nomDuJoueur], si ce joueur meurt durant l'épisode, vous volerez 1 coeur au tueur et connaitrez son rôle.";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveNightEffectCheck() {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveNightEffect() {
		// TODO Auto-generated method stub

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
	public void changeTo(PlayerData player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEpisodeTrue() {
		powerUsed = false;

	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void blind(PlayerData origin) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("necro") && args.length == 2 && !game.necrom && sender.getName().equals(playerWithRole.getName()) && powerUsed) {
			PlayerData p = Main.getData(args[1]);
			if (p==null ) {
				return;
			}
			if (powerUsed) {
				sender.sendMessage("Votre cible est déjà définie");
				return;
			}
			this.target = p;
			powerUsed = true;
			
		}

	}

}
