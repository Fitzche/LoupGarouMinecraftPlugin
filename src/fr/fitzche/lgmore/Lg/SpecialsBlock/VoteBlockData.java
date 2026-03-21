package fr.fitzche.lgmore.Lg.SpecialsBlock;

import fr.fitzche.lgmore.Lg.GameLg;

public class VoteBlockData implements SpecialBlockData {

	public int groupe;
	public GameLg game;
	boolean blocked = true;
	public int voteAvaible;
	public VoteBlockData(int groupe, GameLg game) {
		this.game = game;
		setVoteParam();
	}
	
	public void setVoteParam() {
		
		this.groupe = this.game.groupe;
		this.voteAvaible = groupe;
	}
	
	public void launch() {
		blocked = false;
	}

	@Override
	public SpecialBlockType getType() {
		// TODO Auto-generated method stub
		return SpecialBlockType.Vote;
	}

}
