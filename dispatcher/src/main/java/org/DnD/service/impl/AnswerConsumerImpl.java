package org.DnD.service.impl;

import org.DnD.controller.UpdateHandler;
import org.DnD.model.RabbitQueue;
import org.DnD.service.AnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static org.DnD.model.RabbitQueue.ANSWER_MESSAGE;

@Service
public class AnswerConsumerImpl implements AnswerConsumer {

    //считывает из брокера ответы которы были отправлены из node

    private final UpdateHandler updateHandler;

    public AnswerConsumerImpl(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        System.out.println("получили в диспатчере через брокер! " + ANSWER_MESSAGE);
        updateHandler.SetView(sendMessage);
    }
}
