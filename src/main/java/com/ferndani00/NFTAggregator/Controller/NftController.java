package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.NftCollectionServiceImpl;
import com.ferndani00.NFTAggregator.Service.UserService;
import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.UserDto;
import com.ferndani00.NFTAggregator.models.databaseModels.Nft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NftController {
    @Autowired
    private NftServiceImpl nftService;

    @Autowired
    private NftCollectionServiceImpl collectionService;

    @Autowired
    private UserService userService;

    private List<NftDto> nftDtos;
    private NftCollectionDto nftCollectionDto;

    @GetMapping("/nftDetail/{contractAddress}/{tokenId}")
    public String nftDetail(@PathVariable String contractAddress,
                            @PathVariable String tokenId,
                            Model model) {
        NftDto nftDto = nftService.getListedNftData(contractAddress, tokenId);
        nftCollectionDto = collectionService.getCollection(contractAddress);

        model.addAttribute("collection", nftCollectionDto);
        model.addAttribute("nft", nftDto);

        return "nftDetail";
    }

    @PostMapping("/addToCart/{contractAddress}/{tokenId}")
    public String addToCart(@PathVariable String contractAddress,
                            @PathVariable String tokenId,
                            Authentication authentication,
                            Model model) {
        // Check if a user is logged in, otherwise redirect to the login page
        if (authentication == null) {
            return "redirect:/login";
        }

        NftDto nftDto = nftService.getListedNftData(contractAddress, tokenId);
        NftCollectionDto nftCollectionDto = collectionService.getCollection(contractAddress);
        UserDto userDto = userService.getByEmail(authentication.getName());

        model.addAttribute("collection", nftCollectionDto);
        model.addAttribute("nft", nftDto);
        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());

        for (NftDto nft1 : userDto.getOwnedNfts()) {
            if (nft1.getContractAddress().equals(nftDto.getContractAddress()) && nft1.getTokenId().equals(nftDto.getTokenId())) {

                model.addAttribute("alertType", "info");
                model.addAttribute("alertMessage", "You already own this NFT.");
                return "account";
            }
        }

        /*
        model.addAttribute("collection", nftCollectionDto);
        model.addAttribute("nft", nftDto);
        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
         */

        nftService.addToCart(nftDto, userDto);

        model.addAttribute("alertType", "success");
        model.addAttribute("alertMessage", "NFT added to cart successfully!");

        return "account";
    }


    //addtofavorites is oke dnek ik
    @PostMapping("/addToFavorites/{contractAddress}/{tokenId}")
    public String addToFavorites(@PathVariable String contractAddress,
                                 @PathVariable String tokenId,
                                 Authentication authentication,
                                 Model model) {
        //checked als er een gebruiker is ingelogd, anders wordt hij naar het loginscherm gestuurd
        if (authentication == null) {
            return "redirect:/login";
        }
        NftDto nftDto = nftService.getListedNftData(contractAddress, tokenId);
        nftCollectionDto = collectionService.getCollection(contractAddress);

        model.addAttribute("collection", nftCollectionDto);
        model.addAttribute("nft", nftDto);
        nftDto = nftService.getListedNftData(contractAddress, tokenId);
        nftCollectionDto = collectionService.getCollection(contractAddress);

        model.addAttribute("collection", nftCollectionDto);
        model.addAttribute("nft", nftDto);

        UserDto userDto = userService.getByEmail(authentication.getName());
        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
        model.addAttribute("favoriteNfts", userDto.getFavoriteNfts());
        model.addAttribute("nftsInCart", userDto.getNftsInCart());

        //hier geef ik mee welke user de nft als favorite wilt
        nftDto.setOwner(authentication.getName());
        nftService.addToFavorites(nftDto, userDto);

        return "account";
    }
}
