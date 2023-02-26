package org.DnD.controller.utils;

import org.DnD.controller.utils.enums.KeyboardLayouts;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MessageUtils {
    public SendMessage generateSendMessageWithText(Update update, String text) {
        var message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        return sendMessage;
    }

    public static SendMessage addInlineKeyBoardToMessage(SendMessage sendMessage, KeyboardLayouts keyboardLayouts) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //инлайн клавиатура
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (HashMap<String, String> variableName : keyboardLayouts.getKeyboardLayout()){
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            System.out.println(variableName.values());
            for(Map.Entry<String, String> entry : variableName.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(key);
                button.setCallbackData(value);
                keyboardButtonsRow.add(button);
            }
            rowList.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
    public static EditMessageText addInlineKeyBoardEditMessage(EditMessageText editMessageText, KeyboardLayouts keyboardLayouts) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //инлайн клавиатура
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (HashMap<String, String> variableName : keyboardLayouts.getKeyboardLayout()){
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            System.out.println(variableName.values());
            for(Map.Entry<String, String> entry : variableName.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(key);
                button.setCallbackData(value);
                keyboardButtonsRow.add(button);
            }
            rowList.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }
    public static SendMessage addReplyKeyBoarInMessage(SendMessage sendMessage, KeyboardLayouts keyboardLayouts) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); //инлайн клавиатура
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        for (HashMap<String, String> variableName : keyboardLayouts.getKeyboardLayout()) {
            KeyboardRow keyboardButtonsRow = new KeyboardRow();
            System.out.println(variableName.values());
            for(Map.Entry<String, String> entry : variableName.entrySet()) {
                String buttonText = entry.getKey();
                keyboardButtonsRow.add(buttonText);
            }
            keyboard.add(keyboardButtonsRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
