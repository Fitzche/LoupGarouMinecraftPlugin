package fr.fitzche.lgmore.Util;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;


public class PotionUtil {
	public static int strengthX;

	
  public static final PotionEffect INVINCIBILITY =
	  new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 999, false, false);
  public static final PotionEffect NIGHT_VISION = 
      new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false);
  
  public static final PotionEffect INVISIBILITY = 
      new PotionEffect(PotionEffectType.INVISIBILITY, 6000, 0, false, false);
  


  public static final PotionEffect SPEED = 
      new PotionEffect(PotionEffectType.SPEED, 999999, 0, false, false);

  public static final PotionEffect WEAKNESS = 
      new PotionEffect(PotionEffectType.WEAKNESS, 999999, 0, false, false);
  
  public static final PotionEffect LITTLE_GIRL_INVISIBILITY = 
      new PotionEffect(PotionEffectType.INVISIBILITY, 60, 0, false, false);
  
  public static final PotionEffect RESISTANCE = 
      new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 0, false, false);
  
  public static final PotionEffect SPAWN_RESISTANCE =
      new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 500, 999, false, true);
  
  private PotionUtil() {
	  PotionUtil.strengthX = 1;
    // util class
  }

}
