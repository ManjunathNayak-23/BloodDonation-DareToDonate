package com.developer.blooddonation.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.applozic.mobicomkit.api.account.register.RegistrationResponse
import com.developer.blooddonation.R
import com.github.ybq.android.spinkit.SpinKitView
import io.kommunicate.Kommunicate
import io.kommunicate.callbacks.KMLoginHandler
import io.kommunicate.callbacks.KMLogoutHandler
import io.kommunicate.callbacks.KmCallback


class AssistantActivity : AppCompatActivity() {
    private lateinit var assistantProgress: SpinKitView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assistant)
        assistantProgress = findViewById(R.id.assistantProgress)
        assistantProgress.visibility = View.VISIBLE
        Kommunicate.init(this, "304e1c87c8f0e40572b12cb1647ac2a43")
        Kommunicate.loginAsVisitor(this@AssistantActivity, object : KMLoginHandler {
            override fun onSuccess(registrationResponse: RegistrationResponse?, context: Context?) {


                // You can perform operations such as opening the conversation, creating a new conversation or update user details on success\
                Kommunicate.openConversation(this@AssistantActivity,object:KmCallback{
                    override fun onSuccess(message: Any?) {
                        assistantProgress.visibility = View.INVISIBLE
                       onBackPressed()
                    }

                    override fun onFailure(error: Any?) {
                        assistantProgress.visibility = View.INVISIBLE
                    }

                })

            }

            override fun onFailure(
                registrationResponse: RegistrationResponse,
                exception: Exception
            ) {
                assistantProgress.visibility = View.INVISIBLE
                Toast.makeText(this@AssistantActivity,"Error Occured",Toast.LENGTH_SHORT).show()
                Log.d("Kommunicate", registrationResponse.toString())

                // You can perform actions such as repeating the login call or throw an error message on failure
            }
        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }


}