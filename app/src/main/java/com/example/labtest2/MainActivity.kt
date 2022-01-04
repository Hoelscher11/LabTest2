package com.example.labtest2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //subToast("Welcome")

        if (!dbExists(this, "mydata")) {
            createDB();

        }
        val submitButton = findViewById<Button>(R.id.submitBtn)

        submitButton.setOnClickListener() {
            val username = findViewById<EditText>(R.id.usernametxt).text
            val password = findViewById<EditText>(R.id.passwordtxt).text

            val status = checkDB(username.toString(), password.toString())

            if(status) {
                val intent = Intent(this, SecondActivity::class.java).apply {
                }
                startActivity(intent)
            }
            else {
                val intent = Intent(this, MainActivity::class.java).apply {
                }
                startActivity(intent)
                subToast("Username is not exists")
            }
            /*val intent = Intent(this, SecondActivity::class.java).apply {
            }
            startActivity(intent)*/
        }
    }
    private fun dbExists(c: Context, dbName: String): Boolean {
        val dbFile: File = c.getDatabasePath(dbName)
        return dbFile.exists()
    }
    private fun createDB() {
        val db = openOrCreateDatabase("mydata", MODE_PRIVATE, null)
        subToast("Database MyData is created")
        val sqlText = "CREATE TABLE IF NOT EXISTS user" +
                "(Username VARCHAR(30) NOT NULL," +
                "Password VARCHAR(30) NOT NULL" +
                ");"
        db.execSQL(sqlText)

        subToast("Table User is created")

        var nextSQL = "INSERT INTO user (Username,Password) VALUES ('ahmad','ahmad1234');"
        db.execSQL(nextSQL)
        print("Db is created")
    }


    private fun checkDB(username: String, password: String): Boolean {
        val db = openOrCreateDatabase("mydata", MODE_PRIVATE, null)
        val sql = "SELECT Username, Password FROM user WHERE Username = '$username' AND Password = '$password'"
        val c: Cursor = db.rawQuery(sql, null)
        var out = false
        if(c.count > 0){
            out = true
        }
        return out
    }


    private fun subToast(msg: String) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}

