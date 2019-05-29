package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Voyage

@Dao
interface VoyageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Voyage)

    @Query("SELECT * FROM Voyage WHERE idVoyage=:id")
    fun findById(id: Int): LiveData<Voyage>

    @Query("SELECT * FROM Voyage")
    fun findAll(): LiveData<List<Voyage>>

    @Update
    fun update(vararg data: Voyage)

    @Delete
    fun remove(vararg data: Voyage)
}