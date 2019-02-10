package br.com.fiap.trabalhofinalapplication.view

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import br.com.fiap.trabalhofinalapplication.APIClient
import br.com.fiap.trabalhofinalapplication.R
import br.com.fiap.trabalhofinalapplication.evaluation.api.v1.CustomerApi
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.Customer
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.customers.v1.CustomersReponse
import br.com.fiap.trabalhofinalapplication.evaluation.enums.GenreEnum
import kotlinx.android.synthetic.main.activity_user_add.*
import kotlinx.android.synthetic.main.user_list_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception



class UserAddActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add)

        val sharedPreferences = getSharedPreferences("appconfig",
            Context.MODE_PRIVATE)


        println("token: " + sharedPreferences.getString("JWT_TOKEN", null))

        var customerApi = APIClient.client?.create(CustomerApi::class.java)

        saveButton.setOnClickListener {

            var genre: GenreEnum? = null
            when(genreSpinner.selectedItem.toString()){
                "Masculino" -> genre = GenreEnum.MALE
                "Feminino" -> genre = GenreEnum.FEMALE
            }

            var customer = Customer(
                firstName = firstNameEditText.text.toString(),
                lastName = lastNameEditText.text.toString(),
                documentNumber = documentNumberEditText.text.toString(),
                genre = genre
            )

            customerApi
                ?.create(sharedPreferences.getString("JWT_TOKEN", null), customer)
                ?.enqueue(object : Callback<Customer> {

                override fun onFailure(call: Call<Customer>?, t: Throwable?) {

                    Toast.makeText(
                        this@UserAddActivity,
                        t!!.message,
                        Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(call: Call<Customer>?, response: Response<Customer>?) {

                    if(response?.isSuccessful == true){

                        if(response.code() == 200){

                            Toast.makeText(
                                this@UserAddActivity,
                                "Usuário cadastrado com sucesso",
                                Toast.LENGTH_LONG)
                                .show()

                            this@UserAddActivity.finish()

                        }else{

                            Toast.makeText(
                                this@UserAddActivity,
                                "Falha ao cadastrar usuário",
                                Toast.LENGTH_LONG)
                                .show()
                        }

                    }else{

                        Toast.makeText(
                            this@UserAddActivity,
                            "Falha ao cadastrar usuário",
                            Toast.LENGTH_LONG)
                            .show()
                    }

                }

            })

        }

    }
}
