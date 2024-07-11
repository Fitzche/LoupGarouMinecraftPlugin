package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class PYROMANE implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Pyromane";
    public Camp camp = Camp.Other;
    public boolean fireAspect = false;
    public boolean powerUsed = false;
    public boolean inFire = false;
    public ArrayList<PlayerData> filled = new ArrayList<PlayerData>();

	public PYROMANE(PlayerData player) {
		GameLg game = GameLgUtil.getGameOfPlayer(player, "at pyromane creating");
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		playerWithRole.team = new Team("Pyromane", Camp.Other, game, players, null, "at wolf team creating at == 1200", null, null, true, false, false, false);
		GameLgUtil.getGameOfPlayer(playerWithRole, "at pyromane creating").teams.add(playerWithRole.team);	
		this.playerWithRole = player;
	}


    public void recouvrir(PlayerData player) {
        if (player != null && filled.size()< 2) {
            filled.add(player);
        }
        playerWithRole.sendMessage("Vous avez recouvert "+ player.Name + " d'essence");
    }

    public void fireaspect() {
        this.fireAspect = !fireAspect;
        if (fireAspect) {
            playerWithRole.sendMessage("Vous avez activé votre fire aspect et votre flamme");
        } else {
            playerWithRole.sendMessage("Vous avez désactivé votre fire aspect et votre flamme");
        }
    }

    @Deprecated
    public void allumer() {
        if (powerUsed == true) {
        	playerWithRole.sendMessage("Vous ne pouvez allumer vos cibles qu'une seule fois");
            return;
        } 
        playerWithRole.sendMessage("Vous avez allumé vos cibles");
        ArrayList<PlayerData> tempFired = new ArrayList<PlayerData>();

        for (PlayerData target:GameLgUtil.getGameOfPlayer(playerWithRole, "at allumer() of Pyromane").getPlayerAlive()) {
            if (LocationUtil.getDistanceBetween(target, filled.get(0) )< 20 ||LocationUtil.getDistanceBetween(target, filled.get(0) )< 20) {
                tempFired.add(target);
            }
        }

        filled.addAll(tempFired);

        inFire = true;
        Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

            @Override
            public void run() {
                inFire = false;
            }

        }, 400);

        powerUsed = true;
    }
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner tout seul, pour cela vous avez 15% de chance d'enflammer toute personne que vous taper ou toucher avec une flèche (pouvoir activable/desactivable avec /lg fireaspect), de plus vous pouvez 2 fois dans la partie recouvrir d'essence un joueur à moins de 20 blocs (avec la commande /lg recouvrir nomDuJoueur), puis enflammer tous les joueurs recouvert avec /lg enflammer, ce qui aura pour effet de mettre en feu le joueur pendant 20s ainsi que tous les joueurs se trouvant à moins de 20 blocs de celui-ci, vous possédez fire protection de manière permanente ");
	}
	

	
	public void giveEffectAllTime() {
		playerWithRole.player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 60, false, false));
        if (inFire) {
            for (PlayerData player: filled) {
                player.player.setFireTicks(40);
            }
        }
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	
	
	@Override
	public void giveNightEffect() {
		if (this.playerWithRole.infected) {
			System.out.println("nk.1");
			if (playerWithRole == null) {
				System.out.println("effect can't be gived at null player");
			}
			PotionUtil.giveIncreaseDamage(playerWithRole);
			
			//VOIR SCHEDULER + EFFECT = ERROR ???
			System.out.println("nk.2");
		}
		
		
	}

	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startSpecialEvent() {
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
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}
}
