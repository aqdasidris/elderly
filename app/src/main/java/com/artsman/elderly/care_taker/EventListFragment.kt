package com.artsman.elderly.care_taker

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artsman.elderly.R
import com.artsman.elderly.care_taker.repo.EventRepository
import com.artsman.elderly.care_taker.repo.CareTakerEventRepository
import com.artsman.elderly.care_taker.repo.EventLocalProvider
import com.artsman.elderly.care_taker.repo.EventRemoteProvider
import com.artsman.elderly.core.DatabaseProvider
import com.artsman.elderly.reminders.AddReminderBottomSheet
import com.artsman.elderly.steps.StepBottomSheet
import com.google.android.material.floatingactionbutton.FloatingActionButton


class EventListFragment : Fragment() {

    lateinit var viewModel: EventListViewModel
    lateinit var repo: CareTakerEventRepository
    lateinit var rootView: View;
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: EventAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = EventRepository(requireContext(), EventRemoteProvider(), EventLocalProvider(
            DatabaseProvider.getInstance(requireActivity())))
        viewModel = EventListViewModel(repo)
        mAdapter = EventAdapter()
    }


    private val cta_actions=arrayOf("Add Reminder", "Add Steps")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_event_list, container, false)
        initialiseRecyclerView()
        rootView.findViewById<FloatingActionButton>(R.id.btn_add_event).setOnClickListener {
            AlertDialog
                .Builder(requireContext())
                .setItems(cta_actions) { dialog, position ->
                    Toast.makeText(requireContext(), "${cta_actions[position]} Selected", Toast.LENGTH_SHORT).show()
                    when(position){
                        0 -> {
                            AddReminderBottomSheet.getInstance().let {
                                it.setReminderInputCompleteCallback {
                                    Log.d("DT", "onCreateView: Reminder ${it.message} ${it.dateTime}")
                                }
                                it.show(childFragmentManager, "add_reminder")
                            }


                        }
                        1->{
                            StepBottomSheet.getInstance().let {
                                it.setStepCallBack {
                                    Log.d("Staps", "StepInfo${it.step}")
                                }
                                it.show(childFragmentManager,"add steps")
                            }
                        }
                        else-> {}
                    }
                }.show()



        }
        return rootView
    }

    private fun initialiseRecyclerView() {
        recyclerView = rootView.findViewById(R.id.list_patient)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.subscribe().observe(requireActivity(), Observer {
            when (it) {
                is EventListViewModel.States.Loaded -> {
                    d("DATA", "setData: ${it.items}")
                    mAdapter.setData(it.items)
                }
                //EventListViewModel.States.Loading -> showToast("Loading...")
            }
        })
        viewModel.setAction(EventListViewModel.Actions.Start)

    }

//    private fun showToast(s: String) {
//        rootView.findViewById<TextView>(R.id.text).setText(s)
//    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EventListFragment().apply {

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