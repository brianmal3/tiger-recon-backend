package com.recon.backend.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recon.backend.utils.E;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Initializes Firebase
 */
@RequiredArgsConstructor
@Service
public class FirebaseService {

    private static final Logger LOGGER = Logger.getLogger(FirebaseService.class.getSimpleName());
    static final Gson G = new GsonBuilder().setPrettyPrinting().create();

    @Value("${storageBucket}")
    private String storageBucket;
    @Value("${projectId}")
    private String projectId;


    public void initializeFirebase() throws Exception {
        LOGGER.info(E.AMP+E.AMP+E.AMP+ " .... initializing Firebase ....");
        FirebaseOptions options;
        LOGGER.info(E.AMP+E.AMP+E.AMP+
                " Project Id from Properties: "+E.RED_APPLE + " " + projectId);
        var creds = GoogleCredentials.getApplicationDefault();
        LOGGER.info(E.AMP+E.AMP+E.AMP+E.AMP+E.AMP + "creds: " + creds.toString());
        options = FirebaseOptions.builder()
                .setCredentials(creds)
                .setDatabaseUrl("https://" + projectId + ".firebaseio.com/")
                .setStorageBucket(storageBucket)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        LOGGER.info(E.AMP+E.AMP+E.AMP+E.AMP+E.AMP+
                " Firebase has been initialized: "
                + app.getOptions().getDatabaseUrl()
                + " " + E.RED_APPLE);
        // Initialize Firestore
        // Create Firestore options
        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setCredentials(creds)
                .setProjectId(projectId)
                .build();

        LOGGER.info(E.AMP+E.AMP+E.AMP+E.AMP+E.AMP+
                " Firestore has been initialized:\n \uD83C\uDF3F projectId: "
                + firestoreOptions.getProjectId()
                + "\n \uD83C\uDF3F databaseId: " + firestoreOptions.getDatabaseId()
                + "\n \uD83C\uDF3F scopedCredentials: " + firestoreOptions.getScopedCredentials().toString()
                + "\n " + E.RED_APPLE+ E.RED_APPLE+ E.RED_APPLE+ E.RED_APPLE+ E.RED_APPLE);



    }

}
