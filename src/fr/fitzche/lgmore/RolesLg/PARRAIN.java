package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.LG.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.ParrainChecker;
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
        this.game.resCheckers.add(new ParrainChecker(game, this));

	}

    public void setNexTarget(PlayerData playerD) {
        playersList = new ArrayList<>();
        int x = 1;
        if (GameLgUtil.getPlayersWithOutCamp(game, playerD.camp).size()<1) {
        	x = GameLgUtil.getPlayersWithOutCamp(game, playerD.camp).size();
        }
        
    


        for (int i = 0; i < x; i++) {
            playersList.add(GameLgUtil.getAlPlayerWithoutCampAnd(game, playersList, playerD.camp));
            
        }
        this.target = playerD;
        for (PlayerData p: playersList) {
        	p.sendMessage("Vous obtiendrez une prime en tuant "+ this.target.Name);
        }
        playerWithRole.sendMessage("Vous mettez un prime sur "+ this.target.Name);
        this.target.sendMessage("Le parrain a mis une prime sur vous... Attention");

    }

	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Villageois, pour cela vous pouvez avec la commande /lg prime [nomDuJoueur], la prime sera envoyé à 1 joueurs au hasard du camp adverse à celui du joueur visé, si la cible est tué par ce joueur, vous et le tueur gagnerez chacun 1/2 coeur et 5% de force");
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
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));

			}
			
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
		if (game.timer.temps < 2300) {
			powerUsed = true;
		}else {
			powerUsed = false;
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
                player.setMaxHealth(player.getMaxHealth() + 1);
                player.boostS5 ++;
            }
        }
        playerWithRole.setMaxHealth(playerWithRole.getMaxHealth() + 1);
        playerWithRole.boostS5 ++;
        playerWithRole.sendMessage("Votre cible est morte, vous gagner donc 1/2 coeur et 5% de force");

    }

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur est parrain, il ne pourra pas mettre de prime à cet épisode");
		for (PlayerData p: this.playersList) {
			p.sendMessage(ChatColor.GOLD+"La prime a été retirée");
		}
		this.playerWithRole.sendMessage(ChatColor.GOLD+ "Vous avez été aveuglé, vous ne pourrez pas mettre de prime à cet épisode, si cela est déjà fait, celle-ci sera retirée");
		this.playersList = new ArrayList<PlayerData>();
		this.target = null;
		this.powerUsed = true;
	}

	@Override
	public boolean isInfoRole() {
		
		return true;
	}
}
