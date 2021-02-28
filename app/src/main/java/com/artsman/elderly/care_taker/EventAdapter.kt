package com.artsman.elderly.care_taker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artsman.elderly.R
import com.artsman.elderly.care_taker.repo.*
import com.artsman.elderly.core.StepDataBase

class EventAdapter : ListAdapter<UIEvent<ISupportedEvent>, RecyclerView.ViewHolder>(
    DIFFCALLBACK
) {

    val TYPE_STEPS = 0
    val TYPE_LOCATION = 1
    val TYPE_REMINDER = 2


    companion object {
        val DIFFCALLBACK = object : DiffUtil.ItemCallback<UIEvent<ISupportedEvent>>() {
            override fun areItemsTheSame(
                oldItem: UIEvent<ISupportedEvent>,
                newItem: UIEvent<ISupportedEvent>
            ): Boolean {
                return oldItem.event_id == newItem.event_id
            }

            override fun areContentsTheSame(
                oldItem: UIEvent<ISupportedEvent>,
                newItem: UIEvent<ISupportedEvent>
            ): Boolean {
                return oldItem.event_id == newItem.event_id
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentItem = getItem(position)
        return when (currentItem.event_type) {
            EventType.steps -> {
                TYPE_STEPS
            }
            EventType.location -> TYPE_LOCATION
            EventType.reminder -> TYPE_REMINDER
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var itemView: View
        return when (viewType) {
            TYPE_STEPS -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_event_list, parent, false)
                EventItemViewHolderSteps(itemView)
            }
            TYPE_LOCATION -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_event_list_location, parent, false)
                EventItemViewHolderLocation(itemView)
            }

            TYPE_REMINDER -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_event_list_reminder, parent, false)
                EventItemViewHolderReminder(itemView)
            }
            else -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_event_list, parent, false)
                EventItemViewHolderSteps(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IEventViewHolder<*>) {
            holder.bind(getItem(position) as UIEvent<Nothing>)
        }

    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    class EventItemViewHolderSteps(itemView: View) : RecyclerView.ViewHolder(itemView),
        IEventViewHolder<StepInfo> {
        private var txtEventName: TextView = itemView.findViewById<TextView>(R.id.txtEventName)
        private var txtGoal: TextView = itemView.findViewById<TextView>(R.id.txtGoal)
        private var txtSteps: TextView = itemView.findViewById<TextView>(R.id.txtSteps)
        private var imgAvatar: ImageView? = itemView.findViewById<ImageView>(R.id.img_avatar)


        override fun bind(data: UIEvent<StepInfo>) {
            txtEventName.text = data.event_name
            txtGoal.text = data.event_info.goal+" steps."
            txtSteps.text = data.event_info.steps
        }



    }

    class EventItemViewHolderLocation(itemView: View) : RecyclerView.ViewHolder(itemView),
        IEventViewHolder<LocationInfo> {
        private var txtEventName: TextView = itemView.findViewById<TextView>(R.id.txtEventName)
        private var txtGoal: TextView = itemView.findViewById<TextView>(R.id.txtGoal)
        private var txtSteps: TextView = itemView.findViewById<TextView>(R.id.txtSteps)
        private var imgAvatar: ImageView? = itemView.findViewById<ImageView>(R.id.img_avatar)

        override fun bind(data: UIEvent<LocationInfo>) {
            txtEventName.text = data.event_name
            txtGoal.text = data.event_info.title
            txtSteps.text = "${data.event_info.lat}, ${data.event_info.long}"
        }

    }


 class EventItemViewHolderReminder(itemView: View) : RecyclerView.ViewHolder(itemView),
        IEventViewHolder<ReminderInfo> {
        private var txtEventName: TextView = itemView.findViewById<TextView>(R.id.txtEventName)
        private var txtGoal: TextView = itemView.findViewById<TextView>(R.id.txtGoal)
        private var txtSteps: TextView = itemView.findViewById<TextView>(R.id.txtSteps)
        private var imgAvatar: ImageView? = itemView.findViewById<ImageView>(R.id.img_avatar)

        override fun bind(data: UIEvent<ReminderInfo>) {
            txtEventName.text = data.event_name
            txtGoal.text = data.event_info.title
            txtSteps.text = "${data.event_info.time}"
        }
    }

    interface IEventViewHolder<in T: ISupportedEvent> {
        fun bind(data: UIEvent<T>)
    }

}