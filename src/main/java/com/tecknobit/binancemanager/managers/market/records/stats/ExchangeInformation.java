package com.tecknobit.binancemanager.managers.market.records.stats;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.Filter.FilterType;
import static com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.Filter.FilterType.valueOf;

/**
 * The {@code ExchangeInformation} class is useful to format the exchange information of {@code "Binance"}
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
 * Exchange Information</a>
 */
public class ExchangeInformation {

    /**
     * {@code timezone} is instance that contains timezone information
     */
    private final String timezone;

    /**
     * {@code serverTime} is instance that contains server time information
     */
    private final long serverTime;

    /**
     * {@code jsonInformation} is instance that contains exchange information in JSON format
     */
    private final JsonHelper hInfo;

    /**
     * {@code rateLimits} is instance that contains rate limits list
     */
    private ArrayList<RateLimit> rateLimits;

    /**
     * {@code exchangeFilters} is instance that contains filters list
     */
    private final ArrayList<Filter> exchangeFilters;

    /**
     * {@code symbols} is instance that contains symbols list
     */
    private ArrayList<Symbol> symbols;

    /** Constructor to init {@link ExchangeInformation} object
     * @param timezone: timezone information
     * @param serverTime: server time information
     * @param rateLimits: rate limits list
     * @param exchangeFilters: filters list
     * @param symbols: symbols list
     */
    public ExchangeInformation(String timezone, long serverTime, ArrayList<RateLimit> rateLimits,
                               ArrayList<Filter> exchangeFilters, ArrayList<Symbol> symbols) {
        this.timezone = timezone;
        this.serverTime = serverTime;
        this.rateLimits = rateLimits;
        this.exchangeFilters = exchangeFilters;
        this.symbols = symbols;
        hInfo = null;
    }

    /**
     * Constructor to init {@link ExchangeInformation} object
     *
     * @param exchangeInfo: exchange details as {@link JSONObject}
     */
    public ExchangeInformation(JSONObject exchangeInfo) {
        hInfo = new JsonHelper(exchangeInfo);
        timezone = exchangeInfo.getString("timezone");
        serverTime = exchangeInfo.getLong("serverTime");
        exchangeFilters = returnFilters(hInfo.getJSONArray("exchangeFilters", new JSONArray()));
        assembleRateLimits();
        assembleSymbols();
    }

    /**
     * Method to assemble a Filters list
     *
     * @param jsonFilters: obtained from {@code "Binance"} request
     * @return filters list as {@link ArrayList} of {@link Filter} custom object
     */
    @Returner
    private static ArrayList<Filter> returnFilters(JSONArray jsonFilters) {
        ArrayList<Filter> filters = new ArrayList<>();
        for (int j = 0; j < jsonFilters.length(); j++) {
            JSONObject filter = jsonFilters.getJSONObject(j);
            ArrayList<String> filterKeys = new ArrayList<>(filter.keySet());
            ArrayList<Object> filterValues = new ArrayList<>();
            for (String filterKey : filterKeys)
                filterValues.add(filter.get(filterKey));
            filters.add(new Filter(filterKeys, filterValues, valueOf(filter.getString("filterType"))));
        }
        return filters;
    }

    /**
     * Method to assemble an RateLimits list <br>
     * No-any params required
     */
    private void assembleRateLimits() {
        rateLimits = new ArrayList<>();
        JSONArray jsonRateLimits = hInfo.getJSONArray("rateLimits", new JSONArray());
        for (int j = 0; j < jsonRateLimits.length(); j++)
            rateLimits.add(new RateLimit(jsonRateLimits.getJSONObject(j)));
    }

    /**
     * Method to assemble a Symbols list <br>
     * No-any params required
     */
    private void assembleSymbols() {
        symbols = new ArrayList<>();
        JSONArray jsonSymbols = hInfo.getJSONArray("symbols", new JSONArray());
        for (int j = 0; j < jsonSymbols.length(); j++)
            symbols.add(new Symbol(jsonSymbols.getJSONObject(j)));
    }

    /**
     * Method to get {@link #timezone} instance <br>
     * No-any params required
     *
     * @return {@link #timezone} instance as {@link String}
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Method to get {@link #serverTime} instance <br>
     * No-any params required
     *
     * @return {@link #serverTime} instance as long
     */
    public long getServerTime() {
        return serverTime;
    }

