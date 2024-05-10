package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Endpoints.CollectionEndpoint;
import com.ferndani00.NFTAggregator.Service.NftService;
import com.ferndani00.NFTAggregator.Endpoints.NftEndpoint;
import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.TrendingCollectionServiceImpl;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.tokenDtos.CollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CollectionController {

    private NftServiceImpl nftService = new NftServiceImpl();

    private TrendingCollectionServiceImpl trendingCollectionServiceImpl = new TrendingCollectionServiceImpl();

    private NftEndpoint nftEndPoint = new NftEndpoint();

    private CollectionEndpoint collectionEndpoint = new CollectionEndpoint();

    private List<NftDto> nfts;
    private CollectionDto collectionDto;

    @GetMapping("/collection/{contractAddress}")
    public String collection(@PathVariable String contractAddress, Model model)
    {
        collectionDto = trendingCollectionServiceImpl.mapToCollectionDto(collectionEndpoint.getCollection(contractAddress));
        nfts = nftService.mapToNftDto(nftEndPoint.getListingData(contractAddress));
        model.addAttribute("collection", collectionDto);
        model.addAttribute("nfts", nfts);
        return "collection";
    }
}
