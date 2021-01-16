package com.artsman.elderly.care_taker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artsman.elderly.R
import com.artsman.elderly.patient_info.AssetPatientRepository
import com.artsman.elderly.patient_info.PatientInfoRepository


class PatientListFragment : Fragment() {

    lateinit var viewModel: PatientListViewModel
    lateinit var repo: PatientInfoRepository
    lateinit var rootView: View;
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: PatientAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = AssetPatientRepository(requireContext())
        viewModel= PatientListViewModel(repo)
        mAdapter= PatientAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_patient_list, container, false)
        initialiseRecyclerView()
        return rootView
    }

    private fun initialiseRecyclerView() {
        recyclerView= rootView.findViewById(R.id.list_patient)
        recyclerView.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter= mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.subscribe().observe(requireActivity(), Observer {
            when(it){
                is PatientListViewModel.States.Loaded -> {
                    mAdapter.setData(it.items)
                }
                PatientListViewModel.States.Loading -> showToast("Loading...")
            }
        })
        viewModel.setAction(PatientListViewModel.Actions.Start)
    }

    private fun showToast(s: String) {
        rootView.findViewById<TextView>(R.id.text).setText(s)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PatientListFragment().apply {

            }
    }
}

/*
Todo
1. Declare RecyclerView in XML : Done
2. Initialise RecyclerView in View Code : Done
3. Create Adapter Class: Done
3.1 Create ViewHolder in Adapter Class: Done
4. Attach Adapter to RecyclerView: Done
5. Set data: Done
* */