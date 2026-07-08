package fr.fitzche.lgmore;

import java.util.ArrayList;

import org.bukkit.World;

import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.minecraft.GameListener;

public interface Game {

	public ArrayList<PlayerData> getWinners();
	public ArrayList<PlayerData> getPlayers();
	public GameType getType();
	public World getWorld();
	public String getName();
	public void broadcoast(String message);
	public void setWorld(World world);
	public GameListener getListener();
	public int getMaxNBOfPlayer();
	public int getActualNbOfPlayer();
	public void playerQuit(String name);
	public void playerDefinitlyQuit(String name);
}
