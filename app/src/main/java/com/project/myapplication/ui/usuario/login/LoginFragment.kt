package com.project.myapplication.ui.usuario.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.project.myapplication.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private lateinit var callbackManager: CallbackManager
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.login_fragment, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.status.observe(viewLifecycleOwner, Observer {
            if (it) {

                findNavController().navigate(R.id.listFragment)
            }
        })
        loginViewModel.msg.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank())
                Toast
                    .makeText(
                        requireContext(),
                        it,
                        Toast.LENGTH_LONG
                    ).show()
        })


        // ...
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()


        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonFacebookLogin.setReadPermissions("email", "public_profile")
        buttonFacebookLogin.fragment = this
        buttonFacebookLogin.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        handleFacebookAccessToken(loginResult.accessToken)
                        findNavController().navigate(R.id.listFragment)
                    }

                    override fun onCancel() {
                    }

                    override fun onError(error: FacebookException) {
                    }
                })

        buttonAcessar.setOnClickListener {
            val email = editTextLoginEmail.text.toString()
            val senha = editTextPassword.text.toString()
            loginViewModel.verificarCredenciais(email, senha)
        }

        buttonCadastrar.setOnClickListener {
            findNavController().navigate(R.id.cadastroFragment)
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
                .addOnSuccessListener { task ->
                        val user = auth.currentUser
                    }
                .addOnFailureListener {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(requireContext(), "Authentication failed:${it.message}",
                                Toast.LENGTH_SHORT).show()
                    }
    }
}