package fr.fitzche.lgmore.Lg.SpecialsBlock;

import java.util.ArrayList;
import java.util.HashMap;

public class TreasureBlockData implements SpecialBlockData {
	public TreasureBlockType type;
	public ArrayList<String> playersClickedOne = new ArrayList<String>();
	public boolean used = false;
	
	
	public TreasureBlockData(TreasureBlockType type) {
		this.type = type;
	}
	@Override
	public SpecialBlockType getType() {
		// TODO Auto-generated method stub
		return SpecialBlockType.Treasure;
	}

}
