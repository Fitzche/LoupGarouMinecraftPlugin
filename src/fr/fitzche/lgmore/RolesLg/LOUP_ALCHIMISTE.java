package fr.fitzche.lgmore.RolesLg;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Infections.Virus;
import fr.fitzche.lgmore.RolesLg.Infections.VirusType;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class LOUP_ALCHIMISTE implements RoleInstance {
	public PlayerData playerWithRole;
	
	public static Camp camp = Camp.Wolf;
	public GameLg game;
	public boolean powerUsed = false;
	public String name ="Loup-Garou Alchimiste";
	public HashMap<PlayerData, Integer> players = new HashMap<PlayerData, Integer>();
	
	public LOUP_ALCHIMISTE(PlayerData player) {
		this.playerWithRole = player;
		
		this.game = (GameLg)player.game;
		for (PlayerData p:game.getPlayerAlive()) {
			players.put(p, 0);
		}
		
	}
	
	public static ItemStack logo = new ItemStack(Material.POTION);

	
	
	@Override
	public String getName() {
		
		return (camp.getColor() +name);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Loups Garou (vous possédez la liste de vos alliés loups), pour cela vous pouvez donner un virus à un joueur après etre resté 15min à coté de lui avec la commande /lg virus [nomDuJoueur], vous ne possédez pas force de nuit, il existe 3 type de virus que vous pouvez choisir: "
				+ ChatColor.RED+"L'Epidemie: "+ChatColor.DARK_BLUE+"Le joueur infecté sera averti de son état après 1min, il aura cepandant une chance de donner le même virus au joueurs environnant après 30sec, au bout de 5min, le joueur subira un effet de poison en plus d'un effet de faiblesse durant entre 2 et 10 min de manière aléatoire "
				+ChatColor.RED+"Le Poison: "+ChatColor.DARK_BLUE+ "Le joueur infecté -ou empoisonné- perdra 1/2 coeur toutes les 5minutes jusqu'à ce que le loup garou alchimiste meurt, et il ne retrouvera ses coeur que s'il est l'assassin du loup garou alchimiste, pendant ce temps, l'alchimiste gagne autant de coeur que l'infecté en perd"
				+ ChatColor.RED+"Le Parasite: "+ChatColor.DARK_BLUE+ "Le joueur parasité sera affecté au bout de 10min, alors le joueur pourra soit subir un effet de weakness et une perde de 2 coeur, soit un effet similaire ou virus du poison, soit il sera transformé en idiot du village, soit il deviendra servant des loups, soit il deviendra solo. Pour échapper à ce sort, le joueur peut tuer un autre joueur qui ressucitera en ayant récupéré le parasite. (le parasite ne peut pas rendre un joueur solo si celui-ci est en couple."
						);
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveEffectAllTime() {
		for (PlayerData p:game.getPlayerAlive()) {
			players.put(p,players.get(p)+1);
		}
		
	}


	@Override
	public void giveNightEffect() {
		System.out.println("nk.1");
		if (playerWithRole == null) {
			System.out.println("effect can't be gived at null player");
		}
		
		
		//VOIR SCHEDULER + EFFECT = ERROR ???
		System.out.println("nk.2");
		
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
	
	public void choose(PlayerData p) {
		if (players.get(p) < 900 || powerUsed) {
			playerWithRole.sendMessage(ChatColor.AQUA+ "Vous n'avez pas passé assez de temps à coté de ce joueur, ou vous avez déjà utilisé votre pouvoir");
			return;
		} 
		playerWithRole.sendMessage(ChatColor.AQUA+"Vous choisissez de contaminer "+ p.Name);
		TextComponent text = new TextComponent();
		text.setText("Clicquez ici pour choisir l'épidémie");
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg declencheVirus "+"epid "+p.Name+ " "+ this.playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text);
		
		TextComponent text1 = new TextComponent();
		text1.setText("Clicquez ici pour choisir le poison");
		text1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg declencheVirus "+"pois "+p.Name+ " "+ this.playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text1);
		
		TextComponent text2 = new TextComponent();
		text2.setText("Clicquez ici pour choisir le parasite");
		text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg declencheVirus "+"parasit "+p.Name+ " "+ this.playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text2);
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
		if (args[0].equals("virus")) {
			if (args[1]== null) {
				sender.sendMessage("Il manque du contenu");
			}
			PlayerData alchi = Main.getData(sender);
			if (alchi.getName().equals(playerWithRole.getName())) {
				
				if (powerUsed) {
					alchi.sendMessage(ChatColor.AQUA+"Vous avez déjà utilisé votre pouvoir");
				} else {
					if (Main.getData(args[1]) != null) {
						choose(Main.getData(args[1]));
					}
					
				}
			}
		} 
		if (args[0].equals("declencheVirus")) {
			
			PlayerData target = Main.getData(args[2]);
			PlayerData declencher = Main.getData(args[3]);
			Virus virus;
			switch (args[1]) {
			
			case "epid":
				
				if (powerUsed) {
					sender.sendMessage("Pouvoir déjà utilisé");
					return;
				}
				virus = new Virus(VirusType.EPIDEMIE	, target, declencher, 12000);
				declencher.sendMessage(ChatColor.AQUA+"Vous avez mis une épidémie sur "+target.Name);
				powerUsed = true;
				break;
			case "parasit":
				

				if (powerUsed) {
					sender.sendMessage("Pouvoir déjà utilisé");
					return;
				}
				virus = new Virus(VirusType.PARASITE, target, declencher, 0);
				target.sendMessage(ChatColor.AQUA+"Vous avez mis un parasite sur "+target.Name);
				powerUsed = true;
				break;
			case "pois":
				

				if (powerUsed) {
					sender.sendMessage("Pouvoir déjà utilisé");
					return;
				}
				virus = new Virus(VirusType.POISON, target, declencher, 0);
				sender.sendMessage(ChatColor.AQUA+"Vous avez empoisonné "+target.Name);
				powerUsed = true;
				break;
			
				
				
			} 
		}
		
	}

}
