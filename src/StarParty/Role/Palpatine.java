package StarParty.Role;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;

public class Palpatine implements RoleStar {

	PlayerData palpa;
	public int use = 2;
	public boolean transfo = false;
	public Palpatine(PlayerData palpa) {
		this.palpa = palpa;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onRole() {
		Player p = palpa.player;
		ItemStack sword = new ItemStack(Material.NETHER_STAR);
		ItemUtil.setName(sword, ChatColor.UNDERLINE+"Lightning");
		ItemUtil.addAppaEnchant(sword);
		
		p.getInventory().addItem(sword);
		ItemStack sith = new ItemStack(Material.NETHER_STAR);
		ItemUtil.setName(sith, ChatColor.UNDERLINE+"Sith");
		ItemUtil.addAppaEnchant(sith);
		
		p.getInventory().addItem(sith);

	}

}
