/**
 * Created by tendaimupezeni for smatech-shop-app
 * Date: 10/8/24
 * Time: 12:56 AM
 */

package com.smatech.smatech_shop_app.services;

import org.springframework.scheduling.annotation.Async;
@Async
public interface EmailSenderService {

    void sendEmail(String to,String subject, String body);

}
