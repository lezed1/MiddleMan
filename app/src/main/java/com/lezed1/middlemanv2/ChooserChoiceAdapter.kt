package com.lezed1.middlemanv2

import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chooser_item.view.*

class ChooserChoiceAdapter(private val items: List<ChooserChoice>, private val context: Context, private val onClickListener: (chooserChoice: ChooserChoice) -> Unit) : RecyclerView.Adapter<ChooserChoiceAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.chooser_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chooserChoice = items[position]

        holder.ivActivityIcon.setImageDrawable(chooserChoice.icon)
        holder.tvActivityName.text = chooserChoice.name
        holder.tvClassName.text = chooserChoice.componentName.className

        holder.itemView.setOnClickListener { onClickListener(chooserChoice) }
    }

    data class ChooserChoice(internal val icon: Drawable, internal val componentName: ComponentName, internal val name: CharSequence)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivActivityIcon: ImageView = itemView.iv_activityIcon
        val tvActivityName: TextView = itemView.tv_activityName
        val tvClassName: TextView = itemView.tv_className
    }
}
