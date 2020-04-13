package com.saenz.loginwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotActivity : AppCompatActivity() {
    private lateinit var etMail: EditText

    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        bindView()

    }

    private fun bindView(){
        etMail=findViewById(R.id.et_mail)
        progressBar=findViewById(R.id.progressBar_forgot)
        auth = FirebaseAuth.getInstance()
    }

    fun send(view: View){
        val email = etMail.text.toString()

        if(!TextUtils.isEmpty(email)){
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this){
                        task ->
                        if(task.isSuccessful) {
                            progressBar.visibility = View.VISIBLE
                            startActivity(Intent(this, LoginActivity::class.java))
                        }else
                            Toast.makeText(this,"Error al enviar el email",Toast.LENGTH_LONG)
                    }
            }
    }

}