    /**
     * Method to get {@link #rateLimits} instance <br>
     * No-any params required
     *
     * @return {@link #rateLimits} instance as {@link ArrayList} of {@link RateLimit}
     */
    public ArrayList<RateLimit> getRateLimits() {
        return rateLimits;
    }

    /**
     * Method to get {@link #exchangeFilters} instance <br>
     * No-any params required
     *
     * @return {@link #exchangeFilters} instance as {@link ArrayList} of {@link Filter}
     */
    public ArrayList<Filter> getExchangeFilters() {
        return exchangeFilters;
    }

    /**
     * Method to get {@link #symbols} list filtered <br>
     *
     * @param symbols: filter symbols to fetch
     * @return {@link #symbols} list filtered as {@link ArrayList} of {@link Symbol} if the symbols requested are
     * multiple, if is a single one will be returned as {@link Symbol}
     */
    public <T> T getSymbols(String... symbols) {
        if (symbols != null && symbols.length > 0) {
            ArrayList<Symbol> fSymbols = new ArrayList<>();
            ArrayList<String> lSymbols = new ArrayList<>(Arrays.stream(symbols).toList());
            for (Symbol symbol : this.symbols)
                if (lSymbols.contains(symbol.getSymbol()))
                    fSymbols.add(symbol);
            int fSize = fSymbols.size();
            if (fSize > 0) {
                if (fSize > 1)
                    return (T) fSymbols;
                else
                    return (T) fSymbols.get(0);
            }
        }
        return null;
    }

    /**
     * Method to get {@link #symbols} instance <br>
     * No-any params required
     *
     * @return {@link #symbols} instance as {@link ArrayList} of {@link Symbol}
     */
    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * The {@code RateLimit} class is useful to format the rate limit
     *
     * @author N7ghtm4r3 - Tecknobit
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     * Exchange Information</a>
     */
    public static class RateLimit {

        /**
         * {@code interval} is instance that contains interval for rate limit
         */
        private final RateLimitInterval interval;
        /**
         * {@code rateLimitType} is instance that contains rate limit type
         */
        private final RateLimitType rateLimitType;

        /**
         * {@code intervalNum} is instance that contains interval number for rate limit
         */
        private final int intervalNum;

        /**
         * {@code limit} is instance that contains limit number for rate
         */
        private final int limit;

        /** Constructor to init {@link RateLimit} object
         * @param intervalNum: interval number for rate limit
         * @param limit: limit number for rate
         * @param interval: interval for rate limit
         * @param rateLimitType: rate limit type
         */
        public RateLimit(int intervalNum, int limit, RateLimitInterval interval, RateLimitType rateLimitType) {
            this.intervalNum = intervalNum;
            this.limit = limit;
            this.interval = interval;
            this.rateLimitType = rateLimitType;
        }

        /**
         * Constructor to init {@link RateLimit} object
         *
         * @param rateLimit: rate limit details as {@link JSONObject}
         */
        public RateLimit(JSONObject rateLimit) {
            intervalNum = rateLimit.getInt("intervalNum");
            limit = rateLimit.getInt("limit");
            interval = RateLimitInterval.valueOf(rateLimit.getString("interval"));
            rateLimitType = RateLimitType.valueOf(rateLimit.getString("rateLimitType"));
        }

        /**
         * Method to get {@link #intervalNum} instance <br>
         * No-any params required
         *
         * @return {@link #intervalNum} instance as int
         */
        public int getIntervalNum() {
            return intervalNum;
        }

        /**
         * Method to get {@link #limit} instance <br>
         * No-any params required
         *
         * @return {@link #limit} instance as int
         */
        public int getLimit() {
            return limit;
        }

        /**
         * Method to get {@link #interval} instance <br>
         * No-any params required
         *
         * @return {@link #interval} instance as {@link RateLimitInterval}
         */
        public RateLimitInterval getInterval() {
            return interval;
        }

