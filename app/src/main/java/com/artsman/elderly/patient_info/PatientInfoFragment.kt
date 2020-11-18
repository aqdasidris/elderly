package com.artsman.elderly.patient_info

import android.os.Bundle
import android.transition.Scene
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.artsman.elderly.patient_info.State.LoadedState
import com.artsman.elderly.R
import com.artsman.elderly.databinding.FragmentPatientInfoBinding
import kotlinx.android.synthetic.main.fragment_patient_info.*

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
    private lateinit var viewModel: PatientViewModel


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    var mBinding: FragmentPatientInfoBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentPatientInfoBinding.inflate(layoutInflater)

        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = PatientViewModel(AssetPatientRepository(requireContext()))
        viewModel.getState().observe(this, patientInfoObserver)
        viewModel.setAction(PAction.start_action)
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

    fun loadeddState(data: PatientInfo) {
        mBinding?.sceneRoot?.let {
            val patientBio = Scene.getSceneForLayout(
                it as ViewGroup?,
                R.layout.patient_bio_layout,
                requireContext()
            )
            patientBio.enter()
            patientBio.sceneRoot.findViewById<TextView>(R.id.txtName).text=data.name

        }

    }

    private var patientInfoObserver = Observer<State> { s ->
        Log.d("PatientInfo", "${s.toString()} ")
        when (s) {

            State.LoadingState -> {
                progress_bar.visibility = View.VISIBLE
            }
            is LoadedState -> {
                progress_bar.visibility = View.GONE
                loadeddState(s.data)
            }
        }
    }


}




