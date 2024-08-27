package com.recon.backend.services;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.recon.backend.utils.E;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FireService {

    private static final Logger LOGGER = Logger.getLogger(FireService.class.getSimpleName());
    private static final String tag = "\uD83D\uDD35 \uD83D\uDD35 \uD83D\uDD35  FireService \uD83D\uDD35 ";

    // Method to update data in Firestore
    public void updateDataToFirestore(String collectionName, String documentId, Map<String, Object> data) {
        try {
            var firestore = FirestoreOptions.getDefaultInstance().getService();

            DocumentReference docRef = firestore.collection(collectionName).document(documentId);
            ApiFuture<WriteResult> writeResult = docRef.update(data);
            LOGGER.info(tag +
                    " Data updated in Firestore: " + writeResult.get().getUpdateTime()
                    + " " + E.RED_APPLE);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.severe(tag +
                    " Error updating data in Firestore: " + e.getMessage()
                    + " " + E.RED_APPLE);
        }
    }
    public void writeData(String collectionName, Object data) throws Exception {
        try {
            var firestore = FirestoreOptions.getDefaultInstance().getService();
            DocumentReference docRef = firestore.collection(collectionName).document();
            ApiFuture<WriteResult> writeResult = docRef.set(data);
            LOGGER.info(tag + " Firestore data written to collection: " + collectionName
                    + ", writeTime: " + writeResult.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.info(tag + " Error writing data to Firestore: " + e.getMessage());
            throw new Exception("Error writing data to Firestore" + e.getMessage());
        }
    }

    public List<Object> getAllCollectionData(String collectionName, Class returnObjectType) throws Exception {
        try {
            var firestore = FirestoreOptions.getDefaultInstance().getService();

            List<Object> data = new ArrayList<>();
            firestore.collection(collectionName).get().get().getDocuments().forEach(document -> {
                data.add(document.toObject(returnObjectType));
            });
            LOGGER.info(tag + " Firestore data read from collection: "
                    + collectionName + " found: " + data.size());
            return data;
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.info(tag + " Error reading data from Firestore: " + e);
            throw new Exception("Error reading data from Firestore");
        }
    }

    public void deleteBanks() throws Exception {
        try {
            var firestore = FirestoreOptions.getDefaultInstance().getService();
            for (QueryDocumentSnapshot snap : firestore.collection("Banks").get().get()) {
                snap.getReference().delete();
                LOGGER.info(tag + " Bank deleted: \uD83D\uDD34 " + snap.getId());
            }

        } catch (InterruptedException | ExecutionException e) {
            LOGGER.severe(tag + " Error reading data from Firestore: " + e);
            throw new Exception("Bank deletion failed");
        }
    }
    public List<Object> findDataByProperty(String collectionName, String propertyName, String propertyValue, Class returnObjectType) {
        var list = new ArrayList<Object>();
        try {
            var firestore = FirestoreOptions.getDefaultInstance().getService();

            ApiFuture<QuerySnapshot> documentSnapshot =
                    firestore.collection(collectionName).whereEqualTo(propertyName, propertyValue).get();
            var m = documentSnapshot.get().getDocuments();
            for (QueryDocumentSnapshot snapshot : m) {
                var k = snapshot.toObject(returnObjectType);
                list.add(k);
            }

        } catch (InterruptedException | ExecutionException e) {
            LOGGER.severe(tag + " Error reading data from Firestore: " + e);
            return null;
        }
        return list;
    }

	@Value("${projectId}")
	private String projectId;


}
