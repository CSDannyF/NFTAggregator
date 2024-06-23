package com.ferndani00.NFTAggregator.models.databaseModels;

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
    private long nftId;

    @Column(nullable = false)
    private String contractAddress;

    @Column(nullable = false)
    private String tokenId;

    @ManyToOne
    @JoinColumn(name = "collectionId")
    private NftCollection collection;

    @Column
    private double price;

    @Column
    private String priceSymbol;

    @Column(length = 400)
    private String imageUrl;

}
