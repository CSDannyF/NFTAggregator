package com.ferndani00.NFTAggregator.dto.tokenDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto {
    private CurrencyDto currencyDto;
    private AmountDto amountDto;
}
