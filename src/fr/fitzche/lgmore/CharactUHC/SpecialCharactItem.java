package fr.fitzche.lgmore.CharactUHC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.LineLocationHelper;
import fr.fitzche.lgmore.Util.MathUtil;
import net.md_5.bungee.api.ChatColor;

public class SpecialCharactItem {

	
	
	private JsonObject obj;
	private Material material;
	public String name;
	private CharactUHC uhc;
	private boolean targeting;
	private int distanceTargeting;
	public String id;
	private String description;
	private HashMap<String, Integer> use = new HashMap<String, Integer>();
	

	public SpecialCharactItem(CharactUHC uhc, String name, Material material, JsonObject obj, boolean targeting, int distanceTargeting, String description, int useDefault) {
		this.uhc = uhc;
		this.name = name;
		this.material = material;
		this.obj = obj;
		this.targeting = targeting;
		this.distanceTargeting = distanceTargeting;
		this.id = (ChatColor.MAGIC + ""+ MathUtil.generateAlInt(0, 1000));
		this.description = description;
		this.useDefault = useDefault;
		
		
	}
	
	public void give(PlayerData p) {
		if (p.isOnline) {
			ItemStack i = ItemUtil.getItem(material, 1, name, new ArrayList<String>(Arrays.asList(ChatColor.MAGIC + id, description)));
			ItemUtil.addAppaEnchant(i);
			ItemUtil.hideAttributes(i);
			p.player.getInventory().addItem(i);
		}
	}
	
	Integer useDefault;
	public void run(PlayerData clicker) {
		
		
		if (use.getOrDefault(clicker.getName(), useDefault) > 0) {
			use.put(clicker.getName(), use.getOrDefault(clicker.getName(), useDefault) - 1);
		} else {
			return;
		}
		
		if (targeting) {
			PlayerData targeted = LineLocationHelper.getLineLocations(clicker.player, distanceTargeting, 0.5, 0.25).p;
			try {
				if (targeted != null) {
					Action act = new Action(uhc, clicker, obj, new ArrayList<String>(Arrays.asList(targeted.getName())));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Action act = new Action(uhc, clicker, obj, new ArrayList<String>());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
}
