package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.Endpoints.CollectionEndpoint;
import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import com.ferndani00.NFTAggregator.dto.TrendingCollectionDto;
import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;
import com.ferndani00.NFTAggregator.helperClasses.NumberRounder;
import com.ferndani00.NFTAggregator.models.collectionResponse.Collection;
import com.ferndani00.NFTAggregator.models.collectionResponse.CollectionResponse;
import com.ferndani00.NFTAggregator.models.databaseModels.NftCollection;
import com.ferndani00.NFTAggregator.models.openseaResponse.OpenseaCollection;
import com.ferndani00.NFTAggregator.models.openseaResponse.Root;
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
public class CollectionServiceImpl implements NftCollectionService, CommandLineRunner {

    /*
    Hier in deze klasse zou ik misschien maar op 1 Dto willen werken en niet 2
     */
    //@Autowired
    //private NftServiceImpl nftService;

    @Autowired
    private CollectionEndpoint collectionEndpoint;

    @Autowired
    private NftCollectionRepository nftCollectionRepository;

    // nog niet in gebruik, geeft het api model terug, niet de DTO
    public List<TrendingCollectionDto> getTrendingCollections(String period, int limit)
    {
        TrendingCollectionResponse response = collectionEndpoint.getTrendingCollections(period, limit);

        return mapToTrendingCollectionDto(response);
    }

    public CollectionDto getCollection(String contractAddress)
    {
        CollectionResponse response = collectionEndpoint.getCollection(contractAddress);

        return mapToCollectionDto(response);
    }

    public Root getCollections(String next)
    {
        return collectionEndpoint.getCollections(next);
    }

    public List<NftCollection> mapRootToModel(Root root, List<NftCollection> collections) {

        Set<String> contractAddressesAndNames = new HashSet<>();
        List<OpenseaCollection> apiCollections = root.getCollections();

        List<NftCollection> nftCollections = new ArrayList<>();


        for(NftCollection databaseCollection: collections)
        {
            contractAddressesAndNames.add(databaseCollection.getAddress());
            contractAddressesAndNames.add(databaseCollection.getName());
        }

        for(OpenseaCollection openseaCollection: apiCollections) {
            if(openseaCollection.getContracts().isEmpty()) {
                if(!contractAddressesAndNames.contains(openseaCollection.getName())) {
                    NftCollection nftCollection1 = new NftCollection();
                    nftCollection1.setName(openseaCollection.getName());
                    nftCollection1.setName(openseaCollection.getName());
                    nftCollection1.setSlug(openseaCollection.getName());

                    nftCollections.add(nftCollection1);
                }
            } else {
                if(!contractAddressesAndNames.contains(openseaCollection.getContracts().get(0).getAddress()) || !contractAddressesAndNames.contains(openseaCollection.getName())) {
                    NftCollection nftCollection1 = new NftCollection();
                    nftCollection1.setName(openseaCollection.getName());
                    nftCollection1.setName(openseaCollection.getName());
                    nftCollection1.setSlug(openseaCollection.getName());
                    if(!openseaCollection.getContracts().isEmpty()) {
                        nftCollection1.setAddress(openseaCollection.getContracts().get(0).getAddress());
                    }
                    nftCollections.add(nftCollection1);

                }
            }

        }
        return nftCollections;

        /*
        for(OpenseaCollection collection: root.getCollections())
        {
            //Als er nog niks in de db/lijst die wordt meegegeven staat gaat deze worden uitgevoerd
            if(collections.isEmpty())
            {
                NftCollection nftCollection1 = new NftCollection();
                nftCollection1.setName(collection.getName());
                nftCollection1.setName(collection.getName());
                if(!collection.getContracts().isEmpty()){
                    nftCollection1.setAddress(collection.getContracts().get(0).getAddress());
                }
                nftCollection1.setSlug(collection.getName());

                nftCollections.add(nftCollection1);
                //anders gaat hij ze vergelijken om geen duplicaten te hebben
            } else {
                for(NftCollection nftCollection: collections)
                {
                    if (!Objects.equals(collection.getContracts().get(0).getAddress(), nftCollection.getAddress())) {
                        NftCollection nftCollection1 = new NftCollection();
                        nftCollection1.setName(collection.getName());
                        nftCollection1.setName(collection.getName());
                        nftCollection1.setSlug(collection.getName());
                        if(!collection.getContracts().isEmpty()) {
                            nftCollection1.setAddress(collection.getContracts().get(0).getAddress());
                        }

                        nftCollections.add(nftCollection1);
                        }
                    }
            }
        }
        System.out.println("done");
        return nftCollections;
         */
    }


    //Hier vereenvoudig ik de data van de TrendingCollection API via de mapper.
    public List<TrendingCollectionDto> mapToTrendingCollectionDto(TrendingCollectionResponse response)
    {
        List<TrendingCollectionDto> nftCollectionDtos = new ArrayList<>();

        for(TrendingCollection collection: response.getCollections())
        {
            TrendingCollectionDto trendingCollectionDto = new TrendingCollectionDto();
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

            nftCollectionDtos.add(trendingCollectionDto);
        }
        return nftCollectionDtos;
    }


    //Is hetzelfde als hierboven, enkel geef ik hier 1 collection terug in plaats van een lijst.
    //Kan misschien eenvoudiger via 1 methode
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

    public NftCollection mapCollectionResponseToModel(CollectionResponse collectionResponse)
    {
        NftCollection nftCollection = new NftCollection();

        Collection collection = collectionResponse.getCollections().get(0);

        nftCollection.setName(collection.getName());
        nftCollection.setAddress(collection.getId());
        nftCollection.setSlug(collection.getSlug());
        return nftCollection;
    }

    public NftCollectionDto mapToCollectionDto(NftCollection nftCollection)
    {
        NftCollectionDto nftCollectionDto = new NftCollectionDto();
        nftCollectionDto.setName(nftCollectionDto.getName());
        nftCollectionDto.setContractAddress(nftCollectionDto.getContractAddress());
        nftCollectionDto.setSlug(nftCollection.getSlug());
        //nftCollection.setNfts(nftService.mapDtoToModelList(nftCollectionDto.getnfts?));
        return nftCollectionDto;
    }

    @Override
    public List<CollectionDto> getAll() {
        return null;
    }

    //nu kan er iedereen aan
    @Override
    public List<NftCollection> getAllModel() {
        return nftCollectionRepository.findAll();
    }

    @Override
    public void save(CollectionDto collectionDto) {

    }

    @Override
    public void saveAll(List<NftCollection> nftCollections)
    {

    }

    @Override
    public CollectionDto getById(long id) {
        return null;
    }

    @Override
    public NftCollectionDto getByName(String name) {
        return null;
    }

    public void initializeCollectionsToDb()
    {
        //duurt ongeveer 30-35 seconden..
        long startTime = System.nanoTime();

        String next = "";
        List<NftCollection> collections = new ArrayList<>();
        for(int i = 0; i < 10; i++){
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
        initializeCollectionsToDb();
    }
}
