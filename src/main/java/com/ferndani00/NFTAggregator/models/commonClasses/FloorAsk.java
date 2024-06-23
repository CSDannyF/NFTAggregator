package com.ferndani00.NFTAggregator.models.commonClasses;

import com.ferndani00.NFTAggregator.models.token.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
