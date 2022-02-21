package com.tecknobit.binancemanager.Managers.Market;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.BinanceManager;
import com.tecknobit.binancemanager.Managers.Market.Records.ExchangeInformation;
import com.tecknobit.binancemanager.Managers.Market.Records.OrderBook;
import com.tecknobit.binancemanager.Managers.Market.Records.RecentTrade;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.GET_METHOD;

/**
 * The {@code BinanceMarketManager} class is useful to manage all Binance Market Endpoints
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#market-data-endpoints
 * @author N7ghtm4r3 - Tecknobit
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

    public boolean isMarketServiceWork() throws IOException {
        return getRequestResponse(TEST_CONNECTIVITY_ENDPOINT,"",GET_METHOD).equals("{}");
    }

    public String getExchangeInformation() throws IOException {
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT,"",GET_METHOD);
    }

    public JSONObject getJSONExchangeInformation() throws IOException {
        return new JSONObject(getExchangeInformation());
    }

    public ExchangeInformation getObjectExchangeInformation() throws IOException {
        jsonObject = new JSONObject(getExchangeInformation());
        return new ExchangeInformation(jsonObject.getString("timezone"),
                jsonObject.getLong("serverTime"),
                jsonObject);
    }

    public String getExchangeInformation(String symbol) throws Exception {
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    public JSONObject getJSONExchangeInformation(String symbol) throws Exception {
        return new JSONObject(getExchangeInformation(symbol));
    }

    public ExchangeInformation getObjectExchangeInformation(String symbol) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbol)));
    }

    public String getExchangeInformation(ArrayList<String> symbols) throws Exception {
        StringBuilder params = new StringBuilder();
        for (String symbol : symbols)
            params.append("%22").append(symbol).append("%22,");
        params.replace(params.length()-1,params.length(),"");
        return getRequestResponse(EXCHANGE_INFORMATION_ENDPOINT,"?symbols=["+ params +"]",GET_METHOD);
    }

    public JSONObject getJSONExchangeInformation(ArrayList<String> symbols) throws Exception {
        return new JSONObject(getExchangeInformation(symbols));
    }

    public ExchangeInformation getObjectExchangeInformation(ArrayList<String> symbols) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbols)));
    }

    public String getExchangeInformation(String[] symbols) throws Exception {
       return getExchangeInformation(new ArrayList<>(Arrays.asList(symbols)));
    }

    public JSONObject getJSONExchangeInformation(String[] symbols) throws Exception {
        return new JSONObject(getExchangeInformation(symbols));
    }

    public ExchangeInformation getObjectExchangeInformation(String[] symbols) throws Exception {
        return getObjectExchangeInformation(new JSONObject(getExchangeInformation(symbols)));
    }

    private ExchangeInformation getObjectExchangeInformation(JSONObject jsonObject){
        return new ExchangeInformation(jsonObject.getString("timezone"),
                jsonObject.getLong("serverTime"),
                jsonObject);
    }

    public String getOrderBook(String symbol) throws IOException {
        return getRequestResponse(ORDER_BOOK_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    public JSONObject getJSONOrderBook(String symbol) throws IOException {
        return new JSONObject(getOrderBook(symbol));
    }

    public OrderBook getObjectOrderBook(String symbol) throws IOException {
        jsonObject = new JSONObject(getOrderBook(symbol));
        return new OrderBook(jsonObject.getLong("lastUpdateId"),jsonObject,
                symbol);
    }

    public String getOrderBook(String symbol, int limit) throws IOException {
        return getRequestResponse(ORDER_BOOK_ENDPOINT,"?symbol="+symbol+"&limit="+limit,GET_METHOD);
    }

    public JSONObject getJSONOrderBook(String symbol, int limit) throws IOException {
        return new JSONObject(getOrderBook(symbol,limit));
    }

    public OrderBook getObjectOrderBook(String symbol, int limit) throws IOException {
        jsonObject = new JSONObject(getOrderBook(symbol, limit));
        return new OrderBook(jsonObject.getLong("lastUpdateId"),jsonObject,
                symbol);
    }

    public String getRecentTrade(String symbol) throws IOException {
        return getRequestResponse(RECENT_TRADE_LIST_ENPOINT,"?symbol="+symbol,GET_METHOD);
    }

    public JSONArray getJSONRecentTrade(String symbol) throws IOException {
        return new JSONArray(getRecentTrade(symbol));
    }

    public ArrayList<RecentTrade> getRecentTradeList(String symbol) throws IOException {
        return getRecentTradeList(new JSONArray(getRecentTrade(symbol)));
    }

    public String getRecentTrade(String symbol, int limit) throws IOException {
        return getRequestResponse(RECENT_TRADE_LIST_ENPOINT,"?symbol="+symbol+"&limit="+limit,GET_METHOD);
    }

    public JSONArray getJSONRecentTrade(String symbol, int limit) throws IOException {
        return new JSONArray(getRecentTrade(symbol,limit));
    }

    public ArrayList<RecentTrade> getRecentTradeList(String symbol, int limit) throws IOException {
        return getRecentTradeList(new JSONArray(getRecentTrade(symbol,limit)));
    }

    private ArrayList<RecentTrade> getRecentTradeList(JSONArray jsonArray) {
        ArrayList<RecentTrade> recentTrades = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject recentTrade = jsonArray.getJSONObject(j);
            recentTrades.add(new RecentTrade(recentTrade.getLong("id"),
                    recentTrade.getDouble("price"),
                    recentTrade.getDouble("qty"),
                    recentTrade.getDouble("quoteQty"),
                    recentTrade.getLong("time"),
                    recentTrade.getBoolean("isBuyerMaker"),
                    recentTrade.getBoolean("isBestMatch")
            ));
        }
        return recentTrades;
    }

}
