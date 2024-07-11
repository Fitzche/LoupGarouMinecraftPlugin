package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import net.md_5.bungee.api.ChatColor;

public class CORBEAU implements RoleInstance{

	public static Camp camp = Camp.Villager;
	public static PlayerData playerWithRole;
	public static int numberOfVote = 0;
	public static PlayerData target;
	public String name = "Corbeau";
	
	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return camp.getColor()+name;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner avec le village, pour ce faire votre vote vous octroyera un bonus à chaque fois que voterez pour un loups, de cette manière: "+ "\n" + "1 vote bien choisi vous donnera 2 pommes dorées" + "\n" + "Un 2e vote judicieux vous octroira 4 pommes dorées" +
				"\n" + "3 bons votes vous donnerons 2 coeurs permanents  "+ "\n"+ "Un quatrième vote correct vous octroira resistance;"+"\n"+"Tous le monde saura pour qui vous avez voté";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub
		
	}
	
	public CORBEAU(PlayerData playerWithRole) {
		this.playerWithRole = playerWithRole;
	}
	
	public void addGoodVoted() {
		playerWithRole.sendMessage("Vous avez voté pour la bonne personne !!!");
		
		if (numberOfVote == 0) {
			playerWithRole.player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
		} else if (numberOfVote == 1) {
			playerWithRole.player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 4));

		} else if (numberOfVote == 2) {
			playerWithRole.player.setMaxHealth(playerWithRole.player.getMaxHealth() + 4);
		} 
		numberOfVote++;
	}

	@Override
	public void giveEffectAllTime() {
		if (numberOfVote > 3) {
			playerWithRole.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0, false, false));
		}
		
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
	public void setEpisodeTrue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}

}
