package com.recon.backend.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recon.backend.models.Bank;
import com.recon.backend.models.BankSecrets;
import com.recon.backend.models.CustomResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class BankService {

    private static final Logger LOGGER = Logger.getLogger(BankService.class.getSimpleName());
    private static final String tag = "🦠🦠🦠 BankService 🦠🦠🦠";
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();

    private final FireService fireService;
    private final SecretService secretService;


    public BankService(FireService fireService, SecretService secretService) {
        this.fireService = fireService;
        this.secretService = secretService;
    }

    public CustomResponse createSouthAfricanBanks() {
        LOGGER.info(tag + " ...................................... creating 10 banks");
        var resp = new CustomResponse();
        try {
            secretService.deleteAllSecrets();
            fireService.deleteBanks();
            Bank bank1 = new Bank();
            bank1.setName("First National Bank");
            bank1.setClientId("Tyf4Jack#");
            bank1.setClientSecret("7g86fgTGxsT43U7");
            bank1.setBankId("1");
            bank1.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank1.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank1.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse1 = createBank(bank1);

            Bank bank2 = new Bank();
            bank2.setName("Standard Bank");
            bank2.setClientId("Bamj4srdj#");
            bank2.setClientSecret("GFTRgdr44");
            bank2.setBankId("2");
            bank2.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank2.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank2.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse2 = createBank(bank2);

            Bank bank3 = new Bank();
            bank3.setName("Discovery Bank");
            bank3.setApiKey("derr3f5-shdt-gdhgTyf4srdj#");
            bank3.setBankId("3");
            bank3.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank3.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank3.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse3 = createBank(bank3);

            Bank bank4 = new Bank();
            bank4.setName("Tyme Bank");
            bank4.setApiKey("yT%5GR54a-gdhgTyf4srdj#");
            bank4.setBankId("4");
            bank4.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank4.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank4.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse4 = createBank(bank4);

            Bank bank5 = new Bank();
            bank5.setName("Nedbank");
            bank5.setApiKey("ned87s6-yrhrusssrwT45#");
            bank5.setBankId("5");
            bank5.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank5.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank5.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse5 = createBank(bank5);

            Bank bank6 = new Bank();
            bank6.setName("Capitec Bank");
            bank6.setUserName("frankieboy");
            bank6.setPassword("hjd75De#_");
            bank6.setBankId("6");
            bank6.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank6.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank6.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse6 = createBank(bank6);

            Bank bank7 = new Bank();
            bank7.setName("Investec Bank");
            bank7.setUserName("georgebenz");
            bank7.setPassword("r^TGfsr67A");
            bank7.setBankId("7");
            bank7.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank7.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank7.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse7 = createBank(bank7);

            Bank bank8 = new Bank();
            bank8.setName("Bank Zero");
            bank8.setApiKey("zero-34-gdhgTyf4srdj#");
            bank8.setBankId("8");
            bank8.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank8.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank8.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse8 = createBank(bank8);

            Bank bank9 = new Bank();
            bank9.setName("Absa Bank");
            bank9.setUserName("harry3thomas");
            bank9.setPassword("h%tRh^7$");
            bank9.setBankId("9");
            bank9.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank9.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank9.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse9 = createBank(bank9);

            Bank bank10 = new Bank();
            bank10.setName("African Bank");
            bank10.setClientId("client8id_T%rff");
            bank10.setClientSecret("client^%T3sec5ret_764764");
            bank10.setBankId("10");
            bank10.setAuthUrl("https://api.i.fnb.co.za/apigateway/oauth2/authorize");
            bank10.setBaseUrl("https://api.p.fnb.co.za/apigateway");
            bank10.setTokenUrl("https://api.i.fnb.co.za/apigateway/oauth2/token/v2");
            var customResponse10 = createBank(bank10);

            List<Bank> resultBanks = new ArrayList<>();
            int count = 0;
            if (customResponse1.getStatus() == 200) {
                resultBanks.add(customResponse1.getBanks().get(0));
                count++;
            }
            if (customResponse2.getStatus() == 200) {
                resultBanks.add(customResponse2.getBanks().get(0));
                count++;
            }
            if (customResponse3.getStatus() == 200) {
                resultBanks.add(customResponse3.getBanks().get(0));
                count++;
            }
            if (customResponse4.getStatus() == 200) {
                resultBanks.add(customResponse4.getBanks().get(0));
                count++;
            }
            if (customResponse5.getStatus() == 200) {
                resultBanks.add(customResponse5.getBanks().get(0));
                count++;
            }
            if (customResponse6.getStatus() == 200) {
                resultBanks.add(customResponse6.getBanks().get(0));
                count++;
            }
            if (customResponse7.getStatus() == 200) {
                resultBanks.add(customResponse7.getBanks().get(0));
                count++;
            }
            if (customResponse8.getStatus() == 200) {
                resultBanks.add(customResponse8.getBanks().get(0));
                count++;
            }
            if (customResponse9.getStatus() == 200) {
                resultBanks.add(customResponse9.getBanks().get(0));
                count++;
            }
            if (customResponse10.getStatus() == 200) {
                resultBanks.add(customResponse10.getBanks().get(0));
                count++;
            }

            for (Bank bank : resultBanks) {
                LOGGER.info(tag + "Created  bank: \uD83E\uDD6C\uD83E\uDD6C " + G.toJson(bank) );
            }

            resp.setStatus(200);
            resp.setBanks(resultBanks);
            resp.setMessage(" " + resultBanks.size() + " Banks created");
            LOGGER.info(tag + "Created " + count + " banks");
            return resp;
        } catch (Exception e) {
            LOGGER.info(tag + " createSouthAfricanBanks: Error: " + e.getMessage());
            return handleError(e,"createSouthAfricanBanks");
        }
    }

    public CustomResponse createBank(Bank bank) {
        LOGGER.info(tag + " creating bank: " + bank.getName());
        String bankId = bank.getBankId();
        bank.setDateRegistered(new Date().toString());

        try {
            secretService.deleteBankSecrets(bankId);

            BankSecrets bs = new BankSecrets();
            bs.setBankId(bankId);
            bs.setAuthUrl(bank.getAuthUrl());
            bs.setBaseUrl(bank.getBaseUrl());
            bs.setTokenUrl(bank.getTokenUrl());
            bs.setApiKey(bank.getApiKey());
            bs.setClientId(bank.getClientId());
            bs.setClientSecret(bank.getClientSecret());
            bs.setUserName(bank.getUserName());
            bs.setPassword(bank.getPassword());

            secretService.createBankSecrets(bs);
            //clean up
            var bankToWrite = new Bank();
            bankToWrite.setBankId(bankId);
            bankToWrite.setName(bank.getName());
            bankToWrite.setDateRegistered(new Date().toString());
            fireService.writeData("Banks", bankToWrite);
            LOGGER.info(tag + " result bank, check keys: " + bank.getName());

            var list = new ArrayList<Bank>();
            list.add(bankToWrite);
            var resp = new CustomResponse();
            resp.setStatus(200);
            resp.setBanks(list);
            resp.setMessage("Bank created");
            return resp;

        } catch (Exception e) {
            return handleError(e, "Bank creation failed. ");
        }
    }

    public CustomResponse updateBank(Bank bank) {

        return null;
    }

    public CustomResponse findAll() {
        try {
            List<Object> data = fireService.getAllCollectionData("Banks", Bank.class);
            //todo - REMOVE
//            var bs = secretService.getBankSecrets("1");

            return getCustomResponse(data);
        } catch (Exception e) {
            return handleError(e, "Error ");
        }
    }

    private static CustomResponse handleError(Exception e, String message) {
        LOGGER.info(tag + " error: " + e);
        var resp = new CustomResponse();
        resp.setStatus(500);
        resp.setMessage(message + e.getMessage());
        return resp;
    }

    public CustomResponse findOne(String bankId) {
        try {
            var m = fireService.findDataByProperty("Banks", "bankId", bankId, Bank.class);
            return getCustomResponse(m);
        } catch (Exception e) {
            handleError(e, "Finding Bank failed");
        }
        return null;
    }

    private static CustomResponse getCustomResponse(List<Object> m) {
        var resp = new CustomResponse();
        var list = new ArrayList<Bank>();
        for (Object o : m) {
            list.add((Bank) o);
        }
        resp.setStatus(200);
        resp.setBanks(list);
        resp.setMessage("Banks found: " + list.size());
        return resp;
    }

    public CustomResponse findByName(String name) {
        try {
            var m = fireService.findDataByProperty("Banks", "name", name, Bank.class);
            return getCustomResponse(m);
        } catch (Exception e) {
            handleError(e, "Finding bank failed.");
        }
        return null;
    }

}

