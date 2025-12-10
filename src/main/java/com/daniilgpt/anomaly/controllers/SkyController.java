// src/main/java/com/daniilgpt/anomaly/controllers/SkyController.java
package com.daniilgpt.anomaly.controllers;

import net.minecraft.world.World;

public class SkyController {
    public static void setSkyType(World world, String type) {
        long time;
        switch (type) {
            case "day":
                time = 1000;
                break;
            case "night":
                time = 13000;
                break;
            case "twilight":
                time = 12000;
                break;
            case "fog":
                world.setWeatherBlockState(0, 0, true, true); // Rain and thunder for fog-like
                return;
            default:
                return;
        }
        world.setTimeOfDay(time);
    }
}