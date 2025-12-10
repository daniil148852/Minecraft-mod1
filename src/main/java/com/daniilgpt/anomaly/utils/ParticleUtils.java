// src/main/java/com/daniilgpt/anomaly/utils/ParticleUtils.java
package com.daniilgpt.anomaly.utils;

import net.minecraft.particle.ParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ParticleUtils {
    public static void spawnParticles(ServerWorld world, Vec3d pos, ParticleType<?> type, int count) {
        world.spawnParticles(type, pos.x, pos.y, pos.z, count, 1.0, 1.0, 1.0, 0.1);
    }

    public static void spawnBloomParticles(ServerWorld world, Vec3d pos) {
        // Use flower particles, but since no custom, use note or something colorful
        spawnParticles(world, pos, net.minecraft.particle.ParticleTypes.NOTE, 100);
    }

    public static void highlightBlock(ServerWorld world, BlockPos pos) {
        spawnParticles(world, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), net.minecraft.particle.ParticleTypes.GLOW, 5);
    }
}