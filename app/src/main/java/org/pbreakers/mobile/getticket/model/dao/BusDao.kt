package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Bus

@Dao
interface BusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Bus)

    @Query("SELECT * FROM Bus WHERE idAgence=:id")
    fun findById(id: Int): LiveData<Bus>

    @Query("SELECT * FROM Bus")
    fun findAll(): LiveData<List<Bus>>

    @Update
    fun update(vararg data: Bus)

    @Delete
    fun remove(vararg data: Bus)
}