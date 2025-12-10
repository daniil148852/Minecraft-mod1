// src/main/java/com/daniilgpt/anomaly/effects/TemporaryEffects.java
package com.daniilgpt.anomaly.effects;

import com.daniilgpt.anomaly.utils.AreaUtils;
import com.daniilgpt.anomaly.utils.ParticleUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TemporaryEffects {
    private static class Effect {
        String type;
        Vec3d pos;
        World world;
        int duration;

        Effect(String type, Vec3d pos, World world, int duration) {
            this.type = type;
            this.pos = pos;
            this.world = world;
            this.duration = duration;
        }
    }

    private static final List<Effect> activeEffects = new ArrayList<>();

    public static void addEffect(String type, Vec3d pos, World world, int duration) {
        activeEffects.add(new Effect(type, pos, world, duration));
    }

    public static void tickAll() {
        List<Effect> toRemove = new ArrayList<>();
        for (Effect effect : activeEffects) {
            tickEffect(effect);
            effect.duration--;
            if (effect.duration <= 0) {
                toRemove.add(effect);
            }
        }
        activeEffects.removeAll(toRemove);
    }

    private static void tickEffect(Effect effect) {
        ServerWorld world = (ServerWorld) effect.world;
        switch (effect.type) {
            case "freeze":
                AreaUtils.forEachEntityInRadius(effect.pos, 16, entity -> {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 5));
                    }
                    // Freeze water: would require block changes, skip for simplicity or implement if needed
                });
                ParticleUtils.spawnParticles(world, effect.pos, ParticleTypes.SNOWFLAKE, 50);
                break;
            case "heat":
                ParticleUtils.spawnParticles(world, effect.pos, ParticleTypes.SMOKE, 50);
                ParticleUtils.spawnParticles(world, effect.pos, ParticleTypes.FLAME, 20);
                break;
            case "wind":
                AreaUtils.forEachEntityInRadius(effect.pos, 16, entity -> {
                    entity.addVelocity(0.1, 0, 0.1); // Simple wind push
                });
                ParticleUtils.spawnParticles(world, effect.pos, ParticleTypes.CLOUD, 50);
                break;
            case "dark":
                // Darken: can't easily change light, but add blindness or particles
                AreaUtils.forEachEntityInRadius(effect.pos, 16, entity -> {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20, 1));
                    }
                });
                ParticleUtils.spawnParticles(world, effect.pos, ParticleTypes.ASH, 50);
                break;
        }
    }
}