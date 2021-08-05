package org.example.uttarakhandcovidtracker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
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

                confirmedT.text = "कंफर्म मामले : $confirmedCases"
                deceasedT.text = "कुल मौतें : $deceasedCases"
                activeT.text = "सक्रिय मामले : $activeCases"
                deltaConfirmedT.text = "delta कंफर्म मामले : $deltaconfirmedCases"
                deltaDeathsT.text = "delta कुल मौतें : $deltaDeceasedCases"
                recoveredT.text = "कुल ठीक हो चुके : $recoveredCases"
                updateTimeT.text = "updated : $lastUpdatedAt"
            },
            {
                Toast.makeText(this, "Error in the hood boys!", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}