package com.ferndani00.NFTAggregator.models.token;

import com.ferndani00.NFTAggregator.models.commonClasses.FloorAsk;
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
