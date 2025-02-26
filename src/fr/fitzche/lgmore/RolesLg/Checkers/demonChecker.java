package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

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

public class demonChecker implements ResCheck {
	
	
	public PlayerData player;
	public GameLg game;
	public DEMON demon;
	
	public demonChecker(DEMON demon) {
		this.player = demon.playerWithRole;
		this.game = Main.game;
		this.demon = demon;
	}

	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		for (PlayerData p:demon.pactedPlayers) {
			if (p.getName().equals(e.getEntity().getName())) {
				p.role = RolesLg.DAMNE;
				p.aura = Aura.OBSCUR;
				p.roleIn = new DAMNE(p);
				DAMNE damn = (DAMNE) p.roleIn;
				damn.demon = demon;
				demon.team.add(p);
				p.boostS5 -= 20;
				p.team = demon.team;
				demon.playerWithRole.changeHealth(2);
				demon.playerWithRole.sendMessage(ChatColor.DARK_RED+"Le joueur "+ p.getName()+ " est mort, vous récupérez donc son âme, il doit gagner avec vous, mais possède weakness de manière permanente");
				p.sendMessage(ChatColor.DARK_RED+ "Vous êtes mort, votre âme revient donc au démon avec qui vous avez passé un pacte, faites /lg role pour plus d'info");
				return true;
			}
		}
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		if (e.getEntity().getName().equals(demon.playerWithRole.getName())) {
			for (PlayerData p:demon.pactedPlayers) {
				p.sendMessage(ChatColor.DARK_RED+ "Le démon avez qui vous avez pactisé est mort, vous regagnez donc 1 coeur permanent sans perdre vos avantages");
				p.changeHealth(2);
			}
		}

	}

	@Override
	public boolean hide(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void beforeDie(PlayerDeathEvent e) {
		// TODO Auto-generated method stub

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

}
