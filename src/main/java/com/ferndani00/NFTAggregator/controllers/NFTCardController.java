package com.ferndani00.NFTAggregator.controllers;

import com.ferndani00.NFTAggregator.models.NFT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NFTCardController {

    private List<NFT> nfts = new ArrayList<>();

    @GetMapping("/nftCard")
    public String showNfts(Model model)
    {
        NFT nft1 = new NFT("1","1","bird", 10.5,"static/images/birdnard.jpeg");
        NFT nft2 = new NFT("2","2","doodle", 10.5,"static/images/doodle.jpeg");
        NFT nft3 = new NFT("3","3","jumo", 10.5,"static/images/kumo.jpeg");
        NFT nft4 = new NFT("4","4","noodle", 10.5,"static/images/noodle.jpeg");
        nfts.add(nft1);
        nfts.add(nft2);
        nfts.add(nft3);
        nfts.add(nft4);
        model.addAttribute("nfts", nfts);
        return "nftCard";
    }
}
