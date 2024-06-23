package com.ferndani00.NFTAggregator.APImodels.commonClasses;

import com.ferndani00.NFTAggregator.APImodels.token.Source;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloorAsk {
    public String id;
    public Price price;
    public String maker;
    public int validFrom;
    public int validUntil;
    public Source source;
}
