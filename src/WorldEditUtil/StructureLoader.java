package WorldEditUtil;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;


import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.PasteBuilder;
import com.sk89q.worldedit.world.registry.WorldData;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.jnbt.NBTOutputStream;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.EditSessionBlockChangeDelegate;
import com.sk89q.worldedit.bukkit.adapter.*;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.extent.clipboard.io.SchematicReader;
import com.sk89q.worldedit.extent.clipboard.io.SchematicWriter;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;

public class StructureLoader {
	
	@Deprecated
	public static void save(Location primary, Location secondary, File saveFile) {
		System.out.println("from "+primary.getBlockX()+ "; "+primary.getBlockY()+ "; "+primary.getBlockZ()+ " to "+ secondary.getBlockX()+ "; "+secondary.getBlockY()+ "; "+ secondary.getBlockZ());
		if (!saveFile.exists()) {
			System.out.println("file to create");
			try {
				saveFile.createNewFile();
				System.out.println("file created");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Region regionToSave = new CuboidRegion(locToVector(primary), locToVector(secondary));
		
		Clipboard clip =new BlockArrayClipboard(regionToSave);
		
		SchematicWriter writer = null;
		
		try {
			writer = new SchematicWriter(new NBTOutputStream(new FileOutputStream(saveFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EditSession editSession = new EditSession(BukkitUtil.getLocalWorld(Main.world), 1000);
		ForwardExtentCopy forw = new ForwardExtentCopy(editSession, regionToSave, clip, regionToSave.getMinimumPoint());
		try {
			Operations.complete(forw);
		} catch (WorldEditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       
            	
		try {
			writer.write(clip, Main.worldData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static void place(Location primary,  Clipboard clip) {
		try {
			
			
			EditSession editSession = new EditSession(BukkitUtil.getLocalWorld(Main.world), 1000);
			
			editSession.enableQueue();
			if (clip == null) {
				System.out.println("null");
			}
			
			
			
			
			
			
		    Operation operation = new ClipboardHolder(clip, Main.worldData)
		            .createPaste(editSession, Main.worldData)
		            .to(locToVector(primary))
		            // configure here

		            .build();
		    
		    Operations.complete(operation);
		    
		    Operations.complete(editSession.commit());
		    editSession.flushQueue();
		   
		    
		} catch (WorldEditException e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println("placed");
	}

	public static Clipboard load(File sourceFile) throws IOException {
		
		
		System.out.println("start reading");
		
		SchematicReader reader = new SchematicReader(new NBTInputStream(new FileInputStream(sourceFile)));
		
		Clipboard clip = reader.read(BukkitUtil.getLocalWorld(Main.world).getWorldData());
		System.out.println("readed");
		return clip;
	}
	public static BlockVector locToVector(Location loc) {
		
		return new BlockVector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
}
