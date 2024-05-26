package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.CollectionServiceImpl;
import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.UserService;
import com.ferndani00.NFTAggregator.dto.UserDto;
import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private NftServiceImpl nftService;

    @Autowired
    private CollectionServiceImpl collectionService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    private CollectionDto collectionDto;

    @GetMapping("/account")
    public String login(Authentication authentication, Model model) {

        //checked als er een gebruiker is ingelogd, anders wordt hij naar het loginscherm gestuurd
        if(authentication == null)
        {
            return "redirect:/login";
        }

        String email = authentication.getName();
        UserDto userDto = userService.findByEmail(email);

        model.addAttribute("userDto", userDto);
        model.addAttribute("ownedNfts", userDto.getOwnedNfts());
        return "account";
    }
}
