// src/main/java/com/daniilgpt/anomaly/utils/PlayerUtils.java
package com.daniilgpt.anomaly.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class PlayerUtils {
    public static Vec3d getPlayerPos(PlayerEntity player) {
        return player.getPos();
    }
    // Add more if needed, like inventory handling
}