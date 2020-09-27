package com.artsman.elderly

import androidx.lifecycle.Observer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
internal class ElderlyViewModeTest {

    @Mock
    lateinit var mockObserver: Observer<States>

    @Mock
    lateinit var mockRepo: IRegisterationRepository

    lateinit var viewModel: ElderlyViewModel

    @BeforeEach
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel=ElderlyViewModel(mockRepo)

    }

    @Test
    fun `start and load choose user type screen`(){


        viewModel.getState().observeForever(mockObserver)

        Mockito.verify(mockObserver).onChanged(States.choose_user_state)
        //assertEquals(States.choose_user_state, viewModel.getState())
        viewModel.setAction(Action.back_action)
        Mockito.verify(mockObserver).onChanged(States.kill_state)
        //assertEquals(States.kill_state, viewModel.getState())
    }

    @Test
    fun `choosing guardian option`(){

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
        val registationObject=RegistrationData(name = "Aqdas", phone = "912345678" , address = "Ahmed Tower", email = "aqdas@gmail.com")
        viewModel.getState().observeForever(mockObserver)

        viewModel.putValue("name", "Aqdas")
        viewModel.putValue("address", "Ahmed Tower")
        viewModel.putValue("email", "aqdas@gmail.com")
        viewModel.putValue("phone", "912345678")

        viewModel.setAction(Action.proceed_to_password_login_action)

        Mockito.verify(mockRepo, atLeastOnce()).saveRegistration(registationObject)
        Mockito.verify(mockObserver).onChanged(States.guardian_login_state)
        //assertEquals(States.guardian_login_state, viewModel.getState())
        //assertEquals(States.choose_user_state, viewModel.getState())
    }

    @Test
    fun `choosing patient as user type`(){

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

        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.add_guardian_action)
        Mockito.verify(mockObserver).onChanged(States.add_guardian_state)
        //assertEquals(States.add_guardian_state, viewModel.getState())

        //assertEquals(States.choose_user_state, viewModel.getState())
    }

    @Test
    fun `after guardian log in add event`(){

        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.log_in_action)
        Mockito.verify(mockObserver).onChanged(States.add_event_state)
    }

    @Test
    fun `itcangoback`(){

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

