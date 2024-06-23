package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftCollectionServiceImpl;
import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.UserService;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/account/balance")
    public String accountBalance(Authentication authentication,
                                 Model model) {
        //checked als er een gebruiker is ingelogd, anders wordt hij naar het loginscherm gestuurd
        if (authentication == null) {
            return "redirect:/login";
        }
        UserDto userDto = userService.getByEmail(authentication.getName());
        double amount = 0.0;

        model.addAttribute("userDto", userDto);
        model.addAttribute("amount", amount);
        return "balance";
    }

    @PostMapping("/account/addToBalance")
    public String addToBalance(@RequestParam("amount") Double amount,
                               Authentication authentication,
                               Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }
        UserDto userDto = userService.getByEmail(authentication.getName());
        userService.changeBalance(amount, userDto);

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

        double totalNativePrice = 0.0;
        double totalUsdPrice = 0.0;

        for (NftDto nftDto : userDto.getNftsInCart()) {
            totalNativePrice += nftDto.getNativePrice();
            totalUsdPrice += nftDto.getUsdPrice();
        }

        // Check balance before update
        if (totalNativePrice > userDto.getBalance()) {
            model.addAttribute("userDto", userDto);
            model.addAttribute("userDto", userDto);
            model.addAttribute("ownedNfts", userDto.getOwnedNfts());
            model.addAttribute("favoriteNfts", userDto.getFavoriteNfts());
            model.addAttribute("nftsInCart", userDto.getNftsInCart());

            model.addAttribute("alertType", "warning");
            model.addAttribute("alertMessage", "Your balance is to low.");
            return "account"; // Redirect to account page with error message
        }

        // Update balance and purchase NFTs if balance is sufficient
        userDto.setBalance(userDto.getBalance() - totalNativePrice);
        nftService.buyNftsInCart(userDto);

        model.addAttribute("userDto", userDto);
        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
        model.addAttribute("favoriteNfts", userDto.getFavoriteNfts());
        model.addAttribute("nftsInCart", userDto.getNftsInCart());

        model.addAttribute("alertType", "succes");
        model.addAttribute("alertMessage", "Congrats on your new Nft's!.");

        return "account"; // Consider redirecting to a success page after purchase
    }

    @GetMapping("/checkout")
    public String checkout(Model model,
                           Authentication authentication,
                           BindingResult result) {
        if (authentication == null) {
            return "redirect:/login";
        }

        UserDto userDto = userService.getByEmail(authentication.getName());

        double totalNativePrice = 0.0;
        double totalUsdPrice = 0.0;

        for (NftDto nftDto : userDto.getNftsInCart()) {
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
        nftService.removeFromCart(nftDto, userDto);

        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
        model.addAttribute("favoriteNfts", userDto.getFavoriteNfts());
        model.addAttribute("nftsInCart", userDto.getNftsInCart());

        return "account";
    }
    @GetMapping("/removeFromFavorites/{contractAddress}/{tokenId}")
    public String removeFromFavorites(@PathVariable String contractAddress,
                                    @PathVariable String tokenId,
                                    Authentication authentication,
                                    Model model) {
        //checked als er een gebruiker is ingelogd, anders wordt hij naar het loginscherm gestuurd
        if (authentication == null) {
            return "redirect:/login";
        }

        NftDto nftDto = nftService.getListedNftData(contractAddress, tokenId);
        UserDto userDto = userService.getByEmail(authentication.getName());
        nftService.removeFromFavorites(nftDto, userDto);

        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
        model.addAttribute("favoriteNfts", userDto.getFavoriteNfts());
        model.addAttribute("nftsInCart", userDto.getNftsInCart());

        return "account";
    }
}
