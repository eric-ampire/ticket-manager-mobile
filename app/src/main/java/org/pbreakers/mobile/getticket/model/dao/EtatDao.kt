package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Etat

@Dao
interface EtatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Etat)

    @Query("SELECT * FROM Etat WHERE idEtat=:id")
    fun findById(id: Int): LiveData<Etat>

    @Query("SELECT * FROM Etat")
    fun findAll(): LiveData<List<Etat>>

    @Update
    fun update(vararg data: Etat)

    @Delete
    fun remove(vararg data: Etat)
}