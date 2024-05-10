package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.CollectionServiceImpl;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NftController {

    @Autowired
    private NftServiceImpl nftService;

    @Autowired
    private CollectionServiceImpl collectionService;

    private List<NftDto> nftDtos;
    private NftDto nftDto;
    private CollectionDto collectionDto;

    @GetMapping("/nftDetail/{contractAddress}/{tokenId}")
    public String nftDetail(@PathVariable String contractAddress,@PathVariable String tokenId, Model model)
    {
        nftDto = nftService.getListedNftData(contractAddress, tokenId);
        collectionDto = collectionService.getCollection(contractAddress);

        model.addAttribute("collection", collectionDto);
        model.addAttribute("nft",nftDto);

        return "nftDetail";
    }
}
