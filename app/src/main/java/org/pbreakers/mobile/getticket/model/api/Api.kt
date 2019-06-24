package org.pbreakers.mobile.getticket.model.api

import io.reactivex.Maybe
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    fun findAll()

    @GET("utilisateurs/{login}/{password}")
    fun login(@Path("login") username: String, @Path("password") password: String): Maybe<Utilisateur>
}

interface AgenceApi {

}

interface BilletApi {

}

interface BusApi {

}

interface EtatApi {

}

interface LieuApi {

}

interface PointArretApi {

}

interface RoleApi {

}

interface TransitApi {

}

interface VoyageApi {

}