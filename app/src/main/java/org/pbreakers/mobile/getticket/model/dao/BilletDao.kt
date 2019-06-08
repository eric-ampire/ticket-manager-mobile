package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Billet

@Dao
interface BilletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Billet)

    @Query("SELECT * FROM Billet WHERE idBillet=:id")
    fun findById(id: Int): LiveData<Billet>

    @Query("SELECT * FROM Billet")
    fun findAll(): DataSource.Factory<Int, Billet>

    @Update
    fun update(vararg data: Billet)

    @Delete
    fun remove(vararg data: Billet)

    @Query("SELECT count() FROM Billet")
    fun count(): LiveData<Int>

    @Query("SELECT * FROM Billet WHERE idUtilisateur=:id")
    fun findByIdUser(id: Long): LiveData<List<Billet>>

    @Query("SELECT * FROM Billet WHERE idEtat=:id ORDER BY idEtat")
    fun findByIdEtat(id: Long): LiveData<List<Billet>>
}