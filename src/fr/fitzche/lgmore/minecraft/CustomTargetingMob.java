package fr.fitzche.lgmore.minecraft;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import com.google.common.base.Predicate;

import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;

public class CustomTargetingMob<T extends EntityInsentient> {

    private final Class<T> mobClass;

    public CustomTargetingMob(Class<T> mobClass) {
        this.mobClass = mobClass;
    }

    public T spawn(Location loc, Set<UUID> ignoredPlayers) {
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();

        try {
            // Instanciation du mob NMS
            Constructor<T> cons = mobClass.getConstructor(World.class);
            T entity = cons.newInstance(nmsWorld);

            // Nettoyage des goals
            clearGoals(entity);

            // Ajout des goals de base
            setupDefaultGoals(entity);
            entity.goalSelector.a(2, new PathfinderGoalMeleeAttack((EntityCreature) entity, 1.0D, true));
            entity.targetSelector.a(1, new PathfinderGoalHurtByTarget((EntityCreature) entity, true, mobClass));
            entity.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>((EntityCreature) entity, mobClass, true));

            // Ajout du targeting custom
            setupTargeting(entity, ignoredPlayers);

            // Placement + ajout au monde
            entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            nmsWorld.addEntity(entity);
            

            return entity;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void clearGoals(EntityInsentient ent) throws Exception {
        Field goalB = PathfinderGoalSelector.class.getDeclaredField("b");
        Field targetB = PathfinderGoalSelector.class.getDeclaredField("b");
        goalB.setAccessible(true);
        targetB.setAccessible(true);

        ((List<?>) goalB.get(ent.goalSelector)).clear();
        ((List<?>) targetB.get(ent.targetSelector)).clear();
    }

    private void setupDefaultGoals(EntityInsentient ent) {
        ent.goalSelector.a(0, new PathfinderGoalFloat(ent));
        ent.goalSelector.a(7, new PathfinderGoalRandomStroll((EntityCreature) ent, 1.0D));
        ent.goalSelector.a(8, new PathfinderGoalLookAtPlayer(ent, EntityHuman.class, 8.0F));
        ent.goalSelector.a(8, new PathfinderGoalRandomLookaround(ent));
    }

    private void setupTargeting(EntityInsentient ent, Set<UUID> ignoredPlayers) {
        ent.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityHuman>(
                (EntityCreature) ent,
                EntityHuman.class,
                10,
                true,
                false,
                new Predicate<EntityHuman>() {
                    @Override
                    public boolean apply(EntityHuman human) {
                        return human != null && !ignoredPlayers.contains(human.getUniqueID());
                    }
                }
        ));
    }
}
