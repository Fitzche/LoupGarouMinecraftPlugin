package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import net.md_5.bungee.api.ChatColor;

public class DISCIPLE implements RoleInstance {

    public boolean firstUnlock = false;
    public boolean secondUnlock = false;
    public boolean thirdUnlock = false;

    public boolean powerUsed = false;

    public Location sageLocation;

    public int time = 0;
    public String name ="Disciple";

    public PlayerData sage;
	public PlayerData playerWithRole;
	public DISCIPLE(PlayerData player) {
		this.playerWithRole = player;
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	@Override
	public String getName() {
		return (DISCIPLE.camp.getColor() + name);
	}
	
	public String getDescription() {
		if (sage != null) {
			playerWithRole.sendMessage(ChatColor.GOLD + "Le vieux sage est "+ sage.getName());
		}
		return (Main.info +ChatColor.BLUE+"Vous êtes Disciple, vous devez gagner avec le village, pour cela vous connaissez le role du vieux sage, au bout de 20min avec lui, il obtiendra votre role, au bout de 30min, vous aurez accès à la commande /lg aura [nomDeJoueur] 2 fois qui vous permettra de connaitre l'aura d'un joueur, au bout de 45min vous obtiendrez speed permanent, vous pouvez retrouver le vieux sage avec la commande /lg trouver");
	}
	public static ItemStack logo = new ItemStack(Material.WHEAT);



	public void sageDeath() {
		if (firstUnlock) {
			if (secondUnlock) {
				if (thirdUnlock) {
					this.playerWithRole.setMaxHealth(this.playerWithRole.getMaxHealth() - 6);
				} else {
					this.playerWithRole.setMaxHealth(this.playerWithRole.getMaxHealth() - 4);
				}
			} else {
				this.playerWithRole.setMaxHealth(this.playerWithRole.getMaxHealth() - 2);
			}
		}
	}

	
	public void giveEffectAllTime() {
        if (thirdUnlock) {
            playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false));
        } else if (sage != null && LocationUtil.getDistanceBetween(playerWithRole, sage) < 30) {
            time += 1;
            if (time == 1200 ) {
                sage.sendMessage(ChatColor.GOLD+"Votre Disciple est "+ playerWithRole.Name);
				playerWithRole.sendMessage("Le vieux sage a reçu votre nom");
                firstUnlock = true;
            } else if (time == 1800) {
				playerWithRole.sendMessage("Vous pouvez utiliser la commande /lg aura");
                secondUnlock = true;
            } else if (time == 3600) {
				playerWithRole.sendMessage("Vous obtenez speed de manière permanente");
                thirdUnlock = true;
            }
        }

        if (sage != null && sage.inLife) {
            this.sageLocation = sage.getLocation();
        }
        

		
	}
	
    @Deprecated
	public void giveRoleEffectAndItem(PlayerData player) {
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

            @Override
            public void run() {
                GameLg game = (GameLg)playerWithRole.game;
                ArrayList<PlayerData> sages = RoleUtil.getPlayersWithRole(game, RolesLg.SAGE);
                if (sages.size() != 0) {
                    sage = sages.get(MathUtil.generateAlInt(0, sages.size() -1));
                    playerWithRole.sendMessage(ChatColor.ITALIC + "Le Vieux sage est "+ sage.getName());
                } 
            }

        }, 200);
	}
	
	public static Camp camp = Camp.Villager;
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
		origin.sendMessage(ChatColor.GOLD+ "Ce joueur est disciple, il perd une utilisation de la commande /lg aura");
		this.playerWithRole.sendMessage(ChatColor.GOLD+ "Vous avez été aveuglé, vous perdez une utilisation de votre commande /lg aura");
		this.powerUsed = true;
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("trouver")) {
			
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = Main.strToPlayer.getOrDefault(sender.getName(), null);
				if (senderPlayerData != null) {
					GameLg gameOfSender = (GameLg)senderPlayerData.game;
					if (gameOfSender != null) {
						if (senderPlayerData.getName().equals(playerWithRole.getName())) {
							
							sender.sendMessage(ChatColor.GOLD +"La dernière position connue du vieux sage est "+ sageLocation.getX() + "; "+ sageLocation.getY() + "; "+ sageLocation.getZ() );
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return;
				}
				
			} else {
				sender.sendMessage("Seul un joueur peut effectuer cette commande");
				return;
			}
			
			
		
		}
		
		if (args[0].equals("aura")) {
			
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = Main.strToPlayer.getOrDefault(sender.getName(), null);
				if (senderPlayerData != null) {
					GameLg gameOfSender = (GameLg)senderPlayerData.game;
					if (gameOfSender != null) {
						if (senderPlayerData.getName().equals(playerWithRole.getName())) {
						
							if (secondUnlock) {
								PlayerData target = Main.strToPlayer.getOrDefault(args[1], null);
								if (target == null) {
									sender.sendMessage(ChatColor.RED +"[/lg aura] veuillez choisir un joueur valide");
								} else {
									if (!powerUsed) {
										sender.sendMessage("L'aura du joueur "+ target.Name + " est "+ target.aura);
										powerUsed = true;
									} else {
										sender.sendMessage("L'aura du joueur "+ target.Name + " est "+ target.aura);
										secondUnlock = false;
									}
								}
							}
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return;
				}
				
			} else {
				sender.sendMessage("Seul un joueur peut effectuer cette commande");
				return;
			}
			
			
		
		}
		
	}
}
