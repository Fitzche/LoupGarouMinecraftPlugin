package fr.fitzche.lgmore.minecraft;

import org.bukkit.event.entity.PlayerDeathEvent;

public interface ResCheck {
	public boolean checkRes(PlayerDeathEvent e);
	public void runDeathAction(PlayerDeathEvent e);
}
