package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.TrendingCollectionDto;
import com.ferndani00.NFTAggregator.helperClasses.NumberRounder;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollection;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class TrendingCollectionService {

    // nog niet in gebruik, geeft het api model terug, niet de DTO
    public List<TrendingCollection> getTrendingCollections()
    {
        TrendingCollectionResponseServiceImpl trendingCollectionResponseService = new TrendingCollectionResponseServiceImpl();
        TrendingCollectionResponse response = trendingCollectionResponseService.getAll("24h",20);

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
}
