package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.UserDto;

import java.util.List;

public interface NftService {
    List<NftDto> getAll();

    NftDto addToCart(NftDto nftDto, UserDto userDto);

    NftDto addToFavorites(NftDto nftDto, UserDto userDto);

    void removeFromCart(NftDto nftDto, UserDto userDto);

    void deleteFromFavorites(NftDto nftDto, UserDto userDto);

    void sellOwnedNft(NftDto nftDto);

    List<NftDto> buyNftsInCart(UserDto userDto);

    NftDto getById(long id);

    NftDto getByContractAddressAndTokenId(String contractAddress, String tokenId);
}
