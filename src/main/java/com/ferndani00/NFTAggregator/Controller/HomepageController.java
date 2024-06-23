package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftCollectionServiceImpl;
import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomepageController {
    @Autowired
    private NftCollectionServiceImpl NftCollectionServiceImpl;

    @GetMapping("/")
    public String homepage(Model model) {
        List<NftCollectionDto> trendingCollections = NftCollectionServiceImpl.getTrendingCollections("24h", 15);

        model.addAttribute("trendingCollections", trendingCollections);
        return "index";
    }
}