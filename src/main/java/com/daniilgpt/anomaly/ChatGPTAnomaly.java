// src/main/java/com/daniilgpt/anomaly/ChatGPTAnomaly.java
package com.daniilgpt.anomaly;

import com.daniilgpt.anomaly.commands.AnomalyCommands;
import com.daniilgpt.anomaly.state.AnomalyState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatGPTAnomaly implements ModInitializer {
    public static final String MOD_ID = "chatgpt_anomaly";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("ChatGPT Anomaly активирован!");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            AnomalyCommands.register(dispatcher);
        });
        AnomalyState.init();
        // Register chat responder and other event listeners
        ChatResponder.register();
    }
}