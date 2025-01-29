package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.LOUP_BRUMEUX;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Brume_Checker implements ResCheck {
	LOUP_BRUMEUX brumeux;
	public GameLg game;
	
	public Brume_Checker(LOUP_BRUMEUX lg) {
		this.brumeux = lg;
		this.game = GameLgUtil.getGameOfPlayer(brumeux.playerWithRole, "at brumeux checker");
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hide(PlayerDeathEvent e) {
		if (brumeux.toHide.contains(PlayerUtil.getDataOfPlayer(e.getEntity(), "at hide of brume checker"))) {
			return true;
		}
		return false;
	}
	@Override
	public void beforeDie(PlayerDeathEvent e) {
		TextComponent text = new TextComponent();
		text.setText(e.getEntity().getName() + ChatColor.BLUE+" est mort, vous avez 15s pour "+ChatColor.RED+"cacher sa mort"+ChatColor.BLUE+" ou non en cliquant sur ce message ");
		for (PlayerData ply: RoleUtil.getPlayersWithRole(game, RolesLg.LOUP_BRUMEUX)) {
			text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg hidelgbr "+e.getEntity().getName()+ " "+ ply.Name)));
			ply.player.spigot().sendMessage(text);
			
		}
		
	}
	@Override
	public int onPlayerDamage(PlayerData attacker, PlayerData attacked) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void onVoteEvent(VoteEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAddTragic(int i, Location loc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAddEpic(int i, Location loc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAddOrat(int i, Location loc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
