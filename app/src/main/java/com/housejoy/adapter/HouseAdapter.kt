package com.housejoy.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.housejoy.R
import com.housejoy.model.Project
import com.housejoy.ui.HouseDetailsActivity
import com.housejoy.utils.loadImage
import kotlinx.android.synthetic.main.adapter_note_list.view.*
import kotlinx.android.synthetic.main.content_layout_.view.*


class HouseAdapter(var list: List<Project>) : RecyclerView.Adapter<HouseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_note_list, parent, false))
    }
    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: Project) {
            itemView.apply {
                tv_projectName.text=model.projectName
                tv_sqft.text = model.area
                tv_config.text = model.config
                tv_elevation.text = model.elevation

                image.loadImage(model.imageUrl)
                itemView.setOnClickListener {
                    val sharedIntent = Intent(context, HouseDetailsActivity::class.java)
                        .putExtra("project", model)
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity,
                        Pair.create(itemView.const_lay as View, "const_lay"),
                        Pair.create(itemView.tv_projectName as View, "tv_projectName"),
                        Pair.create(itemView.tv_att as View, "tv_att"),
                        Pair.create(itemView.tv_sqft as View, "tv_sqft"),
                        Pair.create(itemView.tv_config as View, "tv_config"),
                        Pair.create(itemView.tv_elevation as View, "tv_elevation")
                        )
                    context.startActivity(sharedIntent, options.toBundle())
                }
            }
        }
    }

}
