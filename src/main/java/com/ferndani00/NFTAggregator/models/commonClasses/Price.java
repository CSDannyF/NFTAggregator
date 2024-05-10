package com.ferndani00.NFTAggregator.models.commonClasses;

import com.ferndani00.NFTAggregator.models.commonClasses.Amount;
import com.ferndani00.NFTAggregator.models.commonClasses.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private Currency currency;
    private Amount amount;
}
