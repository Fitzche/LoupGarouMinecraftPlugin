package fr.fitzche.lgmore.Util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import fr.fitzche.lgmore.Main;
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
	
	public static World createNewWorld(World world){
	     File worldDir = world.getWorldFolder();
	     String newName = world.getName() + "_temp"+MathUtil.generateAlInt(0, 1000);
	     try {
			FileUtils.copyDirectory(worldDir, new File(worldDir.getParent(), newName));
			File uidFile = new File(Bukkit.getWorldContainer(), newName+"/uid.dat");
			if (uidFile.exists()) {
			    uidFile.delete();
			}
	     } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	     WorldCreator creator = new WorldCreator(newName);
	     
	     return Bukkit.createWorld(creator);
	}
	public static World createNewWorld(){
	     

	     WorldCreator creator = new WorldCreator("world");
	     
	     return Bukkit.createWorld(creator);
	}
	
	/*public static void summonParticle(Location loc) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
	            EnumParticle.REDSTONE, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ() , (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 10, 0 
	             
	        );
		
		
	}*/
}
