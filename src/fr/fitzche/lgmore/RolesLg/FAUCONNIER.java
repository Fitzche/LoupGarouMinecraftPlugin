package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;

public class FAUCONNIER implements RoleInstance {

	public PlayerData playerWithRole;
	
	public int faucon = 3;
	public ArrayList<Location> spottedPoint = new ArrayList<Location>();

	public FAUCONNIER(PlayerData p) {
		this.playerWithRole = p;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Fauconnier";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Vous gagner avec le village, pour cela vous possédez 3 faucons que vous pouvez placer à un endroit précis avec la commande /lg faucon. Si quelqu'un meurt dans les 50 blocs autours, vous connaitrez le role du mort ainsi que le nombre de joueurs alentours et l'aura du tueur. Le faucon aura 20% de chance de mourir. Des particules vous apparaissent là où vous laisser un faucon.";
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveEffectAllTime() {
		for (Location loc:spottedPoint) {
			PlayerUtil.particleFor(playerWithRole.player, loc, Color.BLUE, 0.3);
		}
		

	}

	@Override
	public void giveNightEffectCheck() {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveNightEffect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void episodeEffect() {
		// TODO Auto-generated method stub

	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public void setEpisodeTrue() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage("Ce joueur est fauconnier, vous lui retirez un faucon");
		if (faucon <= 0) {
			if (spottedPoint.size() > 0) {
				playerWithRole.sendMessage("Vous perdez le faucon en "+ spottedPoint.get(0).getBlockX() + "; "+ spottedPoint.get(0).getY()+"; " + spottedPoint.get(0).getZ());
				return;
			}
		}
		playerWithRole.sendMessage("Vous perdez un faucon");
		faucon --;
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("faucon") && sender.getName().equals(playerWithRole.getName())) {
			if (faucon > 0) {
				playerWithRole.sendMessage("Vous placez un faucon en "+ LocationUtil.toString(playerWithRole.player.getLocation()));
				faucon --;
				this.spottedPoint.add(playerWithRole.player.getLocation());
			} else {
				playerWithRole.sendMessage("Il ne vous reste plus de faucon à placer");
			}
		}

	}

}
