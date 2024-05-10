package com.ferndani00.NFTAggregator.Endpoints;

import com.ferndani00.NFTAggregator.ApiHttpClient.ApiHttpClient;
import com.ferndani00.NFTAggregator.models.token.TokenResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class NftEndpoint {

    //DAO stands for Data Access Object. DAO Design Pattern is used to separate the data persistence logic in a separate layer.
    // This way, the service remains completely in dark about how the low-level operations to access the database is done.
    // This is known as the principle of Separation of Logic

    //dit is dan niet het juiste gebruik van een dao

    //opensea api voor 1 nft
    public String getNFT(String contractAddress, String tokenId) {

        ApiHttpClient apiHttpClient = new ApiHttpClient();
        Gson gson = new Gson();

        //https://api.opensea.io/api/v2/chain/{chain}/contract/{address}/nfts/{identifier}
        String url = "https://api.opensea.io/api/v2/collections/" + contractAddress + "/nfts/" + tokenId;
        String response = apiHttpClient.getRequest(url);

        return response;
    }

    //collectionSlug is wat bij opensea achter collections/ staat in url
    public String getCollection(String collectionSlug) {
        ApiHttpClient apiHttpClient = new ApiHttpClient();
        Gson gson = new Gson();

        String url = "https://api.opensea.io/api/v2/collections/" + collectionSlug;
        String response = apiHttpClient.getRequest(url);
        return response;
    }

    //reservoir api met voor specifieke een nft
    public TokenResponse getListingData(String tokenContract, String tokenName) {
        ApiHttpClient apiHttpClient = new ApiHttpClient();
        Gson gson = new Gson();

        String rl = "https://api.reservoir.tools/tokens/v7?tokens=" + tokenContract + "%3A" + tokenName;
        String response = apiHttpClient.getListings(rl);
        TokenResponse tokenResponse = gson.fromJson(response, TokenResponse.class);
        return tokenResponse;
    }

    // dezelfde api als hierboven maar hier haal ik alle listed nft's van een collectie op
    public TokenResponse getListingData(String tokenContract) {
        ApiHttpClient apiHttpClient = new ApiHttpClient();
        Gson gson = new Gson();

        //https://api.reservoir.tools/tokens/v7?collection=0x8a90CAb2b38dba80c64b7734e58Ee1dB38B8992e&limit=10000"
        String url = "https://api.reservoir.tools/tokens/v7?collection=" + tokenContract + "&limit=100";
        String response = apiHttpClient.getListings(url);
        TokenResponse tokenResponse = gson.fromJson(response, TokenResponse.class);
        return tokenResponse;
    }


}
