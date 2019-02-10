package br.com.fiap.trabalhofinalapplication.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.fiap.trabalhofinalapplication.R
import kotlinx.android.synthetic.main.user_add_fragment.*
import kotlinx.android.synthetic.main.user_add_fragment.view.*

class HomeFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var inflater = LayoutInflater.from(container?.context).inflate(R.layout.home_fragment, container, false)

//        inflater.user_add_float_button.setOnClickListener { view ->
//
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//
//        }

        return inflater
    }

}