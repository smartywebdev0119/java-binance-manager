package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *  The {@code DetailMarginOrder} class is useful to format Binance Margin Cancel Order request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class DetailMarginOrder extends Order {

    private final boolean isIsolated;
    private final String origClientOrderId;
    private final double price;
    private final double origQty;
    private final double executedQty;
    private final double cummulativeQuoteQty;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;
    private final JSONObject jsonObject;

    public DetailMarginOrder(String symbol, double orderId, String clientOrderId, boolean isIsolated, String origClientOrderId, double price,
                             double origQty, double executedQty, double cummulativeQuoteQty, String status, String timeInForce,
                             String type, String side, JSONObject jsonObject) {
        super(symbol, orderId, clientOrderId);
        this.isIsolated = isIsolated;
        this.origClientOrderId = origClientOrderId;
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
        this.jsonObject = jsonObject;
    }

    public boolean getIsIsolated() {
        return isIsolated;
    }

    public String getOrigClientOrderId() {
        return origClientOrderId;
    }

    public double getPrice() {
        return price;
    }

    public double getOrigQty() {
        return origQty;
    }

    public double getExecutedQty() {
        return executedQty;
    }

    public double getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public String getType() {
        return type;
    }

    public String getSide() {
        return side;
    }

    /** Method to get stopPrice
     * any params requirde
     * @return stopPrice as double, if is a null field will return -1
     * **/
    public double getStopPrice(){
        return getDoubleValue("stopPrice");
    }

    /** Method to get icebergQty
     * any params requirde
     * @return icebergQty as double, if is a null field will return -1
     * **/
    public double getIcebergQty(){
        return getDoubleValue("icebergQty");
    }

    /** Method to assemble a DetailSpotOrder object
     * @param #key: field to fetch a double value in {@link #jsonObject}
     * @return an DetailSpotOrder object with response data
     * **/
    private double getDoubleValue(String key){
        try {
            return jsonObject.getDouble(key);
        }catch (JSONException e){
            return -1;
        }
    }

    /** Method to assemble a DetailMarginOrder
     * @param #cancelMarginOrder: obtained from Binance's request
     * retrun DetailMarginOrder object
     * **/
    public static DetailMarginOrder assembleDetailMarginOrderObject(JSONObject cancelMarginOrder){
        return new DetailMarginOrder(cancelMarginOrder.getString("symbol"),
                cancelMarginOrder.getLong("orderId"),
                cancelMarginOrder.getString("clientOrderId"),
                cancelMarginOrder.getBoolean("isIsolated"),
                cancelMarginOrder.getString("origClientOrderId"),
                cancelMarginOrder.getDouble("price"),
                cancelMarginOrder.getDouble("origQty"),
                cancelMarginOrder.getDouble("executedQty"),
                cancelMarginOrder.getDouble("cummulativeQuoteQty"),
                cancelMarginOrder.getString("status"),
                cancelMarginOrder.getString("timeInForce"),
                cancelMarginOrder.getString("type"),
                cancelMarginOrder.getString("side"),
                cancelMarginOrder
        );
    }

}