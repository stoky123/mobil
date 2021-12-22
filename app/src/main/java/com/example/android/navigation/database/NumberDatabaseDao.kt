/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NumberDatabaseDao {

    @Insert
    suspend fun insert(number: Number)

    @Update
    suspend fun update(number: Number)

    @Query("DELETE FROM numbers_table")
    suspend fun clear()

    @Query("SELECT * FROM numbers_table ORDER BY numberId DESC LIMIT 1")
    suspend fun getLastNumber(): Number?

    @Query("SELECT * from numbers_table WHERE numberId = :key")
    suspend fun get(key: Long): Number

    @Query("SELECT * FROM numbers_table ORDER BY numberId DESC")
    fun getAllNumber(): LiveData<List<Number>>
}
