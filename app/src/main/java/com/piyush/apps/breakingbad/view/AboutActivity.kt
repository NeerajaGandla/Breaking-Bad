package com.piyush.apps.breakingbad.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.piyush.apps.breakingbad.BuildConfig
import com.piyush.apps.breakingbad.R
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
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("pandeypiyush94@gmail.com"))
            startActivity(Intent.createChooser(intent, "Send Email"))
        }
        iv_github.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/pandeypiyush94"))
            startActivity(browserIntent)
        }
        iv_linkedin.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/piyush-pandey-7755b9121/"))
            startActivity(browserIntent)
        }
        iv_twitter.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/PandeyPiyushK"))
            startActivity(browserIntent)
        }
    }
}