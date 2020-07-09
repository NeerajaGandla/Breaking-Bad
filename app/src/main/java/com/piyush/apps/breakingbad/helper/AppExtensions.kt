package com.piyush.apps.breakingbad.helper

import android.content.Context
import android.widget.Toast

/**
 * Created By Piyush Pandey on 08-07-2020
 * Apps@Piyush
 */

fun Context.showToast(message : String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()