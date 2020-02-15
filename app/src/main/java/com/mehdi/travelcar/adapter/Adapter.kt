package com.mehdi.travelcar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mehdi.travelcar.BR
import com.mehdi.travelcar.ItemDetailActivity
import com.mehdi.travelcar.ItemDetailFragment
import com.mehdi.travelcar.R
import com.mehdi.travelcar.carset.data.CarSet
import com.mehdi.travelcar.databinding.ItemHolderBinding
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var cars = listOf<CarSet>()


    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as CarSet
            val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                val json = Json(JsonConfiguration.Stable)
                putExtra(ItemDetailFragment.ARG_ITEM_ID, json.stringify(CarSet.serializer(), item))
            }
            v.context.startActivity(intent)
        }
    }

    fun setData(dataList: List<CarSet>) {
        cars = emptyList()
        cars = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding? = DataBindingUtil.inflate<ItemHolderBinding>(inflater, R.layout.item_holder, parent, false)

        return ViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cars[position])

        val item = cars[position]
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataObject: CarSet) {

            with(binding) {
                setVariable(BR.car, dataObject)
                executePendingBindings()
            }
        }
    }
}