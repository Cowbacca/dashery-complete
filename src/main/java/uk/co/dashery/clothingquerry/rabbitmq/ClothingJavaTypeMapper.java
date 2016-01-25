package uk.co.dashery.clothingquerry.rabbitmq;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import uk.co.dashery.clothingquerry.clothing.Clothing;

import java.util.List;

class ClothingJavaTypeMapper implements Jackson2JavaTypeMapper {
    @Override
    public void fromJavaType(JavaType javaType, MessageProperties messageProperties) {

    }

    @Override
    public JavaType toJavaType(MessageProperties messageProperties) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.getTypeFactory().constructCollectionType(List.class, Clothing
                .class);
    }

    @Override
    public void fromClass(Class<?> aClass, MessageProperties messageProperties) {

    }

    @Override
    public Class<?> toClass(MessageProperties messageProperties) {
        return null;
    }
}
