/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/14/24
 * Time: 9:27 AM
 */

package com.smatech.smatech_shop_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }