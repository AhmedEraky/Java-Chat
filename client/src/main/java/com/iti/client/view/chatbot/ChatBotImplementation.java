/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.client.view.chatbot;


import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import java.io.File;

/**
 *
 * @author pc
 */
public class ChatBotImplementation {

    String botname = "super";
    String path = getResourcesPath();
    Bot bot = new Bot(botname, path);
    Chat chatSession = new Chat(bot);

    public ChatBotImplementation() {

    }

    public String responseBot(String message) {
        bot.brain.nodeStats();
        String response = chatSession.multisentenceRespond(message);
        System.out.println("hello chatbot");
        return response;
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath+"\\bots";
    }

}
