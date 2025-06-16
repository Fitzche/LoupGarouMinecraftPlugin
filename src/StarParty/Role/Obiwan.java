package StarParty.Role;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;

public class Obiwan implements RoleStar {

	
	PlayerData obi;
	public int use = 2;
	
	public Obiwan(PlayerData obi) {
		this.obi = obi;
	}
	
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		double returned = 1;
		if (defender.Name.equals(obi.Name)) {
			returned -= 0.2;
		}
		return returned;
	}

	@Override
	public void onRole() {
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemUtil.setName(sword, ChatColor.UNDERLINE+"Ataru");
		Player p = obi.player;
		p.getInventory().addItem(sword);

	}

}
