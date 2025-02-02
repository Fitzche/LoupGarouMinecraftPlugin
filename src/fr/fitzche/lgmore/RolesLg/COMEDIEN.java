package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.RegisterType;
import fr.fitzche.lgmore.RolesLg.Checkers.ComedienChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class COMEDIEN implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Comédien";
	public Camp camp = Camp.Villager;
	public GameLg game;
	
	public COMEDIEN(PlayerData player) {
		this.playerWithRole = player;
		this.game = GameLgUtil.getGameOfPlayer(player, "at role creation: ");
		this.game.resCheckers.add(new ComedienChecker(this));
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Villageois, pour cela, votre pouvoir change en fonction du registre de la pièce. Si le registre tragique est à 20%, vous aurez 20% de chance de connaitre l'aura d'un joueur, 20% de chance de connaitre son nombre de kill, et 20% de chance de savoir s'il a un effet. Vous avez une résistance proportionnelle à taux d'Epique, (20% d'epique = 4% de resistance, 50% = 10% et 100% = 20%). Pour un taux de oratoire à 20%, vous aurez 20% de chance de connaitre l'ensemble des pseudo des joueurs ayant voté pour la personne la + votée.");
	}
	public static ItemStack logo = new ItemStack(Material.WHEAT);

	
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
			
			
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));
			}
			
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
		if (game.getRegister() == null || game.getRegister().getType() == null) {
			return;
		}
		if (game.getRegister().getType().equals(RegisterType.Tragic)) {
			
			PlayerData p = GameLgUtil.getAlPlayer(game);
			if (MathUtil.pourcentage(game.getTragic())) {
				playerWithRole.sendMessage(ChatColor.LIGHT_PURPLE+"L'aura du joueur "+ p.getName()+ " est "+p.aura.getName());
			}
			if (MathUtil.pourcentage(game.getTragic())) {
				playerWithRole.sendMessage(ChatColor.LIGHT_PURPLE+"Le joueur "+ p.getName()+ " a tué "+p.numberOfKill+ " kills.");

			}
			if (MathUtil.pourcentage(game.getTragic())) {
				for (PotionEffect ef:p.player.getActivePotionEffects()) {
					
					if (ef.getType().equals(PotionEffectType.DAMAGE_RESISTANCE)) {	
						playerWithRole.sendMessage(ChatColor.LIGHT_PURPLE+"Le joueur "+ p.getName()+ " possède un effet ");
					}
					if (ef.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {	
						playerWithRole.sendMessage(ChatColor.LIGHT_PURPLE+"Le joueur "+ p.getName()+ " possède un effet ");
					}
					if (ef.getType().equals(PotionEffectType.SPEED)) {	
						playerWithRole.sendMessage(ChatColor.LIGHT_PURPLE+"Le joueur "+ p.getName()+ " possède un effet ");
					}
					
				}
				playerWithRole.sendMessage(ChatColor.LIGHT_PURPLE+"Le joueur "+ p.getName()+ " ne possède pas d'effet ");

			}
			
			
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

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur n'est pas un rôle à info");
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}
}
