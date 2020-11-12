package com.artsman.elderly
import androidx.lifecycle.Observer
import com.artsman.elderly.auth.GuardianCode
import com.artsman.elderly.auth.IRegisterationRepository
import com.artsman.elderly.auth.PatientData
import com.artsman.elderly.auth.RegistrationData
import com.artsman.elderly.events.AddEvent
import com.artsman.elderly.events.AddEventViewModel
import com.artsman.elderly.events.EventAction
import com.artsman.elderly.events.EventDataRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
internal class AddEventTest {
    @Mock
    lateinit var mockObserver: Observer<AddEvent>

    lateinit var viewModel: AddEventViewModel

    lateinit var mockRepo: EventDataRepository

    @BeforeEach
    fun setup(){
        MockitoAnnotations.initMocks(this)

    }
    @Test
    fun `add event`(){


        viewModel.setEventState().observeForever(mockObserver)

        Mockito.verify(mockObserver).onChanged(AddEvent.addEventState)
    }

    @Test
    fun `add success screen after add event action`(){

        viewModel.setEventState().observeForever(mockObserver)
        viewModel.setEventAction(EventAction.add_event_action)
        Mockito.verify(mockObserver).onChanged(AddEvent.success_state)
    }
}
