package com.erykhf.android.ohmebreakingbadtechtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erykhf.android.ohmebreakingbadtechtest.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}