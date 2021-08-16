package org.example.uttarakhandcovidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DistrictDataAdapter: RecyclerView.Adapter<DistrictDataViewHolder>() {
    private val items : ArrayList<District>  = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_district_data, parent, false)

        return DistrictDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistrictDataViewHolder, position: Int) {
        val currentItem = items[position]
        val a = currentItem.tested
        val c = currentItem.confirmed
        val d = currentItem.deceased
        val r = currentItem.recovered
        val p = currentItem.population

        holder.district.text = currentItem.district_name
        holder.tested.text = "नमूने जांचे गए : $a"
        holder.confirmed.text = "कंफर्म मामले : $c "
        holder.deceased.text = "कुल मौतें: $d"
        holder.recovered.text = "कुल ठीक हो चुके: $r"
        holder.population.text = "आबादी : $p"
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateDistricts(updatedDistricts : ArrayList<District>) {
        items.clear()
        items.addAll(updatedDistricts)

        notifyDataSetChanged()
    }


}

class DistrictDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val district: TextView = itemView.findViewById(R.id.district_name)
    val tested : TextView = itemView.findViewById(R.id.tested)
    val confirmed : TextView = itemView.findViewById(R.id.confirmed)
    val deceased  : TextView = itemView.findViewById(R.id.deceased)
    val recovered : TextView = itemView.findViewById(R.id.recovered)
    val population : TextView = itemView.findViewById(R.id.population)
}