package fr.fitzche.lgmore.CharactUHC;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.Util.JsonUtil;

public class Jauge {

	
	
	String playerName;
	public String name;
	public int value;
	public int maxValue;
	boolean resetOnFinish;
	int nbOfIteration;
	boolean stopped = false;
	JsonObject action;
	CharactUHC uhc;
	CharactRole role;
	public boolean displayedOnPlayer;
	
	public Jauge(String playerName, JsonObject obj, CharactUHC uhc, CharactRole role) {
		
		
		this.name = JsonUtil.getString(obj, "name", "null");
		this.value = JsonUtil.getInt(obj, "defaultValue", 0);
		this.maxValue = JsonUtil.getInt(obj, "maxValue", 100);
		this.resetOnFinish = JsonUtil.getBool(obj, "resetOnFinish", false);
		this.nbOfIteration = JsonUtil.getInt(obj, "iteration", 1);
		this.action = JsonUtil.getJsonObject(obj, "action");
		this.displayedOnPlayer = JsonUtil.getBool(obj, "displayed", false);
		this.role = role;
		this.uhc = uhc;
		
		
		
		
	}
	
	public void add(int i) {
		if (value  + i >= maxValue) {
			
			
			if (!stopped) {
				Action act = new Action(uhc, Main.getData(playerName), action, new ArrayList<String>());
			}
			
			
			nbOfIteration --;
			if (nbOfIteration <= 0) {
				this.stopped = true;
			}
			if (resetOnFinish) {
				this.value = value+ i - maxValue;
			} else {
				this.stopped = true;
			}
			
		} else {
			this.value = value + i;
		}
	}
}
