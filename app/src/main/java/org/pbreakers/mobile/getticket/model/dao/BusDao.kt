package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.pbreakers.mobile.getticket.model.entity.Bus

@Dao
interface BusDao {

    @Query("SELECT * FROM Bus WHERE idAgence=:id")
    fun findById(id: Int): Maybe<Bus>

    @Query("SELECT * FROM Bus")
    fun findAll(): DataSource.Factory<Int, Bus>

    @Query("SELECT * FROM Bus")
    fun findAllLiveData(): LiveData<List<Bus>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Bus)

    @Update
    fun update(vararg data: Bus) : Single<Int>

    @Delete
    fun remove(vararg data: Bus) : Completable

    @Query("SELECT count() FROM Bus")
    fun count(): LiveData<Int>
}