package com.ferndani00.NFTAggregator.models.openseaResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenseaCollection {
        private String collection;
        private String name;
        private String description;
        private String image_url;
        private String banner_image_url;
        private String owner;
        private String safelist_status;
        private String category;
        private boolean is_disabled;
        private boolean is_nsfw;
        private boolean trait_offers_enabled;
        private boolean collection_offers_enabled;
        private String opensea_url;
        private String project_url;
        private String wiki_url;
        private String discord_url;
        private String telegram_url;
        private String twitter_username;
        private String instagram_username;
        private List<Contract> contracts;
}
