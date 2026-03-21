package fr.fitzche.lgmore.Util;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.bukkit.Material;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BooksUtils {

    private static final int PAGE_LIMIT = 150;

    
    public static void loadRoleAndOpenBook(Player player, String resourceName, InputStream is) {
        try {
            // Lecture du JSON dans les resources
            if (is == null) {
                player.sendMessage("§cImpossible de trouver la ressource : " + resourceName);
                return;
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(content);
			JsonObject object = json.getAsJsonObject();

            // -----------------------------
            // Extraction des champs du rôle
            // -----------------------------
            String name = object.get("name").getAsString();
            String camp = object.get("camp").getAsString();

            double strenght = object.has("strenght") ? object.get("strenght").getAsDouble() : 1.0;
            double resistance = object.has("resistance") ? object.get("resistance").getAsDouble() : 1.0;
            int healthBoost = JsonUtil.getInt(object, "healthBoost", 0);
            
            int reliveTry = object.has("reliveTry") ? object.get("reliveTry").getAsInt() : 0;
            double lostStr = object.has("lostStrenght") ? object.get("lostStrenght").getAsDouble() : 0.0;
            double lostRes = object.has("lostResis") ? object.get("lostResis").getAsDouble() : 0.0;
            double lostHp = object.has("lostHealth") ? object.get("lostHealth").getAsDouble() : 0.0;

            String condRole = object.has("conditionKilledBy") ? object.get("conditionKilledBy").getAsString() : "";
            String condCamp = object.has("conditionKilledByCamp") ? object.get("conditionKilledByCamp").getAsString() : "";

            // -----------------------------
            // Construction du texte du livre
            // -----------------------------
            StringBuilder sb = new StringBuilder();

            sb.append("§lRôle : §r").append(name).append("\n");
            sb.append("Camp : ").append(camp).append("\n\n");

            sb.append("§lStatistiques :§r\n");
            sb.append("Force : ").append(strenght).append("\n");
            sb.append("Résistance : ").append(resistance).append("\n\n");
            sb.append("Vie supplémentaire : ").append((""+healthBoost)).append("\n\n");

            sb.append("§lRessurections :§r\n");
            sb.append("Tentatives : ").append(reliveTry).append("\n");
            sb.append("Perte force : ").append(lostStr).append("\n");
            sb.append("Perte résistance : ").append(lostRes).append("\n");
            sb.append("Perte vie : ").append(lostHp).append("\n\n");

            if (!condRole.isEmpty() || !condCamp.isEmpty()) {
                sb.append("§lConditions de résurrection :§r\n");
                if (!condRole.isEmpty()) sb.append("Tué par rôle : ").append(condRole).append("\n");
                if (!condCamp.isEmpty()) sb.append("Tué par camp : ").append(condCamp).append("\n");
                sb.append("\n");
            }

            // -----------------------------
            // Lecture des pouvoirs (infoPowers)
            // -----------------------------
            if (object.has("infoPowers")) {
                sb.append("§lPouvoirs :§r\n");

                JsonArray array = object.get("infoPowers").getAsJsonArray();
                for (JsonElement el : array) {
                    JsonObject obj = el.getAsJsonObject();

                    if (!obj.has("type") || !obj.has("object")) continue;

                    String type = obj.get("type").getAsString();
                    JsonObject sub = obj.get("object").getAsJsonObject();

                    switch (type) {
                        case "seeCaract":
                            int distance = sub.has("distance") ? sub.get("distance").getAsInt() : 1000;
                            int probaTrue = sub.has("probaTrue") ? sub.get("probaTrue").getAsInt() : 100;
                            int use = sub.has("use") ? sub.get("use").getAsInt() : 1;
                            boolean all = sub.has("all") && sub.get("all").getAsBoolean();
                            boolean knowWho = sub.has("knowWho") && sub.get("knowWho").getAsBoolean();
                            boolean timeAlea = sub.has("timeAlea") && sub.get("timeAlea").getAsBoolean();
                            String caract = sub.has("caract") ? sub.get("caract").getAsString() : "name";
                            String command = sub.has("command") ? sub.get("command").getAsString() : "";

                            sb.append("§e• Vision :§r observe ").append(caract).append("\n");
                            sb.append("Distance : ").append(distance).append("\n");
                            sb.append("Probabilité vraie : ").append(probaTrue).append("%\n");
                            sb.append("Utilisations : ").append(use).append("\n");
                            sb.append("Tous joueurs : ").append(all).append("\n");
                            sb.append("Connaît les cibles : ").append(knowWho).append("\n");
                            sb.append("Déclenchement aléatoire : ").append(timeAlea).append("\n");
                            if (!command.isEmpty()) sb.append("Commande : /lg ").append(command).append("\n");
                            sb.append("\n");
                            break;
                    }
                }
            }

            // -----------------------------
            // Appel de TA fonction openBook
            // -----------------------------
            openBook(player, sb.toString(), "Rôle : " + name);

        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("§cErreur lors de la lecture du rôle.");
        }
    }

    
    
    public static void openBook(Player player, String fullText, String title) {

        // 1. Découper le texte en pages
        List<String> pages = splitIntoPages(fullText, title);

        // 2. Créer le livre Bukkit
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.setTitle(title);
        meta.setAuthor(player.getName());
        meta.setPages(pages);

        book.setItemMeta(meta);

        // 3. Sauvegarder l'objet actuel
        ItemStack old = player.getItemInHand();

        // 4. Mettre le livre dans la main
        player.setItemInHand(book);

        // 5. Convertir en NMS et ouvrir le livre
        EntityPlayer ep = ((CraftPlayer) player).getHandle();
        net.minecraft.server.v1_8_R3.ItemStack nmsBook = CraftItemStack.asNMSCopy(book);
        ep.openBook(nmsBook);

        // 6. Restaurer l'objet d'origine
        player.setItemInHand(old);
    }
    
    

    private static List<String> splitIntoPages(String text, String title) {
        List<String> pages = new ArrayList<>();

        String header = "§l" + title + "§r\n\n";

        int index = 0;
        while (index < text.length()) {
            int end = Math.min(index + PAGE_LIMIT, text.length());
            String pageContent = header + text.substring(index, end);
            pages.add(pageContent);
            index = end;
        }

        return pages;
    }
}
