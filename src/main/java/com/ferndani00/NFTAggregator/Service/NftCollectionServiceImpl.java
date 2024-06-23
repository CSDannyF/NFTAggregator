package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.Endpoints.CollectionEndpoint;
import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import com.ferndani00.NFTAggregator.helperClasses.NumberRounder;
import com.ferndani00.NFTAggregator.models.collectionResponse.Collection;
import com.ferndani00.NFTAggregator.models.collectionResponse.CollectionResponse;
import com.ferndani00.NFTAggregator.models.databaseModels.NftCollection;
import com.ferndani00.NFTAggregator.models.openseaResponse.OpenseaCollection;
import com.ferndani00.NFTAggregator.models.openseaResponse.Root;
import com.ferndani00.NFTAggregator.models.searchResponse.SearchCollection;
import com.ferndani00.NFTAggregator.models.searchResponse.SearchResponse;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollection;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;
import com.ferndani00.NFTAggregator.repository.NftCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
@Service
public class NftCollectionServiceImpl implements NftCollectionService, CommandLineRunner {

    /*
    Hier in deze klasse zou ik misschien maar op 1 Dto willen werken en niet 2
     */

    @Autowired
    private CollectionEndpoint collectionEndpoint;

    @Autowired
    private NftCollectionRepository nftCollectionRepository;

    // nog niet in gebruik, geeft het api model terug, niet de DTO
    public List<NftCollectionDto> getTrendingCollections(String period, int limit) {
        TrendingCollectionResponse response = collectionEndpoint.getTrendingCollections(period, limit);

        return mapToTrendingCollectionDto(response);
    }

    @Override
    public NftCollectionDto getCollection(String contractAddress) {
        CollectionResponse response = collectionEndpoint.getCollection(contractAddress);

        return mapCollectionResponseToDto(response);
    }

    public List<NftCollection> mapRootToModel(Root root, List<NftCollection> collections) {

        Set<String> contractAddressesAndNames = new HashSet<>();
        List<OpenseaCollection> apiCollections = root.getCollections();

        List<NftCollection> nftCollections = new ArrayList<>();


        for (NftCollection databaseCollection : collections) {
            contractAddressesAndNames.add(databaseCollection.getAddress());
            contractAddressesAndNames.add(databaseCollection.getName());
        }

        for (OpenseaCollection openseaCollection : apiCollections) {
            if (openseaCollection.getContracts().isEmpty()) {
                if (!contractAddressesAndNames.contains(openseaCollection.getName())) {
                    NftCollection nftCollection1 = new NftCollection();
                    nftCollection1.setName(openseaCollection.getName());
                    nftCollection1.setName(openseaCollection.getName());
                    nftCollection1.setSlug(openseaCollection.getName());

                    nftCollections.add(nftCollection1);
                }
            } else {
                if (!contractAddressesAndNames.contains(openseaCollection.getContracts().get(0).getAddress()) || !contractAddressesAndNames.contains(openseaCollection.getName())) {
                    NftCollection nftCollection1 = new NftCollection();
                    nftCollection1.setName(openseaCollection.getName());
                    nftCollection1.setName(openseaCollection.getName());
                    nftCollection1.setSlug(openseaCollection.getName());
                    if (!openseaCollection.getContracts().isEmpty()) {
                        nftCollection1.setAddress(openseaCollection.getContracts().get(0).getAddress());
                    }
                    nftCollections.add(nftCollection1);

                }
            }

        }
        return nftCollections;
    }

    @Override
    public List<NftCollectionDto> getAll() {
        return null;
    }

    //nu kan er iedereen aan
    @Override
    public List<NftCollection> getAllModel() {
        return nftCollectionRepository.findAll();
    }

    @Override
    public void save(NftCollectionDto nftCollectionDto) {

    }

    @Override
    public void saveAll(List<NftCollection> nftCollections) {

    }

    //wordt niet gebruikt?
    @Override
    public List<NftCollectionDto> getCollectionsByName(String name) {
        List<NftCollection> nftCollections = nftCollectionRepository.findByNameContainingIgnoreCase(name);
        List<NftCollectionDto> collectionDtos = new ArrayList<>();
        for (NftCollection nftCollection : nftCollections) {
            collectionDtos.add(mapToCollectionDto(nftCollection));
        }
        return collectionDtos;
    }

    //Gebruikt in header voor de zoekfunctie
    public List<NftCollectionDto> getSearchCollections(String searchInput) {
        //vervangt elke spatie in de search met "%20" om de url te laten kloppen
        String search = searchInput.replaceAll(" ", "%20");
        SearchResponse searchResponse = collectionEndpoint.getSearchResponse(search);
        List<NftCollectionDto> nftCollectionsDto = mapSearchResponseToDto(searchResponse);
        return nftCollectionsDto;
    }

    //Hier vereenvoudig ik de data van de TrendingCollection API via de mapper.
    public List<NftCollectionDto> mapToTrendingCollectionDto(TrendingCollectionResponse response) {
        List<NftCollectionDto> nftCollectionDtos = new ArrayList<>();

        for (TrendingCollection collection : response.getCollections()) {
            NftCollectionDto nftCollectionDto = new NftCollectionDto();
            nftCollectionDto.setName(collection.getName());
            nftCollectionDto.setImage(collection.getImage());

            if (collection.getFloorAsk().getPrice() != null) {
                nftCollectionDto.setNativePriceSymbol(collection.getFloorAsk().getPrice().getCurrency().getSymbol());
                double floorPrice = NumberRounder.rounder(collection.getFloorAsk().getPrice().getAmount().getDecimal());
                nftCollectionDto.setNativeFloorPrice(floorPrice);
            }
            nftCollectionDto.setContractAddress(collection.getId());

            double volumeChange = NumberRounder.rounder(collection.getVolumeChange().getOneDay());
            double collectionVolume = NumberRounder.rounder(collection.getCollectionVolume().getOneDay());

            nftCollectionDto.setDailyVolumeChange(volumeChange);
            nftCollectionDto.setDailyVolume(collectionVolume);

            nftCollectionDtos.add(nftCollectionDto);
        }
        return nftCollectionDtos;
    }

