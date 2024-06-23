package com.ferndani00.NFTAggregator.APImodels.collectionResponse;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Volume {
    @SerializedName("1day")
    private double oneDay;
    @SerializedName("7day")
    private double sevenDay;
    @SerializedName("30day")
    private double thirthyDay;
    private double allTime;
}
