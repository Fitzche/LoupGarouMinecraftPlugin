package fr.fitzche.lgmore.Lg.SpecialsBlock;

import java.util.HashMap;

public class VoteBlockData implements SpecialBlockData {

	public int nbOfVote;
	public HashMap<String, String> hasVotedFor = new HashMap<String, String>();
	
	public VoteBlockData(int nbOfVote) {
		this.nbOfVote = nbOfVote;
	}
	@Override
	public SpecialBlockType getType() {
		// TODO Auto-generated method stub
		return SpecialBlockType.Vote;
	}

}
