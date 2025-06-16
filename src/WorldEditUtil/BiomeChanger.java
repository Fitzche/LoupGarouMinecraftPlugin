package WorldEditUtil;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.biome.BaseBiome;

import fr.fitzche.lgmore.Main;




public class BiomeChanger {

	
	
	public static void change(int radius, int biomeId, World world) {
		CuboidRegion region = new CuboidRegion(BukkitUtil.getLocalWorld(world), new Vector(-1000, 0, -1000), new Vector(1000, 255, 1000));
		EditSession editSession = new EditSession(BukkitUtil.getLocalWorld(Main.world), 1000);
		for (int x = 0 - radius; x <= radius; x++) {
            for (int z = 0 - radius; z <= radius; z++) {
                Vector2D position = new Vector2D(x, z);
                editSession.setBiome(position, new BaseBiome(biomeId));
            }
        }
		
		try {
			Operations.complete(editSession.commit());
		} catch (WorldEditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
