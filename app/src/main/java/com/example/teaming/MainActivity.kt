package com.example.teaming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.teaming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainFragment by lazy { MainFragment() }
    private val calFragment by lazy { CalFragment() }
    private val notiFragment by lazy { NotiFragment() }
    private val fileFragment by lazy { FileFragment() }
    private val userFragment by lazy { UserFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationBar()
    }

    private fun initNavigationBar() {
        binding.bottomNavi.run{
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.home -> changeFragment(mainFragment)
                    R.id.cal -> changeFragment(calFragment)
                    R.id.noti -> changeFragment(notiFragment)
                    R.id.file -> changeFragment(fileFragment)
                    R.id.user -> changeFragment(userFragment)
                }
                true
            }
            selectedItemId = R.id.home
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }
}