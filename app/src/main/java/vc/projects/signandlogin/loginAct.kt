package vc.projects.signandlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import vc.projects.signandlogin.databinding.ActivityLoginBinding

class loginAct : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Call this before setting content view

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set content view ONCE only

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener{
            val email = binding.LoginEmail.text.toString()
            val password = binding.LoginPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){ task ->
                        if (task.isSuccessful){
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signUpText.setOnClickListener{
            val intent = Intent(this, SignUpAct::class.java)
            startActivity(intent)
            // Optional: finish current activity
            // finish()
        }

        // Use binding to access the main view
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}