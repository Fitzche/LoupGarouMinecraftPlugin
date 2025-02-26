package fr.fitzche.lgmore.Lg.SpecialsBlock;

import fr.fitzche.lgmore.InfinityStones.Stone;
import fr.fitzche.lgmore.InfinityStones.StonesType;
import fr.fitzche.lgmore.RolesLg.THANOS;

public class StoneBlockData implements SpecialBlockData {

	public StonesType type;
	public THANOS than;
	public boolean taken;
	public Stone stone;
	
	
	public StoneBlockData(Stone stone) {
		this.type = stone.type;
		this.than = stone.thanos;
		this.stone = stone;
	}
	@Override
	public SpecialBlockType getType() {
		// TODO Auto-generated method stub
		return SpecialBlockType.Stone;
	}

}
