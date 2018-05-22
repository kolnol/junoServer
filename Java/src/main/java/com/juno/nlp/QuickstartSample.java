package com.juno.nlp;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class QuickstartSample {
    public static void main(String... args) throws Exception {
        // Instantiates a client
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/Users/Nokid/GoogleCreds/client_secret.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        LanguageServiceSettings settings = LanguageServiceSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
        try (LanguageServiceClient language = LanguageServiceClient.create(settings)) {

            // The text to analyze
            String text = "Gibson";
            Document doc = Document.newBuilder()
                    .setContent(text).setType(Type.PLAIN_TEXT).build();

            // Detects the sentiment of the text
            //List<ClassificationCategory> classes = language.classifyText(doc).getCategoriesList();

            AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder()
                    .setDocument(doc)
                    .build();
            List<Entity> entitiesList = language.analyzeEntities(request).getEntitiesList();
            Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

            for (Entity entity: entitiesList) {
                System.out.println("Category: " + entity);
            }

            System.out.printf("Text: %s%n", text);
            System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
        }
    }
}