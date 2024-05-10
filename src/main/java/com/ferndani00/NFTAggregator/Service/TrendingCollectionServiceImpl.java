package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.TrendingCollectionDto;
import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;
import com.ferndani00.NFTAggregator.helperClasses.NumberRounder;
import com.ferndani00.NFTAggregator.models.collectionResponse.Collection;
import com.ferndani00.NFTAggregator.models.collectionResponse.CollectionResponse;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollection;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class TrendingCollectionServiceImpl {

    // nog niet in gebruik, geeft het api model terug, niet de DTO
    public List<TrendingCollection> getTrendingCollections()
    {
        TrendingCollectionResponseServiceImpl trendingCollectionResponseService = new TrendingCollectionResponseServiceImpl();
        TrendingCollectionResponse response = trendingCollectionResponseService.getAll("24h",20); //hard coded, kan mss flexibeler

        List<TrendingCollection> trendingCollection = response.getCollections();
        return trendingCollection;
    }


    //Hier vereenvoudig ik de data van de TrendinCollection API via de mapper.
    public List<TrendingCollectionDto> mapToTrendingCollectionDto(TrendingCollectionResponse response)
    {
        List<TrendingCollectionDto> trendingCollectionDtos = new ArrayList<>();

        for(TrendingCollection collection: response.getCollections())
        {
            com.ferndani00.NFTAggregator.dto.TrendingCollectionDto trendingCollectionDto = new com.ferndani00.NFTAggregator.dto.TrendingCollectionDto();
            trendingCollectionDto.setName(collection.getName());
            trendingCollectionDto.setImage(collection.getImage());
            trendingCollectionDto.setPriceSymbol(collection.getFloorAsk().getPrice().getCurrency().getSymbol());
            trendingCollectionDto.setContractAddress(collection.getId());

            double volumeChange = NumberRounder.rounder(collection.getVolumeChange().getOneDay());
            double collectionVolume = NumberRounder.rounder(collection.getCollectionVolume().getOneDay());
            double floorPrice = NumberRounder.rounder(collection.getFloorAsk().getPrice().getAmount().getDecimal());

            trendingCollectionDto.setOneDayVolumeChange(volumeChange);
            trendingCollectionDto.setOneDayCollectionVolume(collectionVolume);
            trendingCollectionDto.setNativePrice(floorPrice);

            trendingCollectionDtos.add(trendingCollectionDto);
        }
        return trendingCollectionDtos;
    }


    //is hetzelfde als hierboven, enkel geef ik hier 1 collection terug ipv een lijst.
    //kan misschien eenvoudiger via 1 methode
    public CollectionDto mapToCollectionDto(CollectionResponse response) {

        Collection collection = response.getCollections().get(0);

        CollectionDto collectionDto = new CollectionDto();
        collectionDto.setName(collection.getName());
        collectionDto.setContractAddress(collection.getId());
        collectionDto.setSlug(collection.getSlug());
        collectionDto.setSymbol(collection.getSymbol());
        collectionDto.setImage(collection.getImage());
        collectionDto.setBanner(collection.getBanner());
        collectionDto.setTwitterUrl(collection.getTwitterUrl());
        collectionDto.setDescription(collection.getDescription());
        collectionDto.setTokenCount(collection.getTokenCount());
        collectionDto.setOnSaleCount(collection.getOnSaleCount());
        collectionDto.setCreator(collection.getCreator());
        collectionDto.setNativeCurrency(collection.getFloorAsk().getPrice().getCurrency().getSymbol());
        collectionDto.setOwnerCount(collection.getOwnerCount());

        double floorPriceCurrency = NumberRounder.rounder(collection.getFloorAsk().getPrice().getAmount().getDecimal());
        double floorPriceUsd = NumberRounder.rounder(collection.getFloorAsk().getPrice().getAmount().getUsd());
        double totalVolume = NumberRounder.rounder(collection.getVolume().getAllTime());
        double dailyVolume = NumberRounder.rounder(collection.getVolume().getOneDay());

        collectionDto.setFloorPriceInCurrency(floorPriceCurrency);
        collectionDto.setFloorPriceInUsd(floorPriceUsd);
        collectionDto.setTotalVolume(totalVolume);
        collectionDto.setDailyVolume(dailyVolume);

        return collectionDto;
    }
}
