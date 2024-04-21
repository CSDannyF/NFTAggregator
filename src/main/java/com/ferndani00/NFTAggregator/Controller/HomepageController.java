package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftServiceImpl;
import com.ferndani00.NFTAggregator.Service.TrendingCollectionResponseServiceImpl;
import com.ferndani00.NFTAggregator.Service.TrendingCollectionService;
import com.ferndani00.NFTAggregator.dao.CollectionDao;
import com.ferndani00.NFTAggregator.dao.NftDao;
import com.ferndani00.NFTAggregator.dto.NftDto;
import com.ferndani00.NFTAggregator.dto.TrendingCollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomepageController {

    @Autowired
    private TrendingCollectionResponseServiceImpl trendingCollectionResponseService = new TrendingCollectionResponseServiceImpl();

    @Autowired
    private CollectionDao collectionDao = new CollectionDao();

    @Autowired
    private TrendingCollectionService trendingCollectionService;


    @Autowired
    private NftDao nftDao = new NftDao();

    @Autowired
    private NftServiceImpl nftService = new NftServiceImpl();

    private List<NftDto> nfts;

    private List<TrendingCollectionDto> trendingCollectionDtos;


    //moet nog modulair gemaakt worden + nfts horen hier niet, moeten in een andere html, enkel voor te testen dient dit
    @GetMapping("/")
    public String homepage(Model model)
    {
        trendingCollectionDtos = trendingCollectionService.mapToTrendingCollectionDto(collectionDao.getTrendingCollections("24h", 15));
        nfts = nftService.mapToNftDto(nftDao.getListingData("0x8a90CAb2b38dba80c64b7734e58Ee1dB38B8992e"));

        model.addAttribute("trendingCollectionsDtos", trendingCollectionDtos);
        model.addAttribute("nfts", nfts);
        return "index";
    }

}