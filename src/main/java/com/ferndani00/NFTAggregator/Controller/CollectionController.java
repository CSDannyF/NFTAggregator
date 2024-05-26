package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.CollectionServiceImpl;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CollectionController {

    @Autowired
    private NftServiceImpl nftService;

    @Autowired
    private CollectionServiceImpl collectionServiceImpl;

    private List<NftDto> nfts;
    private CollectionDto collectionDto;

    @GetMapping("/collection/{contractAddress}")
    public String collection(@PathVariable String contractAddress, Model model)
    {
        collectionDto = collectionServiceImpl.getCollection(contractAddress);
        nfts = nftService.getListedNfts(contractAddress);

        model.addAttribute("collection", collectionDto);
        model.addAttribute("nfts", nfts);
        return "collection";
    }
}
