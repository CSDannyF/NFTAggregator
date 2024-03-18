package com.ferndani00.NFTAggregator.models;

import jakarta.persistence.*;
import java.sql.Blob;

//@Entity
//@Table(name = "nft")
public class NFT {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    //@Column(name = "tokenId")
    private String tokenId;

    //@Column(name = "collection")
    private String collection;

    //@Column(name = "price")
    private double price;

    private String imageurl;

    public NFT(String id, String tokenId, String collection, double price, String imageurl) {
        this.id = id;
        this.tokenId = tokenId;
        this.collection = collection;
        this.price = price;
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
