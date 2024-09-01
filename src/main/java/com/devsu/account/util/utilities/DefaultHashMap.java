package com.devsu.account.util.utilities;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.*;

/**
 * Extended Version of {@link HashMap} that provides an extended
 * get method accpeting a default value. The default value is returned if
 * the map does not contain a value for the provided key.
 *
 * @version $Id: HashMap.java 587751 2007-10-24 02:41:36Z vgritsenko $
 * @see http://www.java2s.com/Code/Java/Collections-Data-Structure/ExtendedVersionofjavautilHashMapthatprovidesanextendedgetmethodaccpetingadefaultvalue.htm
 * 
 */
public class DefaultHashMap<K, V> extends HashMap<K, V> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7301238376305533634L;

	public DefaultHashMap() {
        super();
    }

    public DefaultHashMap(int initialCapacity ) {
        super(initialCapacity);
    }

    public DefaultHashMap(int initialCapacity, float loadFactor ) {
        super(initialCapacity, loadFactor);
    }

    public DefaultHashMap(Map<K, V> t) {
        super(t);
    }

    /**
     * Get method extended by default object to be returned when key
     * is not found.
     *
     * @param key key to look up
     * @param _default default value to return if key is not found
     * @return value that is associated with key
     */
    @Override
    @SuppressWarnings("unchecked")
	public V get(Object key) {
        if (this.containsKey(key)) {
            return this.get(key);
        }
        return (V)key;
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseArrayList(Object obj) {
        List<T> list = new ArrayList<>();
        if(obj != null) {
            if (obj.getClass().isArray()) {
                list = (List<T>) Arrays.asList((Object[])obj);

            } else if (obj instanceof Collection<?> valCollection) {
                list = (List<T>) new ArrayList<>(valCollection);
            }
        }
        return list;
    }

}