package com.mruruc.hashing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SecurityProvidersToHtml {
    public static void main(String[] args) throws IOException {
        Map<String, Map<String, List<Provider.Service>>> providersGrouped = Arrays.stream(Security.getProviders())
                .collect(Collectors.toMap(
                        Provider::getName,
                        provider -> provider.getServices().stream()
                                .collect(Collectors.groupingBy(Provider.Service::getType))
                ));

        Path path = Paths.get("src", "main", "resources", "security-providers.html");
        String htmlStart = """
                <!DOCTYPE html>
                <html>
                <head>
                <title>Security Providers</title>
                <style>
                body { font-family: Arial, sans-serif; margin: 40px; background-color: #f9f9f9; color: #333; }
                h1 { font-size: 2em; color: #2c3e50; border-bottom: 2px solid #ccc; padding-bottom: 0.5em; }
                h2 { font-size: 1.5em; color: #34495e; margin-top: 1.5em; }
                h3 { font-size: 1.2em; color: #555; margin: 0.5em 0 0.5em 20px; }
                hr { margin: 2em 0; }
                </style>
                </head>
                <body>
                """;

        String htmlEnd = """
                </body>
                </html>
                """;

        Files.writeString(path, htmlStart, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        for (Map.Entry<String, Map<String, List<Provider.Service>>> providerEntry : providersGrouped.entrySet()) {

            Files.writeString(path, String.format("<h1>%s</h1>%n", providerEntry.getKey()), StandardOpenOption.APPEND);

            for (Map.Entry<String, List<Provider.Service>> typeEntry : providerEntry.getValue().entrySet()) {
                Files.writeString(path, String.format("<h2>%s</h2>%n", typeEntry.getKey()), StandardOpenOption.APPEND);

                for (Provider.Service service : typeEntry.getValue()) {
                    Files.writeString(path, String.format("<h3>%s</h3>%n", service.getAlgorithm()), StandardOpenOption.APPEND);
                }
            }

            Files.writeString(path, "<hr>", StandardOpenOption.APPEND);
        }

        Files.writeString(path, htmlEnd, StandardOpenOption.APPEND);
    }
}