        /**
         * Method to get {@link #rateLimitType} instance <br>
         * No-any params required
         *
         * @return {@link #rateLimitType} instance as {@link RateLimitType}
         */
        public RateLimitType getRateLimitType() {
            return rateLimitType;
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

        /**
         * {@code RateLimitInterval} list of available intervals types
         */
        public enum RateLimitInterval {

            /**
             * {@code "SECOND"} interval type
             */
            SECOND,

            /**
             * {@code "MINUTE"} interval type
             */
            MINUTE,

            /**
             * {@code "HOUR"} interval type
             */
            HOUR,

            /**
             * {@code "DAY"} interval type
             */
            DAY

        }

        /**
         * {@code RateLimitType} list of available rate types
         */
        public enum RateLimitType {

            /**
             * {@code "REQUEST_WEIGHT"} rate type
             */
            REQUEST_WEIGHT,

            /**
             * {@code "ORDERS"} rate type
             */
            ORDERS,

            /**
             * {@code "RAW_REQUESTS"} rate type
             */
            RAW_REQUESTS

        }

    }

    /**
     * The {@code Symbol} class is useful to format a symbol
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#exchange-information">
     *     Exchange Information</a>
     *  @author N7ghtm4r3 - Tecknobit
     */
    public static class Symbol {

        /**
         * {@code symbol} is instance that contains symbol
         */
        private final String symbol;

        /**
         * {@code quoteOrderQtyMarketAllowed} is instance that contains quote order quantity market allowed
         */
        private final boolean quoteOrderQtyMarketAllowed;

        /**
         * {@code status} is instance that contains status of symbol
         */
        private final String status;

        /**
         * {@code baseAsset} is instance that contains base asset of symbol
         */
        private final String baseAsset;

        /**
         * {@code baseAssetPrecision} is instance that contains base asset precision of symbol
         */
        private final int baseAssetPrecision;

        /**
         * {@code quoteAsset} is instance that contains quote asset of symbol
         */
        private final String quoteAsset;

        /**
         * {@code quotePrecision} is instance that contains quote precision of symbol
         */
        private final int quotePrecision;

        /**
         * {@code quoteAssetPrecision} is instance that contains quote asset precision of symbol
         */
        private final int quoteAssetPrecision;

        /**
         * {@code orderTypes} is instance that contains order types of symbol
         */
        private final ArrayList<String> orderTypes;

        /**
         * {@code icebergAllowed} is instance that contains if iceberg is allowed for the symbol
         */
        private final boolean icebergAllowed;

        /**
         * {@code ocoAllowed} is instance that contains if oco is allowed for the symbol
         */
        private final boolean ocoAllowed;

        /**
         * {@code isSpotTradingAllowed} is instance that contains if spot trading is allowed for the symbol
         */
        private final boolean isSpotTradingAllowed;

        /**
         * {@code isMarginTradingAllowed} is instance that contains if margin trading is allowed for the symbol
         */
        private final boolean isMarginTradingAllowed;

        /**
         * {@code filters} is instance that contains filters list of symbol
         */
        private final ArrayList<Filter> filters;

        /**
         * {@code permissions} is instance that contains permissions list of symbol
         */
        private final ArrayList<String> permissions;

        /**
         * {@code baseCommissionPrecision} is instance that contains base commission precision of symbol
         */
        private final int baseCommissionPrecision;

        /**
         * {@code defaultSelfTradePreventionMode} is instance that contains the default self trade prevention mode
         */
        private final SelfTradePreventionMode defaultSelfTradePreventionMode;

        /**
         * {@code allowedSelfTradePreventionModes} is instance that contains allowed self trade prevention modes
         */
        private final ArrayList<SelfTradePreventionMode> allowedSelfTradePreventionModes;

