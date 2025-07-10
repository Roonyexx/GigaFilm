package com.gigaprod.gigafilm.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.api.ApiClient
import com.gigaprod.gigafilm.api.LoginRequest
import com.gigaprod.gigafilm.api.RegisterRequest
import com.gigaprod.gigafilm.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {

    private lateinit var loginInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var loginLink: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        loginInput = view.findViewById(R.id.loginInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        registerButton = view.findViewById(R.id.registerButton)
        loginLink = view.findViewById(R.id.loginLink)

        registerButton.setOnClickListener {
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            lifecycleScope.launch {
                try {
                    val response = ApiClient.serverAuthApi.register(RegisterRequest(login , password))
                    if (response.isSuccessful) {
                        val success = response.body()?.ok
                        if (success == true) {

                            val response = ApiClient.serverAuthApi.login(LoginRequest(login , password),)
                            val token = response.body()?.access_token
                            requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
                                .edit { putString("token", token) }
                            ApiClient.setToken(token!!)
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            requireActivity().finish()
                        } else {
                            showToast("Ошибка:Логин занят другим пользователем")
                        }
                    } else {
                        showToast("Ошибка подключения к серверу")
                    }
                } catch (e: Exception) {
                    Snackbar.make(view,e.toString(), Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        }


        loginLink.setOnClickListener {
            (activity as? AuthActivity)?.showLoginFragment()
        }

        return view
    }

    fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}


