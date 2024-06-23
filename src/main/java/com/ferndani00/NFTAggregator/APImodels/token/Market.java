package com.ferndani00.NFTAggregator.APImodels.token;

import com.ferndani00.NFTAggregator.APImodels.commonClasses.FloorAsk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Market {
    private FloorAsk floorAsk;
}
