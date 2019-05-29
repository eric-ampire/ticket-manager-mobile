package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.PointArret

@Dao
interface PointArretDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: PointArret)

    @Query("SELECT * FROM PointArret WHERE idPointArret=:id")
    fun findById(id: Int): LiveData<PointArret>

    @Query("SELECT * FROM PointArret")
    fun findAll(): LiveData<List<PointArret>>

    @Update
    fun update(vararg data: PointArret)

    @Delete
    fun remove(vararg data: PointArret)
}