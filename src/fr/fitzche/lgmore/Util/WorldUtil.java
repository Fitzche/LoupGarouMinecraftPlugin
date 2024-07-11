package fr.fitzche.lgmore.Util;

import org.bukkit.World;

public class WorldUtil {
	public static String getTime(World world) {
		if (world.getTime() < 12000) {
			return "day";
		} else if (world.getTime() >= 12000 && world.getTime() < 24000) {
			return "night";
		} else if (world.getTime() >= 24000 ) {
			world.setTime(world.getTime() - 24000);
			return "day";
		}
		return null;
	}
}
