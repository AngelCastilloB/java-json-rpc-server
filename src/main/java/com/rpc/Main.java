/*
 * Copyright (c) 2020 Angel Castillo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rpc;

/* IMPORTS *******************************************************************/

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/* IMPLEMENTATION ************************************************************/

/**
 * Application main class.
 */
public class Main
{
    /*
    Example of request.
        {
            "jsonrpc": "2.0",
            "method": "add",
            "params": {
                "key": "Angel",
                "value": "123456789"
            },
            "id": "92739"
        }
    */

    /**
     * Application entry point.
     *
     * @param args Arguments.
     */
    public static void main(String[] args) throws IOException
    {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);

        HttpContext hc1 = server.createContext("/test", new NodeHttpHandler());

        hc1.setAuthenticator(new BasicAuthenticator("post") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals("admin") && pwd.equals("password");
            }
        });

        server.setExecutor(threadPoolExecutor);
        server.start();
    }
}