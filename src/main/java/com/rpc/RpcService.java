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

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* IMPLEMENTATION ************************************************************/

/**
 * Sample RPC service.
 */
@JsonRpcService
public class RpcService
{
    private final Map<String, String> m_data = new HashMap<>();

    /**
     * Creates a new instance of the RPC service.
     */
    public RpcService()
    {
    }

    /**
     * Add method.
     *
     * @param key   The key to be added.
     * @param value The value to be added.
     *
     * @return true if it could be added successfully.
     *
     * @throws KeyAlreadyPresentException thrown if the key was already present.
     */
    @JsonRpcMethod
    public boolean add(@JsonRpcParam("key") final String key, @JsonRpcParam("value") final String value) throws KeyAlreadyPresentException
    {
        if (m_data.containsKey(key))
            throw new KeyAlreadyPresentException();

        m_data.put(key, value);

        return true;
    }

    /**
     * Finds a value on the service given its key.
     *
     * @param key The key.
     *
     * @return The value to be found.
     */
    @JsonRpcMethod
    public String find(@JsonRpcParam("key") final String key)
    {
        return m_data.get(key);
    }

    /**
     * Remove an entry from the service.
     *
     * @param key The key to be removed.
     *
     * @return true if the entry was removed.
     */
    @JsonRpcMethod
    public boolean removeEntry(@JsonRpcParam("key") final String key)
    {
        if (m_data.containsKey(key))
        {
            m_data.remove(key);
            return true;
        }

        return false;
    }

    /**
     * Gets all the entries in the service.
     *
     * @return The entries.
     */
    @JsonRpcMethod
    public List<String> getEntries()
    {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry: m_data.entrySet())
        {
            list.add(String.format("%s:%s", entry.getKey(), entry.getValue()));
        }

        return list;
    }
}
