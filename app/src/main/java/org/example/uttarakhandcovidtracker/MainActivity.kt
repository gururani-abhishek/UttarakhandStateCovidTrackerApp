package org.example.uttarakhandcovidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val confirmedT = findViewById<TextView>(R.id.confirmedT)
        val deceasedT = findViewById<TextView>(R.id.deceasedT)
        val activeT = findViewById<TextView>(R.id.activeT)
        val deltaConfirmedT = findViewById<TextView>(R.id.DeltaConfirmedT)
        val deltaDeathsT = findViewById<TextView>(R.id.DeltaDeathsT)
        val recoveredT = findViewById<TextView>(R.id.RecoveredT)
        val updateTimeT = findViewById<TextView>(R.id.UpdatedTimeT)

        val url = "https://api.covid19india.org/data.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val statewiseDataArray = it.getJSONArray("statewise")
                val UTjsonObject = statewiseDataArray.getJSONObject(36)
                val confirmedCases = UTjsonObject.getString("confirmed")
                val activeCases = UTjsonObject.getString("active")
                val deceasedCases = UTjsonObject.getString("deaths")
                val deltaconfirmedCases = UTjsonObject.getString("deltaconfirmed")
                val deltaDeceasedCases = UTjsonObject.getString("deltadeaths")
                val recoveredCases = UTjsonObject.getString("recovered")
                val lastUpdatedAt = UTjsonObject.getString("lastupdatedtime")

                confirmedT.text = "confirmed : $confirmedCases"
                deceasedT.text = "deaths :  $deceasedCases"
                activeT.text = "active : $activeCases"
                deltaConfirmedT.text = "delta confirmed : $deltaconfirmedCases"
                deltaDeathsT.text = "delta deaths : $deltaDeceasedCases"
                recoveredT.text = "recovered : $recoveredCases"
                updateTimeT.text = "updated on : $lastUpdatedAt"
            },
            {
                Toast.makeText(this, "Error in the hood boys!", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}