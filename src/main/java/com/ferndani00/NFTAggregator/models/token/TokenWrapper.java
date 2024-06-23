package com.ferndani00.NFTAggregator.models.token;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TokenWrapper {
    private Token token;
    private Market market;
    private Date updatedAt;
}
