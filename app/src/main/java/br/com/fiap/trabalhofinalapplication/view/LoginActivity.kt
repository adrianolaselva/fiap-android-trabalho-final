package br.com.fiap.trabalhofinalapplication.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import br.com.fiap.trabalhofinalapplication.APIClient
import br.com.fiap.trabalhofinalapplication.R
import br.com.fiap.trabalhofinalapplication.evaluation.api.v1.OAuthApi
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.oauth.v1.LoginRequest
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.oauth.v1.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    var oAuthApi: OAuthApi? = null

    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        oAuthApi = APIClient.client?.create(OAuthApi::class.java)

        sharedPreferences = getSharedPreferences("appconfig",
            Context.MODE_PRIVATE)

        if(sharedPreferences!!.getBoolean("KEEP_CONNECTED_CHECKBOX", false)) {

            val jwtToken = sharedPreferences!!.getString("JWT_TOKEN", null)

            Log.i("", "token: "+jwtToken)

            if(jwtToken != null){

                this.oAuthApi!!.validate(jwtToken).enqueue(object : Callback<Any>{

                    override fun onFailure(call: Call<Any>?, t: Throwable?) {

                        println("login expirou ou é invalido")
                        Log.i("", "login expirou ou é invaido")

                        this@LoginActivity.cleanSharedPreferences()

                        Toast.makeText(
                            this@LoginActivity,
                            t!!.message,
                            Toast.LENGTH_LONG)
                            .show()

                    }

                    override fun onResponse(call: Call<Any>?, response: Response<Any>?) {

                        if(response?.isSuccessful == true){

                            if(response.code() == 200){

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Redirecionando para tela principal",
                                    Toast.LENGTH_LONG)
                                    .show()

                                var handle =  Handler()

                                handle.postDelayed({
                                    openMain()
                                }, 2000)

                            }else{
                                Log.i("", "login expirou 1")
                                this@LoginActivity.cleanSharedPreferences()

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login expirou",
                                    Toast.LENGTH_LONG)
                                    .show()
                            }

                        }else{
                            Log.i("", "login expirou 2")
                            this@LoginActivity.cleanSharedPreferences()

                            Toast.makeText(
                                this@LoginActivity,
                                "Login expirou",
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                })
            }
        }

        loginButton.setOnClickListener {
            login()
        }


    }

    private fun login(){

        this.oAuthApi!!.login(
            LoginRequest(
                username = loginText.text.toString(),
                password = passwordText.text.toString()
            )
        ).enqueue(object : Callback<LoginResponse>{

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {

                println("falha de login")
                println(t?.message)

                Toast.makeText(
                    this@LoginActivity,
                    t?.message,
                    Toast.LENGTH_LONG)
                    .show()

            }

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {

                    if(response?.isSuccessful == true){

                        var message = response.body()?.message
                        var token = response.body()?.token

                        if(response.code() == 200){


                            Log.i("", keepConnectedCheckBox.isChecked.toString())
                            Log.i("", token)
                            println(token)

                            val editor = this@LoginActivity.sharedPreferences!!.edit()
                            editor.putBoolean("KEEP_CONNECTED_CHECKBOX", keepConnectedCheckBox.isChecked)
                            editor.putString("JWT_TOKEN", token)
                            editor.apply()


                            Toast.makeText(
                                this@LoginActivity,
                                message,
                                Toast.LENGTH_LONG)
                                .show()

                            var handle =  Handler()

                            handle.postDelayed({
                                openMain()
                            }, 2000)

                        }else{
                            Toast.makeText(
                                this@LoginActivity,
                                message,
                                Toast.LENGTH_LONG)
                                .show()
                        }

                }else{
                    var message = response?.body()?.message

                    if(message == null)
                        message = "Dados não conferem"

                    Toast.makeText(
                        this@LoginActivity,
                        message,
                        Toast.LENGTH_LONG)
                        .show()
                }
            }

        })

    }

    fun openMain() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun cleanSharedPreferences() {
        val editor = this.sharedPreferences!!.edit()
        editor.putBoolean("KEEP_CONNECTED_CHECKBOX", false)
        editor.putString("JWT_TOKEN", null)
        editor.apply()
    }

}
