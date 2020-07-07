package com.piyush.apps.breakingbad.view

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.makeramen.roundedimageview.RoundedImageView
import com.piyush.apps.breakingbad.view.adapters.AdapterOccupation
import com.piyush.apps.breakingbad.model.Character
import com.piyush.apps.breakingbad.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    private lateinit var img: RoundedImageView
    private lateinit var mCharacter : Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mCharacter = intent?.extras?.get("character") as Character

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        img = RoundedImageView(this)
        img.id = R.id.iv_img
        img.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, (displayMetrics.heightPixels/1.5).toInt())
        img.setCornerRadius(8f, 8f,0f,0f)
        val constraintSet = ConstraintSet()
        constraintSet.clone(root_cl)
        constraintSet.connect(img.id, ConstraintSet.TOP, root_cl.id, ConstraintSet.TOP)
        constraintSet.applyTo(root_cl)

        Picasso.get().load(mCharacter.img).fit().centerCrop().into(img)
        root_cl.addView(img,0)

        tv_char_name.text = mCharacter.name
        tv_actor_name.text = mCharacter.portrayed

        tv_birthday.text = mCharacter.birthday
        tv_nickname.text = mCharacter.nickname
        tv_status.text = mCharacter.status
        tv_category.text = mCharacter.category

        for (i in mCharacter.appearance.indices) {
            val chip = Chip(this)
            val chipText = "Season ${mCharacter.appearance[i]}"
            chip.text = chipText
            cg_seasons.addView(chip)
        }

        rv_occupation.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            setHasFixedSize(true)
            adapter =
                AdapterOccupation(
                    mCharacter.occupation
                )
            isNestedScrollingEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        root_cl.removeView(img)
    }
}