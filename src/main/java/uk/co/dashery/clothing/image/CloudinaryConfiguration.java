package uk.co.dashery.clothing.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class CloudinaryConfiguration {
    @Value("${cloudinary.url}")
    private String cloudinaryUrl;

    @Bean
    public Cloudinary cloudinary() throws URISyntaxException {
        URI uri = new URI(cloudinaryUrl);


        String[] userInfo = uri.getUserInfo().split(":");
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", uri.getHost(),
                "api_key", userInfo[0],
                "api_secret", userInfo[1]));
    }
}
