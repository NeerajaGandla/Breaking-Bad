package com.piyush.apps.breakingbad.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.piyush.apps.breakingbad.R
import com.piyush.apps.breakingbad.helper.AppUtils.Companion.applyFontToMenuItem
import com.piyush.apps.breakingbad.helper.AppUtils.Companion.hideKeyboard
import com.piyush.apps.breakingbad.helper.Status
import com.piyush.apps.breakingbad.helper.showToast
import com.piyush.apps.breakingbad.view.adapters.AdapterCharacters
import com.piyush.apps.breakingbad.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var isSearchOpened = false
    private lateinit var characterAdapter : AdapterCharacters

    private val appViewModel : AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        observeCharacters()

        iv_back.setOnClickListener {
            hideSearchBar()
        }
        iv_cancel.setOnClickListener {
            edit_search.text = null
            hideKeyboard(this)
        }
        edit_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length != 0) {
                    iv_cancel.visibility = View.VISIBLE
                } else {
                    iv_cancel.visibility = View.GONE
                }
                characterAdapter.filter.filter(s)
            }

        })
    }

    private fun initUI() {
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val m: Menu = nav_view.menu
        for (i in 0 until m.size()) {
            val mi = m.getItem(i)
            val subMenu: SubMenu? = mi.subMenu
            if (subMenu != null && subMenu.size() > 0) {
                for (j in 0 until subMenu.size()) {
                    val subMenuItem: MenuItem = subMenu.getItem(j)
                    applyFontToMenuItem(subMenuItem, this)
                }
            }
            applyFontToMenuItem(mi, this)
        }

        characterAdapter = AdapterCharacters(arrayListOf(), this)
        rv_character.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            setEmptyView(no_result)
            setItemViewCacheSize(20)
            adapter = characterAdapter
        }
    }
    private fun observeCharacters() {
        appViewModel.characters.observe(this, Observer {
            when(it.status) {
                Status.LOADING -> {
                    loading_view.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    it.data?.let {characters ->
                        characterAdapter.updateList(characters)
                        loading_view.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    no_result.visibility = View.VISIBLE
                    loading_view.visibility = View.GONE
                    showToast(it.message!!)
                }
            }
        })
    }

    override fun onBackPressed() {
        when {
            isSearchOpened -> {
                hideSearchBar()
            }
            drawer_layout.isDrawerOpen(GravityCompat.START) -> {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_search -> {
                openSearchBar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hideSearchBar() {
        isSearchOpened = false
        edit_search.text = null
        appbar.visibility = View.VISIBLE
        cl_search.visibility = View.GONE
        val params : CoordinatorLayout.LayoutParams = rv_character.layoutParams as CoordinatorLayout.LayoutParams
        params.topMargin = 0
        rv_character.layoutParams = params
    }
    private fun openSearchBar() {
        isSearchOpened = true
        appbar.visibility = View.GONE
        cl_search.visibility = View.VISIBLE
        val params : CoordinatorLayout.LayoutParams = rv_character.layoutParams as CoordinatorLayout.LayoutParams
        params.topMargin = 16
        rv_character.layoutParams = params
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                overridePendingTransition(
                    R.anim.slide_up,
                    R.anim.slide_down
                )
            }
            R.id.nav_credits -> {
                startActivity(Intent(this, CreditsActivity::class.java))
                overridePendingTransition(
                    R.anim.slide_up,
                    R.anim.slide_down
                )
            }
        }
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        return true
    }
}