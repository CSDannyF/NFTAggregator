package com.ferndani00.NFTAggregator.APImodels.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    private String imageOriginal;
    private String imageMimeType;
    private String tokenURI;
}
