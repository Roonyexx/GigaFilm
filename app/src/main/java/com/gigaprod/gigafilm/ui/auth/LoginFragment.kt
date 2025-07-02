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
import com.gigaprod.gigafilm.ui.main.MainActivity
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var loginInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        loginInput = view.findViewById(R.id.loginInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        loginButton = view.findViewById(R.id.loginButton)
        registerLink = view.findViewById(R.id.registerLink)

        loginButton.setOnClickListener {
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            lifecycleScope.launch {
                try {
                    val response = ApiClient.serverApi.login(LoginRequest(login , password))
                    if (response.isSuccessful) {
                        val token = response.body()?.access_token
                        if (token != null) {
                            requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
                                .edit { putString("token", token) }
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            requireActivity().finish()
                        } else {
                            showToast("Ошибка: токен пустой")
                        }
                    } else {
                        showToast("Неверный логин или пароль")
                    }
                } catch (e: Exception) {

                    showToast(e.toString())
                }
            }
        }



        registerLink.setOnClickListener {
            (activity as? AuthActivity)?.showRegisterFragment()
        }

        return view
    }

    fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}


