package com.artsman.elderly.care_taker

import android.app.usage.UsageEvents
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artsman.elderly.R

class EventAdapter:RecyclerView.Adapter<EventAdapter.EventItemViewHolder>() {

    var currentList= listOf<Event>()
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): EventAdapter.EventItemViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_event_list,parent,false)
        return EventItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder:EventItemViewHolder, position: Int) {
        val itemData=currentList[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setData(items:List<Event>){
        currentList=items
    }

    class EventItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private var txtEventName:TextView=itemView.findViewById<TextView>(R.id.txtEventName)
        private var txtGoal:TextView=itemView.findViewById<TextView>(R.id.txtGoal)
        private var txtSteps:TextView=itemView.findViewById<TextView>(R.id.txtSteps)
        private var imgAvatar: ImageView? =itemView.findViewById<ImageView>(R.id.img_avatar)

        fun bind(data:Event){
            txtEventName.text=data.title
            txtGoal.text=data.goal
            txtSteps.text=data.steps
            imgAvatar?.setImageResource(R.drawable.ic_launcher_background)
        }

    }

}