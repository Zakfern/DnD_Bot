package org.DnD.controller;

import lombok.extern.log4j.Log4j;
import org.DnD.controller.utils.MessageUtils;
import org.DnD.controller.utils.enums.KeyboardLayouts;
import org.DnD.model.RabbitQueue;
import org.DnD.service.AnswerConsumer;
import org.DnD.service.UpdateProducer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j
public class UpdateHandler {
    private TelegramBot telegramBot;
    private MessageUtils messageUtils;
    private UpdateProducer updateProducer;


    public UpdateHandler(
            MessageUtils messageUtils,
            UpdateProducer updateProducer
    ) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update){
        if (update == null){
            //log.error("Received update is null");
            return;
        }
        if (update.hasMessage()) {
            distributeMessageByType(update);
        }
        else if (update.hasCallbackQuery()) {
            distributeMessageByCallbackQuery(update);
        }
        else {
           // log.error("Received unsupported message type " + update);
        }
    }

    private void distributeMessageByCallbackQuery(Update update) {

        updateProducer.produce(RabbitQueue.CALLBACK_QUERY_UPDATE, update);
        System.out.println("Получен CallbackQuery");
        String call_data = update.getCallbackQuery().getData();
        Integer message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat_id);

//        EditMessageText editMessageText = new EditMessageText();
//        editMessageText.setChatId(chat_id);
//        editMessageText.setMessageId(message_id);

        switch (call_data) {
            case "get_to_menu":
                String answer = "Updated message text";
                sendMessage.setText("Выберите действие из наблора возможных");
                SetView(messageUtils.addInlineKeyBoardToMessage(sendMessage, KeyboardLayouts.START_LAYOUT));
                break;
            case "get_random_trap":
                sendMessage.setText("Список случайных ловушек");
                SetView(messageUtils.addReplyKeyBoarInMessage(sendMessage, KeyboardLayouts.TRAPS_LAYOUT));
                break;
            case "get_next_trap":
                System.out.println("next_trap пока не реализован");
                break;
            default:
                System.out.println("Oooops, something wrong ! " + call_data + "не обработан");
        }
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        Long chat_id = update.getMessage().getChatId();
        sendMessage.setChatId(chat_id);
        if (message.hasText()) {
            processTextMessage(update);
        } else if (message.hasDocument()) {
            processDocMessage(update);
        } else if (message.hasPhoto()) {
            processPhotoMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(RabbitQueue.UNKNOWN_MESSAGE_UPDATE, update);
    }

    private void processDocMessage(Update update) {
        updateProducer.produce(RabbitQueue.UNKNOWN_MESSAGE_UPDATE, update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(RabbitQueue.TEXT_MESSAGE_UPDATE, update);
        Long chat_id = update.getMessage().getChatId();
        SendMessage sendMessage = messageUtils.addInlineKeyBoardToMessage(new SendMessage(), KeyboardLayouts.START_LAYOUT);
        sendMessage.setChatId(chat_id);
        sendMessage.setText("Выберите действие из наблора возможных");
        SetView(sendMessage);
    }

    public void SetView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

//    private void sendAnswer(String output, Long chatId) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(output);
//        answerConsumer.consume(sendMessage);
//    }
    private void setUnsupportedMessageTypeView(Update update) {
        updateProducer.produce(RabbitQueue.UNKNOWN_MESSAGE_UPDATE, update);
    }

}
