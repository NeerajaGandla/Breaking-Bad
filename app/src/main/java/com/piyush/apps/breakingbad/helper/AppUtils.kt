package com.piyush.apps.breakingbad.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.piyush.apps.breakingbad.BuildConfig
import com.piyush.apps.breakingbad.util.CustomTypefaceSpan

/**
 * Created By Piyush Pandey on 08-07-2020
 * Apps@Piyush
 */
class AppUtils {
    companion object {

        fun sendEmail(context: Context) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(BuildConfig.EMAIL))
            context.startActivity(Intent.createChooser(intent, "Send Email"))
        }

        fun visitLink(url : String, context: Context) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }

        fun applyFontToMenuItem(mi: MenuItem, context : Context) {
            val font = Typeface.createFromAsset(context.assets, "regular.ttf")
            val mNewTitle = SpannableString(mi.title)
            mNewTitle.setSpan(
                CustomTypefaceSpan("", font),
                0,
                mNewTitle.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            mi.title = mNewTitle
        }

        fun hideKeyboard(context: Activity) {
            val view = context.currentFocus
            view?.let { v ->
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

    }
}