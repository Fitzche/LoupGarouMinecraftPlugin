package fr.fitzche.lgmore.CharactUHC;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.JsonUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.commands.FutureAction;
import fr.fitzche.lgmore.custom.RoleSet;

public class Action {

	
	CharactUHC game;
	String type = "";
	Double force = 1.0;
	private Double timeForce;
	private Boolean self;
	private Boolean onlySelf;
	private int distance;
	private Integer proba = 100;
	private Boolean multipleCommandTarget;
	private Boolean commandTarget;
	private String campTargeted;
	private String roleTargeted;
	private String jauge;
	private Integer jaugeAdd;
	CharactRole role;
	
	public Action(CharactUHC game, PlayerData p, CharactRole role,  JsonObject obj, ArrayList<String> args) {
		
		this.role = role;
		
		this.proba = JsonUtil.getInt(obj, "proba", 100);
		if (!MathUtil.pourcentage(proba)) {
			return;
		}
		
		this.game = game;
		this.type = JsonUtil.getString(obj, "type", "damage");
		this.force = JsonUtil.getDouble(obj, "force", 1.0);
		this.timeForce = JsonUtil.getDouble(obj, "timeForce", 1.0);
		this.self = JsonUtil.getBool(obj, "self", false);
		this.onlySelf = JsonUtil.getBool(obj, "onlySelf", false);
		this.distance = JsonUtil.getInt(obj, "distance", 1000);
		
		this.jauge = JsonUtil.getString(obj, "jauge", "null");
		this.jaugeAdd = JsonUtil.getInt(obj, "jaugeAdd", 0);
		
		
		//optionnel
		this.campTargeted = JsonUtil.getString(obj, "campTargeted", "null");
		this.roleTargeted = JsonUtil.getString(obj, "roleTargeted", "null");
		
		//action activées indépendemment
		JsonArray listAction = JsonUtil.getJsonArray(obj, "actions");
		if (listAction != null) {
			for (JsonElement l:listAction) {
				Action act = new Action(game, p, role, (JsonObject) l, args);
			}
		}
		
		//action à faire avec un décalage
		JsonArray listDelayedAction = JsonUtil.getJsonArray(obj, "delayedActions");
		if (listDelayedAction != null) {
			for (JsonElement l:listDelayedAction) {
				JsonObject o = l.getAsJsonObject();
				if (o != null) {
					JsonObject act = JsonUtil.getJsonObject(o, "action");
					if (act != null) {
						this.game.futures.add(new FutureAction(new BukkitRunnable() {

							@Override
							public void run() {
								Action action = new Action(game, p, role, act, args);
								
							}
							
						}, JsonUtil.getInt(o, "time", 10)));
					}
				}
			}
		}
		
		//si actif, l'unique cible est le joueur correspondant au premier argument de la commande
		this.commandTarget = JsonUtil.getBool(obj, "commandTarget", false);
		//si actif, les uniques cibles sont les joueurs correspondant aux arguments de la commande
		this.multipleCommandTarget = JsonUtil.getBool(obj, "multipleCommandTarget", false);
		
		//si actif, il faut que l'ensemble des conditions correspondent à la valeur donnée
		if (JsonUtil.getJsonObject(obj, "conditions") != null) {
			JsonObject conds = JsonUtil.getJsonObject(obj, "conditions");
			for (Entry<String, JsonElement> cond:conds.entrySet()) {
				if (!this.game.conditions.get(cond.getKey()).equals(cond.getValue().getAsBoolean())) {
					return;
				}
					
			
			}
		}
		
		
		
		ArrayList<Player> ps = new ArrayList<Player>();
		if (onlySelf ) {
			if (p.isOnline) {
				ps.add(p.player);
			}
		} else {
			
			if (this.commandTarget) {
				PlayerData added = Main.getData(args.get(0));
				if (added != null && added.isOnline) {
					ps.add(added.player);
				}
				
			} else if (this.multipleCommandTarget) {
				for (String arg:args) {
					PlayerData added = Main.getData(arg);
					if (added != null && added.isOnline) {
						ps.add(added.player);
					}
				}
				
			} else {
				for (PlayerData ply : game.getPlayers()) {
					if ((LocationUtil.getDistanceBetween(ply, p) <= distance) && (self || !ply.getName().equals(p.getName()))) {
						if (ply.isOnline) {
							ps.add(ply.player);
						}
					
					}
				}
			}
			
		}
		
		ArrayList<Player> psCopie = new ArrayList<Player>();
		for (Player ply:ps) {
			boolean notAdded = false;
			if (this.campTargeted != "null" ) {
				PlayerData plyData = Main.getData(ply);
				if (plyData != null && plyData.isOnline && plyData.game != null && plyData.game instanceof CharactUHC) {
					CharactUHC uhc = (CharactUHC) plyData.game;
					if (uhc.getName().equals(game.getName()) ) {
						boolean hasCamp = false;
						for (RoleSet set:uhc.rolesSet()) {
							if (set.rolesOfPlayer().get(plyData.getName()) != null) {
								if (set.rolesOfPlayer().get(plyData.getName()).campName().equals(campTargeted)) {
									hasCamp = true;
								}
							}
						}
						if (!hasCamp) {
							notAdded = true;
						}
					}
				}
			}
			if (this.roleTargeted != "null" ) {
				PlayerData plyData = Main.getData(ply);
				if (plyData != null && plyData.isOnline && plyData.game != null && plyData.game instanceof CharactUHC) {
					CharactUHC uhc = (CharactUHC) plyData.game;
					if (uhc.getName().equals(game.getName()) ) {
						boolean hasCamp = false;
						for (RoleSet set:uhc.rolesSet()) {
							if (set.rolesOfPlayer().get(plyData.getName()) != null) {
								if (set.rolesOfPlayer().get(plyData.getName()).name().equals(roleTargeted)) {
									hasCamp = true;
								}
							}
						}
						if (!hasCamp) {
							notAdded = true;
						}
					}
				}
			}
		}
		
		StringBuilder strB = new StringBuilder();
		strB.append("Action réalisée:"+ "\n" + "Type:");
		
		switch (this.type) {
		case "damage":
			
			strB.append("Dégat");
			
			for (Player player:ps) {
				player.damage(1*force);
			}
			
			break;
		case "slowness":
			
			strB.append("Slow");
			for (Player player:ps) {
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, timeForce.intValue()  , force.intValue()), false);
			}
			break;
		case "speed":
			strB.append("Speed");
			
