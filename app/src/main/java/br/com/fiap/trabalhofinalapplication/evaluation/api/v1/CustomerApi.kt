package br.com.fiap.trabalhofinalapplication.evaluation.api.v1

import br.com.fiap.trabalhofinalapplication.evaluation.contracts.Customer
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.customers.v1.CustomersReponse
import retrofit2.Call
import retrofit2.http.*

interface CustomerApi {

    @POST("/v1/customers")
    fun create(@Header("Authorization") token: String? = null, @Body customer: Customer): Call<Customer>

    @POST("/v1/customers/{uuid}")
    fun update(@Header("Authorization") token: String? = null, @Path("uuid") uuid: String? = null, @Body customer: Customer): Call<Customer>

    @GET("/v1/customers")
    fun list(@Header("Authorization") token: String? = null): Call<CustomersReponse>

    @GET("/v1/customers")
    fun list(
        @Header("Authorization") token: String? = null,
        @Query("size") size: Int = 10,
        @Query("page") page: Int = 0,
        @Query("dir") dir: String = "ASC"
    ): Call<CustomersReponse>

    @GET("/v1/customers/{uuid}")
    fun load(@Header("Authorization") token: String? = null, @Path("uuid") uuid: String? = null): Call<CustomersReponse>

}