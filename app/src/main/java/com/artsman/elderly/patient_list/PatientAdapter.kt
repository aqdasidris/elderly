package com.artsman.elderly.patient_list

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.artsman.elderly.R
import com.artsman.elderly.patient_activity_bio.PatientBioActivity
import com.artsman.elderly.patient_info.PatientItem
import com.artsman.elderly.patient_list.repo.UIPatient
import com.artsman.elderly.utils.PicassoCircleTransformation
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import okhttp3.Cache.key
import java.lang.Exception
import java.util.*

class PatientAdapter : RecyclerView.Adapter<PatientAdapter.PatientItemViewHolder>() {

    private var currentList= listOf<UIPatient>()
    private var mListener: IPatientClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientItemViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patients, parent, false)
        return PatientItemViewHolder(itemView, mListener)

    }


    override fun onBindViewHolder(holder: PatientItemViewHolder, position: Int) {
        val itemData= currentList[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setData(items: List<UIPatient>){
        currentList= items
        notifyDataSetChanged()
    }

    fun setPatientClickListener(listener: IPatientClickListener){
        mListener= listener
    }


    class PatientItemViewHolder(val itemView: View, val listener: IPatientClickListener?): RecyclerView.ViewHolder(itemView){
        private var txtName: TextView = itemView.findViewById<TextView>(R.id.txtPatientName)
        private var imgAvatar: ImageView = itemView.findViewById<ImageView>(R.id.img_avatar_patient)
        fun bind(data:UIPatient){
            txtName.text = data.patient_name
            imgAvatar.setOnClickListener {
                listener?.onPatientClicked(data)
            }

//            Glide.with(itemView).load(data.photoUrl).into(imgAvatar)

            Picasso.get()
                .load(data.photoUrl)
                .transform(PicassoCircleTransformation())
                .into(imgAvatar, object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        Log.e("Avatar", "onError: ${e?.message}")
                    }

                })

        }


    }

    interface IPatientClickListener{
        fun onPatientClicked(item: UIPatient)
    }
}

