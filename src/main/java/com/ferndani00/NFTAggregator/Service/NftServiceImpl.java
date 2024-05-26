package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.Endpoints.CollectionEndpoint;
import com.ferndani00.NFTAggregator.Endpoints.NftEndpoint;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.helperClasses.NumberRounder;
import com.ferndani00.NFTAggregator.models.collectionResponse.CollectionResponse;
import com.ferndani00.NFTAggregator.models.databaseModels.Nft;
import com.ferndani00.NFTAggregator.models.databaseModels.NftCollection;
import com.ferndani00.NFTAggregator.models.databaseModels.User;
import com.ferndani00.NFTAggregator.models.token.TokenResponse;
import com.ferndani00.NFTAggregator.models.token.TokenWrapper;
import com.ferndani00.NFTAggregator.repository.NftCollectionRepository;
import com.ferndani00.NFTAggregator.repository.NftRepository;
import com.ferndani00.NFTAggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class NftServiceImpl implements NftService {

    @Autowired
    private NftRepository nftRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NftCollectionRepository nftCollectionRepository;

    @Autowired
    private NftEndpoint nftEndPoint;

    @Autowired
    private CollectionEndpoint collectionEndpoint;

    @Override
    public List<NftDto> getAll() {
        return null;
    }

    @Autowired
    private CollectionServiceImpl collectionService;

    @Override
    public void save(NftDto nftDto) {
        Nft nft = mapDtoToModel(nftDto);

        nft.setCollection(nftCollectionRepository.findByAddress(nft.getContractAddress()));

        nftRepository.save(nft);


        if(userRepository.findByEmail(nftDto.getOwner()) != null)
        {
            User user = userRepository.findByEmail(nftDto.getOwner());
            user.getOwnedNfts().add(nft);
            userRepository.save(user);
        } else {
            System.out.println("email not found");
        }


    }

    @Override
    public NftDto getById(long id) {
        return null;
    }

    public NftDto getListedNftData(String contractAddress, String id) {
        TokenResponse response = nftEndPoint.getListingData(contractAddress, id);
        return mapResponseToDto(response);
    }

    public List<NftDto> getListedNfts(String contractAddress) {
        TokenResponse response = nftEndPoint.getListingData(contractAddress);
        return mapToNftDtoList(response);
    }


    //Moet mss via de endpoint worden gedaan om meer data te krijgen
    public NftDto mapModelToDto(Nft nft) {
        NftDto nftDto = new NftDto();
        nftDto.setContractAddress(nft.getContractAddress());
        nftDto.setCollectionName(nftDto.getCollectionName());
        nftDto.setNativePrice(nft.getNftId());
        nftDto.setImageLarge(nft.getImageUrl());
        return nftDto;
    }

    public Nft mapDtoToModel(NftDto nftDto)
    {
        Nft nft = new Nft();
        nft.setTokenId(nftDto.getTokenId());
        nft.setContractAddress(nftDto.getContractAddress());
        nft.setPrice(nftDto.getNativePrice());
        nft.setImageUrl(nftDto.getImageLarge());

        CollectionResponse collectionResponse = collectionEndpoint.getCollection(nftDto.getCollectionId());
        NftCollection nftCollection = collectionService.mapCollectionResponseToModel(collectionResponse);
        nft.setCollection(nftCollection);
        return nft;
    }

    public List<NftDto> mapModelToDtoList(List<Nft> nfts) {
        List<NftDto> nftDtos = new ArrayList<>();
        for (Nft nft : nfts) {
            NftDto nftDto = new NftDto();
            nftDto.setContractAddress(nft.getContractAddress());
            nftDto.setName(nft.getCollection().getName());
            nftDto.setNativePrice(nft.getNftId());
            nftDto.setImageLarge(nft.getImageUrl());
            nftDto.setTokenId(nft.getTokenId());

            nftDtos.add(nftDto);
        }
        return nftDtos;
    }

    public List<Nft> mapDtoToModelList(List<NftDto> nftDos) {
        List<Nft> nfts = new ArrayList<>();
        for (NftDto nftDto : nftDos) {
            Nft nft = new Nft();
            nft.setContractAddress(nftDto.getContractAddress());

            CollectionEndpoint collectionEndpoint = new CollectionEndpoint();
            CollectionResponse collectionResponse = collectionEndpoint.getCollection(nft.getContractAddress());

            CollectionServiceImpl collectionService = new CollectionServiceImpl();
            NftCollection nftCollection = collectionService.mapCollectionResponseToModel(collectionResponse);

            nft.setCollection(nftCollection);
            nft.setPrice(nftDto.getNativePrice());
            nft.setImageUrl(nftDto.getImageLarge());

            nfts.add(nft);
        }
        return nfts;
    }

    public NftDto mapResponseToDto(TokenResponse response) {
        NftDto nftDto = new NftDto();

        TokenWrapper nft = response.getTokens().get(0);

        nftDto.setContractAddress(nft.getToken().getContract());
        nftDto.setTokenId(nft.getToken().getTokenId());
        nftDto.setName(nft.getToken().getName());
        nftDto.setImageSmall(nft.getToken().getImageSmall());
        nftDto.setImageLarge(nft.getToken().getImageLarge());
        nftDto.setKind(nft.getToken().getKind());
        nftDto.setRarityRank(nft.getToken().getRarityRank());
        nftDto.setCollectionId(nft.getToken().getCollection().getId());
        nftDto.setCollectionName(nft.getToken().getCollection().getName());
        nftDto.setOwner(nft.getToken().getOwner());

        //check als de nft te koopt staat, indien niet wordt dit niet geset
        if (nft.getMarket().getFloorAsk().getPrice() != null) {
            nftDto.setPriceSymbol(nft.getMarket().getFloorAsk().getPrice().getCurrency().getSymbol());
            nftDto.setExternalSiteUrl(nft.getMarket().getFloorAsk().getSource().getUrl());

            double nativePrice = NumberRounder.rounder(nft.getMarket().getFloorAsk().getPrice().getAmount().getDecimal());
            double usdPrice = NumberRounder.rounder(nft.getMarket().getFloorAsk().getPrice().getAmount().getUsd());

            nftDto.setNativePrice(nativePrice);
            nftDto.setUsdPrice(usdPrice);
        }

        //eigenaar toevoegen
        String ownerShort = nft.getToken().getOwner().substring(0, 6); //Hier maak ik het adres van de eigenaar korter
        nftDto.setOwnerShort(ownerShort);

        return nftDto;
    }

    //Mapper om de listedTokend/Nfts te mappen in een vereenvoudigde klaqqe
    public List<NftDto> mapToNftDtoList(TokenResponse tokenResponse) {
        List<NftDto> nftDtos = new ArrayList<>();

        for (TokenWrapper tokenWrapper : tokenResponse.getTokens()) {
            NftDto nftDto = new NftDto();
            nftDto.setContractAddress(tokenWrapper.getToken().getContract());
            nftDto.setTokenId(tokenWrapper.getToken().getTokenId());
            nftDto.setName(tokenWrapper.getToken().getName());
            nftDto.setImageSmall(tokenWrapper.getToken().getImageSmall());
            nftDto.setImageLarge(tokenWrapper.getToken().getImageLarge());
            nftDto.setKind(tokenWrapper.getToken().getKind());
            nftDto.setRarityRank(tokenWrapper.getToken().getRarityRank());
            nftDto.setCollectionId(tokenWrapper.getToken().getCollection().getId());
            nftDto.setCollectionName(tokenWrapper.getToken().getCollection().getName());
            nftDto.setOwner(tokenWrapper.getToken().getOwner());

            //check als de nft te koopt staat, indien niet wordt dit niet geset
            if (tokenWrapper.getMarket().getFloorAsk().getPrice() != null) {
                nftDto.setPriceSymbol(tokenWrapper.getMarket().getFloorAsk().getPrice().getCurrency().getSymbol());
                nftDto.setExternalSiteUrl(tokenWrapper.getMarket().getFloorAsk().getSource().getUrl());

                double nativePrice = NumberRounder.rounder(tokenWrapper.getMarket().getFloorAsk().getPrice().getAmount().getDecimal());
                double usdPrice = NumberRounder.rounder(tokenWrapper.getMarket().getFloorAsk().getPrice().getAmount().getUsd());

                nftDto.setNativePrice(nativePrice);
                nftDto.setUsdPrice(usdPrice);
            }

            //eigenaar toevoegen
            //String ownerShort = tokenWrapper.getToken().getOwner().substring(0, 6); //Hier maak ik het adres van de eigenaar korter
            //nftDto.setOwnerShort(ownerShort);

            nftDtos.add(nftDto);
        }
        return nftDtos;
    }
}

