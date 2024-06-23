
package com.ferndani00.NFTAggregator.Controller;

import com.ferndani00.NFTAggregator.Service.NftCollectionServiceImpl;
import com.ferndani00.NFTAggregator.dto.NftCollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HeaderController {
    @Autowired
    private NftCollectionServiceImpl collectionService;

    //indien ik zoek op een art blocks collectie loop ik tegen een fout aan omdat alle collecties onder 1 contractAddress staan
    @GetMapping("/search")
    public String autoComplete(@RequestParam("search") String search, Model model) {
        List<NftCollectionDto> nftCollectionDtos = collectionService.getSearchCollections(search);
        model.addAttribute("collectionDto", nftCollectionDtos);
        return "search";
    }
}

