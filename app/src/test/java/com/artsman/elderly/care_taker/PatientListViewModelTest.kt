package com.artsman.elderly.care_taker

import androidx.lifecycle.Observer
import com.artsman.elderly.InstantExecutorExtension
import com.artsman.elderly.care_taker.PatientListViewModel.States
import com.artsman.elderly.patient_info.PatientInfoRepository
import com.artsman.elderly.patient_info.PatientItem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
internal class PatientListViewModelTest{

    @Mock
    lateinit var observer: Observer<States>

    @Mock
    lateinit var repo: PatientInfoRepository
    lateinit var viewModel: PatientListViewModel

    val sampleItem= PatientItem(id = "id", name = "aqdas", photoUrl = "url")
    @BeforeEach
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel= PatientListViewModel(repo)
    }

    @Test
    fun  `on action start it must return loading state`(){
        Mockito.`when`(repo.getPatientList()).thenReturn(listOf(sampleItem))
        viewModel.subscribe().observeForever(observer)
        viewModel.setAction(PatientListViewModel.Actions.Start)


        Mockito.verify(observer).onChanged(States.Loading)
        Mockito.verify(repo).getPatientList()
        Mockito.verify(observer).onChanged(States.Loaded(listOf(sampleItem)))
    }
}