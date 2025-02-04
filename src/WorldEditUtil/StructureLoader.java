package WorldEditUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bukkit.Location;


import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.registry.WorldData;

import fr.fitzche.lgmore.Main;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.EditSessionBlockChangeDelegate;
import com.sk89q.worldedit.bukkit.adapter.*;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.extent.clipboard.io.SchematicWriter;
import com.sk89q.worldedit.function.operation.Operation;

public class StructureLoader {
	
	public static void save(Location primary, Location secondary, File saveFile) {
		
		Region regionToSave = new CuboidRegion(locToVector(primary), locToVector(secondary));
		Clipboard clip = new BlockArrayClipboard(regionToSave);
		ClipboardWriter writer;
		try {
			writer = ClipboardFormat.SCHEMATIC.getWriter(new FileOutputStream(saveFile));
			try {
				EditSession editSession = new EditSession(BukkitUtil.getLocalWorld(Main.world), 1000);
				
				writer.write(clip, editSession.getWorld().getWorldData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public static Clipboard load(Location primary, Location secondary, File sourceFile) {
		try {
			erreur
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//ClipboardFormats.findByFile(sourceFile).getReader(new FileInputStream(sourceFile));
	}
	public static BlockVector locToVector(Location loc) {
		
		return new BlockVector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
}
