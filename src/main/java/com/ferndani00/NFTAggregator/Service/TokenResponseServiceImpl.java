package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.Endpoints.NftEndpoint;
import com.ferndani00.NFTAggregator.models.token.TokenResponse;
import com.ferndani00.NFTAggregator.models.token.TokenWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class TokenResponseServiceImpl {

    @Autowired
    private NftEndpoint nftEndPoint;

    public TokenResponse getCollectionListings(String contractAdress)
    {
        return nftEndPoint.getListingData(contractAdress);
    }

    public List<TokenWrapper> mapToTokenWrapper()
    {
        return null;
    }
}
