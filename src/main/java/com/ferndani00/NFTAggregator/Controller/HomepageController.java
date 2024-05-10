package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.CollectionServiceImpl;
import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import com.ferndani00.NFTAggregator.dto.TrendingCollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomepageController {

    @Autowired
    private CollectionServiceImpl CollectionServiceImpl;

    @GetMapping("/")
    public String homepage(Model model)
    {
        List<TrendingCollectionDto> trendingCollections = CollectionServiceImpl.getTrendingCollections("24h",15);

        model.addAttribute("trendingCollections", trendingCollections);
        return "index";
    }

}