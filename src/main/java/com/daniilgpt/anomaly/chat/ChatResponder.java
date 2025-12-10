// src/main/java/com/daniilgpt/anomaly/chat/ChatResponder.java
package com.daniilgpt.anomaly.chat;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ChatResponder {
    public static void register() {
        ServerMessageEvents.CHAT_MESSAGE.register(ChatResponder::onChatMessage);
    }

    private static void onChatMessage(SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params) {
        String content = message.getContent().getString().toLowerCase();
        if (content.contains("gpt?") || content.contains("anomaly")) {
            sender.sendMessage(Text.literal("ChatGPT Anomaly здесь! Используй /anomaly для эффектов. (DaniilGPT)"), false);
        }
    }
}