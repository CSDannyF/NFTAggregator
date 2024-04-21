package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dao.CollectionDao;
import com.ferndani00.NFTAggregator.models.trendingCollections.TrendingCollectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class TrendingCollectionResponseServiceImpl {

    @Autowired
    private CollectionDao collectionDao = new CollectionDao();

    //wordt nu enkel gebruikt in een methode van TrendingCollectionResponseServiceImpl die nergens wordt gebruikt.
    public TrendingCollectionResponse getAll(String period, int limit)
    {
        return collectionDao.getTrendingCollections(period, limit);
    }


}
