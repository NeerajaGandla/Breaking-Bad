package com.piyush.apps.breakingbad.view

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.piyush.apps.breakingbad.view.adapters.AdapterCharacters
import com.piyush.apps.breakingbad.model.Character
import com.piyush.apps.breakingbad.util.CustomTypefaceSpan
import com.piyush.apps.breakingbad.R
import com.piyush.apps.breakingbad.model.network.NetworkService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var isSearchOpened = false
    private val charList = ArrayList<Character>()
    private lateinit var characterAdapter : AdapterCharacters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
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
                    applyFontToMenuItem(subMenuItem)
                }
            }
            applyFontToMenuItem(mi)
        }

        rv_character.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            setEmptyView(no_result)
            setItemViewCacheSize(20)
        }

        iv_back.setOnClickListener {
            hideSearchBar()
        }
        iv_cancel.setOnClickListener {
            edit_search.text = null
            hideKeyboard()
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

        fetchCharacters()
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
    private fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
    private fun fetchCharacters() {
        val service = NetworkService.getNetworkService().listCharacters()
        service.enqueue(object : Callback<List<Character>>{
            override fun onResponse(call: Call<List<Character>>, response: Response<List<Character>>) {
                if (response.isSuccessful) {
                    Log.e("check_res", "Success")
                    charList.clear()
                    charList.addAll(response.body()!!)
                    characterAdapter =
                        AdapterCharacters(
                            charList,
                            this@HomeActivity
                        )
                    rv_character.adapter = characterAdapter
                } else {
                    Log.e("check_res", "Success But failed")
                }
                loading_view.visibility = View.GONE
            }
            override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                t.printStackTrace()
                Log.e("check_error", "Failed")
                no_result.visibility = View.VISIBLE
                loading_view.visibility = View.GONE
            }
        })
    }

    private fun applyFontToMenuItem(mi: MenuItem) {
        val font = Typeface.createFromAsset(assets, "regular.ttf")
        val mNewTitle = SpannableString(mi.title)
        mNewTitle.setSpan(
            CustomTypefaceSpan("", font),
            0,
            mNewTitle.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        //mNewTitle.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, mNewTitle.length(), 0); Use this if you want to center the items
        mi.title = mNewTitle
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