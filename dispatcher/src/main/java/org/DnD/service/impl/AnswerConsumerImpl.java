package org.DnD.service.impl;

import org.DnD.controller.UpdateHandler;
import org.DnD.model.RabbitQueue;
import org.DnD.service.AnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AnswerConsumerImpl implements AnswerConsumer {

    //считывает из брокера ответы которы были отправлены из node

    private final UpdateHandler updateHandler;

    public AnswerConsumerImpl(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    @RabbitListener(queues = RabbitQueue.ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        updateHandler.SetView(sendMessage);
    }
}
