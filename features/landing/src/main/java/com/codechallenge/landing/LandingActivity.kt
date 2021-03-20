package com.codechallenge.landing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codechallenge.landing.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityLandingBinding.inflate(layoutInflater)) {
            setContentView(root)
        }
    }


}