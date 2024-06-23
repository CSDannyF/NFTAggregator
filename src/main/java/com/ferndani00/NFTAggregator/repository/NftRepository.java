package com.ferndani00.NFTAggregator.repository;

import com.ferndani00.NFTAggregator.databaseModels.Nft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NftRepository extends JpaRepository<Nft, Long> {
    Nft findByContractAddressAndTokenId(String address, String tokenId);
}
