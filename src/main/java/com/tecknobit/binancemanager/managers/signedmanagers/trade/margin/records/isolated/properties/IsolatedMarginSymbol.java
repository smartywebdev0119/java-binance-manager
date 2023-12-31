package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties;

import org.json.JSONObject;

/**
 * The {@code IsolatedMarginSymbol} class is useful to format an {@code "Binance"}'s isolated margin symbol
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
 * Query Isolated Margin Symbol (USER_DATA)</a>
 */
public class IsolatedMarginSymbol {

    /**
     * {@code symbol} is instance that memorizes symbol of asset
     */
    private final String symbol;

    /**
     * {@code base} is instance that memorizes base asset
     */
    private final String base;

    /**
     * {@code quote} is instance that memorizes quote asset
     */
    private final String quote;

    /**
     * {@code isMarginTrade} is instance that memorizes if is margin trade
     */
    private final boolean isMarginTrade;

    /**
     * {@code isBuyAllowed} is instance that memorizes if is buy allowed
     */
    private final boolean isBuyAllowed;

    /**
     * {@code isSellAllowed} is instance that memorizes if is sell allowed
     */
    private final boolean isSellAllowed;

    /** Constructor to init {@link IsolatedMarginSymbol} object
     * @param symbol: symbol of asset
     * @param base: base asset
     * @param quote: quote asset
     * @param isMarginTrade: is margin trade
     * @param isBuyAllowed: is buy allowed
     * @param isSellAllowed: is sell allowed
     */
    public IsolatedMarginSymbol(String symbol, String base, String quote, boolean isMarginTrade, boolean isBuyAllowed,
                                boolean isSellAllowed) {
        this.symbol = symbol;
        this.base = base;
        this.quote = quote;
        this.isMarginTrade = isMarginTrade;
        this.isBuyAllowed = isBuyAllowed;
        this.isSellAllowed = isSellAllowed;
    }

    /**
     * Constructor to init {@link IsolatedMarginSymbol} object
     *
     * @param isolatedMarginSymbol: isolated margin symbol details as {@link JSONObject}
     */
    public IsolatedMarginSymbol(JSONObject isolatedMarginSymbol) {
        symbol = isolatedMarginSymbol.getString("symbol");
        base = isolatedMarginSymbol.getString("base");
        quote = isolatedMarginSymbol.getString("quote");
        isMarginTrade = isolatedMarginSymbol.getBoolean("isMarginTrade");
        isBuyAllowed = isolatedMarginSymbol.getBoolean("isBuyAllowed");
        isSellAllowed = isolatedMarginSymbol.getBoolean("isSellAllowed");
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
     * Method to get {@link #base} instance <br>
     * No-any params required
     *
     * @return {@link #base} instance as {@link String}
     */
    public String getBase() {
        return base;
    }

    /**
     * Method to get {@link #quote} instance <br>
     * No-any params required
     *
     * @return {@link #quote} instance as {@link String}
     */
    public String getQuote() {
        return quote;
    }

    /**
     * Method to get {@link #isMarginTrade} instance <br>
     * No-any params required
     *
     * @return {@link #isMarginTrade} instance as boolean
     */
    public boolean isMarginTrade() {
        return isMarginTrade;
    }

    /**
     * Method to get {@link #isBuyAllowed} instance <br>
     * No-any params required
     *
     * @return {@link #isBuyAllowed} instance as boolean
     */
    public boolean isBuyAllowed() {
        return isBuyAllowed;
    }

    /**
     * Method to get {@link #isSellAllowed} instance <br>
     * No-any params required
     *
     * @return {@link #isSellAllowed} instance as boolean
     */
    public boolean isSellAllowed() {
        return isSellAllowed;
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
