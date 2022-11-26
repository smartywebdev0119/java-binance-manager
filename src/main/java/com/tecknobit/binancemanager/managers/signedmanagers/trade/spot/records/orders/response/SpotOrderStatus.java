package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code ResultSpotOrder} class is useful to format a {@code "Binance"}'s spot order status details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * New Order (TRADE)</a>
 * @see Order
 * @see SpotOrder
 * @see ResultSpotOrder
 **/
public class SpotOrderStatus extends ResultSpotOrder {

    /**
     * {@code stopPrice} is instance that memorizes stop price value
     * **/
    private final double stopPrice;

    /**
     * {@code icebergQty} is instance that memorizes iceberg quantity value
     * **/
    private final double icebergQty;

    /**
     * {@code time} is instance that memorizes time value
     * **/
    private final long time;

    /**
     * {@code isWorking} is instance that memorizes if is working
     * **/
    private final boolean isWorking;

    /**
     * {@code origQuoteOrderQty} is instance that memorizes origin quote quantity value
     * **/
    private final double origQuoteOrderQty;

    /** Constructor to init {@link SpotOrderStatus} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId: list order identifier
     * @param updateTime: update time
     * @param price: price in order
     * @param origQty: origin quantity in order
     * @param executedQty: executed quantity in order
     * @param cummulativeQuoteQty: cummulative quote quantity
     * @param status: status of the order
     * @param timeInForce: time in force of the order
     * @param type: type of the order
     * @param side: side of the order
     * **/
    public SpotOrderStatus(String symbol, long orderId, long orderListId, String clientOrderId, double price, double origQty,
                           double executedQty, double cummulativeQuoteQty, Status status, TimeInForce timeInForce,
                           OrderType type, Side side, double stopPrice, double icebergQty, long time, long updateTime,
                           boolean isWorking, double origQuoteOrderQty) {
        super(symbol, orderId, orderListId, clientOrderId, updateTime, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        this.stopPrice = stopPrice;
        this.icebergQty = icebergQty;
        this.time = time;
        this.isWorking = isWorking;
        this.origQuoteOrderQty = origQuoteOrderQty;
    }

    /**
     * Constructor to init {@link SpotOrderStatus} object
     *
     * @param spotOrderStatus: spot order status details as {@link JSONObject}
     **/
    public SpotOrderStatus(JSONObject spotOrderStatus) {
        super(spotOrderStatus);
        stopPrice = spotOrderStatus.getDouble("stopPrice");
        icebergQty = spotOrderStatus.getDouble("icebergQty");
        time = spotOrderStatus.getLong("time");
        isWorking = spotOrderStatus.getBoolean("isWorking");
        origQuoteOrderQty = spotOrderStatus.getDouble("origQuoteOrderQty");
        transactTime = hOrder.getLong("updateTime");
    }

    /**
     * Method to get {@link #stopPrice} instance <br>
     * Any params required
     *
     * @return {@link #stopPrice} instance as double
     **/
    public double getStopPrice() {
        return stopPrice;
    }

    /**
     * Method to get {@link #stopPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #stopPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getStopPrice(int decimals) {
        return roundValue(stopPrice, decimals);
    }

    /**
     * Method to get {@link #stopPrice} instance <br>
     * Any params required
     *
     * @return {@link #stopPrice} instance as double
     **/
    public double getIcebergQty() {
        return icebergQty;
    }

    /**
     * Method to get {@link #icebergQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #icebergQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIcebergQty(int decimals) {
        return roundValue(icebergQty, decimals);
    }

    /**
     * Method to get {@link #stopPrice} instance <br>
     * Any params required
     *
     * @return {@link #stopPrice} instance as long
     **/
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * Any params required
     *
     * @return {@link #time} instance as {@link Date}
     **/
    public Date getDateTime() {
        return TimeFormatter.getDate(time);
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * Any params required
     *
     * @return {@link #transactTime} instance as long
     **/
    public long getUpdateTime() {
        return transactTime;
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * Any params required
     *
     * @return {@link #transactTime} instance as {@link Date}
     **/
    public Date getUpdateDate() {
        return TimeFormatter.getDate(transactTime);
    }

    /**
     * Method to get {@link #isWorking} instance <br>
     * Any params required
     *
     * @return {@link #isWorking} instance as boolean
     **/
    public boolean isWorking() {
        return isWorking;
    }

    /**
     * Method to get {@link #origQuoteOrderQty} instance <br>
     * Any params required
     *
     * @return {@link #origQuoteOrderQty} instance as double
     **/
    public double getOrigQuoteOrderQty() {
        return origQuoteOrderQty;
    }

    /**
     * Method to get {@link #origQuoteOrderQty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #origQuoteOrderQty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getOrigQuoteOrderQty(int decimals) {
        return roundValue(origQuoteOrderQty, decimals);
    }

}
