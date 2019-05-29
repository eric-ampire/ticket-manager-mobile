package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Transit

@Dao
interface TransitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Transit)

    @Query("SELECT * FROM Transit WHERE idTransit=:id")
    fun findById(id: Int): LiveData<Transit>

    @Query("SELECT * FROM Transit")
    fun findAll(): LiveData<List<Transit>>

    @Update
    fun update(vararg data: Transit)

    @Delete
    fun remove(vararg data: Transit)
}