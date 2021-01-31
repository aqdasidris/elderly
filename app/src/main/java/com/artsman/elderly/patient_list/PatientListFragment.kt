package com.artsman.elderly.patient_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artsman.elderly.R
import com.artsman.elderly.care_taker.EventAdapter
import com.artsman.elderly.care_taker.EventListViewModel
import com.artsman.elderly.patient_info.AssetPatientRepository
import com.artsman.elderly.patient_info.PatientInfoRepository
import com.artsman.elderly.patient_info.api.PatientInfoAPI
import com.artsman.elderly.patient_list.PatientListAPI.PatientListApi

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PatientListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientListFragment : Fragment() {
    lateinit var viewModel: PatientListViewModel
    lateinit var repo: PatientListRepository
    lateinit var rootView: View;
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: PatientAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = AssetPatientListRepository(requireContext(), PatientListApi())
        viewModel= PatientListViewModel(repo)
        mAdapter= PatientAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_list_patients, container, false)
        initialiseRecyclerView()
        return rootView
    }

    private fun initialiseRecyclerView() {
        recyclerView= rootView.findViewById(R.id.patient_list)
        recyclerView.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter= mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.subscribe().observe(requireActivity(), Observer {
            when(it){
                is PatientListViewModel.States.Loaded -> {
                    Log.d("DATA", "setData: ${it.items}")
                    mAdapter.setData(it.items)
                }
            }
        })
        viewModel.setAction(PatientListViewModel.Actions.Start)
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PatientListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PatientListFragment().apply {

            }
    }
}