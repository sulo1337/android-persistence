package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import com.example.android.trackmysleepquality.generated.callback.OnClickListener

class SleepNightAdapter(val clickListener: SleepNightListener): ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    //how to update the view holder
    override fun onBindViewHolder(holder: SleepNightAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position, clickListener)
    }

    //inflate the actual layout and pass it to the viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepNightAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    //creating a view holder, note: id matches with the actual layout
    //create bind function to tell how to actually bind the data item into this view holder
    //create from function to inflate the layout inside the view holder
    class ViewHolder private constructor(private val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: SleepNight, position: Int, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

//use diff util instead of parsing the whole view holders again
//uses algorithms to find minimum number of change to get to a new array
class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}