        /**
         * Constructor to init {@link Symbol} object
         *
         * @param symbol                           : symbol
         * @param quoteOrderQtyMarketAllowed       : quote order quantity market allowed
         * @param status                           : status of symbol
         * @param baseAsset                        : base asset of symbol
         * @param baseAssetPrecision               : base asset precision of symbol
         * @param quoteAsset                       : quote asset of symbol
         * @param quotePrecision                   : quote precision of symbol
         * @param quoteAssetPrecision              : quote asset precision of symbol
         * @param orderTypes                       : orders type list
         * @param icebergAllowed                   : iceberg is allowed for the symbol
         * @param ocoAllowed                       : oco is allowed for the symbol
         * @param isSpotTradingAllowed             : spot trading is allowed for the symbol
         * @param isMarginTradingAllowed           : margin trading is allowed for the symbol
         * @param filters                          : filters list
         * @param permissions                      : permissions list
         * @param baseCommissionPrecision          : base commission precision of symbol
         * @param defaultSelfTradePreventionMode   default self trade prevention mode
         * @param allowedSelfTradePreventionModes: allowed self trade prevention modes
         */
        public Symbol(String symbol, boolean quoteOrderQtyMarketAllowed, String status, String baseAsset,
                      int baseAssetPrecision, String quoteAsset, int quotePrecision, int quoteAssetPrecision,
                      ArrayList<String> orderTypes, boolean icebergAllowed, boolean ocoAllowed,
                      boolean isSpotTradingAllowed, boolean isMarginTradingAllowed, ArrayList<Filter> filters,
                      ArrayList<String> permissions, int baseCommissionPrecision,
                      SelfTradePreventionMode defaultSelfTradePreventionMode,
                      ArrayList<SelfTradePreventionMode> allowedSelfTradePreventionModes) {
            this.symbol = symbol;
            this.quoteOrderQtyMarketAllowed = quoteOrderQtyMarketAllowed;
            this.status = status;
            this.baseAsset = baseAsset;
            this.baseAssetPrecision = baseAssetPrecision;
            this.quoteAsset = quoteAsset;
            this.quotePrecision = quotePrecision;
            this.quoteAssetPrecision = quoteAssetPrecision;
            this.orderTypes = orderTypes;
            this.icebergAllowed = icebergAllowed;
            this.ocoAllowed = ocoAllowed;
            this.isSpotTradingAllowed = isSpotTradingAllowed;
            this.isMarginTradingAllowed = isMarginTradingAllowed;
            this.filters = filters;
            this.permissions = permissions;
            this.baseCommissionPrecision = baseCommissionPrecision;
            this.defaultSelfTradePreventionMode = defaultSelfTradePreventionMode;
            this.allowedSelfTradePreventionModes = allowedSelfTradePreventionModes;
        }

        /**
         * Constructor to init {@link Symbol} object
         *
         * @param symbol: symbol details as {@link JSONObject}
         */
        public Symbol(JSONObject symbol) {
            JsonHelper hSymbol = new JsonHelper(symbol);
            this.symbol = hSymbol.getString("symbol");
            quoteOrderQtyMarketAllowed = hSymbol.getBoolean("quoteOrderQtyMarketAllowed");
            status = hSymbol.getString("status");
            baseAsset = hSymbol.getString("baseAsset");
            baseAssetPrecision = hSymbol.getInt("baseAssetPrecision");
            quoteAsset = hSymbol.getString("quoteAsset");
            quotePrecision = hSymbol.getInt("quotePrecision");
            quoteAssetPrecision = hSymbol.getInt("quoteAssetPrecision");
            orderTypes = returnEnumsList(hSymbol.getJSONArray("orderTypes", new JSONArray()));
            icebergAllowed = hSymbol.getBoolean("icebergAllowed");
            ocoAllowed = hSymbol.getBoolean("ocoAllowed");
            isSpotTradingAllowed = hSymbol.getBoolean("isSpotTradingAllowed");
            isMarginTradingAllowed = hSymbol.getBoolean("isMarginTradingAllowed");
            filters = returnFilters(hSymbol.getJSONArray("filters", new JSONArray()));
            permissions = returnEnumsList(hSymbol.getJSONArray("permissions", new JSONArray()));
            baseCommissionPrecision = hSymbol.getInt("baseCommissionPrecision");
            defaultSelfTradePreventionMode = SelfTradePreventionMode.valueOf(hSymbol.getString("defaultSelfTradePreventionMode"));
            allowedSelfTradePreventionModes = new ArrayList<>();
            JSONArray jModes = hSymbol.getJSONArray("allowedSelfTradePreventionModes", new JSONArray());
            for (int j = 0; j < jModes.length(); j++)
                allowedSelfTradePreventionModes.add(SelfTradePreventionMode.valueOf(jModes.getString(j)));
        }

        /**
         * Method to assemble an enums list
         *
         * @param jsonList: obtained from {@code "Binance"} request
         * @return enums from exchange as {@link ArrayList} of {@link String}
         */
        @Returner
        private ArrayList<String> returnEnumsList(JSONArray jsonList) {
            ArrayList<String> enumValues = new ArrayList<>();
            for (int j = 0; j < jsonList.length(); j++)
                enumValues.add(jsonList.getString(j));
            return enumValues;
        }

