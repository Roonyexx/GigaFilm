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
import androidx.fragment.app.Fragment
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.ui.main.AuthActivity
import com.gigaprod.gigafilm.ui.main.MainActivity

class LoginFragment : Fragment() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        emailInput = view.findViewById(R.id.emailInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        loginButton = view.findViewById(R.id.loginButton)
        registerLink = view.findViewById(R.id.registerLink)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            // Пример успешного входа:
            val token = "example_token"
            requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
                .edit().putString("token", token).apply()

            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        registerLink.setOnClickListener {
            (activity as? AuthActivity)?.showRegisterFragment()
        }

        return view
    }
}
