package br.com.fiap.trabalhofinalapplication.view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.fiap.trabalhofinalapplication.R
import br.com.fiap.trabalhofinalapplication.evaluation.contracts.customers.v1.CustomersReponse
import kotlinx.android.synthetic.main.user_row.view.*
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
            Log.i("", "posição: "+position.toString())
            Log.i("", "Tamanho: "+customerResponse!!.content!!.size.toString())

            var customer = customerResponse!!.content!![position]

            Log.i("", customer.toString())

            holder.view.firstNameTextView?.text = customer.firstName
            holder.view.lastNameTextView?.text = customer.lastName
            holder.view.documentNumberTextView?.text = customer.documentNumber
        }catch (e: Exception){

        }
    }

}


class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}