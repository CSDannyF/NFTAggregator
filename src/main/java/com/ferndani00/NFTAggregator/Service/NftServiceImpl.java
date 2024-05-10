package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.Endpoints.NftEndpoint;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.helperClasses.NumberRounder;
import com.ferndani00.NFTAggregator.models.token.TokenResponse;
import com.ferndani00.NFTAggregator.models.token.TokenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class NftServiceImpl implements NftService{

    @Autowired
    private NftEndpoint nftEndPoint = new NftEndpoint();

    //Mapper om de listedTokend/Nfts te mappen in een vereenvoudigde klaqqe
    public List<NftDto> mapToNftDto(TokenResponse tokenResponse)
    {
        List<NftDto> nftDtos = new ArrayList<>();

        for(TokenWrapper tokenWrapper : tokenResponse.getTokens())
        {
            NftDto nftDto = new NftDto();
            nftDto.setContract(tokenWrapper.getToken().getContract());
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
            if(tokenWrapper.getMarket().getFloorAsk().getPrice() != null) {
                nftDto.setPriceSymbol(tokenWrapper.getMarket().getFloorAsk().getPrice().getCurrency().getSymbol());
                nftDto.setExternalSiteUrl(tokenWrapper.getMarket().getFloorAsk().getSource().getUrl());

                double nativePrice = NumberRounder.rounder(tokenWrapper.getMarket().getFloorAsk().getPrice().getAmount().getDecimal());
                double usdPrice = NumberRounder.rounder(tokenWrapper.getMarket().getFloorAsk().getPrice().getAmount().getUsd());

                nftDto.setNativePrice(nativePrice);
                nftDto.setUsdPrice(usdPrice);
            }

            //eigenaar toevoegen
            String ownerShort = tokenWrapper.getToken().getOwner().substring(0,6); //Hier maak ik het adres van de eigenaar korter
            nftDto.setOwnerShort(ownerShort);

            nftDtos.add(nftDto);
        }
        return nftDtos;
    }

    @Override
    public List<NftDto> getAll() {
        return null;
    }

    @Override
    public void save(NftDto nftDto) {

    }

    @Override
    public NftDto getById(long id) {
        return null;
    }
}
