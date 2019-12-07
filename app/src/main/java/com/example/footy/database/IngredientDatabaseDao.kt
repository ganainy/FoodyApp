/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.footy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.footy.network.Ingredient

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface IngredientDatabaseDao {

    @Insert
    fun insert(ingredient: Ingredient)


    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from ingredient_table WHERE databaseIngredientId = :key")
    fun get(key: Long): Ingredient?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM ingredient_table")
    fun clearAll()


    @Query("DELETE FROM ingredient_table where strMeal==:name")
    fun delete(name: String?)

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM ingredient_table ORDER BY databaseIngredientId DESC")
    fun getAllIngredients(): List<Ingredient>


    @Query("SELECT strMeal FROM ingredient_table")
    fun getFavoriteIngredientsNames(): List<String>
}

