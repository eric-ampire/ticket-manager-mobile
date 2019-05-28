package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Agence

@Dao
interface AgenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Agence)

    @Query("SELECT * FROM Agence WHERE id=:id")
    fun findById(id: Int): LiveData<Agence>

    @Query("SELECT * FROM Agence")
    fun findAll(): LiveData<List<Agence>>

    @Update
    fun update(vararg data: Agence)

    @Delete
    fun remove(vararg data: Agence)
}