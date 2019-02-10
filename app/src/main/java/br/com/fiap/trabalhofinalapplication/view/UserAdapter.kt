package br.com.fiap.trabalhofinalapplication.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.fiap.trabalhofinalapplication.APIClient
import br.com.fiap.trabalhofinalapplication.R
import br.com.fiap.trabalhofinalapplication.evaluation.api.v1.CustomerApi
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.Customer
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.customers.v1.CustomersReponse
import kotlinx.android.synthetic.main.user_row.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class UserAdapter(var customerResponse: CustomersReponse?) : RecyclerView.Adapter<CustomViewHolder>() {


    override fun getItemCount(): Int {
        return customerResponse!!.numberOfElements
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.user_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        try{
            var customer = customerResponse!!.content!![position]
            holder.view.idTextView?.text = customer.id
            holder.view.firstNameTextView?.text = customer.firstName
            holder.view.lastNameTextView?.text = customer.lastName
            holder.view.documentNumberTextView?.text = customer.documentNumber
        }catch (e: Exception){
            Log.e("", e.message)
        }

        holder.itemView.setOnClickListener {
            var customer = customerResponse!!.content!![position]
            var intent = Intent(it.context, UserEditActivity::class.java)
            intent.putExtra("id", customer.id)
            it.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {

            var customer = customerResponse!!.content!![position]

            val builder = AlertDialog.Builder(it.context)
            builder.setTitle(it.context.getString(R.string.removeConfirmResource))

            val options = arrayOf("SIM","NÃO")

            builder.setItems(options,{_, which ->

                val selected = options[which]

                when(selected){
                    "SIM" -> remover(it, customer, position)
                }
            })

            val dialog = builder.create()
            dialog.show()

            return@setOnLongClickListener true
        }
    }

    fun remover(it: View, customer: Customer, position: Int) {

        var sharedPreferences = it.context.getSharedPreferences("appconfig",
            Context.MODE_PRIVATE)

        var customerApi = APIClient.client?.create(CustomerApi::class.java)

        customerApi!!
            .remove(sharedPreferences.getString("JWT_TOKEN", null), customer.id)
            .enqueue(object : Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

                    Toast.makeText(
                        it.context,
                        t!!.message,
                        Toast.LENGTH_LONG)
                        .show()

                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

                    if(response?.isSuccessful == true){

                        if(response.code() == 200){

                            Toast.makeText(
                                it.context,
                                "Registro removido com sucesso",
                                Toast.LENGTH_LONG)
                                .show()

                            this@UserAdapter.customerResponse!!.content!!.removeAt(position)
                            this@UserAdapter.notifyDataSetChanged()
                        }else{

                            Toast.makeText(
                                it.context,
                                "Falha ao remover registro",
                                Toast.LENGTH_LONG)
                                .show()
                        }

                    }else{
                        Toast.makeText(
                            it.context,
                            "Falha ao remover registro",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }

            })

    }

}


class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}