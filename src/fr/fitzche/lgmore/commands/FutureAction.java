package fr.fitzche.lgmore.commands;

import org.bukkit.scheduler.BukkitRunnable;

public class FutureAction {
	public int timeBeforeRun;
	public BukkitRunnable action;
	
	public FutureAction(BukkitRunnable action, int timeBeforeRun) {
		this.action = action;
		this.timeBeforeRun = timeBeforeRun;
	}

}
