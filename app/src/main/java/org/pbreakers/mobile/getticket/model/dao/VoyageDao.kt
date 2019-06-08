package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import io.reactivex.Completable
import org.pbreakers.mobile.getticket.model.entity.Voyage

@Dao
interface VoyageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Voyage)

    @Query("SELECT * FROM Voyage WHERE idVoyage=:id")
    fun findById(id: Int): LiveData<Voyage>

    @Query("SELECT * FROM Voyage")
    fun findAll(): DataSource.Factory<Int, Voyage>

    @Update
    fun update(vararg data: Voyage): Completable

    @Delete
    fun remove(vararg data: Voyage): Completable

    @Query("SELECT * FROM Voyage")
    fun findAllLiveData(): LiveData<List<Voyage>>
}