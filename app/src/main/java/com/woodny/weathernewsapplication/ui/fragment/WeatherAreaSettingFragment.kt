package com.woodny.weathernewsapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.woodny.weathernewsapplication.R
import com.woodny.weathernewsapplication.databinding.FragmentWeatherAreaSettingBinding
import com.woodny.weathernewsapplication.viewmodel.WeatherAreaSettingFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherAreaSettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class WeatherAreaSettingFragment : Fragment() {
    private val viewModel: WeatherAreaSettingFragmentViewModel by viewModels()
    private lateinit var binding: FragmentWeatherAreaSettingBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherAreaSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherAreaSettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherAreaSettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // LiveDataのデータをバインドする際にうまく通知させるようにする
        binding.lifecycleOwner = viewLifecycleOwner
        // レイアウトで定義したviewModelに実際のviewModelの参照を渡す
        binding.viewmodel = viewModel

        viewModel.navigate.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    "ToMain" ->
                        findNavController().navigate(R.id.action_weatherAreaSettingFragment_to_mainFragment)
                }
            }
        }

    }
}