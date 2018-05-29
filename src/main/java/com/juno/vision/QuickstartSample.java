package com.juno.vision;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QuickstartSample {
    public static void main(String... args) throws Exception {
        // The path to the image file to annotate
        String fileName = "/Users/Nokid/Google Drive/Juno Accounting App/Java Google Vision API/src/main/java/com/juno/vision/imgs/Register_Order_Receipt.JPG";

        // Reads the image file into memory
        Path path = Paths.get(fileName);
        byte[] img = Files.readAllBytes(path);
        GoogleReceiptAnalyser analyser = new GoogleReceiptAnalyser(img);
        System.out.println("****************");
        System.out.println("Is Receipt: " + (analyser.isReceipt() ? "true" : "false"));
        System.out.println("Text: \n" + analyser.getText());
    }
}
// [END vision_quickstart]