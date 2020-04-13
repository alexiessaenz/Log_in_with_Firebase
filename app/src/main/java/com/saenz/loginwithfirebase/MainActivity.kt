package com.saenz.loginwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btLogin: Button
    private lateinit var btReg: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()

    }

    private fun bindView(){
        btLogin=findViewById(R.id.bt_login)
        btReg=findViewById(R.id.bt_reg)

        btLogin.setOnClickListener(){
            startActivity(Intent(this,LoginActivity::class.java))
        }
        btReg.setOnClickListener(){
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}
