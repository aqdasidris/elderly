package com.artsman.elderly.care_taker

import androidx.lifecycle.Observer
import com.artsman.elderly.InstantExecutorExtension
import com.artsman.elderly.care_taker.EventListViewModel.States
import com.artsman.elderly.patient_info.PatientInfoRepository
import com.artsman.elderly.patient_info.PatientItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.OngoingStubbing

@ExtendWith(InstantExecutorExtension::class)
internal class EventListViewModelTest{

    @Mock
    lateinit var observer: Observer<States>

    @Mock
    lateinit var repo: CareTakerEventRepository
    lateinit var viewModel: EventListViewModel

    val sampleItem= PatientItem(id = "id", name = "aqdas", photoUrl = "url")
    @BeforeEach
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel= EventListViewModel(repo)
    }


}


