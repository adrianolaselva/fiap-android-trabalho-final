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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity() {

    var sharedPreferences: SharedPreferences? = null

    var oAuthApi: OAuthApi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences("appconfig",
            Context.MODE_PRIVATE)

        oAuthApi = APIClient.client?.create(OAuthApi::class.java)

        if(sharedPreferences!!.getBoolean("KEEP_CONNECTED_CHECKBOX", false)) {

            val jwtToken = sharedPreferences!!.getString("JWT_TOKEN", null)

            Log.i("", "token: "+jwtToken)

            if(jwtToken != null){

                this.oAuthApi!!.validate(jwtToken).enqueue(object : Callback<Any> {

                    override fun onFailure(call: Call<Any>?, t: Throwable?) {

                        println("login expirou ou é invalido")
                        Log.i("", "login expirou ou é invaido")

                        this@SplashScreenActivity.cleanSharedPreferences()

                        Toast.makeText(
                            this@SplashScreenActivity,
                            t!!.message,
                            Toast.LENGTH_LONG)
                            .show()

                    }

                    override fun onResponse(call: Call<Any>?, response: Response<Any>?) {

                        if(response?.isSuccessful == true){

                            if(response.code() == 200){

                                Toast.makeText(
                                    this@SplashScreenActivity,
                                    getString(R.string.authenticatedOkResource),
                                    Toast.LENGTH_LONG)
                                    .show()

                                var handle =  Handler()

                                handle.postDelayed({
                                    openMain()
                                }, 2000)

                            }else{

                                this@SplashScreenActivity.cleanSharedPreferences()

                                Toast.makeText(
                                    this@SplashScreenActivity,
                                    getString(R.string.expiredSessionResource),
                                    Toast.LENGTH_LONG)
                                    .show()
                            }

                        }else{
                            Log.i("", "login expirou 2")
                            this@SplashScreenActivity.cleanSharedPreferences()

                            Toast.makeText(
                                this@SplashScreenActivity,
                                getString(R.string.expiredSessionResource),
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                })
            }
        }else{
            var handle = Handler()
            handle.postDelayed({
                openLogin()
            }, 2000)
        }
    }

    fun openLogin() {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
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
