package com.goit.ApiTelegramBot;

import com.goit.Utils.BotAnswer;
import com.goit.org.buttons.BotCommands;
import com.goit.org.buttons.Buttons;
import com.goit.org.functionalInteface.MessageSender;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


//Main class of TelegramBot
public class CurrencyBot extends TelegramLongPollingBot implements BotCommands, MessageSender {
    private static final BotAnswer answer = new BotAnswer();

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    //Bot constructor
    public CurrencyBot() {
        Buttons.initKeyboard();
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //Main method that get message from chat
    @Override
    public void onUpdateReceived(Update update) {
        String receive;
        long CHAT_ID;

        if (update.hasMessage()) {
            CHAT_ID = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(CHAT_ID);

            if (update.getMessage().hasText()) {
                receive = update.getMessage().getText();
                answer.botAnswerUtils(receive, message, this);
                sendMessage(message);
            }
        } else if (update.hasCallbackQuery()) {
            CHAT_ID = update.getCallbackQuery().getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(CHAT_ID);
            receive = update.getCallbackQuery().getData();
            if (receive.contains("_CHANGING")) {
                EditMessageReplyMarkup newMessage = new EditMessageReplyMarkup();
                newMessage.setChatId(CHAT_ID);
                newMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                answer.botChangingAnswerUtils(receive, newMessage, this);
                sendNewMessage(newMessage);
            } else if (receive.contains("_CHECKED")) {
                EditMessageReplyMarkup newMessage = new EditMessageReplyMarkup();
                newMessage.setChatId(CHAT_ID);
                newMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                answer.botChangingAnswerUtils(receive, newMessage, this);
                sendNewMessage(newMessage);
            }
            answer.botAnswerUtils(receive, message, this);
            sendMessage(message);
        }
    }

    //Send message in chat
    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //Send edited message in chat
    public void sendNewMessage(EditMessageReplyMarkup newMessage) {
        try {
            execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
