package com.ferndani00.NFTAggregator.repository;

import com.ferndani00.NFTAggregator.models.databaseModels.NftCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NftCollectionRepository extends JpaRepository<NftCollection, Long> {
}
