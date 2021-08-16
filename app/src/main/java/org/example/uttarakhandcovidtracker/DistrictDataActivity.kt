package org.example.uttarakhandcovidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest

class DistrictDataActivity : AppCompatActivity() {
    private lateinit var mAdapter : DistrictDataAdapter
    val string = arrayListOf("Almora", "Bageshwar", "Chamoli", "Champawat", "Dehradun", "Haridwar", "Nainital", "Pauri Garhwal", "Pithoragarh", "Rudraprayag", "Tehri Garhwal", "Udham Singh Nagar", "Uttarkashi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_district_data)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = DistrictDataAdapter()
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        val url = "https://data.covid19india.org/v4/min/data.min.json"
        val jsonArrayRequest = JsonObjectRequest (
        Request.Method.GET, url, null,
            {
                val districtArray = ArrayList<District>()

                val jsonObject = it.getJSONObject("UT")
                val jsonArray_districts  = jsonObject.getJSONObject("districts")

                for(i in 0 until jsonArray_districts.length()) {
                    val jsonObject_district = jsonArray_districts.getJSONObject(string[i])
                    val jsonObjectDistrictTotal = jsonObject_district.getJSONObject("total")
                    val meta = jsonObject_district.getJSONObject("meta")
                    val population = meta.getString("population")
                    val district = District(
                        string[i],
                        jsonObjectDistrictTotal.getString("tested"),
                        jsonObjectDistrictTotal.getString("confirmed"),
                        jsonObjectDistrictTotal.getString("deceased"),
                        jsonObjectDistrictTotal.getString("recovered"),
                        population
                    )
                    districtArray.add(district)
                }
                mAdapter.updateDistricts(districtArray)

            },
            {
                Toast.makeText(this, "error in the hood boys!", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)

    }
}