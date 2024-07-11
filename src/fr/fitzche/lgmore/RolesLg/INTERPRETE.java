package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.RoleUtilLg;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class INTERPRETE implements RoleInstance{
	public Camp camp = Camp.Villager;
	public PlayerData playerWithRole;
	public ArrayList<RolesLg> roles = new ArrayList<RolesLg>();
	public RoleInstance roleAct;
	public RolesLg choosen;
	public boolean created = false;
	public INTERPRETE it;
	public String name ="Interprete";
	public INTERPRETE(PlayerData player) {
		this.playerWithRole= player;
		this.it = this;
		
	}
	
	@Deprecated
	public void interpreter(RolesLg role) {
		
		roles.remove(role);
		playerWithRole.roleIn = RoleUtilLg.createRole(role, playerWithRole.player);
		playerWithRole.sendMessage("Vous interprétez: "+ roleAct.getName());
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				playerWithRole.roleIn = it;
				
			}
			
		}, 24000);
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return camp.getColor()+ name;
	}

	@Override
	public String getDescription() {
		
		String t = ChatColor.DARK_BLUE + "Vous devez gagner avec le village, pour cela vous pouvez interpréter un role parmis 3 roles qui vous seront communiqué peu avant chaque épisode, vous posséderez les effets et pouvoirs de ce role, chaque rôle ne peut être interprété qu'une seule fois";
		if (roleAct != null) {
			String fal = "Vous êtes "+ roleAct.getName() +roleAct.getDescription();
			return t + "\n" + fal;
		} else {
			return t;
		}
		
	}

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		if (roleAct != null) {
			roleAct.giveRoleEffectAndItem(player);
		}
		
	}

	@Override
	public void giveEffectAllTime() {
		if (roleAct != null) {
				roleAct.giveEffectAllTime();
			}
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (roleAct != null) {
				roleAct.giveNightEffectCheck();
			}
		
	}

	@Override
	public void giveNightEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveDayEffect() {
		if (roleAct != null) {
				roleAct.giveDayEffect();	
			}
		
	}

	@Override
	public void episodeEffect() {
		if (roleAct != null) {
				roleAct.episodeEffect();	
			}
		
	}

	@Deprecated
	@Override
	public void setEpisodeTrue() {
		
		if (roles.size()==0 && !created) {
			ArrayList<RolesLg> rol = RoleUtilLg.getRoleofCamp(RoleUtilLg.existingRoles, camp, "feur");

			int one = MathUtil.generateAlInt(0, rol.size() - 1);
			int  two = MathUtil.generateAlInt(0, rol.size() - 1);
			int three = MathUtil.generateAlInt(0, rol.size() - 1);
			roles.add(rol.get(one));
			roles.add(rol.get(two));
			roles.add(rol.get(three));
			
			created = true;
		}
		
		this.roleAct = null;
		if (choosen != null) {
			this.interpreter(choosen);
		}
		
		this.choosen = null;
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				playerWithRole.sendMessage("Vous pouvez choisir un role à interpréter: " + "\n");
				if (roles.size() == 0) {
					System.out.println("roles of inteprete are null");
				}
				for (RolesLg role: roles) {
					TextComponent text = new TextComponent();
					text.setText(ChatColor.DARK_GREEN+"-Clicquez ici pour choisir d'interpréter ce role:" + role.getName());
					text.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/lg chooseInter " + role.getName()+ " " + playerWithRole.Name));
					playerWithRole.player.spigot().sendMessage(text);
				}
				
			}
			
		}, 600/*22800*/);
		
	}

	@Override
	public void startSpecialEvent() {
		if (roleAct != null) {
				roleAct.startSpecialEvent();	
			}
		
	}

}
