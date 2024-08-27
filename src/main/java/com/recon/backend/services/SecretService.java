package com.recon.backend.services;

import com.google.cloud.secretmanager.v1.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.ByteString;
import com.recon.backend.models.BankSecrets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.zip.CRC32C;
import java.util.zip.Checksum;

@Service
public class SecretService {
    private static final Logger LOGGER = Logger.getLogger(SecretService.class.getSimpleName());
    private static final String tag = "\uD83D\uDD11\uD83D\uDD11\uD83D\uDD11 SecretService \uD83D\uDD11 ";
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();
    SecretManagerServiceClient secretManagerServiceClient;
    private final FireService fireService;

    public SecretService(FireService fireService) {
        this.fireService = fireService;
    }

    static final String suffix =  "_BANK_SECRETS";
    public void createBankSecrets(BankSecrets bs) throws Exception {
        var key = bs.getBankId() + suffix;
        createSecret(key, G.toJson(bs));
    }

    public void createSecret(String key, String value) throws Exception {

        LOGGER.info(tag + "Creating secret with \uD83D\uDCA6 key: " + key + " and value: " + value);

        try {
            secretManagerServiceClient = SecretManagerServiceClient.create();
            // Set the secret name to the key
            Secret secret = Secret.newBuilder()
                    .setName(key) // Use the key as the secret name
                    .setReplication(Replication.newBuilder()
                            .setAutomatic(Replication.Automatic.newBuilder().build())
                            .build()) // Add replication
                    .build();

            // Use projectName.toString() for the correct path
            ProjectName projectName = ProjectName.of(projectId);
            Secret response = secretManagerServiceClient.createSecret(projectName.toString(), key, secret);
            // Add the secret value using SecretVersion
            AddSecretVersionRequest secretVersionRequest = AddSecretVersionRequest.newBuilder()
                    .setParent(response.getName()) // Set the parent secret
                    .setPayload(SecretPayload.newBuilder()
                            .setData(ByteString.copyFromUtf8(value)))
                    .build();

            secretManagerServiceClient
                    .addSecretVersion(secretVersionRequest);

            LOGGER.info(tag + "Created secret \uD83E\uDD6C " + key + " with name: "
                    + response.getName() + " \uD83E\uDD6C isInitialized \uD83C\uDF4E "
                    + response.isInitialized());
        } catch (Exception e) {
            LOGGER.info(tag + " Error creating secret: " + e);
            throw new Exception("Error creating secret: " + e);
        } finally {
            secretManagerServiceClient.close();
            LOGGER.info(tag + "SecretManagerServiceClient closed ");
        }

    }

    @Value("${projectId}")
    private String projectId;

    // Access the payload for the given secret version if one exists. The version
    // can be a version number as a string (e.g. "5") or an alias (e.g. "latest").
    public String accessSecretVersion(String secretId, String versionId)
            throws IOException {
        LOGGER.info(tag + "accessSecretVersion secret with \uD83D\uDCA6 secretId: " + secretId);

        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretId, versionId);
            // Access the secret version.
            AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);
            byte[] data = response.getPayload().getData().toByteArray();
            Checksum checksum = new CRC32C();
            checksum.update(data, 0, data.length);
            if (response.getPayload().getDataCrc32C() != checksum.getValue()) {
                LOGGER.info("Data corruption detected.");
                return null;
            }

            String payload = response.getPayload().getData().toStringUtf8();
            LOGGER.info(tag + "secret is: " + payload);
            return payload;
        }
    }

    private void deleteSecret(String secretId) throws IOException {
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            SecretName secretName = SecretName.of(projectId, secretId);
            client.deleteSecret(secretName);
            client.close();
            LOGGER.info(tag + "Deleted secret \uD83D\uDD34 " + secretId);
        }
    }

    public void deleteBankSecrets(String bankId) throws IOException {
        LOGGER.info(tag + "Delete bank secrets, bankId: " + bankId);
        try {
            var secret = accessSecretVersion(bankId + suffix, "1");
            if (secret != null) {
                deleteSecret(bankId + suffix);
            }
        } catch (Exception e) {
            LOGGER.info(tag + "No secret to delete");
        }

    }

    public void deleteAllSecrets() throws IOException {
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            ProjectName projectName = ProjectName.of(projectId);

            // Get all secrets.
            SecretManagerServiceClient.ListSecretsPagedResponse pagedResponse
                    = client.listSecrets(projectName);
            // List all secrets.
            pagedResponse
                    .iterateAll()
                    .forEach(
                            secret -> {
                                LOGGER.info("Secret found : \uD83D\uDD11 " + secret.getName());
                                try {
                                    String[] s = secret.getName().split("/");
                                    String key = s[3];
                                    LOGGER.info(tag + " Key to delete: " + key);
                                    deleteSecret(key);
                                } catch (IOException e) {
                                    LOGGER.info(tag + "\uD83D\uDC7F Delete of secret failed: " + e.getMessage());
                                }
                            });

        }
    }

    public BankSecrets getBankSecrets(String bankId) throws IOException {
        LOGGER.info(tag + "getBankSecrets secret with \uD83D\uDCA6 bankId: " + bankId);

        var secret = accessSecretVersion(bankId + "_BANK_SECRETS", "1");
        var bs = G.fromJson(secret, BankSecrets.class);
        LOGGER.info(tag + " Bank secrets: " + G.toJson(bs));
        return bs;
    }

}
