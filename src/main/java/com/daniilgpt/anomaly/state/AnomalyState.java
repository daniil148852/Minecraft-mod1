// src/main/java/com/daniilgpt/anomaly/state/AnomalyState.java
package com.daniilgpt.anomaly.state;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class AnomalyState {
    private static float gravity = 1.0f; // Default gravity multiplier

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            TemporaryEffects.tickAll();
            GravityController.applyGravity(server.getOverworld()); // Apply to overworld for simplicity
        });
    }

    public static float getGravity() {
        return gravity;
    }

    public static void setGravity(float value) {
        gravity = value;
    }
}