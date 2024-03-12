package com.ferndani00.NFTAggregator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NFTCardController {

    @GetMapping("/nftCard")
    public String showCards(Model model)
    {
        //List<NFT> nfts = nftService.getAll();
        //model.addAttribute("nfts", nfts);
        return "nftCard";
    }
}
