package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel;

import java.util.ArrayList;

/**
 * The {@code OpenOrders} class is useful to format a OpenOrders object
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class OpenOrders {

    private final ArrayList<CancelOrder> cancelSingleOrders;
    private final ArrayList<CancelOrderComposed> cancelOrderComposeds;

    public OpenOrders(ArrayList<CancelOrder> singleOrders, ArrayList<CancelOrderComposed> cancelOrderComposeds) {
        this.cancelSingleOrders = singleOrders;
        this.cancelOrderComposeds = cancelOrderComposeds;
    }

    public ArrayList<CancelOrder> getCancelSingleOrders() {
        return cancelSingleOrders;
    }

    public CancelOrder getCancelSingleOrder(int index){
        return cancelSingleOrders.get(index);
    }

    public ArrayList<CancelOrderComposed> getCancelOrderComposeds() {
        return cancelOrderComposeds;
    }

    public CancelOrderComposed getComposedCancelOrder(int index){
        return cancelOrderComposeds.get(index);
    }

}
