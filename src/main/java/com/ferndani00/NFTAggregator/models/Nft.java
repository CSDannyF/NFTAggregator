package com.ferndani00.NFTAggregator.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nft")
public class Nft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String contractAddress;

    private String tokenId;

    private String collection;

    private double price;

    private String imageUrl;

}
