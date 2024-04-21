package com.ferndani00.NFTAggregator.models.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amount {
    private String raw;
    private double decimal;
    private double usd;
    //private double native;
}
