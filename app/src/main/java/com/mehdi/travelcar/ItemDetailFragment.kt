package com.mehdi.travelcar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mehdi.travelcar.carset.data.CarSet
import com.mehdi.travelcar.databinding.ActivityMainBinding
import com.mehdi.travelcar.databinding.ItemDetailFragmentBinding
import com.mehdi.travelcar.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_holder.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: CarSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                val json = Json(JsonConfiguration.Stable)
                item = json.parse(CarSet.serializer(), it.get(ARG_ITEM_ID).toString())
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
