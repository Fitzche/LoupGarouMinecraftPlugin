package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.commands.SpecialItemHolder;

public class ShopInv implements InvFunct {

	
	HashMap<String, Integer> deathAnnounces = new HashMap<String, Integer>();
	HashMap<String, Integer> grades = new HashMap<String, Integer>();
	HashMap<String, Integer> itemsS = new HashMap<String, Integer>();
	HashMap<String, ArrayList<String>> deathAnnouncesC = new HashMap<String, ArrayList<String>>();
	HashMap<String,ArrayList<String>> gradesC = new HashMap<String, ArrayList<String>>();
	HashMap<String, ArrayList<String>> itemsSC = new HashMap<String, ArrayList<String>>();
	
	
	public ShopInv(PlayerData p, Inventory backInv) {
		
		
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		deathAnnounces.put("avec un blue en pleine face ", 12);
		
		grades.put("TOJI", 30);
		gradesC.put("TOJI", new ArrayList<String>(Arrays.asList(ChatColor.AQUA + "Conditions: " , ChatColor.GRAY + "-Avoir un ratio d'arène PvP supérieur à 1.5", ChatColor.GRAY +"-avoir plus de 50 kills en PvP Zone", ChatColor.GRAY +"-avoir éveillé Toji")));
		
		grades.put("Loup Garou", 15);
		gradesC.put("Loup Garou", new ArrayList<String>(Arrays.asList(ChatColor.AQUA + "Conditions: " , ChatColor.GRAY + "Avoir gagné une partie de Loup Garou")));
		
		grades.put("Grade 4", 10);
		gradesC.put("Grade 4", new ArrayList<String>(Arrays.asList(ChatColor.AQUA + "Conditions: " , ChatColor.GRAY + "Avoir au moins 300xp")));
		
		grades.put("Grade 3", 10);
		gradesC.put("Grade 3", new ArrayList<String>(Arrays.asList(ChatColor.AQUA + "Conditions: " , ChatColor.GRAY + "Avoir au moins 600xp", ChatColor.GRAY +"Avoir au moins 20kills en zone PvP")));
		
		grades.put("Grade 2", 20);
		gradesC.put("Grade 2", new ArrayList<String>(Arrays.asList(ChatColor.AQUA + "Conditions: " , ChatColor.GRAY + "Avoir au moins 1000xp", ChatColor.GRAY + "Avoir au moins 50 kills en zone PvP", ChatColor.GRAY + "Avoir au moins 10 objets spéciaux")));
		
		grades.put("Grade 1", 40);
		gradesC.put("Grade 1", new ArrayList<String>(Arrays.asList(ChatColor.AQUA + "Conditions: " , ChatColor.GRAY + "Avoir au moins 1500xp", ChatColor.GRAY + "Avoir au moins 100 kills en zone PvP", ChatColor.GRAY + "Avoir au moins 20 objets spéciaux", ChatColor.GRAY + "Avoir déjà activé un item spécial de rang S")));
		
		grades.put("Grade S", 100);
		gradesC.put("Grade S", new ArrayList<String>(Arrays.asList(ChatColor.AQUA + "Conditions: " , ChatColor.GRAY + "Avoir au moins 3000xp", ChatColor.GRAY + "Avoir au moins 200 kills en zone PvP", ChatColor.GRAY + "Avoir au moins 40 objets spéciaux", ChatColor.GRAY + "Avoir déjà activé un item spécial de rang S", ChatColor.GRAY + "avoir activé une extension de territoire")));
		
		
		
		itemsS.put("Toji", 10);
		itemsS.put("Alea", 4);
		itemsS.put("Dash", 2);
		
		
		for (String str :deathAnnounces.keySet()) {
		
			ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Annonce de kill", deathAnnounces.get(str)+""+ ChatColor.GOLD+ " Feathers"));
			list1.addAll(deathAnnouncesC.getOrDefault(str, new ArrayList<String>()));
			items.add(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.IRON_SWORD, 1,  ChatColor.GREEN +str, list1))  );
		
		
		}
		for (String str :grades.keySet()) {
			
		
			ArrayList<String> list2 = new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Grades", grades.get(str)+""+ ChatColor.GOLD+ " Feathers"));
			list2.addAll(gradesC.getOrDefault(str, new ArrayList<String>()));
			items.add(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.PAPER, 1,  ChatColor.GREEN +str, list2))  );
		}
		for (String str :itemsS.keySet()) {
			ArrayList<String> list3 = new ArrayList<String>(Arrays.asList(ChatColor.GRAY+"Items Spéciaux", itemsS.get(str)+""+ ChatColor.GOLD+ " Feathers"));
			list3.addAll(itemsSC.getOrDefault(str, new ArrayList<String>()));
			items.add(ItemUtil.addAppaEnchant(ItemUtil.getItem(Material.POTION, 1,  ChatColor.GREEN +str, list3))  );
		}
		
		
		StringChooseInv inv = new StringChooseInv(p, items, backInv, this);
		
		
		
	}
	
	
	
	@Override
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores, ItemStack returnedItem) {
		
		String name = clickedName.substring(2, clickedName.length());
		if (lores.size() > 1) {
			if (lores.get(1).equals(ChatColor.GRAY+"Annonce de kill")) {
				int cost = this.deathAnnounces.get(name);
				for (String str:p.specialDeathAnnounces)  {
					if (str.equals(name) || str.equals(clickedName)) {
						p.sendMessage("Vous possédez déjà ce cosmétique");
						return;
					} 
				}
				
				if (cost <= p.feathers) {
					p.specialDeathAnnounces.add(clickedName);
					p.feathers -= cost;
					p.sendMessage("Vous avez acheté l'annonce de mort: "+ clickedName);
				} else {
					p.sendMessage(Main.exclamation + ChatColor.RED+"Vous n'avez pas assez de Feather");
				}
				
			} else if (lores.get(1).equals(ChatColor.GRAY+"Grades")) {
				int cost = this.grades.get(name);
				
				for (String str:p.grades)  {
					if (str.equals(name) || str.equals(clickedName)) {
						p.sendMessage("Vous possédez déjà ce cosmétique");
						return;
					} 
				}
				
				if (cost <= p.feathers) {
					
					
					if (name.equals("TOJI")) {
						if (p.pvpZoneKill < 50 || (p.pvpZoneDeath != 0 && p.pvpZoneKill / p.pvpZoneDeath < 1.5) || !p.toji) {
							p.sendMessage(Main.info+ " Vous ne respectez pas les conditions pour acheter cet objet");
							return;
						}
					}
					if (name.equals("Loup Garou")) {
						if (!p.hasWinLg) {
							p.sendMessage(Main.info+ " Vous ne respectez pas les conditions pour acheter cet objet");
							return;
						}
					}
					if (name.equals("Grade 4")) {
						if (p.getXp() < 300) {
							p.sendMessage(Main.info+ " Vous ne respectez pas les conditions pour acheter cet objet");
							return;
						}
					}
					if (name.equals("Grade 3")) {
						if (p.getXp() < 600 || p.pvpZoneKill < 20) {
							p.sendMessage(Main.info+ " Vous ne respectez pas les conditions pour acheter cet objet");
							return;
						}
					}
					if (name.equals("Grade 2")) {
						if (p.getXp() < 1000 || p.pvpZoneKill < 50 || p.specialItemsOwned == null || p.specialItemsOwned.size() < 10) {
							p.sendMessage(Main.info+ " Vous ne respectez pas les conditions pour acheter cet objet");
							return;
						}
					}
					if (name.equals("Grade 1")) {
						if (p.getXp() < 1500 || p.pvpZoneKill < 100 || p.specialItemsOwned == null || p.specialItemsOwned.size() < 20 || !p.hasSpecialActivated) {
							p.sendMessage(Main.info+ " Vous ne respectez pas les conditions pour acheter cet objet");
							return;
						}
					}
					if (name.equals("Grade S")) {
						if (p.getXp() < 3000 || p.pvpZoneKill < 200 || p.specialItemsOwned == null || p.specialItemsOwned.size() < 40 || !p.hasSpecialActivated || !p.hasTerritotyOpened) {
							p.sendMessage(Main.info+ " Vous ne respectez pas les conditions pour acheter cet objet");
							return;
						}
					}
					p.grades.add(name);
					p.feathers -= cost;
					p.sendMessage("Vous avez acheté le grade: "+ name);
				} else {
					p.sendMessage(Main.exclamation + ChatColor.RED+"Vous n'avez pas assez de Feather");
				}
				
			} else if (lores.get(1).equals(ChatColor.GRAY+"Items Spéciaux")) {
				int cost = this.itemsS.get(name);
				
				
				
				if (cost <= p.feathers) {
					p.grades.add(name);
					p.feathers -= cost;
					SpecialItemHolder.addInvItem(p, name);
					p.sendMessage("Vous avez acheté l'item spécial: "+ name);
				} else {
					p.sendMessage(Main.exclamation + ChatColor.RED+"Vous n'avez pas assez de Feather");
				}
			}
			
			
		}

	}

}
