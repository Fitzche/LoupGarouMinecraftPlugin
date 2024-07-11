package fr.fitzche.lgmore.Util;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;


public class PotionUtil {
	public static int strengthX;

	
  public static final PotionEffect INVINCIBILITY =
	  new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 999, false, false);
  public static final PotionEffect NIGHT_VISION = 
      new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, false, false);
  
  public static final PotionEffect INVISIBILITY = 
      new PotionEffect(PotionEffectType.INVISIBILITY, 6000, 0, false, false);
  
  public static final PotionEffect STRENGTH  = 
      new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0, false, false);

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

  public static void giveIncreaseDamage(PlayerData player) {
    PotionEffect power = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
        power = potionEffect;
      }
    }
  
    if(power == null) {
      player.player.addPotionEffect(STRENGTH);
    }
  }

  public static void clearIncreaseDamage(PlayerData player) {
    PotionEffect power = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
        power = potionEffect;
      }
    }
  
    
    
    if(power != null) {
      player.player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
    }
  }

  public static void clearInvisibility(PlayerData player) {
    PotionEffect nightVision = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.INVISIBILITY)) {
        nightVision = potionEffect;
      }
    }
  
    if(nightVision != null) {
      player.player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }
  }

  public static void giveInvisiblity(PlayerData player) {
    PotionEffect nightVision = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.INVISIBILITY)) {
        nightVision = potionEffect;
      }
    }
  
    if(nightVision == null) {
      player.player.addPotionEffect(INVISIBILITY);
    }
  }

  public static void giveResistance(PlayerData player) {
    PotionEffect resistance = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.DAMAGE_RESISTANCE)) {
        resistance = potionEffect;
      }
    }
  
    if(resistance == null) {
      player.player.addPotionEffect(RESISTANCE);
    }
  }

  public static void clearSpeed(PlayerData player) {
    PotionEffect speed = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.SPEED)) {
        speed = potionEffect;
      }
    }
  
    
  
    if(speed != null) {
      player.player.removePotionEffect(PotionEffectType.SPEED);
    }
  }

  public static void giveSpeed(PlayerData player) {
    PotionEffect speed = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.SPEED)) {
        speed = potionEffect;
      }
    }
  
    if(speed == null) {
      player.player.addPotionEffect(SPEED);
    }
  }

  public static void clearWeakness(PlayerData player) {
    PotionEffect weakness = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.WEAKNESS)) {
        weakness = potionEffect;
      }
    }
    
    if(weakness != null) {
      player.player.removePotionEffect(PotionEffectType.WEAKNESS);
    }
  }

  public static void giveWeakness(fr.fitzche.lgmore.PlayerData player) {
    PotionEffect weakness = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.WEAKNESS)) {
        weakness = potionEffect;
      }
    }
  
    if(weakness == null) {
      player.player.addPotionEffect(WEAKNESS);
    }
  }

  public static void clearNightVision(fr.fitzche.lgmore.PlayerData player) {
    PotionEffect nightVision = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.NIGHT_VISION)) {
        nightVision = potionEffect;
      }
    }
  
    if(nightVision != null) {
      player.player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
  }

  public static void giveNightVision(fr.fitzche.lgmore.PlayerData player) {
    PotionEffect nightVision = null;
    for(PotionEffect potionEffect : player.player.getActivePotionEffects()) {
      if(potionEffect.getType().equals(PotionEffectType.NIGHT_VISION)) {
        nightVision = potionEffect;
      }
    }
  
    if(nightVision == null) {
      player.player.addPotionEffect(NIGHT_VISION);
    }
  }
}
