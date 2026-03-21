package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.RolesLg.DAMNE;
import fr.fitzche.lgmore.RolesLg.DEMON;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class demonChecker implements ResCheck {
	
	
	public PlayerData player;
	public GameLg game;
	public DEMON demon;
	
	public demonChecker(DEMON demon) {
		this.player = demon.playerWithRole;
		this.game = (GameLg) demon.playerWithRole.game;
		this.demon = demon;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		
		
		if (killer.getName().equals(player.getName()) && demon.toRevive.getOrDefault(e.getEntity().getName(), false)) {
			PlayerData p = Main.getData(e.getEntity());
			p.role = RolesLg.DAMNE;
			
			
			p.considVill = RolesLg.DAMNE.isConsidVill();
			p.considWolf = RolesLg.DAMNE.isConsidWolf();
			p.camp = Camp.DEMON;
			p.appCamp = Camp.DEMON;
			p.sendMessage(Main.info+ "Vous êtes mort de la main du démon, vous devenez donc une âme damnée et devez gagner avec celui-ci");
			player.sendMessage(Main.info+ "Le joueur "+ p.getName() + " est passée à votre service");
			p.changeHealth(-4);
			return true;
		}
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		return "";
		
	}

	@Override
	public boolean hide(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void beforeDie(PlayerDeathEvent e) {
		if (!e.getEntity().getName().equals(player.getName()) && !Main.getData(e.getEntity()).role.equals(RolesLg.DAMNE)) {
			TextComponent text = new TextComponent();
			text.setText(Main.exclamation + ChatColor.DARK_RED+ "Le joueur "+e.getEntity().getName() + " en cliquant sur ce message, vous perdrez alors 2 coeurs permanants, celui ci deviendra un damné");
			text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg demonRevive "+player.Name)));
			if (player.isOnline) {
				player.player.spigot().sendMessage(text);
			}
			
		}

	}

	@Override
	public int onPlayerDamage(PlayerData attacker, PlayerData attacked) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onVoteEvent(VoteEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddTragic(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddEpic(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddOrat(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		return false;
	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "demonChecker";
	}

}
