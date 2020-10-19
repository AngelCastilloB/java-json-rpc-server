/*
 * MIT License
 *
 * Copyright (c) 2020 Angel Castillo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.rpc;

/* IMPORTS *******************************************************************/

import com.github.arteam.simplejsonrpc.server.JsonRpcServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/* IMPLEMENTATION ************************************************************/

/**
 * Node HTTP handler.
 */
class NodeHttpHandler implements HttpHandler
{
    private static final int RESPONSE_CODE_METHOD_NOT_ALLOWED = 405;
    private static final int RESPONSE_CODE_OK                 = 200;

    private final RpcService rpcService = new RpcService();
    private final JsonRpcServer rpcServer = new JsonRpcServer();

    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     * @param httpExchange the exchange containing the request from the
     *      client and used to send the response
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        OutputStream outputStream = httpExchange.getResponseBody();

        // We do not support GET method, so we reject with an error.
        if (httpExchange.getRequestMethod().equals("GET"))
        {
            String notSupportedMessage = "HTTP GET is not supported";
            httpExchange.sendResponseHeaders(RESPONSE_CODE_METHOD_NOT_ALLOWED, notSupportedMessage.length());
            outputStream.write(notSupportedMessage.getBytes());
            outputStream.flush();
            outputStream.close();
            return;
        }

        String request = new String(httpExchange.getRequestBody().readAllBytes());

        System.out.println(request);

        String response = rpcServer.handle(request, rpcService);

        System.out.println(response);

        httpExchange.sendResponseHeaders(RESPONSE_CODE_OK, response.length());
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
