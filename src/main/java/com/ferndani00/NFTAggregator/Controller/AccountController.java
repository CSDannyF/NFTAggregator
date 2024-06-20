package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftCollectionServiceImpl;
import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.UserService;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.UserDto;
import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private NftServiceImpl nftService;

    @Autowired
    private NftCollectionServiceImpl collectionService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    private CollectionDto collectionDto;

    @GetMapping("/account")
    public String login(Authentication authentication, Model model) {
        //checked als er een gebruiker is ingelogd, anders wordt hij naar het loginscherm gestuurd
        if (authentication == null) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        UserDto userDto = userService.getByEmail(email);

        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
        model.addAttribute("favoriteNfts", userDto.getFavoriteNfts());
        model.addAttribute("nftsInCart", userDto.getNftsInCart());

        return "account";
    }

    @PostMapping("/buyNftsInCart")
    public String buyNftsInCart(Authentication authentication,
                                Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        UserDto userDto = userService.getByEmail(authentication.getName());

        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
        model.addAttribute("favoriteNfts", userDto.getFavoriteNfts());
        model.addAttribute("nftsInCart", userDto.getNftsInCart());

        nftService.buyNftsInCart(userDto);
        return "redirect:/account";
    }

    @GetMapping("/checkout")
    public String checkout(Model model,
                           Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        UserDto userDto = userService.getByEmail(authentication.getName());
        double totalNativePrice = 0.0;
        double totalUsdPrice = 0.0;

        for (NftDto nftDto: userDto.getNftsInCart()) {
            totalNativePrice += nftDto.getNativePrice();
            totalUsdPrice += nftDto.getUsdPrice();
        }

        model.addAttribute("user", userDto);
        model.addAttribute("nftsInCart", userDto.getNftsInCart());
        model.addAttribute("totalNativePrice", totalNativePrice);
        model.addAttribute("totalUsdPrice", totalUsdPrice);

        return "checkout";
    }

    @GetMapping("/removeFromCart/{contractAddress}/{tokenId}")
    public String removeFromCart(@PathVariable String contractAddress,
                                 @PathVariable String tokenId,
                                 Authentication authentication,
                                 Model model) {
        //checked als er een gebruiker is ingelogd, anders wordt hij naar het loginscherm gestuurd
        if (authentication == null) {
            return "redirect:/login";
        }

        NftDto nftDto = nftService.getListedNftData(contractAddress, tokenId);
        UserDto userDto = userService.getByEmail(authentication.getName());

        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
        model.addAttribute("favoriteNfts", userDto.getFavoriteNfts());
        model.addAttribute("nftsInCart", userDto.getNftsInCart());

        nftService.removeFromCart(nftDto, userDto);
        return "account";
    }
}
