package com.ferndani00.NFTAggregator.databaseModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column
    private String walletAddress;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private double balance;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "ownedNfts",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "nftId")}
    )
    private List<Nft> ownedNfts;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "favoritedNfts",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "nftId")}
    )
    private List<Nft> FavoritedNfts;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "cart",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "nftId")}
    )
    private List<Nft> nftsInCart;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "userRoles",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<Role> roles = new ArrayList<>();
}
