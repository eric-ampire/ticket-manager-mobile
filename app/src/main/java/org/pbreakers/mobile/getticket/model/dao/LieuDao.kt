package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Lieu

@Dao
interface LieuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Lieu)

    @Query("SELECT * FROM Lieu WHERE idLieu=:id")
    fun findById(id: Long): LiveData<Lieu>

    @Query("SELECT * FROM Lieu")
    fun findAll(): LiveData<List<Lieu>>

    @Update
    fun update(vararg data: Lieu)

    @Delete
    fun remove(vararg data: Lieu)
}