package br.com.fiap.trabalhofinalapplication.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.fiap.trabalhofinalapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import kotlinx.android.synthetic.main.user_add_fragment.*
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        var fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.frameLayout, HomeFragment())
        fragment.commit()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.nav_home -> {

                var fragment = supportFragmentManager.beginTransaction()
                fragment.replace(R.id.frameLayout, HomeFragment())
                fragment.commit()

            }

            R.id.nav_user_add -> {

                var fragment = supportFragmentManager.beginTransaction()
                fragment.replace(R.id.frameLayout, UserListFragment())
                fragment.commit()

            }

            R.id.nav_about -> {

                var fragment = supportFragmentManager.beginTransaction()
                fragment.replace(R.id.frameLayout, AboutFragment())
                fragment.commit()

            }

            R.id.nav_exit -> {

                var sharedPreferences = getSharedPreferences("appconfig",
                    Context.MODE_PRIVATE)

                var editor = sharedPreferences.edit()
                editor.putBoolean("KEEP_CONNECTED_CHECKBOX", false)
                editor.putString("JWT_TOKEN", null)
                editor.apply()

                finish()

            }

        }



        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
