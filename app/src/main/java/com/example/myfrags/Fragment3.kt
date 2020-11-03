package com.example.myfrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.lang.NumberFormatException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment() {
    private lateinit var fragsData: FragsData
    private lateinit var numberObserver: Observer<String>

    private lateinit var button: Button
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_3, container, false)

        text = view.findViewById(R.id.current) as TextView
        button = view.findViewById(R.id.button_decrease) as Button

        fragsData = ViewModelProvider(requireActivity()).get(FragsData::class.java)
        numberObserver = Observer {
            text.text = it
        }

        fragsData.counter.observe(viewLifecycleOwner, numberObserver)

        button.setOnClickListener {
            val i: String? = try {
                (fragsData.counter.value?.toInt()!! - 1).toString()
            }catch (ex: NumberFormatException){
                fragsData.counter.value
            }
            fragsData.counter.value = i
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
