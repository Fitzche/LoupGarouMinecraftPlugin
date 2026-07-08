package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.custom.CustomGame;
import fr.fitzche.lgmore.custom.CustomGameType;

public class JoinChoose implements InvFunct {

	
	public JoinChoose(PlayerData p, Inventory backInv) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		ArrayList<CustomGameType> typeList = CustomGameType.getUnslotValues();
		for (String gamemode:Main.dispoGamemodes) {
			CustomGameType type = null;
			try {
				type = CustomGameType.getEmptySlot(typeList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (type != null) {
				type.setName(gamemode);
				typeList.add(type);
			} else {
				p.sendMessage("mode de jeu non chargé");
			}
			
		}
		
		
		for (CustomGameType type:typeList) {
			items.add(type.getItem());
		}
		
		StringChooseInv inv = new StringChooseInv(p, items, backInv, this);
	}
	@Override
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores, ItemStack returnedItem) {
		
		
		
		
		
		
		
		for (CustomGameType type:CustomGameType.values()) {
			if (clickedName.equals(type.getItem().getItemMeta().getDisplayName())) {
				if (Main.games.get(type) == null) {
					Main.games.put(type, new ArrayList<CustomGame>(Arrays.asList(type.createGame())));
					p.sendMessage("Veuillez rééessayer");				
				} else {
					if (Main.games.get(type).size() < 1) {
						p.sendMessage("Veuillez rééessayer après quelques secondes");
						Main.games.get(type).add(type.createGame());
							
					} else {
						if (p.isFree()) {
							for (CustomGame g:Main.games.get(type)) {
								if (g.canJoin(p)) {
									
									if (type.equals(CustomGameType.SettlerGame)) {
										g.addPlayer(p);
										p.game = g;
										return;
									}
									if (type.isSlot()) {
										g.addPlayer(p);
										p.game = g;
										return;
									}
									//g.addPlayer(p);
									p.sendMessage("bloqué temporairement (ou pas), et si t pas content, et bah faut DEGAGER !!!");
									return;
									
									
								}
							}
						}
					}
				}
			}
		}

	}

}
