package com.ferndani00.NFTAggregator.APImodels.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Source {
    private String id;
    private String domain;
    private String name;
    private String icon;
    private String url;
}
