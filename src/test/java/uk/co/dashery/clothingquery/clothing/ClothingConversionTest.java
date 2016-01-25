package uk.co.dashery.clothingquery.clothing;


import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.dashery.clothingquery.ClothingTestUtils.expectedClothing;

public class ClothingConversionTest {

    private Jackson2JsonMessageConverter messageConverter;

    @Before
    public void setUp() {
        messageConverter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(Clothing[].class);
        messageConverter.setClassMapper(classMapper);
    }

    @Test
    public void testConvertsFromJsonMessage() throws Exception {
        Message message = givenAMessageWithTwoJsonProducts();

        Clothing[] convertedClothing = (Clothing[]) messageConverter.fromMessage(message);

        assertThat(convertedClothing, is(expectedClothing()));
    }

    private Message givenAMessageWithTwoJsonProducts() {
        String productsJson = "[" +
                "{\"id\":\"id123\"," +
                "\"merchant\":\"A Test Brand\"," +
                "\"name\":\"Test Item\"," +
                "\"description\":\"<p>Some description or other.</p>\"," +
                "\"price\":10000," +
                "\"link\":\"a_link.html\"," +
                "\"imageLink\":\"image.jpg\"}," +
                "{\"id\":\"id456\"," +
                "\"merchant\":\"Another Day\"," +
                "\"name\":\"Another Dollar\"," +
                "\"description\":\"&lt;p&gt;A different description.&lt;/p&gt;\"" +
                ",\"price\":200," +
                "\"link\":\"different_link\"," +
                "\"imageLink\":\"image2.jpg\"}" +
                "]";
        return new Message(productsJson.getBytes(), getMessageProperties());
    }

    private MessageProperties getMessageProperties() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        return messageProperties;
    }
}
