package fr.fitzche.lgmore.Lg.SpecialsBlock;

import java.util.HashMap;

public class TreasureBlockData implements SpecialBlockData {
	public TreasureBlockType type;
	
	
	public TreasureBlockData(TreasureBlockType type) {
		this.type = type;
	}
	@Override
	public SpecialBlockType getType() {
		// TODO Auto-generated method stub
		return SpecialBlockType.Vote;
	}

}
