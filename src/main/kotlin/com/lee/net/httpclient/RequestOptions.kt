package com.lee.net.httpclient


/**
 * Represents options for configuring a request to an endpoint.
 *
 * @property headers A mutable map of header names to their respective values to be sent with the request.
 * @property urlParameters A mutable map of URL parameter names to their respective values to be appended to the request URL.
 * @property timeout Http operations timeouts.
 */
public data class RequestOptions(
    public val headers: Map<String, String> = emptyMap(),
    public val urlParameters: Map<String, String> = emptyMap(),
    public val timeout: Timeout? = null,
)
