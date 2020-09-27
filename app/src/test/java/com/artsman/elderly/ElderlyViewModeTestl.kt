package com.artsman.elderly

import androidx.lifecycle.Observer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
internal class ElderlyViewModeTestl {

    @Mock
    lateinit var mockObserver: Observer<States>

    @BeforeEach
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `start and load choose user type screen`(){
        val viewModel=ElderlyViewModel()

        viewModel.getState().observeForever(mockObserver)

        Mockito.verify(mockObserver).onChanged(States.choose_user_state)
        //assertEquals(States.choose_user_state, viewModel.getState())
        viewModel.setAction(Action.back_action)
        Mockito.verify(mockObserver).onChanged(States.kill_state)
        //assertEquals(States.kill_state, viewModel.getState())
    }

    @Test
    fun `choosing guardian option`(){
        val viewModel=ElderlyViewModel()
        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.guardian_button_action)
        Mockito.verify(mockObserver).onChanged(States.guardian_registration_name_email_state)
        //assertEquals(States.guardian_registration_name_email_state, viewModel.getState())
        viewModel.setAction(Action.back_action)
        Mockito.verify(mockObserver, times(2)).onChanged(States.choose_user_state)
        //assertEquals(States.choose_user_state, viewModel.getState())
    }

    @Test
    fun `after filling name contact proceed to address screen`(){
        val viewModel=ElderlyViewModel()
        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.name_email_next_action)
        Mockito.verify(mockObserver).onChanged(States.adding_guardian_address_contact_state)
        //assertEquals(States.adding_guardian_address_contact_state, viewModel.getState())
        viewModel.setAction(Action.back_action)
        Mockito.verify(mockObserver).onChanged(States.guardian_registration_name_email_state)
        //assertEquals(States.guardian_registration_name_email_state, viewModel.getState())
    }
    @Test
    fun `after adding contact address proceed to set password and log`(){
        val viewModel=ElderlyViewModel()
        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.proceed_to_password_login_action)
        Mockito.verify(mockObserver).onChanged(States.guardian_login_state)
        //assertEquals(States.guardian_login_state, viewModel.getState())
        //assertEquals(States.choose_user_state, viewModel.getState())
    }

    @Test
    fun `choosing patient as user type`(){
        val viewModel=ElderlyViewModel()
        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.patient_user_type_action)
        Mockito.verify(mockObserver).onChanged(States.patient_login_states)
        //assertEquals(States.patient_login_states, viewModel.getState())
        viewModel.setAction(Action.back_action)
        Mockito.verify(mockObserver, times(2)).onChanged(States.choose_user_state)
        //assertEquals(States.choose_user_state, viewModel.getState())
    }

    @Test
    fun `after patient log in proceed to add guardian`(){
        val viewModel=ElderlyViewModel()
        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.add_guardian_action)
        Mockito.verify(mockObserver).onChanged(States.add_guardian_state)
        //assertEquals(States.add_guardian_state, viewModel.getState())

        //assertEquals(States.choose_user_state, viewModel.getState())
    }

    @Test
    fun `after guardian log in add event`(){
        val viewModel=ElderlyViewModel()
        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.log_in_action)
        Mockito.verify(mockObserver).onChanged(States.add_event_state)
    }

    @Test
    fun `itcangoback`(){
        val viewModel=ElderlyViewModel()
        viewModel.getState().observeForever(mockObserver)
        Mockito.verify(mockObserver).onChanged(States.choose_user_state)
        viewModel.setAction(Action.guardian_button_action)
        Mockito.verify(mockObserver).onChanged(States.guardian_registration_name_email_state)
        viewModel.setAction(Action.back_action)
        Mockito.verify(mockObserver, times(2)).onChanged(States.choose_user_state)
        viewModel.setAction(Action.log_in_action)
        Mockito.verify(mockObserver).onChanged(States.add_event_state)
        viewModel.setAction(Action.back_action)
        Mockito.verify(mockObserver).onChanged(States.guardian_login_state)
    }
}

