package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import net.md_5.bungee.api.ChatColor;

public class CORBEAU implements RoleInstance{

	public static Camp camp = Camp.Villager;
	public PlayerData playerWithRole;
	public int numberOfVote = 0;
	public PlayerData target;
	public static String name = "Corbeau";
	
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
		return Main.info +ChatColor.BLUE+"Vous devez gagner avec le village, pour ce faire votre vote vous octroyera un bonus à chaque fois que voterez pour le joueur le + voté, de cette manière: "+ "\n" + "1 vote bien choisi vous donnera 2 pommes dorées" + "\n" + "Un 2e vote judicieux vous octroira 4 pommes dorées" +
				"\n" + "3 bons votes vous donnerons 2 coeurs permanents  "+ "\n"+ "Un quatrième vote correct vous octroira resistance 0.5;"+"\n"+"Dans ce cas, votre vote sera rendu publique.";
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
			playerWithRole.changeHealth(2);
		} else if (numberOfVote == 3) {
			playerWithRole.changeHealth(2);
		}
		numberOfVote++;
	}

	@Override
	public void giveEffectAllTime() {
		
		
	}

	@Override
	public void giveNightEffectCheck() {
		giveNightEffect();
		
	}

	@Override
	public void giveNightEffect() {
		
		
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

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur n'est pas un rôle à info");
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
