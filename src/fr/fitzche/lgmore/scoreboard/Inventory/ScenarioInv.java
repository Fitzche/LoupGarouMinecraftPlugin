package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.ItemUtil;

public class ScenarioInv implements InvFunct {

	
	
	public GameLg game;
	public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public StringChooseInv chose;
	public Inventory backInv;
	
	public ScenarioInv(GameLg game) {
		this.game = game;
		
	}
	public void setInv(PlayerData p, Inventory backInv) {
		
		String theAct = "disable";
		String dirAct = "disable";
		String necrAct = "disable";
		String swapDAct = "disable";
		String swapTAct = "disable";
		String swapQAct = "disable";
		String swapPAct = "disable";
		this.backInv = backInv;
		
		if (game.scenarioAct.getOrDefault("Théâtre", false)) {
			theAct = "enable";
		}
		if (game.scenarioAct.getOrDefault("DirectFights", false)) {
			dirAct = "enable";
		}
		if (game.scenarioAct.getOrDefault("Necromancie", false)) {
			necrAct = "enable";
		}
		if (game.scenarioAct.getOrDefault("SwapperTrio", false)) {
			swapTAct = "enable";
		}
		if (game.scenarioAct.getOrDefault("SwapperQuatuor", false)) {
			swapQAct = "enable";
		}
		if (game.scenarioAct.getOrDefault("SwapperDouble", false)) {
			swapDAct = "enable";
		}
		if (game.scenarioAct.getOrDefault("SwapperFive", false)) {
			swapPAct = "enable";
		}
		setItem(0, Material.BOOK_AND_QUILL,"Théâtre",  new ArrayList<String>(Arrays.asList(
				theAct,
				"Trois registres: épiques, tragiques et oratoires, sur le thème du théâtre existent.",
				"Les actions des joueurs font varier le taux de chaque registres, le registre le + haut influence la partie en fonction de son taux:",
				"Epique: loups et roles de combats sont mis en avant, mais la pagaille peut brouiller les roles; provoqué par la mort trop rapide des roles à infos",
				"Oratoire: L'art de la rhétorique est mis en avant. Les votes et le débats entre joueurs sont mis en avant. Provoqués par peu de morts, beaucoup de votants, de recherches et d'accusation",
				"Tragique: La mort des guerriers et la trahison plonge le village dans le désespoir. L'inflitration et l'assassinat discrets sont de mises: morts cachées, groupes réduits, votes réduits, combats directs moins propices."
				)));
		
		
		setItem(1, Material.DIAMOND_SWORD, "DirectFights", new ArrayList<String>(Arrays.asList(
				dirAct, 
				"Active automatiquement meetup ",
				"camp des joueurs affichés sur leur pseudo (couleurs)",
				"ne tient pas en compte des changements de camps",
				"tp aléatoire seulement de 100 blocs."
				)));
		
		setItem(2, Material.ROTTEN_FLESH, "Necromancie", new ArrayList<String>(Arrays.asList(
				necrAct, 
				"neccessite un ou plusieurs nécromancien ",
				"pouvoirs Nécromancien basique retirés",
				"Nécromancien +2 coeurs",
				"tués par nécromanciens gagne avec lui"
				)));
		setItem(3, Material.ARROW, "SwapperDouble", new ArrayList<String>(Arrays.asList(
				swapDAct, 
				"Tous le meme role, sans effet", 
				"deux camp: rouges et bleu, répartis de manière aléatoire",
				"chaque joueur possède 3 vie, et en gagne une en tuant un autre joueur",
				"un joueur tué rejoint le camp de son tueur"
				)));
		setItem(4, Material.ARROW, "SwapperTrio", new ArrayList<String>(Arrays.asList(
				swapTAct, 
				"Tous le meme role, sans effet", 
				"trois camp: rouges, rose et bleu, répartis de manière aléatoire",
				"chaque joueur possède 3 vie, et en gagne une en tuant un autre joueur",
				"un joueur tué rejoint le camp de son tueur"
				)));
		setItem(5, Material.ARROW, "SwapperQuadrio", new ArrayList<String>(Arrays.asList(
				swapQAct, 
				"Tous le meme role, sans effet", 
				"quatres camp: rouges, rose, vert et bleu, répartis de manière aléatoire",
				"chaque joueur possède 3 vie, et en gagne une en tuant un autre joueur",
				"un joueur tué rejoint le camp de son tueur"
				)));
		setItem(6, Material.ARROW, "SwapperFive", new ArrayList<String>(Arrays.asList(
				swapPAct, 
				"Tous le meme role, sans effet", 
				"cinq camp: rouges, rose, vert, jaune et bleu, répartis de manière aléatoire",
				"chaque joueur possède 3 vie, et en gagne une en tuant un autre joueur",
				"un joueur tué rejoint le camp de son tueur"
				)));
		this.chose = new StringChooseInv(p, items, backInv, this);
		
		if (chose.invs.size() > 0) {
			p.player.openInventory(chose.invs.get(0));
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
	
	
	public void click(PlayerData p, GameLg game, String clickedName, ArrayList<String> lores) {
		switch (clickedName) {
			case "Théâtre":
				
				if (!game.scenarioAct.getOrDefault("Théâtre", false)) {
					game.scenarioAct.put("Théâtre", true);
					p.sendMessage("registres activés");
				} else {
					game.scenarioAct.put("Théâtre", false);
					p.sendMessage("registres désactivés");
				}
				break;
			case "DirectFights":
				 
				if (!game.scenarioAct.getOrDefault("DirectFights", false)) {
					game.scenarioAct.put("DirectFights", true);
					p.sendMessage("direct fight activés");
				} else {
					game.scenarioAct.put("DirectFights", false);
					p.sendMessage("direct fight désactivés");
				}
				break;
			case "Necromancie":
				if (!game.scenarioAct.getOrDefault("Necromancie", false)) {
					game.scenarioAct.put("Necromancie", true);
					p.sendMessage("Necromancie activée");
				} else {
					game.scenarioAct.put("Necromancie", false);
					p.sendMessage("Necromancie désactivés");
				}
				break;
			case "SwapperDouble":
				if (!game.scenarioAct.getOrDefault("SwapperDouble", false)) {
					game.scenarioAct.put("SwapperDouble", true);
					p.sendMessage("Swapper double activée");
				} else {
					game.scenarioAct.put("SwapperDouble", false);
					p.sendMessage("Swapper double désactivés");
				}
				break;
			case "SwapperTrio":
				if (!game.scenarioAct.getOrDefault("SwapperTrio", false)) {
					game.scenarioAct.put("SwapperTrio", true);
					p.sendMessage("Swapper trio activée");
				} else {
					game.scenarioAct.put("SwapperTrio", false);
					p.sendMessage("Swapper trio désactivés");
				}
				break;
			case "SwapperQuadrio":
				if (!game.scenarioAct.getOrDefault("SwapperQuatuor", false)) {
					game.scenarioAct.put("SwapperQuatuor", true);
					p.sendMessage("Swapper quadrio activée");
				} else {
					game.scenarioAct.put("SwapperQuatuor", false);
					p.sendMessage("Swapper quadrio désactivés");
				}
				break;
			case "SwapperFive":
				if (!game.scenarioAct.getOrDefault("SwapperFive", false)) {
					game.scenarioAct.put("SwapperFive", true);
					p.sendMessage("Swapper five activée");
				} else {
					game.scenarioAct.put("SwapperFive", false);
					p.sendMessage("Swapper five désactivés");
				}
				break;
				
		}	
		setInv(p, this.backInv);
	}
}
