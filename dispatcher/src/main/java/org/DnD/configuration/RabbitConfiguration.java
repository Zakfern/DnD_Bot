package org.DnD.configuration;



import org.DnD.model.RabbitQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public Queue unknownMessageQueue(){
        return new Queue(RabbitQueue.UNKNOWN_MESSAGE_UPDATE);
    }
    @Bean
    public Queue textMessageQueue(){
        return new Queue(RabbitQueue.TEXT_MESSAGE_UPDATE);
    }
    @Bean
    public Queue answerMessageQueue(){
        return new Queue(RabbitQueue.ANSWER_MESSAGE);
    }
    @Bean
    public Queue callBackQueryUpdate(){
        return new Queue(RabbitQueue.CALLBACK_QUERY_UPDATE);
    }

}
