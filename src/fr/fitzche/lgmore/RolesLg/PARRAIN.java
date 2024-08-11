package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class PARRAIN implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Parrain";
	public Camp camp = Camp.Villager;
    public GameLg game;
    public boolean powerUsed = false;

    public ArrayList<PlayerData> playersList = new ArrayList<>();
    public PlayerData target;

	public PARRAIN(PlayerData player) {
		this.playerWithRole = player;
        this.game = GameLgUtil.getGameOfPlayer(player, "at parrain creation");

	}

    public void setNexTarget(PlayerData playerD) {
        playersList = new ArrayList<>();
        int x = 5;
        if (GameLgUtil.getPlayersWithOutCamp(game, playerD.camp).size()<5) {
        	x = GameLgUtil.getPlayersWithOutCamp(game, playerD.camp).size();
        }
        
    


        for (int i = 0; i < x; i++) {
            playersList.add(GameLgUtil.getAlPlayerWithoutCampAnd(game, playersList, playerD.camp));
        }

        this.target = playerD;

    }

	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Villageois, pour cela vous pouvez à chaque épisode mettre une prime sur la tête d'un joueur à chaque épisode, qui sera envoyé à 5 joueurs au hasard du camp adverse à ce joueur, si ce joueur est tué par un de ces 5 joueurs, vous ainsi que le tueur gagnerez 1/2 coeur et 5% de force");
	}
	public static ItemStack logo = new ItemStack(Material.STONE_SWORD);

	
	public void giveEffectAllTime() {
		//null
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
		if (game.timer.temps < 3500) {
			powerUsed = true;
		}else {
			playerWithRole.sendMessage("Vous pouvez utiliser la commande /lg prime [nom du joueur] pour mettre une prime sur la tête d'un joueur");
		}
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

    public void targetDeath(String name) {
        for (PlayerData player: this.playersList) {
            if (player.Name.equals(name)) {
                player.sendMessage("Vous avez tué la cible d'une prime, vous gagner donc 5% de force et 1/2 coeur");
                player.player.setMaxHealth(player.player.getMaxHealth() + 1);
                player.boostS5 ++;
            }
        }
        playerWithRole.player.setMaxHealth(playerWithRole.player.getMaxHealth() + 1);
        playerWithRole.boostS5 ++;
        playerWithRole.sendMessage("Votre cible est morte, vous gagner donc 1/2 coeur et 5% de force");

    }
}
