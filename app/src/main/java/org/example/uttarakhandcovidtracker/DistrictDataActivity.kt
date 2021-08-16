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
        val url = "https://api.covid19india.org/v2/state_district_wise.json"
        val jsonArrayRequest = JsonArrayRequest (
        Request.Method.GET, url, null,
            {
                val districtArray = ArrayList<District>()

                val jsonObject = it.getJSONObject(35)
                val jsonArray_districts  = jsonObject.getJSONArray("districtData")

                for(i in 0 until jsonArray_districts.length()) {
                    val jsonObject_district = jsonArray_districts.getJSONObject(i)
                    val district = District(
                        jsonObject_district.getString("district"),
                        jsonObject_district.getString("active"),
                        jsonObject_district.getString("confirmed"),
                        jsonObject_district.getString("deceased"),
                        jsonObject_district.getString("recovered")
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