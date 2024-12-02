package fr.fitzche.lgmore.RolesLg;

import org.bukkit.ChatColor;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;

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

}
