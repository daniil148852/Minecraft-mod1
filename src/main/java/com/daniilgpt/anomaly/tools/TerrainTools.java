// src/main/java/com/daniilgpt/anomaly/tools/TerrainTools.java
package com.daniilgpt.anomaly.tools;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.Heightmap;

public class TerrainTools {
    public static void flattenArea(World world, BlockPos center, int radius) {
        int y = world.getTopY(Heightmap.Type.WORLD_SURFACE, center.getX(), center.getZ()) - 1;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos pos = center.add(x, 0, z);
                int topY = world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ());
                for (int dy = topY; dy > y - 1; dy--) {
                    world.setBlockState(new BlockPos(pos.getX(), dy, pos.getZ()), Blocks.AIR.getDefaultState());
                }
                world.setBlockState(new BlockPos(pos.getX(), y, pos.getZ()), Blocks.GRASS_BLOCK.getDefaultState());
                world.setBlockState(new BlockPos(pos.getX(), y - 1, pos.getZ()), Blocks.DIRT.getDefaultState());
            }
        }
    }
}