    public NftCollectionDto mapCollectionResponseToDto(CollectionResponse response) {
        Collection collection = response.getCollections().get(0);

        NftCollectionDto nftCollectionDto = new NftCollectionDto();
        nftCollectionDto.setName(collection.getName());
        nftCollectionDto.setContractAddress(collection.getId());
        nftCollectionDto.setSlug(collection.getSlug());
        nftCollectionDto.setSymbol(collection.getSymbol());
        nftCollectionDto.setImage(collection.getImage());
        nftCollectionDto.setBanner(collection.getBanner());
        nftCollectionDto.setTwitterUrl(collection.getTwitterUrl());
        nftCollectionDto.setDescription(collection.getDescription());
        nftCollectionDto.setTokenCount(collection.getTokenCount());
        nftCollectionDto.setOnSaleCount(collection.getOnSaleCount());
        nftCollectionDto.setCreator(collection.getCreator());
        nftCollectionDto.setOwnerCount(collection.getOwnerCount());

        if (collection.getFloorAsk().getPrice() != null) {
            nftCollectionDto.setNativePriceSymbol(collection.getFloorAsk().getPrice().getCurrency().getSymbol());

            double floorPriceUsd = NumberRounder.rounder(collection.getFloorAsk().getPrice().getAmount().getUsd());
            double floorPriceCurrency = NumberRounder.rounder(collection.getFloorAsk().getPrice().getAmount().getDecimal());

            nftCollectionDto.setFloorPriceInUsd(floorPriceUsd);
            nftCollectionDto.setNativeFloorPrice(floorPriceCurrency);
        }

        double allTimeVolume = NumberRounder.rounder(collection.getVolume().getAllTime());
        double dailyVolume = NumberRounder.rounder(collection.getVolume().getOneDay());

        nftCollectionDto.setAllTimeVolume(allTimeVolume);
        nftCollectionDto.setDailyVolume(dailyVolume);

        return nftCollectionDto;
    }

    public NftCollection mapCollectionResponseToModel(CollectionResponse collectionResponse) {
        NftCollection nftCollection = new NftCollection();

        Collection collection = collectionResponse.getCollections().get(0);

        nftCollection.setName(collection.getName());
        nftCollection.setAddress(collection.getId());
        nftCollection.setSlug(collection.getSlug());
        return nftCollection;
    }

    public NftCollectionDto mapToCollectionDto(NftCollection nftCollection) {
        NftCollectionDto nftCollectionDto = new NftCollectionDto();
        nftCollectionDto.setName(nftCollectionDto.getName());
        nftCollectionDto.setContractAddress(nftCollectionDto.getContractAddress());
        nftCollectionDto.setSlug(nftCollection.getSlug());
        //nftCollection.setNfts(nftService.mapDtoToModelList(nftCollectionDto.getnfts?));
        return nftCollectionDto;
    }

    private List<NftCollectionDto> mapSearchResponseToDto(SearchResponse searchResponse) {
        List<NftCollectionDto> nftCollectionDtos = new ArrayList<>();
        for (SearchCollection collection : searchResponse.getCollections()) {
            NftCollectionDto nftCollectionDto = new NftCollectionDto();

            nftCollectionDto.setChainId(collection.getChainId());
            nftCollectionDto.setContractAddress(collection.getContract());
            nftCollectionDto.setName(collection.getName());
            nftCollectionDto.setImage(collection.getImage());
            nftCollectionDto.setAllTimeVolume(collection.getAllTimeVolume());


            if (collection.getFloorAskPrice() != null) {
                double floorPriceUsd = NumberRounder.rounder(collection.getFloorAskPrice().getAmount().getUsd());
                double floorPriceCurrency = NumberRounder.rounder(collection.getFloorAskPrice().getAmount().getDecimal());
                nftCollectionDto.setNativeFloorPrice(floorPriceCurrency);
                nftCollectionDto.setFloorPriceInUsd(floorPriceUsd);
                nftCollectionDto.setNativePriceSymbol(collection.getFloorAskPrice().getCurrency().getSymbol());
            }

            nftCollectionDtos.add(nftCollectionDto);
        }
        return nftCollectionDtos;
    }

    //om Collections aan de DB toe te voegen
    public void initializeCollectionsToDb() {
        //duurt ongeveer 30-35 seconden..
        long startTime = System.nanoTime();

        String next = "";
        List<NftCollection> collections = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Root root = collectionEndpoint.getCollections(next);
            collections = mapRootToModel(root, getAllModel());
            next = root.getNext();
            nftCollectionRepository.saveAll(collections);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        double durationSeconds = duration / 1000000000.0;
        System.out.println("miliseconds: " + duration);
        System.out.println("seconds: " + durationSeconds);

    }

    @Override
    public void run(String... args) throws Exception {
        //initializeCollectionsToDb();
    }
}
