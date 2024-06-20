package com.ferndani00.NFTAggregator.repository;

import com.ferndani00.NFTAggregator.models.databaseModels.NftCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NftCollectionRepository extends JpaRepository<NftCollection, Long> {
    NftCollection findByName(String name);
    NftCollection findByAddress(String address);
    List<NftCollection> findByNameContainingIgnoreCase(String name);
}
