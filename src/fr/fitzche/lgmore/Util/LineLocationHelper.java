package fr.fitzche.lgmore.Util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LineLocationHelper {

    /**
     * Récupère une liste de Location le long de la trajectoire du joueur,
     * à partir de ses yeux. Le parcours s'arrête dès qu'on rencontre un bloc non AIR
     * ou un autre joueur (différent du joueur initial).
     *
     * @param player Le joueur dont on utilise la vue.
     * @param maxDistance La distance maximale à parcourir sur la ligne.
     * @param step La distance (en blocs) entre deux positions échantillonnées.
     * @return Une liste de Location.
     */
    public static LineRapport getLineLocations(Player player, double maxDistance, double step, double checkRadius) {
        List<Location> locations = new ArrayList<>();
        // Départ depuis l'œil du joueur pour une trajectoire cohérente avec la vue.
        Location start = player.getEyeLocation();
        // Normalisation de la direction pour que la multiplication par "step" soit précise.
        Vector direction = start.getDirection().normalize();
        World world = start.getWorld();

        // Parcourt la trajectoire de 0 jusqu'à maxDistance.
        for (double distance = 0; distance <= maxDistance; distance += step) {
            // Calcul de la nouvelle position le long de la direction.
            Location loc = start.clone().add(direction.clone().multiply(distance));
            locations.add(new Location(loc.getWorld(), loc.getX(), loc.getY() -1, loc.getZ()));

            // Vérifie si le bloc à cette position n'est pas de l'air.
            if (!loc.getBlock().getType().equals(Material.AIR)) {
                // On s'arrête si un bloc non vide est rencontré.
                break;
            }

            // Vérifie la présence d'entités sur cette position avec un petit rayon
            // (pour éviter de "sauter" un joueur en raison d'un pas trop grand).
            if (checkRadius > 0) {
            	Collection<Entity> nearby = world.getNearbyEntities(loc, checkRadius, checkRadius, checkRadius);
            	for (Entity entity : nearby) {
            		if (entity instanceof Player && !entity.equals(player)) {
            			// On s'arrête si un autre joueur a été détecté.
            			return new LineRapport(Main.getData(entity), locations);
            		}
            	}
            }
            
        }
        return new LineRapport(null, locations);
    }
    /**
     * Récupère une liste de Location le long de la trajectoire du joueur,
     * à partir de ses yeux. Le parcours s'arrête dès qu'on rencontre un bloc non AIR
     * ou un autre joueur (différent du joueur initial).
     *
     * @param player Le joueur dont on utilise la vue.
     * @param maxDistance La distance maximale à parcourir sur la ligne.
     * @param step La distance (en blocs) entre deux positions échantillonnées.
     * @return Une liste de Location.
     */
    public static LineRapport getLineLocations(Player player, double maxDistance, double step, double checkRadius, double xModifier, double yModifier, double zModifier) {
        List<Location> locations = new ArrayList<>();
        // Départ depuis l'œil du joueur pour une trajectoire cohérente avec la vue.
        Location start = player.getEyeLocation();
        // Normalisation de la direction pour que la multiplication par "step" soit précise.
        Vector direction = start.getDirection().normalize();
        World world = start.getWorld();

        // Parcourt la trajectoire de 0 jusqu'à maxDistance.
        for (double distance = 0; distance <= maxDistance; distance += step) {
            // Calcul de la nouvelle position le long de la direction.
            Location loc = start.clone().add(direction.clone().multiply(distance));
            locations.add(new Location(loc.getWorld(), loc.getX() + xModifier, loc.getY() -1 + yModifier, loc.getZ() + zModifier));

            // Vérifie si le bloc à cette position n'est pas de l'air.
            if (!loc.getBlock().getType().equals(Material.AIR)) {
                // On s'arrête si un bloc non vide est rencontré.
                break;
            }

            // Vérifie la présence d'entités sur cette position avec un petit rayon
            // (pour éviter de "sauter" un joueur en raison d'un pas trop grand).
            if (checkRadius > 0) {
            	Collection<Entity> nearby = world.getNearbyEntities(loc, checkRadius, checkRadius, checkRadius);
            	for (Entity entity : nearby) {
            		if (entity instanceof Player && !entity.equals(player)) {
            			// On s'arrête si un autre joueur a été détecté.
            			return new LineRapport(Main.getData(entity), locations);
            		}
            	}
            }
        }
        return new LineRapport(null, locations);
    }
    
    
    public static PlayerData focus(Player p) {
    	
    	return getLineLocations(p, 50, 0.2, 0.1).p;
    }
    /**
     * Applique un knockback sur le joueur target pour l’éloigner du joueur source.
     *
     * @param target  Le joueur qui recevra le knockback.
     * @param source  Le joueur dont la position détermine la direction du knockback.
     * @param strength La force du knockback (un multiplicateur) ; par exemple 1.0 ou 2.0.
     */
    public static void applyKnockback(Player target, Player source, double strength) {
        // Calcule le vecteur allant de la source vers le target
        Vector knockbackDirection = target.getLocation().toVector().subtract(source.getLocation().toVector());
        
        // On peut choisir d'ignorer la composante verticale pour un knockback purement horizontal :
        knockbackDirection.setY(0);
        // Normalisation pour obtenir une direction unitaire
        knockbackDirection.normalize();
        
        // Optionnel : ajouter une petite impulsion verticale pour un effet plus réaliste (exemple : 0.3)
        knockbackDirection.setY(0.3);
        
        // Multiplie le vecteur par la force souhaitée
        knockbackDirection.multiply(strength);
        
        // Applique le vecteur résultant au joueur cible
        target.setVelocity(knockbackDirection);
    }
}

