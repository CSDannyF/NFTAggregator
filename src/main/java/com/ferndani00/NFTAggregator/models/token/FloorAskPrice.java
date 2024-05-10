package com.ferndani00.NFTAggregator.models.token;

import com.ferndani00.NFTAggregator.models.commonClasses.Amount;
import com.ferndani00.NFTAggregator.models.commonClasses.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloorAskPrice {
    private Currency currency;
    private Amount amount;
}
