package com.mehdi.travelcar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mehdi.travelcar.entities.CarEntity
import com.mehdi.travelcar.databinding.ItemDetailFragmentBinding
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    private var item: CarEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                val json = Json(JsonConfiguration.Stable)
                item = json.parse(CarEntity.serializer(), it.get(ARG_ITEM_ID).toString())
                activity?.toolbar_layout?.title = item?.make
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil
            .inflate< ItemDetailFragmentBinding >(
                inflater,
                R.layout.item_detail_fragment,
                container,
                false
            )

        binding.setVariable(BR.carObject, item)

        for (value in item?.equipments.orEmpty()) {
            when (value) {
                "GPS" -> binding.gps.visibility = View.VISIBLE
                "Siege enfant" -> binding.siege.visibility = View.VISIBLE
                "Assistance 24h/24" -> binding.assistance.visibility = View.VISIBLE
                "Climatisation" -> binding.freezer.visibility = View.VISIBLE
                "ABS" -> binding.abs.visibility = View.VISIBLE
                "Airbags" -> binding.airbag.visibility = View.VISIBLE
            }
        }

        return binding.root

    }

    companion object{
        const val ARG_ITEM_ID = "item_id"

        @JvmStatic
        @BindingAdapter("app:load_image")
        fun setImageUrl(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl).apply(RequestOptions().fitCenter())
                .into(view)
        }
    }
}