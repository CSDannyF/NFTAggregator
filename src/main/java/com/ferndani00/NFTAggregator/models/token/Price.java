package com.ferndani00.NFTAggregator.models.token;

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
