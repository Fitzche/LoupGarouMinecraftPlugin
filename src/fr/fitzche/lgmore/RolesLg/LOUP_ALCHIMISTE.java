package fr.fitzche.lgmore.RolesLg;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class LOUP_ALCHIMISTE implements RoleInstance {
	public PlayerData playerWithRole;
	public PotionEffect STRENGTH;
	public static Camp camp = Camp.Wolf;
	public GameLg game;
	public boolean powerUsed = false;
	public String name ="Loup-Garou Alchimiste";
	public HashMap<PlayerData, Integer> players = new HashMap<PlayerData, Integer>();
	
	public LOUP_ALCHIMISTE(PlayerData player) {
		this.playerWithRole = player;
		this.STRENGTH = PotionUtil.STRENGTH;
		this.game = GameLgUtil.getGameOfPlayer(player, "at lg alchimiste creation");
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
		return (ChatColor.DARK_BLUE+"Vous devez gagner avec les Loups Garou (vous possédez la liste de vos alliés loups), pour cela vous pouvez donner un virus à un joueur après etre resté 15min à coté de lui, il existe 3 type de virus que vous pouvez choisir: "
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
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg declencheVirus epid"+p.Name+ " "+ this.playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text);
		
		TextComponent text1 = new TextComponent();
		text1.setText("Clicquez ici pour choisir le poison");
		text1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg declencheVirus pois"+p.Name+ " "+ this.playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text1);
		
		TextComponent text2 = new TextComponent();
		text2.setText("Clicquez ici pour choisir le parasite");
		text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg declencheVirus parasit"+p.Name+ " "+ this.playerWithRole.Name)));
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

}