        /**
         * Method to get {@link #symbol} instance <br>
         * No-any params required
         *
         * @return {@link #symbol} instance as {@link String}
         */
        public String getSymbol() {
            return symbol;
        }

        /**
         * Method to get {@link #quoteOrderQtyMarketAllowed} instance <br>
         * No-any params required
         *
         * @return {@link #quoteOrderQtyMarketAllowed} instance as boolean
         */
        public boolean isQuoteOrderQtyMarketAllowed() {
            return quoteOrderQtyMarketAllowed;
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         */
        public String getStatus() {
            return status;
        }

        /**
         * Method to get {@link #baseAsset} instance <br>
         * No-any params required
         *
         * @return {@link #baseAsset} instance as {@link String}
         */
        public String getBaseAsset() {
            return baseAsset;
        }

        /**
         * Method to get {@link #baseAssetPrecision} instance <br>
         * No-any params required
         *
         * @return {@link #baseAssetPrecision} instance as int
         */
        public int getBaseAssetPrecision() {
            return baseAssetPrecision;
        }

        /**
         * Method to get {@link #quoteAsset} instance <br>
         * No-any params required
         *
         * @return {@link #quoteAsset} instance as {@link String}
         */
        public String getQuoteAsset() {
            return quoteAsset;
        }

        /**
         * Method to get {@link #quotePrecision} instance <br>
         * No-any params required
         *
         * @return {@link #quotePrecision} instance as int
         */
        public int getQuotePrecision() {
            return quotePrecision;
        }

        /**
         * Method to get {@link #quoteAssetPrecision} instance <br>
         * No-any params required
         *
         * @return {@link #quoteAssetPrecision} instance as int
         */
        public int getQuoteAssetPrecision() {
            return quoteAssetPrecision;
        }

        /**
         * Method to get {@link #orderTypes} instance <br>
         * No-any params required
         *
         * @return {@link #orderTypes} instance as {@link ArrayList} of {@link String}
         */
        public ArrayList<String> getOrderTypesList() {
            return orderTypes;
        }

        /**
         * Method to get an order type from {@link #orderTypes} list
         *
         * @param index: index to fetch the order type
         * @return order type as {@link String}
         */
        public String getOrderType(int index) {
            return orderTypes.get(index);
        }

        /**
         * Method to get {@link #icebergAllowed} instance <br>
         * No-any params required
         *
         * @return {@link #icebergAllowed} instance as boolean
         */
        public boolean isIcebergAllowed() {
            return icebergAllowed;
        }

        /**
         * Method to get {@link #ocoAllowed} instance <br>
         * No-any params required
         *
         * @return {@link #ocoAllowed} instance as boolean
         */
        public boolean isOcoAllowed() {
            return ocoAllowed;
        }

        /**
         * Method to get {@link #isSpotTradingAllowed} instance <br>
         * No-any params required
         *
         * @return {@link #isSpotTradingAllowed} instance as boolean
         */
        public boolean isSpotTradingAllowed() {
            return isSpotTradingAllowed;
        }

        /**
         * Method to get {@link #isMarginTradingAllowed} instance <br>
         * No-any params required
         *
         * @return {@link #isMarginTradingAllowed} instance as boolean
         */
        public boolean isMarginTradingAllowed() {
            return isMarginTradingAllowed;
        }

        /**
         * Method to get {@link #filters} list filtered <br>
         *
         * @param types: list of types to filter the list
         * @return {@link #filters} instance as {@link ArrayList} of {@link Filter} if types are multiple, if
         * type is a single one will be returned the filter as {@link Filter}
         */
        public <T> T getFiltersList(FilterType... types) {
            if (types != null && types.length > 0) {
                ArrayList<Filter> filters = new ArrayList<>();
                ArrayList<FilterType> lTypes = new ArrayList<>(Arrays.stream(types).toList());
                for (Filter filter : this.filters)
                    if (lTypes.contains(filter.getFilterType()))
                        filters.add(filter);
                int fSize = filters.size();
                if (fSize > 0) {
                    if (fSize > 1)
                        return (T) filters;
                    else
                        return (T) filters.get(0);
                }
            }
            return null;
        }

        /**
         * Method to get {@link #filters} instance <br>
         * No-any params required
         *
         * @return {@link #filters} instance as {@link ArrayList} of {@link Filter}
         */
        public ArrayList<Filter> getFiltersList() {
            return filters;
        }

        /**
         * Method to get a filter from {@link #filters} list
         *
         * @param index: index from fetch the filter
         * @return filter as {@link Filter}
         */
        public Filter getFilter(int index) {
            return filters.get(index);
        }

        /**
         * Method to get {@link #permissions} instance <br>
         * No-any params required
         *
         * @return {@link #permissions} instance as {@link ArrayList} of {@link String}
         */
        public ArrayList<String> getPermissionsList() {
            return permissions;
        }

        /**
         * Method to get a permission from {@link #permissions} list
         *
         * @param index: index from fetch the permission
         * @return filter as {@link String}
         */
        public String getPermission(int index) {
            return permissions.get(index);
        }

        /**
         * Method to get {@link #defaultSelfTradePreventionMode} instance <br>
         * No-any params required
         *
         * @return {@link #defaultSelfTradePreventionMode} instance as {@link String}
         */
        public SelfTradePreventionMode getDefaultSelfTradePreventionMode() {
            return defaultSelfTradePreventionMode;
        }

        /**
         * Method to get {@link #allowedSelfTradePreventionModes} instance <br>
         * No-any params required
         *
         * @return {@link #allowedSelfTradePreventionModes} instance as {@link ArrayList} of {@link String}
         */
        public ArrayList<SelfTradePreventionMode> getAllowedSelfTradePreventionModes() {
            return allowedSelfTradePreventionModes;
        }

        /**
         * Method to get a permission from {@link #allowedSelfTradePreventionModes} list
         *
         * @param index: index from fetch the allowed self trade prevention mode
         * @return allowed self trade prevention mode as {@link String}
         */
        public SelfTradePreventionMode getAllowedSelfTradePreventionMode(int index) {
            return allowedSelfTradePreventionModes.get(index);
        }

        /**
         * Method to get {@link #baseCommissionPrecision} instance <br>
         * No-any params required
         *
         * @return {@link #baseCommissionPrecision} instance as int
         */
        public int getBaseCommissionPrecision() {
            return baseCommissionPrecision;
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

    }

    /**
     * The {@code Filter} class is useful to format filter of {@link ExchangeInformation} of {@code "Binance"}
     *
     *  @author N7ghtm4r3 - Tecknobit
     */
    public static class Filter {

        /**
         * {@code filterType} is instance that contains type of the filter
         */
        private final FilterType filterType;

        /**
         * {@code keys} is instance that contains keys of the filters
         */
        private final ArrayList<String> keys;

        /**
         * {@code values} is instance that contains values of the filters
         */
        private final ArrayList<Object> values;

        /** Constructor to init {@link Filter} object
         * @param keys: keys of the filters
         * @param values: values of the filters
         * @param filterType: type of the filter
         */
        public Filter(ArrayList<String> keys, ArrayList<Object> values, FilterType filterType) {
            this.keys = keys;
            this.values = values;
            this.filterType = filterType;
        }

        /**
         * Method to get {@link #keys} instance <br>
         * No-any params required
         *
         * @return {@link #keys} instance as {@link ArrayList} of {@link String}
         */
        public ArrayList<String> getKeys() {
            return keys;
        }

        /**
         * Method to get {@link #values} instance <br>
         * No-any params required
         *
         * @return {@link #values} instance as {@link ArrayList}
         */
        public ArrayList<?> getValues() {
            return values;
        }

        /**
         * Method to get {@link #filterType} instance <br>
         * No-any params required
         *
         * @return {@link #filterType} instance as {@link FilterType}
         */
        public FilterType getFilterType() {
            return filterType;
        }

        /**
         * Method to get filter details list filtered<br>
         *
         * @param valuesKey: list of values key to fetch
         * @return filter details as {@link HashMap} of {@link FilterDetails} or if is a single
         * filter requested as {@link FilterDetails}
         */
        public <T> T getFilterDetails(String... valuesKey) {
            if (valuesKey != null && valuesKey.length > 0) {
                HashMap<String, FilterDetails> filterDetails = new HashMap<>();
                ArrayList<String> vKeys = new ArrayList<>(Arrays.stream(valuesKey).toList());
                for (int j = 0; j < keys.size(); j++) {
                    String key = keys.get(j);
                    if (vKeys.contains(key))
                        filterDetails.put(key, new FilterDetails(key, values.get(j)));
                }
                int size = filterDetails.size();
                if (size > 0) {
                    if (size > 1)
                        return (T) filterDetails;
                    else
                        return (T) filterDetails.get(valuesKey[0]);
                }
            }
            return null;
        }

        /**
         * Method to get filter details list <br>
         * No-any params required
         *
         * @return filter details as {@link HashMap} of {@link FilterDetails}
         */
        public HashMap<String, FilterDetails> getFilterDetails() {
            HashMap<String, FilterDetails> filterDetails = new HashMap<>();
            for (int j = 0; j < keys.size(); j++) {
                String key = keys.get(j);
                filterDetails.put(key, new FilterDetails(key, values.get(j)));
            }
            return filterDetails;
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

        /**
         * {@code FilterType} list of available filter types
         */
        public enum FilterType {

            /**
             * {@code "PRICE_FILTER"} filter type
             */
            PRICE_FILTER,

            /**
             * {@code "PERCENT_PRICE"} filter type
             */
            PERCENT_PRICE,

            /**
             * {@code "LOT_SIZE"} filter type
             */
            LOT_SIZE,

            /**
             * {@code "MIN_NOTIONAL"} filter type
             */
            MIN_NOTIONAL,

            /**
             * {@code "ICEBERG_PARTS"} filter type
             */
            ICEBERG_PARTS,

            /**
             * {@code "MARKET_LOT_SIZE"} filter type
             */
            MARKET_LOT_SIZE,

            /**
             * {@code "MAX_NUM_ORDERS"} filter type
             */
            MAX_NUM_ORDERS,

            /**
             * {@code "MAX_NUM_ALGO_ORDERS"} filter type
             */
            MAX_NUM_ALGO_ORDERS,

            /**
             * {@code "TRAILING_DELTA"} filter type
             */
            TRAILING_DELTA,

            /**
             * {@code "PERCENT_PRICE_BY_SIDE"} filter type
             */
            PERCENT_PRICE_BY_SIDE,

            /**
             * {@code "MAX_POSITION"} filter type
             */
            MAX_POSITION,

            /**
             * {@code "NOTIONAL"} filter type
             */
            NOTIONAL

        }

        /**
         * The {@code FilterDetails} class is useful to format details of a {@link Filter}
         *
         * @author N7ghtm4r3 - Tecknobit
         */
        public static class FilterDetails {

            /**
             * {@code key} is instance that contains key of the filter
             */
            private final String key;

            /**
             * {@code value} is instance that contains value of the filter
             */
            private final Object value;

            /**
             * Constructor to init {@link FilterDetails} object
             *
             * @param key:   key of the filter
             * @param value: value of the filter
             */
            public FilterDetails(String key, Object value) {
                this.key = key;
                this.value = value;
            }

            /**
             * Method to get {@link #key} instance <br>
             * No-any params required
             *
             * @return {@link #key} instance as {@link String}
             */
            public String getKey() {
                return key;
            }

            /**
             * Method to get {@link #value} instance <br>
             * No-any params required
             *
             * @return {@link #value} instance as {@link Object}
             */
            public <T> T getValue() {
                return (T) value;
            }

            /**
             * Returns a string representation of the object <br>
             * No-any params required
             *
             * @return a string representation of the object as {@link String}
             */
            @Override
            public String toString() {
                return new JSONObject(this).toString();
            }

        }

    }

    /**
     * The {@code ExchangePermissions} list of available exchange permissions
     */
    public enum ExchangePermission {

        /**
         * The {@code "SPOT"}  exchange permission
         */
        SPOT,

        /**
         * The {@code "MARGIN"}  exchange permission
         */
        MARGIN,

        /**
         * The {@code "FUTURES"}  exchange permission
         */
        FUTURES,

        /**
         * The {@code "LEVERAGED"}  exchange permission
         */
        LEVERAGED

    }

    /**
     * The {@code SelfTradePreventionMode} list of available self trade prevention modes
     */
    public enum SelfTradePreventionMode {

        /**
         * The {@code EXPIRE_TAKER} self trade prevention mode
         */
        EXPIRE_TAKER,

        /**
         * The {@code EXPIRE_MAKER} self trade prevention mode
         */
        EXPIRE_MAKER,

        /**
         * The {@code EXPIRE_BOTH} self trade prevention mode
         */
        EXPIRE_BOTH,

        /**
         * The {@code NONE} self trade prevention mode
         */
        NONE

    }

}
