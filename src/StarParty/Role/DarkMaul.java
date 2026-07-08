package StarParty.Role;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;

public class DarkMaul implements RoleStar {

	public PlayerData maul;
	public boolean sith = false;
	public boolean used = false;
	public DarkMaul(PlayerData maul) {
		this.maul = maul;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		
		return 1;
	}

	@Override
	public void onRole() {
		ItemStack sword = new ItemStack(Material.NETHER_STAR);
		ItemUtil.setName(sword, ChatColor.UNDERLINE+"Sith");
		ItemUtil.addAppaEnchant(sword);
		
		maul.player.getInventory().addItem(sword);

	}

}
