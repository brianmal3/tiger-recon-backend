package com.recon.backend.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recon.backend.models.Bank;
import com.recon.backend.models.BankSecrets;
import com.squareup.okhttp.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class CommunicationsService {

    private static final Logger LOGGER = Logger.getLogger(CommunicationsService.class.getSimpleName());
    private static final String tag = "\uD83C\uDF0D\uD83C\uDF0D\uD83C\uDF0D CommunicationsService \uD83C\uDF0D ";
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    private final OkHttpClient client = new OkHttpClient();
    private final FireService fireService;
    private final SecretService secretService;

    public CommunicationsService(FireService fireService, SecretService secretService) {
        this.fireService = fireService;
        this.secretService = secretService;
    }

    public String connectWithGet(String url) throws Exception {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .build();

            client.setConnectTimeout(120, TimeUnit.SECONDS);
            client.setReadTimeout(20,TimeUnit.SECONDS);
            client.setRetryOnConnectionFailure(true);

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException(" Bad response " + response.message());
            }
            var m = response.body().string();
            var json = G.fromJson(m, Object.class);
            if (json instanceof List) {
                List<Object> list = (List<Object>)json;
                LOGGER.info(tag + " \uD83D\uDE21 " + list.size() + " rows");
            }
            return m;

        } catch (Exception e) {
            throw new Exception(tag +" Unexpected Communications Error: \uD83D\uDC7F\uD83D\uDC7F\uD83D\uDC7F "
                    + e.getMessage());
        }
    }
    public String connectWithPost(String url, String data) throws Exception {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"),data);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                LOGGER.info(tag + " " +responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            var m = response.body().toString();
            LOGGER.info(tag + m);
            return m;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public String getAuthentication(String bankId) throws IOException {

        BankSecrets bs = secretService.getBankSecrets(bankId);
        var m = fireService.findDataByProperty("Banks",
                "bankId",bankId, Bank.class);
        if (m.isEmpty()) {
            return null;
        }
        Bank bank = (Bank)m.get(0);
        //todo - check auth style required, eg. apiKey, username etc.
        if (bank.getName().equalsIgnoreCase("First National Bank")) {
            return handleFNB(bs);
        }
        return null;
    }
    private String handleFNB(BankSecrets bs) {
        var scope = "i_can";
        var auth = new AuthData("client_credentials", scope);

//        connectWithPost()
        return null;


    }
    private static class AuthData {
        String grant_type;
        String scope;

        public AuthData(String grant_type, String scope) {
            this.grant_type = grant_type;
            this.scope = scope;
        }
    }
}
