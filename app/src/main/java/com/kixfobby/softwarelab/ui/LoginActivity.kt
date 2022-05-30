package com.kixfobby.softwarelab.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kixfobby.softwarelab.databinding.ActivityLoginBinding
import com.kixfobby.softwarelab.model.login.LoginRequest
import com.kixfobby.softwarelab.model.login.LoginResponse
import com.kixfobby.softwarelab.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.login.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.isNotEmpty() && Pattern.compile("^[a-zA-Z0-9]+$").matcher(password).matches()) {
                    loginUser(email, password)
                } else {
                    Snackbar.make(view, "Enter a valid password", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(view, "Enter a valid email", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    private fun loginUser(email: String, password: String) {
        val call: Call<LoginResponse?>? = ApiInterface.getClient().login(LoginRequest(email, password))
        call?.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                Log.e("rawData", response.raw().toString())
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val message: String = response.body()!!.message.toString()
                        val name: String = response.body()!!.data?.name.toString()
                        val mEmail: String = response.body()!!.data?.email.toString()
                        Log.e("onSuccess: name-message", "$name - $message")
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                        if (email == mEmail) {
                            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login Failed!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.e("Error", "onFailure: Throw $t")
            }
        })
    }
}