package com.black.common.utils;

import cn.hutool.json.JSONUtil;
import com.black.entity.ChatGptException;
import com.black.entity.aiReq.AiReq;
import com.black.entity.aiReq.chat.AiChatReq;
import com.black.entity.aiRes.AiRes;
import com.black.entity.aiRes.chat.AiChatRes;

import com.squareup.okhttp.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class AiClientBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String token;
    private final OkHttpClient httpClient;

    private static final String userName = "123456";
    private static final String password = "123456";

    public static AiClientBuilder getInstance() {
        return new AiClientBuilder("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");
    }

    public AiClientBuilder(String token) {
        this.token = token;
        this.httpClient = getProxyClient();
    }

    public AiRes completions(AiReq aiReq) throws ChatGptException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONUtil.parse(aiReq).toString());
        Request build = new Request.Builder().url("https://api.openai.com/v1/completions")
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody)
                .build();
        try {
            Response execute = httpClient.newCall(build).execute();
            try (ResponseBody body = execute.body();
                 InputStream inputStream = body.byteStream()) {
                String resp = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                return JSONUtil.toBean(resp, AiRes.class);
            }
        } catch (Exception e) {
            logger.info("请求异常！" + aiReq, e);
            throw new ChatGptException(e);
        }
    }

    public AiChatRes chatCompletions(AiChatReq aiChatReq) throws ChatGptException {
        System.out.println(aiChatReq);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONUtil.parse(aiChatReq).toString());
        Request build = new Request.Builder().url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody)
                .build();
        try {
            Response execute = httpClient.newCall(build).execute();
            try (ResponseBody body = execute.body();
                 InputStream inputStream = body.byteStream();) {
                String resp = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                return JSONUtil.toBean(resp, AiChatRes.class);
            }
        } catch (Exception e) {
            logger.info("请求异常！" + aiChatReq, e);
            throw new ChatGptException(e);
        }
    }

    private OkHttpClient getProxyClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(180, TimeUnit.SECONDS);
        String property = System.getProperty("os.name");
        if (property.toLowerCase().startsWith("win")) {
            client.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 18714)));//本地测试环境
        } else {
            client.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.48.7", 18714)));//服务器测试环境
        }
        client.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {
                if (response.request().header("Proxy-Authorization") != null) {
                    return null;
                }
                String credential = Credentials.basic(userName, password);
                return response.request().newBuilder()
                        .header("Proxy-Authorization", credential)
                        .build();
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                if (response.request().header("Proxy-Authorization") != null) {
                    return null;
                }
                String credential = Credentials.basic(userName, password);
                return response.request().newBuilder()
                        .header("Proxy-Authorization", credential)
                        .build();
            }
        });
        return client;
    }
}
