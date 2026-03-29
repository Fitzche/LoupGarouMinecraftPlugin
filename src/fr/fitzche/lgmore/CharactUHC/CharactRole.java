package fr.fitzche.lgmore.CharactUHC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.JsonUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.custom.CustomRole;
import fr.fitzche.lgmore.custom.RoleSet;


public class CharactRole implements CustomRole{

	
	public PlayerData playerData;
	public String name;
	public String campName;
	public RoleSet set;
	public CharactUHC game;
	
	//STAT
	private double strenght = 1;
	private double resistance = 1;
	
	//ABILITIES
	public ArrayList<VisionPower> powersVision = new ArrayList<VisionPower>();
	
	
	//RELIVE Conditions
	int reliveTry = 0;
	private double lostResis = 0;
	private double lostStrenght = 0;
	private double lostHealth;
	private String conditionKilledBy = "";
	private String conditionKilledByCamp = "";
	
	public JsonObject objson;
	private Integer boostHealth;
	public String roleListName = "notReferenced";
	private HashMap<String, JsonObject> commands = new HashMap<String, JsonObject>();
	private JsonObject attackAction;
	private JsonObject deathAction;
	
	
	public HashMap<String, HashMap<String, Jauge>> jauges = new HashMap<String, HashMap<String, Jauge>>();
	private JsonObject killAct;
	private JsonObject damageAction;
	public HashMap<Integer, JsonObject> timedAction;
	private HashMap<String, Integer> commandsUse;
	
