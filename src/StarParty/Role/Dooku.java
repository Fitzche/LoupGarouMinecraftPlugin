package StarParty.Role;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import StarParty.RoleStar;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LineLocationHelper;

public class Dooku implements RoleStar {

	
	public PlayerData dooku;
	public boolean duel = false;
	public String opponent = "";
	public Dooku(PlayerData dooku) {
		this.dooku = dooku;
	}
	@Override
	public double onDamage(PlayerData attacker, PlayerData defender) {
		if ((attacker.getName().equals(opponent) || attacker.getName().equals(dooku.getName())) ) {
			if (!(defender.getName().equals(opponent) || defender.getName().equals(dooku.getName()))) {
				attacker.sendMessage("Vous êtes en duel, vous ne pouvez attaquez que votre adversaire");
				LineLocationHelper.applyKnockback(attacker.player, defender.player, 1);
				return 0;
			}
		} 
		if (defender.getName().equals(dooku.getName()) || defender.getName().equals(opponent)) {
			if (!(attacker.getName().equals(dooku.getName()) || attacker.getName().equals(opponent))) {
				attacker.sendMessage("Ce joueur est en duel, vous ne pouvez pas l'attaquer");
				LineLocationHelper.applyKnockback(attacker.player, defender.player, 1);
				return 0;
			}
		}
		
		if (attacker.getName().equals(dooku.getName()) && defender.roleStar.hasLightsaber) {
			return 1.15;
		}
		return 1;
	}

	@Override
	public void onRole() {
		ItemStack sword = new ItemStack(Material.NETHER_STAR);
		ItemUtil.setName(sword, ChatColor.UNDERLINE+"Makashi");
		ItemUtil.addAppaEnchant(sword);
		Player p = (Player) dooku.player;
		p.getInventory().addItem(sword);

	}

}
