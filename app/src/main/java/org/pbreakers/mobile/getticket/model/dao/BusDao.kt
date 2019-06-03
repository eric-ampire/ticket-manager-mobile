package org.pbreakers.mobile.getticket.model.dao

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single
import org.pbreakers.mobile.getticket.model.entity.Bus

@Dao
interface BusDao {

    @Query("SELECT * FROM Bus WHERE idAgence=:id")
    fun findById(id: Int): Maybe<Bus>

    @Query("SELECT * FROM Bus")
    fun findAll(): Maybe<List<Bus>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Bus) : Single<Int>

    @Update
    fun update(vararg data: Bus) : Single<Int>

    @Delete
    fun remove(vararg data: Bus) : Single<Int>
}