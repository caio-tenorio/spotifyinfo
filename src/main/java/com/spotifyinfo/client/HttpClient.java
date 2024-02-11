package com.spotifyinfo.client;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class HttpClient {
    Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private final OkHttpClient httpClient;
    private static final Integer DEFAULT_CONNECTION_TIMEOUT = 1;
    private static final Integer DEFAULT_SOCKET_TIMEOUT = 30;

    HttpClient() {
        this.httpClient = new OkHttpClient();
    }


    public String get(String url, Map<String, String> queryParams, SpotifyHeaders<String, String> headers) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        if(queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        Request.Builder request = new Request.Builder()
                .url(urlBuilder.build().toString());

        if (headers != null) {
            Headers.Builder headersBuilder = new Headers.Builder();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
            request.headers(headersBuilder.build());
        }

        try (Response response = this.httpClient.newCall(request.build()).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = this.httpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String post(String url, Map<String, String> formBody, SpotifyHeaders<String, String> headers) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : formBody.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }

        Headers.Builder headersBuilder = new Headers.Builder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersBuilder.add(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formBuilder.build())
                .headers(headersBuilder.build())
                .build();

        try (Response response = this.httpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
