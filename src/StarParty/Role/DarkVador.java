package StarParty.Role;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;

public class DarkVador implements RoleStar {

	public PlayerData vador;
	public boolean sith = false;
	
	public DarkVador(PlayerData vador) {
		this.vador = vador;
	}
	
	public int strangle = 2;
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		if (attacker.getName().equals(vador.getName())) {
			return 1.15;
		}
		return 1;
	}

	@Override
	public void onRole() {
		ItemStack sword = new ItemStack(Material.NETHER_STAR);
		ItemUtil.setName(sword, ChatColor.UNDERLINE+"Strangle");
		ItemUtil.addAppaEnchant(sword);
		Player p = vador.player;
		p.getInventory().addItem(sword);
		
		ItemStack sword2 = new ItemStack(Material.NETHER_STAR);
		ItemUtil.setName(sword2, ChatColor.UNDERLINE+"Sith");
		ItemUtil.addAppaEnchant(sword2);
		
		vador.player.getInventory().addItem(sword2);

	}

}
