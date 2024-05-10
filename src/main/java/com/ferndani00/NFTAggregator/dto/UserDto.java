package com.ferndani00.NFTAggregator.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String walletAddress;
    private double balance;
    private List<NftDto> ownedNfts;
    private List<NftDto> favoriteNfts;
    private List<NftDto> NftsInCart;
}
