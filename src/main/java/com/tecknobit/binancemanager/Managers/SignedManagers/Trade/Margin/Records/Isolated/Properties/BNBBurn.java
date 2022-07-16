package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

/**
 * The {@code BNBBurn} class is useful to format all Binance BNBBurn request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class BNBBurn {

    private boolean spotBNBBurn;
    private boolean interestBNBBurn;

    public BNBBurn(boolean spotBNBBurn, boolean interestBNBBurn) {
        this.spotBNBBurn = spotBNBBurn;
        this.interestBNBBurn = interestBNBBurn;
    }

    public boolean isSpotBNBBurn() {
        return spotBNBBurn;
    }

    public void setSpotBNBBurn(boolean spotBNBBurn) {
        this.spotBNBBurn = spotBNBBurn;
    }

    public boolean isInterestBNBBurn() {
        return interestBNBBurn;
    }

    public void setInterestBNBBurn(boolean interestBNBBurn) {
        this.interestBNBBurn = interestBNBBurn;
    }

}
