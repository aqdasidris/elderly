
package com.artsman.elderly.add_patient

import android.content.Intent
import android.os.Bundle
import android.transition.Scene
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.artsman.elderly.R
import com.artsman.elderly.patient_list.PatientListFragment
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPatientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPatientFragment : Fragment() {
    internal lateinit var mSceneRoot:View
    private lateinit var viewModel:AddPatientViewModel
    private val addPatientObserver=Observer<AddPatientViewModel.States>{s->
        setState(s)
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_add_patient, container, false)
        mSceneRoot=view.findViewById<View>(R.id.sceneRoot)
        viewModel= AddPatientViewModel()
        viewModel.getState().observe(this,addPatientObserver)
        return view
    }
    fun configureAddPatientScreen(){
        val addPatientScreen=Scene.getSceneForLayout(mSceneRoot as ViewGroup?,R.layout.add_patient_layout,requireContext())
        addPatientScreen.enter()
        val addPatientbtn=mSceneRoot.findViewById<Button>(R.id.btn_proceed_backto_patientlist)
        addPatientbtn.setOnClickListener {
            loadBackToPatientlist()
        }
    }

    fun setState(state: AddPatientViewModel.States){
        if(state== AddPatientViewModel.States.LoadedState){
            configureAddPatientScreen()
        }
    }

    fun loadBackToPatientlist(){
        val fragmentManager=getFragmentManager()
        val fragmentTransaction=fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.sceneRoot,  PatientListFragment())
        fragmentTransaction?.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPatientFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPatientFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}