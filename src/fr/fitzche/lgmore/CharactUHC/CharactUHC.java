package fr.fitzche.lgmore.CharactUHC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Minage.MinageWorld;
import fr.fitzche.lgmore.Util.JsonUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.commands.FutureAction;
import fr.fitzche.lgmore.custom.CustomGame;
import fr.fitzche.lgmore.custom.CustomGameDataSet;
import fr.fitzche.lgmore.custom.CustomParam;
import fr.fitzche.lgmore.custom.CustomTimer;
import fr.fitzche.lgmore.custom.RoleSet;
import fr.fitzche.lgmore.minecraft.GameListener;

public class CharactUHC implements CustomGame{

	
	public CharactUHC(String path) {
		
		
		File file = new File(("Gamemodes/"+path));
		file.setWritable(true);
		file.setReadable(true);
	
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				FileWriter writer = new FileWriter(file);
			
				writer.write(Main.defaultJsonGamemodeString);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		InputStream is = null;
		
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (is == null) { throw new IllegalStateException("Impossible de trouver json dans les dossiers"); }
		String content = null;
		
		try {
			content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		JsonParser parser = new JsonParser();
		JsonElement json = parser.parse(content);
		JsonObject object = json.getAsJsonObject();
		
		boolean hasMinage = JsonUtil.getBool(object, "hasMinageinage", false);
		int timeMinage = JsonUtil.getInt(object, "minageTime", 10);
		int boostMinage = JsonUtil.getInt(object, "boostMinage", 1);
		
		//conditions par défaut (s'il est  n'est pas initialisée, la condition sera false par défaut
		if (JsonUtil.getJsonObject(object, "conditions") != null) {
			JsonObject conds = JsonUtil.getJsonObject(object, "conditions");
			for (Entry<String, JsonElement> cond:conds.entrySet()) {
				conditions.put(cond.getKey(), cond.getValue().getAsBoolean());
					
			
			}
		}
		
		//conditions numériques par défaut (s'il est  n'est pas initialisée, la condition sera false par défaut
				if (JsonUtil.getJsonObject(object, "numConditions") != null) {
					JsonObject condsNum = JsonUtil.getJsonObject(object, "numConditions");
					for (Entry<String, JsonElement> cond:condsNum.entrySet()) {
						numConditions.put(cond.getKey(), cond.getValue().getAsInt());
							
					
					}
				}
		
		
		
		if (object.get("roleListNames") != null) {
			
			
			JsonArray roleListNamesJson = object.get("roleListNames").getAsJsonArray();
			for (JsonElement elm:roleListNamesJson) {
				JsonObject obj = elm.getAsJsonObject();
				
				int timeApp = JsonUtil.getInt(obj, "timeApplication", MathUtil.generateAlInt(3, 20));
				String listName = JsonUtil.getString(obj, "listName", "unreferenced");
				this.charactRoleLists.add(new CharactRoleList(this, listName, timeApp));
			}
			
			
		}
		

		
		for (CharactRoleList list:this.charactRoleLists) {
			this.set.add(list);
		}
		
		data  = new CustomGameDataSet(
				false,
				500, 
				"", 
				hasMinage,
				1* boostMinage,
				1* boostMinage, 
				1 * boostMinage, 
				timeMinage,
				false,
				set, 
				new ArrayList<CustomParam>(Arrays.asList())
				);
		
		
		
	}
	
	public ArrayList<PlayerData> basePlayers = new ArrayList<PlayerData>();
	public ArrayList<PlayerData> Alives = new ArrayList<PlayerData>();
	World world;
	String name = "Game-"+("CharactUhc-"+MathUtil.generateAlInt(0, 1000));
	CustomTimer timer;
	
	ArrayList<RoleSet> set;
	HashMap<String, Boolean> conditions = new HashMap<String, Boolean>();
	
	
	
	
	
	
	private CustomGameDataSet data;
	public String hoster = "";
	private boolean started = false;
	private ArrayList<CharactRoleList> charactRoleLists = new ArrayList<CharactRoleList>();
	public ArrayList<FutureAction> futures = new ArrayList<FutureAction>();
	public HashMap<String, Integer> numConditions;

	
	
	@Override
	public boolean canJoin(PlayerData p) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void addPlayer(PlayerData p) {
		if (this.basePlayers.size() == 0) {
			this.hoster = p.getName();
			p.sendMessage("Vous pouvez à tout moment lancer la partie en executant la commande /lg startCharact");
		}
		this.basePlayers.add(p);
		
	}

	@Override
	public ArrayList<PlayerData> getWinners() {
		// TODO Auto-generated method stub
		return this.Alives;
	}

	@Override
	public ArrayList<PlayerData> getPlayers() {
		if (started) {
			return this.Alives;
		} else {
			return basePlayers;
		}
		
	}

	@Override
	public GameType getType() {
		// TODO Auto-generated method stub
		return GameType.CharactUhc;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return this.world;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void broadcoast(String message) {
		for (PlayerData p:this.Alives) {
			p.sendMessage(message);
		}
		
	}

	@Override
	public void setWorld(World world) {
		this.world = world;
		
	}

	@Override
	public GameListener getListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxNBOfPlayer() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public int getActualNbOfPlayer() {
		// TODO Auto-generated method stub
		return Alives.size();
	}

	@Override
	public void askRunFuturesActions() {
		for (FutureAction act:this.futures) {
			act.timeBeforeRun--;
			if (act.timeBeforeRun == 0) {
				act.action.run();
				act.timeBeforeRun = -1;
			}
		}
		
	}

	@Override
	public CustomTimer getTimer() {
		// TODO Auto-generated method stub
		return this.timer;
	}

	@Override
	public void eachSecond() {
		for (PlayerData p:this.Alives) {
			for (RoleSet setRole:this.set) {
				for (VisionPower pow: ((CharactRole) setRole.rolesOfPlayer().get(p.getName())).powersVision) {
					if (pow.aleaTime && MathUtil.pourcentage(1) && MathUtil.pourcentage(10)) {
						pow.run();
					}
				}
				if (((CharactRole) setRole.rolesOfPlayer().get(p.getName())) != null) {
					CharactRole role = ((CharactRole) setRole.rolesOfPlayer().get(p.getName()));
					if (role.timedAction.get(this.timer.temps) != null) {
						Action act = new Action(this, p, role, role.timedAction.get(this.timer.temps), new ArrayList<String>());
					}
				}
				
			}
			
			
		}
		
	}
	
	
	

	@Override
	public ArrayList<RoleSet> rolesSet() {
		
		return this.set;
	}

	@Override
	public CustomGameDataSet getData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	@Override
	public void start() {
		CustomGame.run(this);
		this.started = true;
		
	}

	

	@Override
	public void playerDefinitlyQuit(String playerName) {
		CustomGame.death(this, playerName);
		
	}

	@Override
	public boolean hasStarted() {
		// TODO Auto-generated method stub
		return this.started;
	}

	@Override
	public void playerQuit(String name) {
		if (!this.hasStarted()) {
			this.basePlayers.remove(Main.getData(name));
			if (name.equals(hoster)) {
				if (this.getPlayers().size() > 0) {
					hoster = this.getPlayers().get(0).getName();
					this.getPlayers().get(0).sendMessage("Vous pouvez à tout moment lancer la partie en executant la commande /lg startCharact");

				}
				
			}
		}
		
	}

	@Override
	public void setTimer(CustomTimer customTimer) {
		this.timer = customTimer;
		
	}

	

}
