package com.ferndani00.NFTAggregator.dto.tokenDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloorAskPriceDto {
    private String id;
    private CurrencyDto currencyDto;
    private AmountDto amountDto;
    private String maker; //mss niet nodig
    private SourceDto source;
    private PriceDto priceDto;
}
