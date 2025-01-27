package fr.fitzche.lgmore.Util;

import java.util.ArrayList;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;

public class VoteEvent {

	
	private GameLg game;
	private PlayerData mostVoted;
	private boolean hasCorb;
	private int nbVote;
	private ArrayList<PlayerData> voterFor;
	
	public VoteEvent(GameLg game, PlayerData mostVoted, int nbOfVoteFor,boolean hasCorbVoteFor) {
		this.game = game;
		this.mostVoted = mostVoted;
		this.nbVote = nbOfVoteFor;
		this.hasCorb = hasCorbVoteFor;
	}
	
	public boolean hasCorbVoteFor() {
		return hasCorb;
	}
	
	public GameLg getGame() {
		return this.game;
	}
	
	public PlayerData getVoted() {
		return this.mostVoted;
	}
	
	public int getNbOfVote() {
		return this.nbVote;
	}
	
	public PlayerData getForWhoHeVote(PlayerData p) {
		if (game.getPlayerAlive().contains(p)) {
			return p.voted;
		}
		return null;
	}
}
