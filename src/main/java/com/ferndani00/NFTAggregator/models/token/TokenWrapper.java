package com.ferndani00.NFTAggregator.models.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenWrapper {
    private Token token;
    private Market market;
    private Date updatedAt;
}
