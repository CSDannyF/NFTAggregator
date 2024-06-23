package com.ferndani00.NFTAggregator.APImodels.token;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenResponse {
    private List<TokenWrapper> tokens;
}
