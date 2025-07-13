package fr.fitzche.lgmore.RolesLg;

import java.awt.print.Book;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;

import fr.fitzche.lgmore.RolesLg.Checkers.demonChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class DEMON implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Démon";
	public Camp camp = Camp.Other;
	public GameLg game;
	public ArrayList<String> namesChoosen = new ArrayList<String>();
	
	public ArrayList<PlayerData> pactedPlayers = new ArrayList<PlayerData>();
	
	
	public void pacte(PlayerData ply) {
		
	}
	
	public DEMON(PlayerData player) {
		this.playerWithRole = player;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		
		
		this.game = game;
		
		game.resCheckers.add(new demonChecker(this));
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		this.playerWithRole.sendMessage(ChatColor.GOLD+"Vous pouvez pactiser avec les joueurs suivants: ");
		for (String str: this.namesChoosen) {
			this.playerWithRole.sendMessage(ChatColor.GOLD+"-"+str);
		}
		
		return (ChatColor.DARK_BLUE+"Vous êtes démon, vous gagnez tout seul, pour cela vous avez le droit à l'enchant tranchant 4. De plus vous pouvez proposer un pacte à un joueur avec la commande /lg pactiser [nomDuJoueur], si celui-ci accepte, vous et lui perdrez 2 coeurs permanents. Il obtiendra un role d'un joueur au hasard, recevra 5 pommes d'or et sera vu positivement par les roles à infos. Vous obtiendrez son role. Si ce joueur vient à mourir, vous récupérer 1 coeur, et celui-ci réssucitera avec weakness et sans son role, il devra alors gagner avec vous. S'il meurt à nouveau, vous récupérerez 1 autre coeur, et il mourra définitivement. Vous n'avez pas de limite de pacte, mais attention à ne pas trop vous affaiblier"+ ChatColor.GOLD+"\n"+"Vous commencez à 12 coeurs permanents");
	}
	public static ItemStack logo = new ItemStack(Material.GOLD_SWORD);

	
	public void giveEffectAllTime() {
		//null
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		playerWithRole.changeHealth(4);
		int c = 0;
		for (int i = 0; i < 3; i++ ) {
			c++;
			String name = ((GameLg)player.game).getPlayerAlive().get(MathUtil.generateAlInt(0, ((GameLg)player.game).getPlayerAlive().size() -1)).getName();
			
			boolean already = false;
			for (String str:namesChoosen) {
				if (name.equals(str)) {
					already = true;
				}
			}
			
			if (already && c<5) {
				i --;
			} else {
				this.namesChoosen.add(name);
			}
			
		}
	}
	
	
	
	
	@Override
	public void giveNightEffect() {
		
		
		
	}

	@Override
	public void giveDayEffect() {
		
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
		if (args[0].equals("pacteAccept")) {
			PlayerData target = Main.strToPlayer.getOrDefault(args[1], playerWithRole);
			PlayerData demon = Main.strToPlayer.getOrDefault(args[2], playerWithRole);
			if (target == null || demon == null) {
				sender.sendMessage("erreur dans les noms de joueurs saisis");
				return;
			}
			if (!demon.getName().equals(playerWithRole.getName())) {
				return;
			}
			
			
			if (target.inLove) {
				target.sendMessage(ChatColor.LIGHT_PURPLE+"Vous ne pouvez pas accepter un pacte en étant en couple");
				return;
			}
			
			
			if (target.pacteAccept) {
				return;
			}
			target.pacteAccept = true;
			target.sendMessage(ChatColor.DARK_RED+ "Vous avez accepté le pacte du démon, veuillez référer au message précédent pour en connaitre les règles.");
			demon.sendMessage(ChatColor.DARK_RED+ "Le joueur "+target.getName()+ " a accepté votre pacte");
			
			
			pactedPlayers.add(target);
			
			target.aura = Aura.LUMINOUS;
			target.camp = Camp.Villager;
			
			PlayerData revealed = GameLgUtil.getAlPlayer(this.game);
			target.sendMessage(ChatColor.RED+"Le joueur "+ revealed.getName()+ " est "+revealed.role.getCampOfRole().getColor()+ revealed.role.getName());
			
			if (!target.isOnline) {
				target.left.life -= 4;
				
			} else {
				target.changeHealth(-4);
				target.player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 5));
			}
			if (!demon.isOnline) {
				demon.left.life -= 4;
			} else {
				demon.changeHealth(-4);
			}
			
			
		}
		
		
		if (args[0].equals("pactiser")) {
			PlayerData target = Main.strToPlayer.getOrDefault(args[1], playerWithRole);
			if (target == null) {
				return;
			}
			Player player = (Player) sender;
			PlayerData demon = Main.strToPlayer.getOrDefault(sender.getName(), null);
			if (demon != null && demon.getName().equals(playerWithRole.getName())) {
				boolean present = false;
				
				
				for (String str: namesChoosen) {
					if (str.equals(args[1])) {
						present = true;
					}
				}
				
				if (!present) {
					demon.sendMessage("Ce joueur ne fait pas partie des joueurs avec qui vous pouvez pactiser");
					return;
				}
				namesChoosen.remove(args[1]);
				
				TextComponent text = new TextComponent();
				text.setText(ChatColor.DARK_RED+ "Le Démon vous propose un pacte, clicquez ici pour l'accepter, ainsi vous perdrez 2 coeurs permanents, mais en contrepartie, votre aura et votre camp seront vus par les rôles à info comme positifs, vous gagnerez 5 golden apple, et vous obtiendrez le rôle d'un joueur au hasard. Cependant si vous venez à mourir, le démon récupérera votre âme, votre aura et votre camp seront vus comme négatifs, et vous devrez gagner la partie avec le démon tout en ayant weakness.");
				text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg pacteAccept "+target.Name+ " "+ player.getName())));
				target.player.spigot().sendMessage(text);
				
				int tries = 0;
				if (game.getNumberOfPlayer() > 4) {
					PlayerData choosen;
					do {
						tries++;
						choosen = game.getPlayerAlive().get(MathUtil.generateAlInt(0, game.getPlayerAlive().size() - 1));
					} while ((choosen.getName().equals(demon.getName() )||namesChoosen.contains(choosen.getName()))&& tries < 5);
					
					if (choosen.getName().equals(demon.getName() )) {
						demon.sendMessage("Salut, Il y a une erreur avec le role, c'est pas de chance il y avait 1 chance sur 3125 que ça arrive, en contrepartie tu gagne 1 coeur");
						demon.changeHealth(2);
					} else {
						namesChoosen.add(choosen.getName());
						demon.sendMessage("Vous pouvez pactiser avec un nouveau joueur: "+ choosen.getName());
					}
				}
				
				
				return;
			}
			
		} 
		
	}
}
