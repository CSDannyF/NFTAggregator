package com.ferndani00.NFTAggregator.ApiHttpClient;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiHttpClient {
    public HttpClient httpClient;
    public ApiHttpClient()
    {
        this.httpClient = HttpClient.newHttpClient();
    }

    // opensea
    public String getRequest(String url)
    {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .header("accept", "application/json")
                    .header("x-api-key", "abd041307f164bb7806fb5c409e7dc4f")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // reservoir
    public String getListings(String url)
    {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .header("accept", "application/json")
                    .header("x-api-key","a4e99918-df9b-5ac6-bf3a-8054453166a2")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

