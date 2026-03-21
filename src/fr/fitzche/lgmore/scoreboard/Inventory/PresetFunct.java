package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.Util.ItemUtil;

public class PresetFunct implements InvFunct {
	
	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public PresetFunct(PlayerData p, Inventory backInv) {
		
		

		
		
		setItem(0, Material.WHEAT, "Classic", new ArrayList<String>(Arrays.asList(
				"Compo et events classiques (25 Joueurs)"
				)));
		
		
		StringChooseInv inv = new StringChooseInv(p	, items, backInv, this);

		
		if (inv.invs.size() > 0 && p.isOnline) {
			p.player.openInventory(inv.invs.get(0));
		}
	}
	
	public void setItem(int i, Material mat, String str, ArrayList<String> lores) {
		ItemStack item = new ItemStack(mat);
		if (lores != null) {
			ItemUtil.setLore(item, lores);
		}
		
		ItemUtil.setName(item, str);
		this.items.add(item);
	}

	@Override
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores,  ItemStack returnedItem) {
		
		if (!(game instanceof GameLg)) {
			return;
		}
		GameLg game1 = (GameLg) game;
		switch (clickedName) {
		case "Classic":
			
			switch (game1.players.size()) {
				case 25:
					game1.roles = new ArrayList<RolesLg>(Arrays.asList(
							RolesLg.DISCIPLE,
							RolesLg.SAGE,
							RolesLg.MONTREUR,
							RolesLg.ALLUMEUR,
							
							RolesLg.PARRAIN,
							RolesLg.SORCIERE,
							RolesLg.SALVATEUR,
							
							RolesLg.SOEUR,
							RolesLg.SOEUR,
							RolesLg.ANCIEN,
							RolesLg.ERMITE,
							
							RolesLg.IDIOT_DU_VILLAGE,
							RolesLg.SIMPLE_VILLAGER,
							
							RolesLg.PYROMANE,
							RolesLg.ANGE,
							RolesLg.ASSASSIN, 
							
							RolesLg.INFECT_PERE_DES_LOUPS,
							RolesLg.LOUP_BRUMEUX, 
							RolesLg.LOUP_CRAINTIF,
							RolesLg.LOUP_HURLEUR,
							RolesLg.LOUP_MYSTIQUE,
							RolesLg.SIMPLE_WOLF,
							
							RolesLg.CUPIDON,
							RolesLg.SORCIER,
							RolesLg.ENFANT_SAUVAGE
							
							
							
							));
					
					game1.probasEvents.put("Exposed"	, 20);
					game1.probasEvents.put("Brume"	, 5);
					game1.probasEvents.put("Premonition"	, 5);
					game1.probasEvents.put("Loup Solitaire"	, 20);
					game1.probasEvents.put("Nombre Batiments Leurre"	, 10);
					game1.probasEvents.put("Nombre Batiments à Bonus"	, 10);
					game1.probasEvents.put("AutomaticCheckWin"	, 100);
					
					break;
			}
			break;
		}

	}

}
