package com.piyush.apps.breakingbad.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.piyush.apps.breakingbad.R
import kotlinx.android.synthetic.main.activity_credits.*
import kotlinx.android.synthetic.main.app_toolbar.*

class CreditsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        tv_toolbar.text = getString(R.string.menu_credits)

        tv_api_doc.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://breakingbadapi.com/"))
            startActivity(browserIntent)
        }
        tv_api_link.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/timbiles/Breaking-Bad--API"))
            startActivity(browserIntent)
        }
    }
}