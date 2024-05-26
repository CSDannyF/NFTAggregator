package com.ferndani00.NFTAggregator.models.databaseModels;

import com.ferndani00.NFTAggregator.models.databaseModels.Nft;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "collection")
public class NftCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long collectionId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = true)
    private String address;

    @Column(nullable = false)
    private String slug;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    private List<Nft> nfts;
}
