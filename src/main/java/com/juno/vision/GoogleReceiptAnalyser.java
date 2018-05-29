package com.juno.vision;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import com.google.cloud.vision.v1.WebDetection.WebEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleReceiptAnalyser implements ReceiptAnalyser {
    private byte[] receiptImage;
    private GoogleCredentials credentials;
    private List<AnnotateImageResponse> results;

    public GoogleReceiptAnalyser(byte[] receiptImage) {
        this.receiptImage = receiptImage;
        this.credentials = ComputeEngineCredentials.create();//GoogleCredentials.fromStream(new FileInputStream("/Users/Nokid/GoogleCreds/client_secret.json"))
        //.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        doRdquest();
    }

    private void doRdquest() {
        // Instantiates a client

        ImageAnnotatorSettings settings = null;
        try {
            settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERR] cannot set image settings");
            return;
        }

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create(settings)) {

            ByteString imgBytes = ByteString.copyFrom(receiptImage);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature textDetection = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();
            Feature webDetection = Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build();

            //Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(textDetection)
                    .addFeatures(webDetection)
                    .setImage(img)
                    .build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            this.results = response.getResponsesList();
            vision.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isReceipt() {
        boolean isReceipt = false;
        for (AnnotateImageResponse res :
                results) {
            if(res.hasError()) {
                System.out.printf("Error: %s\n", res.getError().getMessage());
                return false;
            }

            WebDetection annotation = res.getWebDetection();

            //System.out.println("Entity:Id:Score");
            //System.out.println("===============");
            for (WebEntity entity:
                    annotation.getWebEntitiesList()) {
                if(entity.getDescription().toLowerCase().equals("receipt")) isReceipt = true;
                //System.out.println(entity.getDescription() + " : " + entity.getEntityId() + " : "+ entity.getScore());
            }

            //List<WebDetection.WebLabel> labels= annotation.getBestGuessLabelsList();

            /*for (WebDetection.WebLabel label:
                 labels) {
                if(label.getLabel().toLowerCase().equals("receipt")) isReceipt = true;
                //System.out.println("Best label is: " + label.getLabel());
            }*/

        }
        return isReceipt;
    }

    @Override
    public String getText() {
        StringBuilder resultedText = new StringBuilder();
        for (AnnotateImageResponse res : results) {
            if (res.hasError()) {
                System.out.printf("Error: %s\n", res.getError().getMessage());
                return null;
            }
            // For full list of available annotations, see http://g.co/cloud/vision/docs
            TextAnnotation annotation = res.getFullTextAnnotation();
            for (Page page: annotation.getPagesList()) {
                String pageText = "";
                for (Block block : page.getBlocksList()) {
                    String blockText = "";
                    for (Paragraph para : block.getParagraphsList()) {
                        String paraText = "";
                        for (Word word: para.getWordsList()) {
                            String wordText = "";
                            for (Symbol symbol: word.getSymbolsList()) {
                                wordText = wordText + symbol.getText();
                            }
                            paraText = paraText + wordText;
                        }
                        // Output Example using Paragraph:
                        //System.out.println("Paragraph: \n" + paraText);
                        //System.out.println("Bounds: \n" + para.getBoundingBox() + "\n");
                        blockText = blockText + paraText;
                    }
                    pageText = pageText + blockText;
                }
            }
            //System.out.println(annotation.getText());
            resultedText.append(annotation.getText());
        }
        return resultedText.toString();
    }

}
