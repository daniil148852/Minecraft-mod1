// src/main/java/com/daniilgpt/anomaly/effects/WorldEffects.java
package com.daniilgpt.anomaly.effects;

import com.daniilgpt.anomaly.utils.ParticleUtils;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class WorldEffects {
    public static void createPulse(ServerWorld world, Vec3d pos) {
        // Simulate pulse wave with particles
        for (int i = 0; i < 360; i += 10) {
            double x = Math.cos(i) * 5;
            double z = Math.sin(i) * 5;
            ParticleUtils.spawnParticles(world, pos.add(x, 0, z), ParticleTypes.END_ROD, 10);
        }
        // Push entities away, etc.
    }
}