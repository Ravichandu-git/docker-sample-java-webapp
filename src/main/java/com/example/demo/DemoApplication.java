package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

    private static boolean specialMode = false;

    public static void main(String[] args) {
        System.out.println("Application starting with args: " + Arrays.toString(args));
        List<String> argList = Arrays.asList(args);

        if (argList.contains("--special")) {
            specialMode = true;
            System.out.println("Special mode activated!");
        } else {
            System.out.println("Running in default mode.");
        }

        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    static class GreetingController {

        @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
        public ResponseEntity<String> greet() {
            String htmlContent = generateHtmlPage(DemoApplication.specialMode);
            return ResponseEntity.ok(htmlContent);
        }

        private String generateHtmlPage(boolean isSpecialMode) {
            String title, message, bodyBg, messageColor, borderColor;

            if (isSpecialMode) {
                title = "Special Mode Active!";
                message = "Hello from the ✨ SPECIAL ✨ World!";
                bodyBg = "#e0f7fa";
                messageColor = "#00796b";
                borderColor = "#004d40";
            } else {
                title = "Default Mode";
                message = "Hello from the DEFAULT World!";
                bodyBg = "#fff3e0";
                messageColor = "#ef6c00";
                borderColor = "#e65100";
            }

            return """
                   <!DOCTYPE html>
                   <html lang="en">
                   <head>
                       <meta charset="UTF-8">
                       <meta name="viewport" content="width=device-width, initial-scale=1.0">
                       <title>%s</title>
                       <style>
                           body {
                               font-family: sans-serif;
                               background-color: %s;
                               display: flex;
                               justify-content: center;
                               align-items: center;
                               min-height: 100vh;
                               margin: 0;
                           }
                           .container {
                               background-color: #ffffff;
                               padding: 40px;
                               border-radius: 10px;
                               box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                               text-align: center;
                               border: 3px solid %s;
                           }
                           h1 {
                               color: %s;
                               font-size: 2.5em;
                           }
                       </style>
                   </head>
                   <body>
                       <div class="container">
                           <h1>%s</h1>
                       </div>
                   </body>
                   </html>
                   """.formatted(title, bodyBg, borderColor, messageColor, message);
        }

        @GetMapping("/health")
        public Map<String, String> healthCheck() {
            // Proper JSON response
            return Map.of("status", "UP");
        }
    }
}
