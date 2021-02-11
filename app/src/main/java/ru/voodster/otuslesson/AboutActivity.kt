package ru.voodster.otuslesson

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class AboutActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID = 0
        const val EXTRA_TEXT  = "EXTRA_TEXT"
    }

    private var filmID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

    }


}