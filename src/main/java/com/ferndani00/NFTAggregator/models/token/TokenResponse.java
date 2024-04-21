package com.ferndani00.NFTAggregator.models.token;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenResponse {
    private List<TokenWrapper> tokens;
}
