package com.juno.Controllers;

import com.juno.entities.MockReceiptResponseTesting;
import com.juno.vision.GoogleReceiptAnalyser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class ReceiptAnalyserController {

    @PostMapping("/analyseImage")
    public ResponseEntity<?> analyse(@RequestParam("image") MultipartFile image) {
        if (image.getContentType() != null) {
            if(image.getContentType().contains("image")) {
                System.out.println("GOT IMAGE!!");
                try {
                    GoogleReceiptAnalyser analyser = new GoogleReceiptAnalyser(image.getBytes());
                    if(analyser.isReceipt()) {
                        return ResponseEntity.ok(new MockReceiptResponseTesting(analyser.getText()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().body("Can not process");
                }

            }
        }

        return ResponseEntity.badRequest().body("The file contains no receipt!");
    }
}
