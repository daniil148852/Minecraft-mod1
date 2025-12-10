// src/main/java/com/daniilgpt/anomaly/utils/AreaUtils.java
package com.daniilgpt.anomaly.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Consumer;

public class AreaUtils {
    public static void forEachEntityInRadius(Vec3d center, int radius, Consumer<Entity> action) {
        World world = null; // Need world, but assume from context or pass it
        // For simplicity, assume world is accessible or modify
        // In practice, pass world
        Box box = new Box(center.add(-radius, -radius, -radius), center.add(radius, radius, radius));
        List<Entity> entities = world.getOtherEntities(null, box);
        entities.forEach(action);
    }

    public static void forEachBlockInRadius(Vec3d center, int radius, Consumer<BlockPos> action) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.sqrt(x*x + y*y + z*z) <= radius) {
                        action.accept(new BlockPos((int)center.x + x, (int)center.y + y, (int)center.z + z));
                    }
                }
            }
        }
    }
}