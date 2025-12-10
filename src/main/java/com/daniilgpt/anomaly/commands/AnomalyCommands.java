// src/main/java/com/daniilgpt/anomaly/commands/AnomalyCommands.java
package com.daniilgpt.anomaly.commands;

import com.daniilgpt.anomaly.effects.TemporaryEffects;
import com.daniilgpt.anomaly.effects.WorldEffects;
import com.daniilgpt.anomaly.state.AnomalyState;
import com.daniilgpt.anomaly.utils.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class AnomalyCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("anomaly")
                .then(CommandManager.literal("flatten").executes(AnomalyCommands::flatten))
                .then(CommandManager.literal("freeze").executes(AnomalyCommands::freeze))
                .then(CommandManager.literal("heat").executes(AnomalyCommands::heat))
                .then(CommandManager.literal("wind").executes(AnomalyCommands::wind))
                .then(CommandManager.literal("bloom").executes(AnomalyCommands::bloom))
                .then(CommandManager.literal("dark").executes(AnomalyCommands::dark))
                .then(CommandManager.literal("setGravity")
                        .then(CommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 2.0f))
                                .executes(context -> setGravity(context, FloatArgumentType.getFloat(context, "value")))))
                .then(CommandManager.literal("pulse").executes(AnomalyCommands::pulse))
                .then(CommandManager.literal("sky")
                        .then(CommandManager.argument("type", StringArgumentType.word())
                                .suggests((context, builder) -> {
                                    builder.suggest("day");
                                    builder.suggest("night");
                                    builder.suggest("twilight");
                                    builder.suggest("fog");
                                    return builder.buildFuture();
                                })
                                .executes(context -> sky(context, StringArgumentType.getString(context, "type")))))
                .then(CommandManager.literal("scan").executes(AnomalyCommands::scan))
        );
    }

    private static int flatten(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        World world = source.getWorld();
        BlockPos pos = player.getBlockPos();
        TerrainTools.flattenArea(world, pos, 16);
        source.sendFeedback(Text.literal("Мир выровнен! (DaniilGPT)"), false);
        return 1;
    }

    private static int freeze(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        Vec3d pos = player.getPos();
        World world = source.getWorld();
        TemporaryEffects.addEffect("freeze", pos, world, 200); // 10 seconds
        source.sendFeedback(Text.literal("Заморозка активирована! (DaniilGPT)"), false);
        return 1;
    }

    private static int heat(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        Vec3d pos = player.getPos();
        World world = source.getWorld();
        TemporaryEffects.addEffect("heat", pos, world, 200);
        source.sendFeedback(Text.literal("Нагрев активирован! (DaniilGPT)"), false);
        return 1;
    }

    private static int wind(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        Vec3d pos = player.getPos();
        World world = source.getWorld();
        TemporaryEffects.addEffect("wind", pos, world, 200);
        source.sendFeedback(Text.literal("Ветер активирован! (DaniilGPT)"), false);
        return 1;
    }

    private static int bloom(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        Vec3d pos = player.getPos();
        ServerWorld world = source.getWorld();
        ParticleUtils.spawnBloomParticles(world, pos);
        source.sendFeedback(Text.literal("Цветочный взрыв! (DaniilGPT)"), false);
        return 1;
    }

    private static int dark(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        Vec3d pos = player.getPos();
        World world = source.getWorld();
        TemporaryEffects.addEffect("dark", pos, world, 200);
        source.sendFeedback(Text.literal("Тьма активирована! (DaniilGPT)"), false);
        return 1;
    }

    private static int setGravity(CommandContext<ServerCommandSource> context, float value) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        AnomalyState.setGravity(value);
        source.sendFeedback(Text.literal("Гравитация установлена на " + value + "! (DaniilGPT)"), false);
        return 1;
    }

    private static int pulse(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        Vec3d pos = player.getPos();
        ServerWorld world = source.getWorld();
        WorldEffects.createPulse(world, pos);
        source.sendFeedback(Text.literal("Импульс запущен! (DaniilGPT)"), false);
        return 1;
    }

    private static int sky(CommandContext<ServerCommandSource> context, String type) {
        ServerCommandSource source = context.getSource();
        World world = source.getWorld();
        SkyController.setSkyType(world, type);
        source.sendFeedback(Text.literal("Небо изменено на " + type + "! (DaniilGPT)"), false);
        return 1;
    }

    private static int scan(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Entity player = source.getEntity();
        if (player == null) return 0;
        Vec3d pos = player.getPos();
        ServerWorld world = source.getWorld();
        AreaUtils.forEachBlockInRadius(pos, 16, blockPos -> {
            ParticleUtils.highlightBlock(world, blockPos);
        });
        source.sendFeedback(Text.literal("Сканирование завершено! (DaniilGPT)"), false);
        return 1;
    }
}