			for (Player player:ps) {
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, timeForce.intValue()  , force.intValue()), false);
			}
			break;
		case "blindness":
			strB.append("Blindness");
			
			for (Player player:ps) {
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, timeForce.intValue()  , force.intValue()), false);
			}
			break;
		case "regen":
			strB.append("Regen");
			
			for (Player player:ps) {
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, timeForce.intValue()  , force.intValue()), false);
			}
			break;
		case "fireResistance":
			strB.append("FireResistance");
			
			for (Player player:ps) {
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, timeForce.intValue()  , force.intValue()), false);
			}
			break;
		case "fire":
			strB.append("Mise en feu");
			for (Player player:ps) {
				player.setFireTicks(this.timeForce.intValue());
			}
			break;
		case "invicibility":
			strB.append("Invincibilité");
			for (Player player:ps) {

				player.setNoDamageTicks(this.timeForce.intValue());
			}
			
			
			
			break;
		case "tp":
			strB.append("Invincibilité");
			if (ps.size() > 0) {
				if (p.isOnline) {
					p.player.teleport(ps.get(MathUtil.generateAlInt(0, ps.size() - 1)));
				}
				
			}
			
			
			
			break;
		case "heal":
			strB.append("Instant Heal");
			for (Player player:ps) {
				int heal = 1 * this.force.intValue();
				if (player.getMaxHealth() -player.getHealth() >  heal) {
					player.setMaxHealth(player.getMaxHealth());
				} else {
					player.setMaxHealth(player.getHealth() + heal);
				}
			}
			break;
		case "jauge":
			strB.append("Jauge Augmentation");
			for (Player ply:ps) {
				if (this.role.jauges.get(jauge) != null && this.role.jauges.get(jauge).get(ply.getName()) != null) {
					
					Jauge cJauge = this.role.jauges.get(jauge).get(ply.getName());
					if (cJauge != null) {
						this.role.jauges.get(jauge).get(ply.getName()).add(jaugeAdd);
						if (cJauge.displayedOnPlayer) {
							PlayerUtil.sendActionBar(p.player, cJauge.name + ": "+cJauge.value + "/"+ cJauge.maxValue);
						}
					}
						
				}
			}
			break;
			
		case "conditionChanged":
			this.game.conditions.put(JsonUtil.getString(obj, "conditionChanged", "null"), !this.game.conditions.getOrDefault(JsonUtil.getString(obj, "conditionChanged", "null"), false));
			break;
			
		case "spectator":
			
			if (p.isOnline) {
				PlayerUtil.spectator(p.player);
			}
			this.game.futures.add(new FutureAction(new BukkitRunnable() {

				@Override
				public void run() {
					if (p.isOnline) {
						PlayerUtil.survival(p.player);
					}
					
					
				}
				
			}, timeForce.intValue()));
			break;
		}
		
		
		
		strB.append("\n"+ "Joueurs ciblés:"+ "\n"); {
			for (Player player:ps) {
				strB.append(player.getName()+ "\n");
			}
		}
		
		if (!JsonUtil.getBool(obj, "withoutMessage", false)) {
			p.sendMessage(strB.toString());
			if (p.isOnline) {
				String id = String.valueOf(MathUtil.generateAlInt(0, 1000));
				PlayerUtil.sendClickableText("clickez ici pour obtenir un rapport de l'action", "/lg clicText "+ id, p.player);
				Main.clicTexts.put(id, strB.toString());
			}
		}
		
		
		
		
	}
	
	
}
