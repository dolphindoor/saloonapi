package com.saloon.services;

import com.dolphindoors.resource.jpa.CrudApi;
import com.dolphindoors.resource.utilities.JUtils;
import com.saloon.dto.SmsParam;
import com.saloon.entity.AppConfig;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author richardnarh
 */
public class DefaultService {
    @Inject private CrudApi crudApi;
    
        public String sms(List<String> numbers, String message){
        String msg = "Failed sending SMS";
        String apiKey = AppConfig.query(crudApi.getEm())
                .where(AppConfig._configName, "sms.api.key")
                .execute().getConfigValue();
        
        String sender = AppConfig.query(crudApi.getEm())
                .where(AppConfig._configName, "sms.sender.id")
                .execute().getConfigValue();
        
        
        
        String apiUrl = "https://api.smsonlinegh.com/v5/message/sms/send";
        
        SmsParam param = new SmsParam(message, 0, sender, numbers);
        
        String payload = JUtils.Json().toJson(param);
        
        RequestBody requestBody = RequestBody.create(payload, okhttp3.MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(requestBody)
                    .addHeader("Host", "api.smsonlinegh.com")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json; charset=utf-8")
                    .addHeader("Authorization", "key "+apiKey)
                    .build();
        
        try {
            Response response = JUtils.http().newCall(request).execute();
            if(response.isSuccessful()){
                String body = response.body().string();
                System.out.println("body: "+body);
                msg = "SMS sent successfully.";
            }else {
                msg = "Failed: " + response.code() + " - " + response.message();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return msg;
    }
}
