package com.example.musicapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var pinEditText: EditText
    private lateinit var usernameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedPin = sharedPreferences.getString("PIN", "")

        if (savedPin.isNullOrEmpty()) {
            // PIN not set, navigate to set login data activity
            setContentView(R.layout.signup)

            val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
            pinEditText = findViewById(R.id.pinEditText)
            val confirmPinEditText = findViewById<EditText>(R.id.confirmPinEditText)
            val saveButton = findViewById<Button>(R.id.saveButton)

            saveButton.setOnClickListener {
                val username = usernameEditText.text.toString()
                val pin = pinEditText.text.toString()
                val confirmPin = confirmPinEditText.text.toString()

                if(username.equals("") && pin.equals("") && confirmPin.equals("")) {
                    Toast.makeText(this@MainActivity, "Fields are empty", Toast.LENGTH_SHORT)
                }
                else if (pin == confirmPin) {
                    // Save username and PIN to SharedPreferences
                    sharedPreferences.edit().apply {
                        putString("Username", username)
                        putString("PIN", pin)
                        apply()
                    }

                    Toast.makeText(
                        this@MainActivity,
                        "Username and PIN saved",
                        Toast.LENGTH_SHORT
                    ).show()
                    startact(this@MainActivity,username)

                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "PINs do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            // PIN already set, show login activity
            setContentView(R.layout.login)

            pinEditText = findViewById(R.id.pinEditText)
            usernameTextView = findViewById(R.id.usernameTextView)
            val enterPinButton = findViewById<Button>(R.id.enterPinButton)

            // Set the username to the TextView
            val savedUsername = sharedPreferences.getString("Username", "") ?: ""
            usernameTextView.text = "Welcome, $savedUsername"

            enterPinButton.setOnClickListener {
                val enteredPin = pinEditText.text.toString()
                val savedPin = sharedPreferences.getString("PIN", "")

                if (enteredPin == savedPin) {
                    // Correct PIN entered, proceed to landing page
                    startact(this@MainActivity,savedUsername)
                    finish()
                } else {
                    if(enteredPin.equals("")){
                        Toast.makeText(
                            this@MainActivity,
                            "PIN Field Empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            this@MainActivity,
                            "Incorrect PIN",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    fun startact(context: Context,name:String){
        val intent = Intent(context, LandingPage::class.java)
        intent.putExtra("username",name)
        startActivity(intent)
    }
}