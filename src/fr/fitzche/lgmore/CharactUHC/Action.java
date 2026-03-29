package fr.fitzche.lgmore.CharactUHC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
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
import fr.fitzche.lgmore.Util.LineLocationHelper;
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
	public HashMap<String, JsonObject> numConditions;
	
	public Action(CharactUHC game, PlayerData p,  JsonObject obj, ArrayList<String> args) {
		
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
				Action act = new Action(game, p, (JsonObject) l, args);
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
								Action action = new Action(game, p, act, args);
								
							}
							
						}, JsonUtil.getInt(o, "time", 10)));
					}
				}
			}
		}
		
		//si actif, repète après le temps donné
		int repeatTime = JsonUtil.getInt(obj, "repeat", -1);
		int repeatMax = JsonUtil.getInt(obj, "repeatMax", -1);
		if (repeatTime > 0 && repeatMax > 0) {
			this.game.futures.add(new FutureAction(new BukkitRunnable() {

				@Override
				public void run() {
					JsonObject objCop = obj.getAsJsonObject();
					objCop.remove("repeatMax");
					objCop.addProperty("repeatMax",(repeatMax - 1));
					Action act = new Action(game, p, objCop, args);
					
				}
				
			}, repeatTime));
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
		//si actif, il faut que l'ensemble des conditions numériques correspondent à la valeur donnée
		if (JsonUtil.getJsonArray(obj, "numConditions") != null) {
			JsonArray conds = JsonUtil.getJsonArray(obj, "numConditions");
			for (JsonElement l:conds) {
				int x = this.game.numConditions.getOrDefault(JsonUtil.getString(l.getAsJsonObject(), "name", "null"), -1);
				int value = JsonUtil.getInt(l.getAsJsonObject(), "value", -1);
				boolean inverted = JsonUtil.getBool(l.getAsJsonObject(), "inverted", false);
				if (x > 0 && value > 0) {
					
					
					switch (JsonUtil.getString(l.getAsJsonObject(), "comparator", "more")) {
					case "more":
						if (x <= value) {
							return;
						}
						break;
					case "less":
						if (x >= value) {
							return;
						}
						break;
					case "equals":
						if (x != value) {
							return;
						}
						break;
					}
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
			if (!notAdded && Main.getData(ply) != null && Main.getData(ply).isOnline) {
				psCopie.add(ply);
			}
		}
		ps = psCopie;
		
		
		StringBuilder strB = new StringBuilder();
		strB.append(Main.info + ChatColor.RED+"Action réalisée:"+ ChatColor.GOLD+"\n" + "Type:"+ChatColor.GRAY);
		
		switch (this.type) {
		case "damage":
			
			strB.append("Dégat");
			
			for (Player player:ps) {
				player.damage(1*force);
			}
			
			break;
			
			//à faire: considère un nouveau type d'action "changeCamp" qui est accompagné d'un "changedCamp":String (nom camp)
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
		case "changeNumConds":
			try {
				if (JsonUtil.getJsonArray(obj, "numConditionsChanged") != null) {
					for (JsonElement j:JsonUtil.getJsonArray(obj, "numConditionsChanged")) {
						JsonObject objNum = j.getAsJsonObject();
						
						if (JsonUtil.getBool(objNum, "set", false)) {
							this.game.numConditions.put(JsonUtil.getString(objNum, "numConditionChanged", "null"), objNum.get("numConditionChange").getAsInt());

						} else {
							this.game.numConditions.put(JsonUtil.getString(objNum, "numConditionChanged", "null"), this.game.numConditions.getOrDefault(JsonUtil.getString(objNum, "numConditionChanged", "null"), -1) + JsonUtil.getInt(objNum, "numConditionChange", 0));

						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
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
			strB.append("conditionChanged");
			this.game.conditions.put(JsonUtil.getString(obj, "conditionChanged", "null"), !this.game.conditions.getOrDefault(JsonUtil.getString(obj, "conditionChanged", "null"), false));
			break;
			
		case "grrr":
			strB.append("GRRR");
			for (Player player:ps) {
				
				player.playSound(p.getLocation(), Sound.WOLF_GROWL, 1, 1);
			}
			
			break;
		case "hurlement":
			strB.append("Hurlement");
			for (Player player:ps) {
				player.playSound(p.getLocation(), Sound.WOLF_HOWL, 1, 1);
				
			}
			break;
		case "expulsion":
			
			strB.append("Expulsion");
			for (Player player:ps) {
				if (p.isOnline) {
					LineLocationHelper.applyKnockback(player, p.player, force);
				}
				
				
			}
			
			break;

		case "message":
			if (JsonUtil.getJsonArray(obj, "messages") != null) {
				for (JsonElement l:JsonUtil.getJsonArray(obj, "messages")) {
					if (l.getAsJsonObject() != null) {
						ChatColor color;
						switch (JsonUtil.getString(l.getAsJsonObject(), "color", "white")) {
						case "white":
							color = ChatColor.WHITE;
							break;
						case "gold":
							color = ChatColor.GOLD;
							break;
						case "green":
							color = ChatColor.GREEN;
							break;
						case "blue":
							color = ChatColor.BLUE;
							break;
						case "red":
							color = ChatColor.RED;
							break;
						case "purple":
							color = ChatColor.DARK_PURPLE;
							break;
						default:
							color = ChatColor.GRAY;
							break;
								
						
						}
						for (Player player:ps) {
							
							player.sendMessage(color+ JsonUtil.getString(l.getAsJsonObject(), "message", "message non valide"));
							
						}
						
						
					}
				}
			}
			
			break;
		case "attraction":
			
			strB.append("Attraction");
			for (Player player:ps) {
				if (p.isOnline) {
					LineLocationHelper.applyForce(player, p.getLocation(), force);
				}
				
				
			}
			
			break;
		case "spectator":
			strB.append("Spectator");
			for (Player player:ps) {
				
				this.game.futures.add(new FutureAction(new BukkitRunnable() {
	
					@Override
					public void run() {
						PlayerUtil.survival(player);
						
						
						
					}
					
				}, timeForce.intValue()));
			}
			break;
		case "maxHealth":
			strB.append("MaxHealth augmentation");
			for (Player player:ps) {
				player.setMaxHealth(player.getMaxHealth() + force);
			}
			
			break;
		case "fly":
			strB.append("Fly");
			for (Player player:ps) {
				
				player.setAllowFlight(true);
				player.setFlying(true);
				this.game.futures.add(new FutureAction(new BukkitRunnable() {
	
					@Override
					public void run() {
						player.setAllowFlight(false);
						player.addPotionEffect( new PotionEffect(PotionEffectType.JUMP, timeForce.intValue(), force.intValue()));
						
						
					}
					
				}, timeForce.intValue()));
			}
			break;
		}
		
		
		
		strB.append(ChatColor.GOLD+"\n"+ "Joueurs ciblés:"+ChatColor.GRAY+ "\n"); {
			for (Player player:ps) {
				strB.append(player.getName()+ "\n");
			}
		}
		
		if (!JsonUtil.getBool(obj, "withoutMessage", true)) {
			p.sendMessage(strB.toString());
			if (p.isOnline) {
				String id = String.valueOf(MathUtil.generateAlInt(0, 1000));
				PlayerUtil.sendClickableText("clickez ici pour obtenir un rapport de l'action", "/lg clicText "+ id, p.player);
				Main.clicTexts.put(id, strB.toString());
			}
		}
		
		
		
		
	}
	
	
}
