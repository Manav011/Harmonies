package com.example.musicapp

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.musicapp.roomdb.AppDatabase
import com.example.musicapp.roomdb.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var appDatabase: AppDatabase
    private lateinit var pinEditText: EditText
    private lateinit var usernameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appDatabase = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            if (!appDatabase.userDao().isUserExists()) {
                showSignupScreen()
            } else {
                val user = getUserFromDatabase()
                showLoginScreen(user)
            }
        }
    }

    private suspend fun getUserFromDatabase(): User? {
        return withContext(Dispatchers.IO) {
            val userDao = appDatabase.userDao()
            userDao.getUserById()
        }
    }

    private fun showSignupScreen() {
        setContentView(R.layout.signup)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        pinEditText = findViewById(R.id.pinEditText)
        val confirmPinEditText = findViewById<EditText>(R.id.confirmPinEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val pin = pinEditText.text.toString()
            val confirmPin = confirmPinEditText.text.toString()

            if (username.isEmpty() || pin.isEmpty() || confirmPin.isEmpty()) {
                Toast.makeText(this@MainActivity, "Fields are empty", Toast.LENGTH_SHORT).show()
            } else if (pin == confirmPin) {
                // Save user to the database in the background
                lifecycleScope.launch {
                    val user = User(username = username, pin = pin)
                    saveUserToDatabase(user)

                    Toast.makeText(this@MainActivity, "Username and PIN saved", Toast.LENGTH_SHORT).show()
                    startAct(username)
                    finish()
                }
            } else {
                Toast.makeText(this@MainActivity, "PINs do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoginScreen(user: User?) {
        setContentView(R.layout.login)

        pinEditText = findViewById(R.id.pinEditText)
        usernameTextView = findViewById(R.id.usernameTextView)
        val enterPinButton = findViewById<Button>(R.id.enterPinButton)

        usernameTextView.text = "Welcome, ${user?.username}"

        enterPinButton.setOnClickListener {
            val enteredPin = pinEditText.text.toString()

            if (enteredPin == user?.pin) {
                // Correct PIN, proceed to the landing page
                startAct(user.username)
                finish()
            } else {
                if (enteredPin.isEmpty()) {
                    Toast.makeText(this@MainActivity, "PIN Field Empty", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Incorrect PIN", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun saveUserToDatabase(user: User) {
        withContext(Dispatchers.IO) {
            val userDao = appDatabase.userDao()
            userDao.insert(user)
        }
    }

    private fun startAct(username: String) {
        val intent = Intent(this, LandingPage::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}