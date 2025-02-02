package fr.fitzche.lgmore.Lg.SpecialsBlock;

import java.util.HashMap;

public class TreasureBlockData implements SpecialBlockData {

	
	
	public TreasureBlockData(int nbOfVote) {
		this.nbOfVote = nbOfVote;
	}
	@Override
	public SpecialBlockType getType() {
		// TODO Auto-generated method stub
		return SpecialBlockType.Vote;
	}

}
