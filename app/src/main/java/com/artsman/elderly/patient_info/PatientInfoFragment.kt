package com.artsman.elderly.patient_info

import android.os.Bundle
import android.transition.Scene
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.Debug.getState
import androidx.lifecycle.Observer
import com.artsman.elderly.patient_info.State.LoadedState
import com.artsman.elderly.R
import com.artsman.elderly.States
import com.artsman.elderly.databinding.FragmentGuardianRegistrationBinding
import kotlinx.android.synthetic.main.fragment_patient_info.*
import kotlinx.android.synthetic.main.patient_bio_layout.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PatientInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    // private lateinit var viewModel: PatientViewModel
    // private var patientInfoObserver = Observer<State> { s ->
    //   getState(s)
    //}


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    var mBinding: FragmentGuardianRegistrationBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentGuardianRegistrationBinding.inflate(layoutInflater)
        // viewModel = PatientViewModel(AssetPatientRepository(requireContext()))
        //viewModel.getState().observe(this, patientInfoObserver)
        loadeddState()
        return mBinding?.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PatientInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PatientInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun loadeddState() {
        mBinding?.sceneRoot?.let {
            val patientBio = Scene.getSceneForLayout(
                it as ViewGroup?,
                R.layout.patient_bio_layout,
                requireContext()
            )
            patientBio.enter()

        }

    }


}


//private var patientInfoObserver = Observer<State> { s ->
//  when (s) {

//    State.LoadingState -> {
//      if (s == State.LoadingState) {
//        progress_bar.visibility = View.VISIBLE

//            } else {
//              progress_bar.visibility = View.GONE
//        }
//
//      }
//    is LoadedState ->{
//      loadedState()
//}
// }
//}
//private var patientInfoObserver = Observer<State> { s ->
//  getState(s)

//}

//fun getState(state:State){
//if(state==State.LoadingState) {
//  progress_bar.visibility = View.VISIBLE
//} else if(state==LoadedState){
//  progress_bar.visibility=View.GONE
//    loadedState()
//  }
//}

//fun loadingState() {
//  val load =
//    Scene.getSceneForLayout(mSceneRoot as ViewGroup?, R.layout.fragment_patient_info, requireContext())
// load.enter()
//}
//fun getState(state:State){
//  if(state==State.LoadingState) {
//    loadingState()
//} else if(state==LoadedState){
//  loadedState()
//}
//}


