package fr.fitzche.lgmore.RolesLg;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;

public class LOUP_MANIPULATEUR implements RoleInstance {

	
	public int powerUsed = 3;
	public PlayerData playerWithRole;
	
	
	
	public LOUP_MANIPULATEUR(PlayerData p) {
		this.playerWithRole = p;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Loup Manipulateur";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner avec les loups-garou, vous ne possédez pas force I de nuit. Vous pourrez 3 fois dans la partie aveugler un joueur avec la commande /lg aveugler [nomDuJoueur], si le joueur aveuglé possède un role \"à info\", vous connaitrez son rôle et son pouvoir sera mis en pause pour l'épisode.";
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
		// TODO Auto-generated method stub

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
		if (args[0].equals("aveugler")) {
			
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = Main.getData(sender);
				if (senderPlayerData != null) {
					GameLg gameOfSender = (GameLg)senderPlayerData.game;
					if (gameOfSender != null) {
						if (senderPlayerData.getName().equals(playerWithRole.getName())) {
							
							
							if (powerUsed == 0) {
								sender.sendMessage(ChatColor.RED+"Il ne vous reste plus assez d'utilisation");
								return;
							}
							Main.getData(args[1]).roleIn.blind(senderPlayerData);
							if (!Main.getData(args[1]).roleIn.isInfoRole()) {
								playerWithRole.sendMessage("Ce n'est pas un role à info");
							}
							powerUsed --;
							gameOfSender.addorat(5, playerWithRole.getLocation());
							return;
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return;
				}
				
			} 
		}
		
	}

}
