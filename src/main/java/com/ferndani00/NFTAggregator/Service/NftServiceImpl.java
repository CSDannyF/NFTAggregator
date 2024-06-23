package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.Endpoints.CollectionEndpoint;
import com.ferndani00.NFTAggregator.Endpoints.NftEndpoint;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.UserDto;
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
    private NftCollectionServiceImpl collectionService;

    //TODO in nftowned nog de owner setten
    @Override
    public NftDto addToCart(NftDto nftDto, UserDto userDto) {
        Nft nft = mapDtoToModel(nftDto);
        nft.setCollection(nftCollectionRepository.findByAddress(nft.getContractAddress()));
        List<Nft> nftsInCart = userRepository.findByEmail(userDto.getEmail()).getNftsInCart();
        User user = userRepository.findByEmail(userDto.getEmail());
        User newUser = new User();

        List<Nft> nftsinDb = nftRepository.findAll();

        if (!isNftInList(user.getOwnedNfts(), nft)) {

            //check is nft is in nft table and not in cart
            if (isNftInList(nftsinDb, nft) && !isNftInList(nftsInCart, nft)) {
                System.out.println("exists in db and not cart");
                Nft nftFromDb = nftRepository.findByContractAddressAndTokenId(nft.getContractAddress(), nft.getTokenId());
                user.getNftsInCart().add(nftFromDb);
                newUser = userRepository.save(user);
                Nft newCartNft = newUser.getNftsInCart().getLast();
                return mapModelToDto(newCartNft);

                //check if nft is not in nft table and not in cart
            } else if (!isNftInList(nftsinDb, nft) && !isNftInList(nftsInCart, nft)) {
                System.out.println("does not exist in db and not cart");

                user.getNftsInCart().add(nft);
                newUser = userRepository.save(user);
                Nft newCartNft = newUser.getNftsInCart().getLast();
                return mapModelToDto(newCartNft);

                //otherwise already in nft table and in cart
            } else {
                System.out.println("already in db and cart");
                return getByContractAddressAndTokenId(nftDto.getContractAddress(), nftDto.getTokenId());
            }
        } else {
            return nftDto;
        }
    }

    @Override
    public NftDto addToFavorites(NftDto nftDto, UserDto userDto) {
        Nft nft = mapDtoToModel(nftDto);
        nft.setCollection(nftCollectionRepository.findByAddress(nft.getContractAddress()));
        List<Nft> favoritedNfts = userRepository.findByEmail(userDto.getEmail()).getFavoritedNfts();
        User user = userRepository.findByEmail(userDto.getEmail());
        User newUser = new User();

        List<Nft> nftsinDb = nftRepository.findAll();

        //check is nft is in nft table and not as favorite
        if (isNftInList(nftsinDb, nft) && !isNftInList(favoritedNfts, nft)) {
            System.out.println("exists in db and not favorite");
            Nft nftFromDb = nftRepository.findByContractAddressAndTokenId(nft.getContractAddress(), nft.getTokenId());
            user.getFavoritedNfts().add(nftFromDb);
            newUser = userRepository.save(user);
            Nft newfavoriteNft = newUser.getFavoritedNfts().getLast();
            return mapModelToDto(newfavoriteNft);

            //check if nft is not in nft table and not as favorite
        } else if (!isNftInList(nftsinDb, nft) && !isNftInList(favoritedNfts, nft)) {
            System.out.println("does not exist in db and not favorite");

            user.getFavoritedNfts().add(nft);
            newUser = userRepository.save(user);
            Nft newfavoriteNft = newUser.getFavoritedNfts().getLast();
            return mapModelToDto(newfavoriteNft);

            //otherwise already in nft table and as favorite
        } else {
            return getByContractAddressAndTokenId(nftDto.getContractAddress(), nftDto.getTokenId());
        }
    }

    @Override
    public void removeFromCart(NftDto nftDto, UserDto userDto) {

        Nft nft = mapDtoToModel(nftDto);
        User user = userRepository.findByEmail(userDto.getEmail());
        Nft nftToBeRemoved = getNftInList(user.getNftsInCart(), nft);

        user.getNftsInCart().remove(nftToBeRemoved);
        userRepository.save(user);
    }

    @Override
    public void removeFromFavorites(NftDto nftDto, UserDto userDto) {
        Nft nft = mapDtoToModel(nftDto);
        User user = userRepository.findByEmail(userDto.getEmail());
        Nft nftToBeRemoved = getNftInList(user.getFavoritedNfts(), nft);

        user.getFavoritedNfts().remove(nftToBeRemoved);
        User newuser = userRepository.save(user);
    }

    @Override
    public void sellOwnedNft(NftDto nftDto) {
        //nog niet geimplementeerd
    }

    @Override
    public List<NftDto> buyNftsInCart(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        user.getOwnedNfts().addAll(user.getNftsInCart());
        user.getNftsInCart().removeAll(user.getNftsInCart());
        user.setBalance(userDto.getBalance());

        User newUser = userRepository.save(user);
        List<NftDto> newOwnedNft = mapModelToDtoList(newUser.getOwnedNfts());
        return newOwnedNft;
    }

    @Override
    public NftDto getById(long id) {
        return null;
    }

    @Override
    public NftDto getByContractAddressAndTokenId(String contractAddress, String tokenId) {
        Nft nft = new Nft();
        nft = nftRepository.findByContractAddressAndTokenId(contractAddress, tokenId);
        return mapModelToDto(nft);
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

    public Nft mapDtoToModel(NftDto nftDto) {
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
            nftDto.setNativePrice(nft.getPrice());
            nftDto.setImageLarge(nft.getImageUrl());
            nftDto.setTokenId(nft.getTokenId());
            if (nft.getCollection() != null) //waarom is dit null?
            {
                nftDto.setName(nft.getCollection().getName());
            }
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

            NftCollectionServiceImpl collectionService = new NftCollectionServiceImpl();
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

    private Nft getNftInList(List<Nft> nftList, Nft nft) {
        for (Nft nft1 : nftList) {
            if (nft1.getContractAddress().equals(nft.getContractAddress()) && nft1.getTokenId().equals(nft.getTokenId())) {
                nft = nft1;
            }
        }
        return nft;
    }

    //mss enkel degene hierboven gebruiken met isEmpty()
    private boolean isNftInList(List<Nft> nftList, Nft nft) {
        boolean isInList = false;
        for (Nft nft1 : nftList) {
            if (nft1.getContractAddress().equals(nft.getContractAddress()) && nft1.getTokenId().equals(nft.getTokenId()))
                isInList = true;
        }
        return isInList;
    }
}

