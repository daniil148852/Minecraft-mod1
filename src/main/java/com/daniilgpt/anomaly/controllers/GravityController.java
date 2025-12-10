// src/main/java/com/daniilgpt/anomaly/controllers/GravityController.java
package com.daniilgpt.anomaly.controllers;

import com.daniilgpt.anomaly.state.AnomalyState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class GravityController {
    public static void applyGravity(World world) {
        float gravity = AnomalyState.getGravity();
        if (gravity == 1.0f) return;
        List<Entity> entities = world.getEntitiesByClass(Entity.class, new Box(-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE), e -> true);
        for (Entity entity : entities) {
            if (!entity.isOnGround()) {
                entity.setVelocity(entity.getVelocity().multiply(1, gravity, 1));
            }
        }
    }
}