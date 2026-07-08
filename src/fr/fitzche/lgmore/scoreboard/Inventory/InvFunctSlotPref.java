package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

/**
 * InvFunctSlotPref — réarrangement de l'inventaire préférentiel via StringChooseInv.
 *
 * Flux :
 *   1. openMainMenu(p)  → StringChooseInv listant les 14 slots configurables
 *   2. Joueur clique sur un slot (ex. "sword") → InvFunctSlotPref mémorise la clé
 *   3. Deuxième StringChooseInv affichant les numéros de slots 0–35
 *   4. Joueur choisit un numéro → __slotPref__ mis à jour + confirmation
 *
 * Utilisation depuis GeneralMenu ou SpecialItemHolder :
 *   new InvFunctSlotPref().openMainMenu(playerData);
 */
public class InvFunctSlotPref {

    // ── Définition des slots configurables ────────────────────────────────────
    // LinkedHashMap pour conserver l'ordre d'affichage
    private static final LinkedHashMap<String, SlotDef> SLOT_DEFS = new LinkedHashMap<>();

    static {
        SLOT_DEFS.put("sword",      new SlotDef("Épée",        Material.DIAMOND_SWORD,    0));
        SLOT_DEFS.put("bow",        new SlotDef("Arc",          Material.BOW,              1));
        SLOT_DEFS.put("arrow",      new SlotDef("Flèches",      Material.ARROW,            2));
        SLOT_DEFS.put("gap",        new SlotDef("Pommes d'or",  Material.GOLDEN_APPLE,     3));
        SLOT_DEFS.put("beef",       new SlotDef("Bœuf cuit",    Material.COOKED_BEEF,      4));
        SLOT_DEFS.put("water",      new SlotDef("Seau d'eau",   Material.WATER_BUCKET,     5));
        SLOT_DEFS.put("stone1",     new SlotDef("Pierre 1",     Material.STONE,            6));
        SLOT_DEFS.put("stone2",     new SlotDef("Pierre 2",     Material.STONE,            7));
        SLOT_DEFS.put("stone3",     new SlotDef("Pierre 3",     Material.STONE,            8));
        SLOT_DEFS.put("stone4",     new SlotDef("Pierre 4",     Material.STONE,            9));
        SLOT_DEFS.put("helmet",     new SlotDef("Casque",       Material.DIAMOND_HELMET,   36));
        SLOT_DEFS.put("chestplate", new SlotDef("Plastron",     Material.DIAMOND_CHESTPLATE, 37));
        SLOT_DEFS.put("legging",    new SlotDef("Jambières",    Material.IRON_LEGGINGS,    38));
        SLOT_DEFS.put("boots",      new SlotDef("Bottes",       Material.IRON_BOOTS,       39));
    }

    // ── Étape 1 : menu principal — choisir quel slot configurer ───────────────
    public void openMainMenu(PlayerData p) {
        ArrayList<ItemStack> items = new ArrayList<>();

        for (Map.Entry<String, SlotDef> entry : SLOT_DEFS.entrySet()) {
            String key    = entry.getKey();
            SlotDef def   = entry.getValue();
           
            if (p.__slotPref__ == null) {
           	 p.__slotPref__ = new HashMap<String, Integer>();
           }
            int current   = p
            		.__slotPref__
            		.getOrDefault(
            				key, 
            				def
            				.defaultSlot);

            ItemStack item = ItemUtil.getItem(
                def.material, 1,
                ChatColor.YELLOW + "" + ChatColor.BOLD + def.label,
                new ArrayList<>(Arrays.asList(
                    ChatColor.GRAY  + "Slot actuel : " + ChatColor.AQUA + current,
                    ChatColor.GRAY  + "Clé interne : " + ChatColor.WHITE + key
                ))
            );
            items.add(item);
        }

        // L'InvFunct de l'étape 1 : au clic → ouvre le sélecteur de slot
        InvFunct step1Functer = (player, game, clickedName, lores, returnedItem) -> {
            // Retrouver la clé à partir du nom de l'item (couleurs ChatColor incluses)
            String targetKey = null;
            for (Map.Entry<String, SlotDef> e : SLOT_DEFS.entrySet()) {
                if ((ChatColor.YELLOW + "" + ChatColor.BOLD + e.getValue().label).equals(clickedName)) {
                    targetKey = e.getKey();
                    break;
                }
            }
            if (targetKey == null) return;

            openSlotPicker(player, targetKey);
        };

        new StringChooseInv(p, items, null, step1Functer);
    }

    // ── Étape 2 : sélecteur de numéro de slot (0–35) ─────────────────────────
    private void openSlotPicker(PlayerData p, String slotKey) {
        SlotDef def = SLOT_DEFS.get(slotKey);
        ArrayList<ItemStack> slots = new ArrayList<>();

        for (int i = 0; i <= 35; i++) {
            // Matériau visuel : émeraude pour le slot actuel, papier sinon
            int current = p.__slotPref__.getOrDefault(slotKey, def.defaultSlot);
            Material mat = (i == current) ? Material.EMERALD : Material.PAPER;

            ItemStack item = ItemUtil.getItem(
                mat, 1,
                ChatColor.WHITE + "Slot " + i,
                new ArrayList<>(Arrays.asList(
                    ChatColor.GRAY + "Placer " + ChatColor.YELLOW + def.label
                        + ChatColor.GRAY + " en slot " + ChatColor.AQUA + i,
                    (i == current)
                        ? ChatColor.GREEN + "✔ Slot actuel"
                        : ChatColor.GRAY  + "Cliquer pour choisir"
                ))
            );
            slots.add(item);
        }

        // L'InvFunct de l'étape 2 : au clic → applique le slot choisi
        InvFunct step2Functer = (player, game, clickedName, lores, returnedItem) -> {
            // Le nom est "Slot X" — on parse X
            String raw = ChatColor.stripColor(clickedName).trim(); // "Slot X"
            if (!raw.startsWith("Slot ")) return;
            int chosenSlot;
            try {
                chosenSlot = Integer.parseInt(raw.substring(5).trim());
            } catch (NumberFormatException ex) {
                return;
            }
            if (chosenSlot < 0 || chosenSlot > 35) return;

            player.__slotPref__.put(slotKey, chosenSlot);

            player.sendMessage(
                ChatColor.GREEN + "✔ " + def.label
                + ChatColor.WHITE + " déplacé en slot "
                + ChatColor.AQUA  + chosenSlot
            );

            // Retour au menu principal après modification
            openMainMenu(player);
        };

        new StringChooseInv(p, slots, null, step2Functer);
    }

    // ── Données statiques par slot ────────────────────────────────────────────
    private static class SlotDef {
        final String   label;
        final Material material;
        final int      defaultSlot;

        SlotDef(String label, Material material, int defaultSlot) {
            this.label       = label;
            this.material    = material;
            this.defaultSlot = defaultSlot;
        }
    }
}