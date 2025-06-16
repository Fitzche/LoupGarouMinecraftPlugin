package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;

import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class PYROMANE implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Pyromane";
    public Camp camp = Camp.Other;
    public boolean fireAspect = false;
    public boolean powerUsed = false;
    public boolean inFire = false;
    public ArrayList<PlayerData> filled = new ArrayList<PlayerData>();
    public GameLg game;

	public PYROMANE(PlayerData player) {
		this.playerWithRole = player;
		this.game = player.game;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		
	}


    public void recouvrir(PlayerData player) {
        if (player != null && filled.size()< 3) {
            filled.add(player);
            playerWithRole.sendMessage("Vous avez recouvert "+ player.getName() + " d'essence");

        }
    }

    public void fireaspect() {
        this.fireAspect = !fireAspect;
        if (fireAspect) {
            playerWithRole.sendMessage("Vous avez activé votre fire aspect et votre flamme");
        } else {
            playerWithRole.sendMessage("Vous avez désactivé votre fire aspect et votre flamme");
        }
    }

    @Deprecated
    public void allumer() {
    	if (game == null) {
    		playerWithRole.sendMessage("vous n'est pas dans une partie (erreur pyromane allumer()");
    		return;
    	}
        if (powerUsed == true) {
        	playerWithRole.sendMessage("Vous ne pouvez allumer vos cibles qu'une seule fois");
            return;
        } 
        playerWithRole.sendMessage("Vous avez allumé vos cibles");
        ArrayList<PlayerData> tempFired = new ArrayList<PlayerData>();

        for (PlayerData target:this.game.getPlayerAlive()) {
            if (LocationUtil.getDistanceBetween(target, filled.get(0) )< 20 ||LocationUtil.getDistanceBetween(target, filled.get(0) )< 20) {
                tempFired.add(target);
            }
        }

        filled.addAll(tempFired);

        inFire = true;
        Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

            @Override
            public void run() {
                inFire = false;
            }

        }, 400);

        powerUsed = true;
    }
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez gagner tout seul, pour cela vous avez 15% de chance d'enflammer toute personne que vous taper ou toucher avec une flèche (pouvoir activable/desactivable avec /lg switchfire), de plus vous pouvez 3 fois dans la partie recouvrir d'essence un joueur à moins de 20 blocs (avec la commande /lg recouvrir nomDuJoueur), puis enflammer tous les joueurs recouverts avec /lg enflammer, ce qui aura pour effet de mettre en feu le joueur pendant 20s ainsi que tous les joueurs se trouvant à moins de 20 blocs de celui-ci, vous possédez fire protection de manière permanente ");
	}
	

	
	public void giveEffectAllTime() {
		playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 60, false, false));
        if (inFire) {
            for (PlayerData player: filled) {
            	if (player.isOnline){
                	player.player.setFireTicks(40);
            	}
            }
        }
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	
	
	@Override
	public void giveNightEffect() {
		
		
		
	}

	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void episodeEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEpisodeTrue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}


	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur n'est pas un rôle à info");
		
	}


	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("switchfire")) {
			System.out.println("switch");
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = Main.strToPlayer.getOrDefault(sender.getName(), null);
				if (senderPlayerData != null) {
					GameLg gameOfSender = senderPlayerData.game;
					if (gameOfSender != null) {
						if (senderPlayerData.getName().equals(playerWithRole.getName())) {
							
							fireaspect();
							return;
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return;
				}
				
			} else{
				sender.sendMessage("Seul un joueur peut effectuer cette commande");
				return;
			} 
		}
		if (args[0].equals("enflammer")) {
			System.out.println("enflammed");
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = Main.strToPlayer.getOrDefault(sender.getName(), null);
				if (senderPlayerData != null) {
					GameLg gameOfSender = senderPlayerData.game;
					if (gameOfSender != null) {
						if (senderPlayerData.getName().equals(playerWithRole.getName())) {
							
							allumer();
							return;
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return;
				}
				
			} 
		}
		if (args[0].equals("recouvrir")) {
			
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = Main.strToPlayer.getOrDefault(sender.getName(), null);
				if (senderPlayerData != null) {
					GameLg gameOfSender = senderPlayerData.game;
					if (gameOfSender != null) {
						if (senderPlayerData.getName().equals(playerWithRole.getName())) {
							PlayerData target = Main.strToPlayer.getOrDefault(args[1], null);
							if (target == null) {
								sender.sendMessage("Le joueur visé n'existe pas");
								return;
							}
							
							recouvrir(target);
							return;
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return;
				}
				
			}
		}
		
		
		
	}
}
