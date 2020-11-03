package com.example.myfrags

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.lang.NumberFormatException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment4.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment4 : Fragment() {

    private lateinit var fragsData: FragsData
    private lateinit var numberObserver: Observer<String>
    private lateinit var editText: EditText
    private lateinit var textWatcher: TextWatcher
    private var turnOffWatcher: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_4, container, false)

        editText = view.findViewById(R.id.editTextNumber)
        fragsData = ViewModelProvider(requireActivity()).get(FragsData::class.java)
        numberObserver = Observer { newString ->
            turnOffWatcher = true
            editText.setText(newString.toString())
        }
        fragsData.counter.observe(viewLifecycleOwner, numberObserver)

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!turnOffWatcher) {
                    if (s.isNotEmpty() && s[0] == '-'){
                        val i:String = try {
                            s.substring(1,s.length).toInt()
                            s.substring(0,s.length)
                        }catch (ex: NumberFormatException){
                            fragsData.counter.value!!
                        }
                        fragsData.counter.value = i
                    }
                    else {
                        fragsData.counter.value = s.toString()
                    }

                } else {
                    turnOffWatcher = !turnOffWatcher
                }
            }
        }

        editText.addTextChangedListener(textWatcher)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
