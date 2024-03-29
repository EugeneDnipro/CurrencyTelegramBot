package com.goit.Utils;

import com.goit.org.buttons.BotCommands;
import com.goit.org.buttons.Buttons;
import com.goit.org.functionalInteface.MessageSender;
import lombok.Getter;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;

public class MessageUtil implements BotCommands {
    @Getter
    private static final NotificationScheduler shedule = null;

    public static void setTime(SendMessage message, String time) {
        message.setText("Оберіть час оповіщення. \n " +
                "Поточний час оповіщень: " + time);
        message.setReplyMarkup(Buttons.initTimeKeyboard());
    }

    public static void returnMenu(SendMessage message, String text) {
        message.setText(text);
        message.setReplyMarkup(Buttons.startMarkup());
    }

    public static void startBot(SendMessage message) {
        message.setText("Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют");
        message.setReplyMarkup(Buttons.startMarkup());
    }

    public static void getInfo(SendMessage message, String text) {
        message.setText(text);
        message.setReplyMarkup(Buttons.startMarkup());
    }

    public static void sendHelpText(SendMessage message) {
        message.setText(HELP_TEXT);
    }

    public static void setSettings(SendMessage message) {
        message.setText("Налаштування");
        message.setReplyMarkup(Buttons.settingMarkup());
    }

    public static void chooseBank(SendMessage message, UserPreferences user) {
        message.setText("Оберіть банк");
        message.setReplyMarkup(Buttons.banks(user));
    }

    public static void chooseBankChanging(EditMessageReplyMarkup message, UserPreferences user) {
        message.setReplyMarkup(Buttons.banksChanging(user));
    }

    public static void countFloatPoint(SendMessage message, UserPreferences user) {
        message.setText("Оберіть кількість знаків після коми");
        message.setReplyMarkup(Buttons.setFloatPoint(user));
    }

    public static void countFloatPointChanging(EditMessageReplyMarkup message, UserPreferences user) {
        message.setReplyMarkup(Buttons.setFloatPointChanging(user));
    }

    public static void chooseCurrency(SendMessage message, UserPreferences user) {
        message.setText("Оберіть валюту");
        message.setReplyMarkup(Buttons.chooseCurrency(user));
    }

    public static void chooseCurrencyChanging(EditMessageReplyMarkup message, UserPreferences user) {
        message.setReplyMarkup(Buttons.chooseCurrencyChanging(user));
    }

    @NonNull
    public static void startSchedule(String chatId, SendMessage message, String text, int hours, MessageSender sender) {
        stopSchedule(chatId);
        try {
            BotAnswer.getSchedules().put(chatId, new NotificationScheduler(hours, () -> {
                getInfo(message, text);
                sender.sendMessage(message);
            }));
            BotAnswer.getSchedules().get(chatId).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void stopSchedule(String chatId) {
        if (BotAnswer.getSchedules().get(chatId) != null) {
            BotAnswer.getSchedules().get(chatId).stop();
            BotAnswer.getSchedules().remove(chatId);
        }
    }
}
