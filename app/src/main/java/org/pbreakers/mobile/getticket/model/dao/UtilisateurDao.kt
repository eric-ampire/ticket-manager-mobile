package org.pbreakers.mobile.getticket.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.pbreakers.mobile.getticket.model.entity.Utilisateur

@Dao
interface UtilisateurDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg data: Utilisateur)

    @Query("SELECT * FROM Utilisateur WHERE idUtilisateur=:id")
    fun findById(id: Int): LiveData<Utilisateur>

    @Query("SELECT * FROM Utilisateur")
    fun findAll(): LiveData<List<Utilisateur>>

    @Update
    fun update(vararg data: Utilisateur)

    @Delete
    fun remove(vararg data: Utilisateur)

    @Query("SELECT * FROM Utilisateur WHERE pseudoUtilisateur=:pseudo AND passwordUtilisateur=:password")
    fun findByPseudoAndPassword(pseudo: String, password: String): LiveData<Utilisateur>
}