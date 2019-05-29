package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Billet

@Dao
interface BilletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Billet)

    @Query("SELECT * FROM Billet WHERE idBillet=:id")
    fun findById(id: Int): LiveData<Billet>

    @Query("SELECT * FROM Billet")
    fun findAll(): LiveData<List<Billet>>

    @Update
    fun update(vararg data: Billet)

    @Delete
    fun remove(vararg data: Billet)
}