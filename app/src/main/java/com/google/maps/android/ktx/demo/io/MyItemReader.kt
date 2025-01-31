/*
 * Copyright 2023 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.maps.android.ktx.demo.io

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.demo.model.MyItem
import org.json.JSONArray
import org.json.JSONException
import java.io.InputStream

/**
 * Helper class to read in cluster items from a resource
 */
class MyItemReader {

    /**
     * Returns a list of cluster items read from the provided [inputStream]
     */
    @Throws(JSONException::class)
    fun read(inputStream: InputStream): List<MyItem> {
        val json = inputStream.bufferedReader().use { it.readText() }
        val array = JSONArray(json)
        return List(array.length()) { index ->
            val obj = array.getJSONObject(index)
            val lat = obj.getDouble("lat")
            val lng = obj.getDouble("lng")
            val title = obj.optString("title", null)
            val snippet = obj.optString("snippet", null)
            val zIndex = obj.optDouble("zIndex", Double.NaN).takeIf { it.isNaN() }?.toFloat()

            MyItem(LatLng(lat, lng), title, snippet, zIndex)
        }
    }
}
