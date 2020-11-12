package com.artsman.elderly

import androidx.lifecycle.Observer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
internal class PatientViewModelTest {
    @Mock
    lateinit var mockObserver: Observer<State>

    @Mock
    lateinit var mockRepo: PatientInfoRepository

    lateinit var viewModel: PatientViewModel


    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = PatientViewModel(mockRepo)

    }

    @Test
    fun `after start state load load state`() {

        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(PAction.start_action)
        Mockito.verify(mockObserver).onChanged(State.LoadingState)
        Mockito.verify(mockRepo).fetchPatient()
    }

    @Test
    fun `Data should be loaded from repository`() {
        Mockito.`when`(mockRepo.fetchPatient()).thenReturn(sample_data)
        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(PAction.start_action)
        Mockito.verify(mockObserver).onChanged(State.LoadingState)
        Mockito.verify(mockRepo).fetchPatient()
        Mockito.verify(mockObserver).onChanged(State.LoadedState(sample_data))
    }









val sample_data="""{
  "id" : "12456",
  "name": "owais idris",
  "contact_info": {
    "Mobile_Number": "9870792198",
    "email": "owaistnt@gmail.com",
    "landline_number":"23016368"
  },
  "Emergency_Contacts":[
      {
        "name": "Aqdas Idris",
        "contact_number":"8828151521"
    },
    {
        "name": "Anas Idris",
        "contact_number":"7208101921"
    },
    {
        "name": "Mohd Idris",
        "contact_number":"9969376817"
    },
    {
        "name": "Khaleda Idris",
        "contact_number":"9869365398",
        "contact_number_2":"913743538"
        
    }
    ],
    "Bio":{
    "age":"30",
    "weight":"80kg",
    "medical conditions":["obesity", "constipation"]
    },
    "address":{
      "line_1":"1202, ahmed tower",
      "line_2":"madhav rao gagan marg",
      "city":"mumbai",
      "district":"byculla (w)",
      "pincode": "400011"
    },
    "Guardian_code":"4xy2ow"
}
"""
}
