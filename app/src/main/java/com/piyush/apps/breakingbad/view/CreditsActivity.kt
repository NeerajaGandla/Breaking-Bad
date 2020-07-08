package com.piyush.apps.breakingbad.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.piyush.apps.breakingbad.BuildConfig
import com.piyush.apps.breakingbad.R
import com.piyush.apps.breakingbad.helper.AppUtils.Companion.visitLink
import kotlinx.android.synthetic.main.activity_credits.*
import kotlinx.android.synthetic.main.app_toolbar.*

class CreditsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        tv_toolbar.text = getString(R.string.title_credits)

        tv_api_doc.setOnClickListener {
            visitLink(BuildConfig.API_WEBSITE, this)
        }
        tv_api_link.setOnClickListener {
            visitLink(BuildConfig.API_GITHUB, this)
        }
    }
}