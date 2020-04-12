package com.saenz.loginwithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TimePicker
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etlastname: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var datefake: TimePicker

    lateinit var progressBar: ProgressBar

    lateinit var dbReference: DatabaseReference
    lateinit var database: FirebaseDatabase
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bindView()
    }

    private fun bindView(){
        etName=findViewById(R.id.et_name)
        etlastname=findViewById(R.id.et_last_name)
        etEmail=findViewById(R.id.et_email)
        etPass=findViewById(R.id.et_pass)

        progressBar= findViewById(R.id.progressBar)
        database= FirebaseDatabase.getInstance()
        auth=     FirebaseAuth.getInstance()
        dbReference= database.reference.child("User")
        datefake= TimePicker(this)
    }

     fun register(view: View){
        createNewAccount()
    }

    private fun createNewAccount(){
        val name: String = etName.text.toString()
        val lastName: String= etlastname.text.toString()
        val email: String= etEmail.text.toString()
        val password: String = etPass.text.toString()

        if (!TextUtils.isEmpty(name) &&
            !TextUtils.isEmpty(lastName) &&
            !TextUtils.isEmpty(email) &&
            !TextUtils.isEmpty(password)){
            progressBar.visibility=View.VISIBLE
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)
                        val userBD= dbReference.child(user!!.uid)
                        userBD.child("Name").setValue(name)
                        userBD.child("lastName").setValue(lastName)
                        action()
                    }
                }
        }
    }
    private fun action(){
        startActivity(Intent(this,LoginActivity::class.java))
    }
    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task ->
                if(task.isComplete ){
                    Toast.makeText(this, "Email Enviado", Toast.LENGTH_LONG).show()
                }else Toast.makeText(this, "Error al enviar el email", Toast.LENGTH_LONG).show()
            }
    }

}
