package org.example.uttarakhandcovidtracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class StateDataActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val confirmedT = findViewById<TextView>(R.id.confirmedT)
        val deceasedT = findViewById<TextView>(R.id.deceasedT)
        val testedT = findViewById<TextView>(R.id.testedT)
        val deltaConfirmedT = findViewById<TextView>(R.id.DeltaConfirmedT)
        val populationT = findViewById<TextView>(R.id.PopulationT)
        val recoveredT = findViewById<TextView>(R.id.RecoveredT)
        val updateTimeT = findViewById<TextView>(R.id.UpdatedTimeT)

        val url = "https://data.covid19india.org/v4/min/data.min.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {

            val stateDataObject = it.getJSONObject("UT")
            val delta21_14 = stateDataObject.getJSONObject("delta21_14")
            val deltaConfirmedCases = delta21_14.getString("confirmed")
            val meta = stateDataObject.getJSONObject("meta")
            val lastUpdatedAt = meta.getString("last_updated")
                val x = ", "
                val timeInStandard = lastUpdatedAt.substring(0, 10) + x + lastUpdatedAt.substring(11, lastUpdatedAt.length)

            updateTimeT.text = "updated : $timeInStandard"
            val total = stateDataObject.getJSONObject("total")

                val population =  meta.getString("population")
                populationT.text = "आबादी : $population"
            val confirmedCases = total.getString("confirmed")
            val deceasedCases = total.getString("deceased")
            val recoveredCases = total.getString("recovered")
            val tested = total.getString("tested")




                confirmedT.text = "कंफर्म मामले : $confirmedCases"
                deceasedT.text = "कुल मौतें : $deceasedCases"
                testedT.text = "नमूने जांचे गए : $tested"
                deltaConfirmedT.text = "delta कंफर्म मामले : $deltaConfirmedCases"
                recoveredT.text = "कुल ठीक हो चुके : $recoveredCases"
                deltaConfirmedT.text = "delta कंफर्म मामले : $deltaConfirmedCases"

            },
            {
                Toast.makeText(this, "Error in the hood boys!", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun DistrictWiseDataPressed(view: View) {
        val intent = Intent(this, DistrictDataActivity::class.java)
        startActivity(intent)
    }

}