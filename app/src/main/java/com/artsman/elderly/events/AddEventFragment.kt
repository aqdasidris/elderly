package com.artsman.elderly.events

import android.os.Bundle
import android.transition.Scene
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.artsman.elderly.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddEventFragment : Fragment() {
    private lateinit var rSceneRoot: View
    private lateinit var viewModel: AddEventViewModel
    private val eventObserver = Observer<AddEvent>{ s->
        setEventState(s)
    }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel= AddEventViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=  inflater.inflate(R.layout.fragment_add_event2, container, false)
        viewModel.setEventState().observe(this, eventObserver)
        rSceneRoot=view.findViewById<View>(R.id.addEventSceneRoot)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddEventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddEventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun addEventScreenConfiguration(){
        val addEventScreen=Scene.getSceneForLayout(rSceneRoot as ViewGroup?, R.layout.layout_add_event_guardian, requireContext())
        addEventScreen.enter()
        val btnAddEvent= rSceneRoot.findViewById<Button>(R.id.btn_add_event)
        btnAddEvent.setOnClickListener { viewModel.setEventAction(EventAction.add_event_action) }
    }

    fun sucessSceneConfiguration(){
        val successScreen=Scene.getSceneForLayout(rSceneRoot as ViewGroup?,R.layout.event_add_success, requireContext())
        successScreen.enter()
    }

    private fun setEventState(addEvent: AddEvent){
        if(addEvent==AddEvent.addEventState){
            addEventScreenConfiguration()
        }
        else if(addEvent==AddEvent.success_state){
            sucessSceneConfiguration()
        }
    }


}