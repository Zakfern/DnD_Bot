package org.DnD.service.impl;

import lombok.extern.log4j.Log4j;
import org.DnD.model.RabbitQueue;
import org.DnD.service.ConsumerService;
import org.DnD.service.ProducerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.DnD.model.RabbitQueue.*;

@Service
@Log4j
public class ConsumerServiceImpl  implements ConsumerService {
    private  final ProducerService producerService;

    public ConsumerServiceImpl(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        var message = update.getMessage();
        var chatId = message.getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Hi im from Node");
        producerService.produceAnswer(sendMessage);


    }
}
