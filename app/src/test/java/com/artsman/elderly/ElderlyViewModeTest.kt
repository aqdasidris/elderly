package com.artsman.elderly

import androidx.lifecycle.Observer
import com.artsman.elderly.auth.GuardianCode
import com.artsman.elderly.auth.IRegisterationRepository
import com.artsman.elderly.auth.PatientData
import com.artsman.elderly.auth.RegistrationData
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
    fun `given start action it must load user type state`(){


        viewModel.getState().observeForever(mockObserver)

        Mockito.verify(mockObserver).onChanged(States.user_type_state)
        //assertEquals(States.user_type_state, viewModel.getState())
        viewModel.setAction(Action.BackAction)
        Mockito.verify(mockObserver).onChanged(States.kill_state)
        //assertEquals(States.kill_state, viewModel.getState())
    }

    @Test
    fun `given GuardianAction it must load personal info state and on back action must return to user type state `(){

        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.GuardianButtonAction)
        Mockito.verify(mockObserver).onChanged(States.guardian_registration_personal_info_state)
        //assertEquals(States.guardian_registration_personal_info_state, viewModel.getState())
        viewModel.setAction(Action.BackAction)
        Mockito.verify(mockObserver, times(2)).onChanged(States.user_type_state)
        //assertEquals(States.user_type_state, viewModel.getState())
    }

    @Test
    fun `given action for name and email it must load contact info state`(){

        viewModel.getState().observeForever(mockObserver)


        viewModel.setAction(Action.NameEmailNextAction)


        Mockito.verify(mockObserver).onChanged(States.adding_guardian_contact_info_state)




        //assertEquals(States.adding_guardian_address_contact_state, viewModel.getState())
        viewModel.setAction(Action.BackAction)
        Mockito.verify(mockObserver).onChanged(States.guardian_registration_personal_info_state)
        //assertEquals(States.guardian_registration_personal_info_state, viewModel.getState())
    }


    @Test
    fun `given contact information when proceed to signup action is performed then load guardian signup state`(){
        val registationObject= RegistrationData(name = "Aqdas", phone = "912345678" , address = "Ahmed Tower", email = "aqdas@gmail.com", pincode="400008")
        viewModel.getState().observeForever(mockObserver)

        viewModel.putValue("name", "Aqdas")
        viewModel.putValue("address", "Ahmed Tower")
        viewModel.putValue("email", "aqdas@gmail.com")
        viewModel.putValue("phone", "912345678")
        viewModel.putValue("pincode", "400008")

        viewModel.setAction(Action.ProceedToPasswordLoginAction)

        Mockito.verify(mockRepo, atLeastOnce()).saveRegistration(registationObject)
        Mockito.verify(mockObserver).onChanged(States.guardian_login_state)
        //assertEquals(States.guardian_login_state, viewModel.getState())
        //assertEquals(States.user_type_state, viewModel.getState())
    }

    @Test
    fun `choosing patient as user type`(){

        viewModel.getState().observeForever(mockObserver)
        viewModel.setAction(Action.PatientUserTypeAction)

        Mockito.verify(mockObserver).onChanged(States.patient_login_state)
        //assertEquals(States.patient_login_state, viewModel.getState())
        viewModel.setAction(Action.BackAction)
        Mockito.verify(mockObserver, times(2)).onChanged(States.user_type_state)
        //assertEquals(States.user_type_state, viewModel.getState())
    }

    @Test
    fun `after patient log in proceed to add guardian`(){
        val getPatientEmail= PatientData(mail= "aqdas.idris@gmail.com")


        viewModel.getState().observeForever(mockObserver)
        viewModel.putValue("mail", "aqdas.idris@gmail.com")


        viewModel.setAction(Action.AddGuardianAction)
        Mockito.verify(mockRepo, atLeastOnce()).getPatientMail(getPatientEmail)
        Mockito.verify(mockObserver).onChanged(States.add_guardian_state)
        //assertEquals(States.add_guardian_state, viewModel.getState())

        //assertEquals(States.user_type_state, viewModel.getState())
    }

    @Test
    fun `after guardian log in add event`(){
        val guardianLoginData= GuardianCreds(email="aqdas.idris@gmail.com", setPassword="aqdas", confirmPassword="aqdas")

        viewModel.getState().observeForever(mockObserver)
        viewModel.putValue("email","aqdas.idris@gmail.com")
        viewModel.putValue("setpassword","aqdas")
        viewModel.putValue("confirmpassword", "aqdas")
        viewModel.setAction(Action.SignUpAction)
        Mockito.verify(mockRepo, atLeastOnce()).getGuardianCreds(guardianLoginData)
        Mockito.verify(mockObserver).onChanged(States.user_type_state)
    }

    @Test
    fun `after patient log in add guardian`(){
        val guardiandatacheck= GuardianCode(guardiancode = "123456")
        viewModel.getState().observeForever(mockObserver)
        viewModel.putValue("guardiancode", "123456")
        viewModel.setAction(Action.DoneAction)
        Mockito.verify(mockRepo, atLeastOnce()).getGuardianCode(guardiandatacheck)
        Mockito.verify(mockObserver, times(2)).onChanged(States.user_type_state)
    }

    @Test
    fun `test for back action`(){

        viewModel.getState().observeForever(mockObserver)
        Mockito.verify(mockObserver).onChanged(States.user_type_state)
        viewModel.setAction(Action.GuardianButtonAction)
        Mockito.verify(mockObserver).onChanged(States.guardian_registration_personal_info_state)
        viewModel.setAction(Action.BackAction)
        Mockito.verify(mockObserver, times(2)).onChanged(States.user_type_state)
        viewModel.setAction(Action.LoginIntentAction)
        Mockito.verify(mockObserver).onChanged(States.log_in_state)
    }

    @Test
    fun `given action login intent it must load login state`(){
        viewModel.getState().observeForever(mockObserver)
        Mockito.verify(mockObserver).onChanged(States.user_type_state)
        viewModel.setAction(Action.GuardianButtonAction)
        Mockito.verify(mockObserver).onChanged(States.guardian_registration_personal_info_state)
        viewModel.setAction(Action.LoginIntentAction)
        Mockito.verify(mockObserver).onChanged(States.log_in_state)
    }
}

