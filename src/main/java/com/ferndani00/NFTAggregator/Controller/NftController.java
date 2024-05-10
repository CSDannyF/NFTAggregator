package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Endpoints.CollectionEndpoint;
import com.ferndani00.NFTAggregator.Endpoints.NftEndpoint;
import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.TrendingCollectionServiceImpl;
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
    private NftServiceImpl nftService = new NftServiceImpl();

    @Autowired
    private TrendingCollectionServiceImpl trendingCollectionServiceImpl = new TrendingCollectionServiceImpl();

    private NftEndpoint nftEndpoint = new NftEndpoint();
    private CollectionEndpoint collectionEndpoint = new CollectionEndpoint();


    private List<NftDto> nftDtos;
    private NftDto nftDto;
    private CollectionDto collectionDto;

    @GetMapping("/nftDetail/{contractAddress}/{tokenId}")
    public String nftDetail(@PathVariable String contractAddress,@PathVariable String tokenId, Model model)
    {
        nftDtos = nftService.mapToNftDto(nftEndpoint.getListingData(contractAddress, tokenId));
        nftDto = nftDtos.get(0);

        collectionDto = trendingCollectionServiceImpl.mapToCollectionDto(collectionEndpoint.getCollection(contractAddress));
        model.addAttribute("collection", collectionDto);
        model.addAttribute("nft",nftDto);

        return "nftDetail";
    }
}
