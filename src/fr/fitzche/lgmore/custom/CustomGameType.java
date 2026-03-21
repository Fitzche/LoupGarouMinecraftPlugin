package fr.fitzche.lgmore.custom;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.MathUtil;


public enum CustomGameType {

	
	ClockTower(false), SettlerGame(false),
	Charact0(true),
	Charact1(true),
	Charact2(true),
	Charact3(true),
	Charact4(true),
	Charact5(true),
	Charact6(true),
	Charact7(true),
	Charact8(true),
	Charact9(true),
	Charact10(true),
	Charact11(true),
	Charact12(true),
	Charact13(true),
	Charact14(true),
	Charact15(true),
	Charact16(true),
	Charact17(true),
	Charact18(true),
	Charact19(true),
	Charact20(true),
	Charact21(true),
	Charact22(true),
	Charact23(true),
	Charact24(true),
	Charact25(true),
	Charact26(true),
	Charact27(true),
	Charact28(true),
	Charact29(true),
	
	;
	
	

	private boolean slotEnum;
	private String charactGameName;
	
	CustomGameType(boolean slotEnum) {
		this.slotEnum = slotEnum;
		
	}
	public void setName(String name) {
		this.charactGameName = name;
	}
	
	public boolean isSlot() {
		return slotEnum;
	}
	
	public static ArrayList<CustomGameType> getUnslotValues() {
		ArrayList<CustomGameType> types = new ArrayList<CustomGameType>();
		for (CustomGameType type:CustomGameType.values()) {
			if (!type.isSlot()) {
				types.add(type);
			}
		}
		return types;
	}
	
	public CustomGame createGame() {
		
		switch (this) {
		
		case SettlerGame: 
			return new fr.fitzche.lgmore.settlerGame.SettlerGame();
			
		default:
			break;
		
		}
		if (this.slotEnum) {
			return new fr.fitzche.lgmore.CharactUHC.CharactUHC(charactGameName);
		}
		
		
		return null;
	}
	
	public ItemStack getItem() {
		switch (this) {
		case ClockTower:
			return ItemUtil.getItem(Material.COMPASS, 1, "ClockTower", new ArrayList<String>(Arrays.asList("Un jeu en cours de dev")));
		
		case SettlerGame:
			return ItemUtil.getItem(Material.BANNER, 1, "SettlerGame", new ArrayList<String>(Arrays.asList("Construisez votre colonie et défendez là. L'équipe la puissante économiquement, architecturalement et millitairement l'emporte")));
		default:
			break;
		
		}
		
		if (this.slotEnum) {
			ArrayList<String> list = new ArrayList<String>(Arrays.asList("FITZCHE", "BoomAsko", "Valtor91", "Aquaaarel", "TheGuill84", "LeSantorinien", "Chirora", "LebonvieuxXynos", "Kinglez"));
			ItemStack item = ItemUtil.getCustomHead(list.get(MathUtil.generateAlInt(0, list.size() - 1)));
			ItemUtil.setName(item, charactGameName);
			return item;
		}
		
		return null;
	}
	public static CustomGameType getEmptySlot(ArrayList<CustomGameType> typeList) {
		for (CustomGameType type:CustomGameType.values()) {
			if (type.isSlot() && !typeList.contains(type)) {
				return type;
				
			}
		}
		return null;
	}
	
}
