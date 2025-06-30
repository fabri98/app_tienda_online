package com.tienda_api_rest.config.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "ddefpmote",
                "api_key",    "462634969518747",
                "api_secret", "08_KkDWS5DP7NFmPwtbqJGUTx9w",
                "secure", true
        ));
    }

}
