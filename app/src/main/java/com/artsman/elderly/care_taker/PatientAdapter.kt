package com.artsman.elderly.care_taker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artsman.elderly.R
import com.artsman.elderly.patient_info.PatientItem

class PatientAdapter : RecyclerView.Adapter<PatientAdapter.PatientItemViewHolder>() {


    private var currentList= listOf<PatientItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient_care_taker, parent, false)
        return PatientItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PatientItemViewHolder, position: Int) {
        val itemData= currentList[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setData(items: List<PatientItem>){
        currentList= items
    }


    class PatientItemViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView){
        private var txtName: TextView = itemView.findViewById<TextView>(R.id.txtName)
        private var imgAvatar: ImageView = itemView.findViewById<ImageView>(R.id.img_avatar)

        fun bind(data: PatientItem){
            txtName.text = data.name
            imgAvatar.setImageResource(R.drawable.ic_launcher_background)
        }

    }
}