	public CharactRole(String pathToRole, RoleSet set, CharactUHC game) {
		
		this.game = game;
		this.set = set;
		try {
			
			File file = new File(Main.plug.getDataFolder(), ("roles/"+pathToRole));
			if (!file.exists()) {
				file.createNewFile();
				file.setWritable(true);
				FileWriter writer = new FileWriter(file);
				
				writer.write(Main.defaultJsonRoleSTring);
			}
			InputStream is = new FileInputStream(file);

			if (is == null) { throw new IllegalStateException("Impossible de trouver json dans le JAR"); }
			String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			
			
			
			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(content);
			JsonObject object = json.getAsJsonObject();
			this.objson = object;
			
			System.out.println(json.toString());
			if (object.get("strenght") != null) {
				this.strenght = object.get("strenght").getAsDouble();
			}
			this.roleListName = JsonUtil.getString(object, "roleListName", "notReferenced");
			this.resistance = JsonUtil.getDouble(object, "resistance", 1.0);
			
			this.boostHealth = JsonUtil.getInt(object, "boostHealth", 0);
			
			this.name = object.get("name").getAsString();
			
			
			
			
			if (object.get("reliveTry") != null) {
				this.reliveTry = object.get("reliveTry").getAsInt();
			} 
			
			if (object.get("lostStrenght") != null) {
				this.lostStrenght =  object.get("lostStrenght").getAsDouble();
	
			}
			if (object.get("lostResis") != null) {
				this.lostResis =  object.get("lostResis").getAsDouble();

			}
			if (object.get("lostHealth") != null) {
				this.lostHealth = object.get("lostHealth").getAsDouble();

			}
			
			this.campName = object.get("camp").getAsString();
			
			
			//CONDITIONS
			if (object.get("conditionKilledBy") != null) {
				this.conditionKilledBy = object.get("conditionKilledBy").getAsString();
			}
			if (object.get("conditionKilledByCamp") != null) {
				this.conditionKilledByCamp = object.get("conditionKilledByCamp").getAsString();
	
			}
			
			
			
			//items
			JsonArray items = JsonUtil.getJsonArray(object, "items");
			if (items != null) {
				for (JsonElement l:items) {
					for (SpecialCharactItem i:game.items) {
						if (l.getAsString().equals(i.name)) {
							i.give(this.playerData);
						}
					}
					
				}
			}
			
			
			
			
			//Action par commande:
			for (JsonElement elm:object.get("commands").getAsJsonArray()) {
				JsonObject elmObj = elm.getAsJsonObject();
				this.commandsUse.put(elmObj.get("command").getAsString(), JsonUtil.getInt(elmObj, "use", 1));
				this.commands.put(elmObj.get("command").getAsString(), elmObj.get("action").getAsJsonObject());
			}
			
			//Action au temps:
			for (JsonElement elm:object.get("timedActions").getAsJsonArray()) {
				JsonObject elmObj = elm.getAsJsonObject();
				this.timedAction.put(JsonUtil.getInt(elmObj, "time", -1), elmObj.get("action").getAsJsonObject());
			}
			
			
			//jauges (JsonList de JsonObject correspondant aux jauges)
			JsonArray elements = JsonUtil.getJsonArray(object, "jauges");
			if (elements != null) {
				for (JsonElement jsonElm:elements) {
					JsonObject jsonObj = jsonElm.getAsJsonObject();
					jauges.put(JsonUtil.getString(jsonObj, "name", "null"), new HashMap<String, Jauge>());
					
				}
			}
			
			
			//Action avec attaque
			this.attackAction = JsonUtil.getJsonObject(object, "attackAction");
			
			//Action avec mort
			this.deathAction = JsonUtil.getJsonObject(object, "deathAction");
			//Action avec kill
			this.killAct = JsonUtil.getJsonObject(object, "killAction");
			//Action avec dommage
			this.damageAction = JsonUtil.getJsonObject(object, "damageAction");
			
			//POUVOIR VISION
			if (object.get("infoPowers") != null) {
				JsonArray array = object.get("infoPowers").getAsJsonArray();
				for (JsonElement el:array) {
					JsonObject obj = el.getAsJsonObject();
					
					if (obj.get("type") != null && obj.get("object") != null) {
						
						switch (obj.get("type").getAsString()) {
						case "seeCaract":
							
							JsonObject subObject = obj.get("object").getAsJsonObject();
							
							int distance = 1000;
							if (subObject.get("distance") != null) {
								distance = subObject.get("distance").getAsInt();
							}
							int probaTrue = 100;
							if (subObject.get("probaTrue") != null) {
								probaTrue = subObject.get("probaTrue").getAsInt();
							}
							int use = 1;
							if (subObject.get("use") != null) {
								use = subObject.get("use").getAsInt();
							}
							boolean all = false;
							if (subObject.get("all") != null) {
								all = subObject.get("all").getAsBoolean();
							}
							boolean knowWho = false;
							if (subObject.get("knowWho") != null) {
								knowWho = subObject.get("knowWho").getAsBoolean();
							}
							boolean timeAlea = false;
							if (subObject.get("timeAlea") != null) {
								timeAlea = subObject.get("timeAlea").getAsBoolean();
							}
							String caract = "name";
							if (subObject.get("caract") != null) {
								caract = subObject.get("caract").getAsString();
							}
							String command = "";
							if (subObject.get("command") != null) {
								caract = subObject.get("command").getAsString();
							}
							
							
							
							VisionPower power = new VisionPower(this, "seeCaract", all, distance, command, timeAlea, caract, probaTrue, knowWho, use);
							this.powersVision.add(power);
							break;
							
							/*format d'un ensemble d'un pouvoir 
							 * "distance": distance au delà de laquelle le pouvoir ne peut pas agir (par defaut 1000)
							 * "probaTrue":probabilité que l'information ne soit pas biaisée (par défaut 100)
							 * "use": nombre d'utilisation (nombre naturel)
							 * "all": le pouvoir a-t-il effet sur tous les joueurs respectant les autres conditions (true) ou juste un joueur (false)
							 * "command":commande permettant d'utiliser le format sur un joueur selon le format /lg [commande] [nomDuJoueur]; si pas rempli ou égal à "", la commande n'est pas utilisable
							 * "knowWho":connait les joueurs concernés par le pouvoir (true) ou non (false)
							 * "caract": nom de la caractéristique observée (une valeur du json du role observé (string, int ou boolean, comme le nom, le pourcentage de force, le nombre de ressurection possible,etc)
							 * "timeAlea": le pouvoir a 0.1% de chance de se déclencher à chaque seconde (s'il reste des utilisation) (true) ou pas (false). (correspond à une fois toutes les 15-17min environ)
							 * 
							 * 
							 * */
							
						
						}
					}
				}
				
				
				/*
				 * format d'un ensemble "infoPower", entre accolade les clés suivantes sont attendus* ou peuvent etre attendues:
				 * "type": type du pouvoir (parmis: "")
				 * "object": un autre sous ensemble dont les clés dépendent du type
				 * 
				 * 
				 * */
			}
			
			
			
			/*format d'un ensemble "infoPower", entre accolade les clés suivantes sont attendus* ou peuvent etre attendues:
			 * "name":nom du role (chaine de caractère)
			 * "camp"*:nom du camp du joueur (chaine de caractères
			 * "strenght": pourcentage force (nombre relatif) 
			 * "resistance": pourcentage resistance (nombre relatif)
			 * "attackAction": renvoie un JsonObject correspondant à action à l'attaque
			 * "deathAction": renvoie un JsonObject correspondant à action à la mort
			 * "commands": list d'objet avec {"command" (String), et "action" (format jsonObject)}
			 * "reliveTry":nombre de ressurections possibles (nombre naturel)
			 * "lostStrenght":perte de force à la ressurection (nombre relatif)
			 * "lostResis":perte de force à la ressurection (nombre relatif)
			 * "lostHealth": perte de vie à la réssurection (nombre relatif)
			 * "conditionKilledBy": nom d'un role, le joueur peut réssuciter que si c'est ce role qui l'a tué
			 * "conditionKilledByCamp": nom d'un camp, le joueur peut réssuciter que si c'est ce camp qui l'a tué
			 * "boostHealth": boost de vie
			 * 
			 * */
			
			
			
			 
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}
	
	
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String campId() {
		// TODO Auto-generated method stub
		return "0";
	}

	@Override
	public String campName() {
		// TODO Auto-generated method stub
		return campName;
	}
	
	public void command(String[] args) {
		for (VisionPower p:this.powersVision) {
			if (p.command != "" && p.command.equals(args[1])) {
				if (p.use <= 0) {
					return;
				} else {
					playerData.sendMessage("Vous utilisez votre pouvoir: ");
					p.run();
				}
				
			}
			
		}
		
		for (String str:this.commands.keySet()) {
			if (str != "" && str.equals(args[1])) {
				ArrayList<String> argsCopie = new ArrayList<String>();
				for (String arg:args) {
					argsCopie.add(arg);
				}
				argsCopie.remove(0);
				argsCopie.remove(0);
				
				if (commandsUse.get(str) == null) {
					commandsUse.put(str, 1);
				}
				if (commandsUse.getOrDefault(str, 1) > 0) {
					Action act = new Action(game, playerData, commands.get(str), argsCopie);
					commandsUse.put(str, commandsUse.get(str) - 1);
				} else {
					playerData.sendMessage(Main.exclamation+ "Nombre Max d'utilisation atteint");
				}
				
			}
		}
	}

	@Override
	public double attackModif(double damage, String damaged) {
		if (this.attackAction != null) {
			Action act = new Action(game, playerData, this.attackAction, new ArrayList<String>(Arrays.asList(damaged)));

		}
		return damage * (1+this.strenght);
	}

	@Override
	public double damageModif(double damage, String damager) {
		
		if (this.damageAction != null) {
			Action act = new Action(game, playerData, this.damageAction, new ArrayList<String>(Arrays.asList(damager)));

		}

		return damage * (1-this.resistance);
	}



	@Override
	public void death() {
		if (this.deathAction != null) {
			Action act = new Action(game, playerData,  this.deathAction, new ArrayList<String>());
		}
		
		
	}

	@Override
	public boolean checkDeath(String killed, String killer) {
		
		
		if (killer.equals(this.playerData.getName())) {
			if (killAct!= null) {
				Action act = new Action(game, playerData, killAct, new ArrayList<String>());
			}
			
		}
		
		
		if (this.reliveTry > 0 && killed.equals(this.playerData.getName())) {
			
			if (!this.conditionKilledBy.equals("") && !this.conditionKilledBy.equals(this.set.rolesOfPlayer().get(killer).name())) {
				return false;
			}
			
			if (!this.conditionKilledByCamp.equals("") && !this.conditionKilledByCamp.equals(this.set.rolesOfPlayer().get(killer).campName())) {
				return false;
			}
			
			this.reliveTry --;
			this.resistance -=  lostResis;
			this.strenght -= lostStrenght;
			this.lostHealth -= lostHealth;
			
			
			return true;
		}
		return false;
	}

	@Override
	public void setPlayer(PlayerData playerData) {
		this.playerData = playerData;
		
	}

	@Override
	public void application() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attribution() {
		this.playerData.changeHealth(this.boostHealth);
		
	}

	
	
}



