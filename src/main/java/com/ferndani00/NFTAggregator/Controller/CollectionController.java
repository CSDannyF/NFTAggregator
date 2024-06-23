package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.NftCollectionServiceImpl;
import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import com.ferndani00.NFTAggregator.dto.NftDto;
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
    private NftCollectionServiceImpl nftCollectionServiceImpl;

    @GetMapping("/collection/{contractAddress}")
    public String collection(@PathVariable String contractAddress, Model model) {
        NftCollectionDto nftCollectionDto = nftCollectionServiceImpl.getCollection(contractAddress);
        List<NftDto> nfts = nftService.getListedNfts(contractAddress);

        model.addAttribute("collection", nftCollectionDto);
        model.addAttribute("nfts", nfts);
        return "collection";
    }
}
