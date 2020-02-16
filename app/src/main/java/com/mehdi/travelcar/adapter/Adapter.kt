package com.mehdi.travelcar.adapter

import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
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
import com.mehdi.travelcar.entities.CarEntity
import com.mehdi.travelcar.databinding.ItemHolderBinding
import kotlinx.android.synthetic.main.item_holder.view.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var cars = listOf<CarEntity>()
    private var carsNotFiltered = listOf<CarEntity>()

    private var filterText: String = ""

    private lateinit var context: Context

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as CarEntity
            val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                val json = Json(JsonConfiguration.Stable)
                putExtra(ItemDetailFragment.ARG_ITEM_ID, json.stringify(CarEntity.serializer(), item))
            }
            v.context.startActivity(intent)
        }
    }

    fun setData(dataList: List<CarEntity>) {
        cars = emptyList()
        cars = dataList
        carsNotFiltered = dataList
        notifyDataSetChanged()
    }

    fun filterData(text : String) {
        filterText = text.toLowerCase()
        cars = carsNotFiltered.filter { s -> s.make!!.contains(text, ignoreCase = true) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var binding: ViewDataBinding? = DataBindingUtil.inflate<ItemHolderBinding>(inflater, R.layout.item_holder, parent, false)

        context = parent.context

        return ViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, cars[position], filterText)

        val item = cars[position]
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, dataObject: CarEntity, filterText: String) {

            if (filterText.isNotEmpty()) {
                val spannable = SpannableString(dataObject.make)
                spannable.setSpan(
                    BackgroundColorSpan(context.resources.getColor(R.color.colorAccent)),
                    dataObject.make!!.toLowerCase().indexOf(filterText), dataObject.make!!.toLowerCase().indexOf(filterText) + filterText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                binding.root.make.text = spannable
            }
            else {
                binding.root.make.text = dataObject.make
            }

            with(binding) {
                setVariable(BR.car, dataObject)
                executePendingBindings()
            }
        }
    }
}