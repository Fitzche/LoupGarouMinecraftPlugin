package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.SpecialsBlock.VoteBlockData;
import fr.fitzche.lgmore.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;


//InvFunct for Choose a playerToVote, linked to a specific VoteBlock, lg
public class PlayerVoteChoose implements InvFunct {

	public VoteBlockData block;
	public ArrayList<ItemStack> items;
	public PlayerData p;
	
	public PlayerVoteChoose(VoteBlockData block, PlayerData p) {
		this.block = block;
		this.items = new ArrayList<ItemStack>();
		for (PlayerData ply:p.canVoted) {
			this.items.add(ItemUtil.getCustomHead(ply.getName()));
		}
		StringChooseInv inv = new StringChooseInv(p, items, null, this);
	}
	
	
	@Override
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores, ItemStack returnedItem) {
		if (clickedName != null && p != null) {
			for (PlayerData ply1:p.canVoted) {
				if (ply1.getName().equals(clickedName)) {
					if (this.block.voteAvaible > 0) {
						ply1.voted = ply1;
						ply1.voted.vote ++;
						this.block.voteAvaible --;
							ply1.sendMessage(Main.lgmoreMark + ChatColor.DARK_GREEN + "Vous portez votre vote sur "+ ChatColor.DARK_AQUA+ clickedName);
					} else {
						ply1.sendMessage(Main.exclamation + ChatColor.RED + "Il n'y a plus de place dans cette urne !");
					}
					
				}
			}
		}

	}

}
