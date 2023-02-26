package org.DnD.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducer {
    //принимает ответы из RabbitMQ
    void produce(String rabbitQueue, Update update);
}
