package com.tecknobit.binancemanager.Managers.Market;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.BinanceManager;

import java.io.IOException;

/**
 *  The {@code BinanceMarketManager} class is useful to manage all Binance Market Endpoints
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#market-data-endpoints
 * **/

public class BinanceMarketManager extends BinanceManager {

    /** Constructor with an endpoint give by parameter
     * @param #baseEndpoint base endpoint choose from BASE_ENDPOINTS array
     * **/
    public BinanceMarketManager(String baseEndpoint) throws SystemException, IOException {
        super(baseEndpoint);
    }

    /**
     * Constructor with an endpoint give by list auto research
     * **/
    public BinanceMarketManager() throws SystemException, IOException {
        super(null);
    }

}
