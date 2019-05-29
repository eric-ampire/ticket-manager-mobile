package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Role

@Dao
interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Role)

    @Query("SELECT * FROM Role WHERE idRole=:id")
    fun findById(id: Int): LiveData<Role>

    @Query("SELECT * FROM Role")
    fun findAll(): LiveData<List<Role>>

    @Update
    fun update(vararg data: Role)

    @Delete
    fun remove(vararg data: Role)
}