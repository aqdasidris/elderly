package com.artsman.elderly.patient_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artsman.elderly.R
import com.artsman.elderly.patient_info.PatientItem
import com.artsman.elderly.utils.PicassoCircleTransformation
import com.squareup.picasso.Picasso
import okhttp3.Cache.key
import java.util.*

class PatientAdapter : RecyclerView.Adapter<PatientAdapter.PatientItemViewHolder>() {

    private var currentList= listOf<PatientListItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientItemViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patients, parent, false)
        return PatientItemViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: PatientItemViewHolder, position: Int) {
        val itemData= currentList[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setData(items: List<PatientListItem>){
        currentList= items
        notifyDataSetChanged()
    }


    class PatientItemViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView){
        private var txtName: TextView = itemView.findViewById<TextView>(R.id.txtPatientName)
        private var imgAvatar: ImageView = itemView.findViewById<ImageView>(R.id.img_avatar_patient)


        fun bind(data: PatientListItem){
            txtName.text = data.name

            Picasso.get().load(data.photoUrl).transform(PicassoCircleTransformation()).into(imgAvatar)


        }

    }
}

