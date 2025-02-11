package fr.fitzche.lgmore.Util;

import org.bukkit.Location;
import org.bukkit.World;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

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
	
	/*public static void summonParticle(Location loc) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
	            EnumParticle.REDSTONE, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ() , (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 10, 0 
	             
	        );
		
		
	}*/
}
