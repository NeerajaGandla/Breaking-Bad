package com.piyush.apps.breakingbad.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.piyush.apps.breakingbad.BuildConfig
import com.piyush.apps.breakingbad.R
import com.piyush.apps.breakingbad.helper.AppUtils.Companion.sendEmail
import com.piyush.apps.breakingbad.helper.AppUtils.Companion.visitLink
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.app_toolbar.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val versionText = "Version ${BuildConfig.VERSION_NAME}"
        tv_app_version.text = versionText
        tv_toolbar.text = getString(R.string.title_about)

        iv_mail.setOnClickListener {
           sendEmail(this)
        }
        iv_github.setOnClickListener {
            visitLink(BuildConfig.GITHUB, this)
        }
        iv_linkedin.setOnClickListener {
            visitLink(BuildConfig.LINKEDIN, this)
        }
        iv_twitter.setOnClickListener {
            visitLink(BuildConfig.TWITTER, this)
        }
    }
}