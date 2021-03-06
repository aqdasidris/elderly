package com.artsman.elderly.auth

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.Scene
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.artsman.elderly.*
import com.artsman.elderly.Action.*
import com.artsman.elderly.add_patient.AddPatientActivity
import com.artsman.elderly.auth.user_auth_api.UserAuthApi
import com.artsman.elderly.core.getRetrofitInstance
import com.artsman.elderly.data.AppPreference
import com.artsman.elderly.data.IPreferenceHelper
import com.artsman.elderly.patient_list.PatientListFragment
import kotlinx.android.synthetic.main.fragment_patient_info.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuardianRegistration.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuardianRegistrationFragment : Fragment(), ICanHandleBackPress {
    // TODO: Rename and change types of parameters
        private lateinit var mSceneRoot: View
    private lateinit var viewModel: ElderlyViewModel
    private val elderlyObserver = Observer<States>{ s->
        setState(s)
    }
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }


    private fun getPrefHelper(): IPreferenceHelper=AppPreference(activity as Context)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_guardian_registration, container, false)
        viewModel= ElderlyViewModel(RegistrationRepo(getPrefHelper(), UserAuthApi(
            getRetrofitInstance(requireContext()))))
        viewModel.getState().observe(this, elderlyObserver)
        mSceneRoot=view.findViewById<View>(R.id.sceneRoot)
        return view


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuardianRegistration.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuardianRegistration().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun configureRegistrationNameEmailScreen(){
        val nameEmailRegistration=Scene.getSceneForLayout(
            mSceneRoot as ViewGroup?,
            R.layout.layout_registeration_name_email,
            requireContext()
        )
        nameEmailRegistration.enter()

        val edtName= mSceneRoot.findViewById<EditText>(R.id.edt_name)
        val edtEmail= mSceneRoot.findViewById<EditText>(R.id.edt_email)
        val btn_nxt=mSceneRoot.findViewById<Button>(R.id.btn_next)
        btn_nxt.setOnClickListener{
            viewModel.putValue(ElderlyViewModel.DATA_EMAIL, edtEmail.text.toString())
            viewModel.setAction(NameEmailNextAction)
        }
        val btn_login=mSceneRoot.findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {
            viewModel.setAction(LoginIntentAction)
        }
        edtName.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.putValue(ElderlyViewModel.DATA_NAME, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })



    }
    fun configureRegistrationContactAddressScreen(){
        val contactAddressRegistration=Scene.getSceneForLayout(
            mSceneRoot as ViewGroup?,
            R.layout.layout_contact_address_registration,
            requireContext()
        )
        contactAddressRegistration.enter()
        val edtContact=mSceneRoot.findViewById<EditText>(R.id.edt_contact)
        val edtAddress=mSceneRoot.findViewById<EditText>(R.id.edt_address)
        val edtPincode=mSceneRoot.findViewById<EditText>(R.id.edt_pincode)
        val btn_next_page_password=mSceneRoot.findViewById<Button>(R.id.btn_next_page_password)
        btn_next_page_password.setOnClickListener{
            viewModel.putValue(ElderlyViewModel.PHONE, edtContact.text.toString())
            viewModel.putValue(ElderlyViewModel.ADDRESS, edtAddress.text.toString())
            viewModel.putValue(ElderlyViewModel.PINCODE, edtPincode.text.toString())
            viewModel.setAction(ProceedToPasswordLoginAction)}
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun configureSetpasswordScreen(){
        val configuresetpassword=Scene.getSceneForLayout(
            mSceneRoot as ViewGroup?,
            R.layout.layout_login,
            requireContext()
        )
        configuresetpassword.enter()
        val edtUsername=mSceneRoot.findViewById<EditText>(R.id.edt_username)
        val edtSetPassword=mSceneRoot.findViewById<EditText>(R.id.edt_set_password)
        val edtConfirmPassword=mSceneRoot.findViewById<EditText>(R.id.edt_password)

        val btn_done=mSceneRoot.findViewById<Button>(R.id.btn_done)
        btn_done.setOnClickListener {
            viewModel.putValue(ElderlyViewModel.DATA_EMAIL, edtUsername.text.toString())
            viewModel.putValue(ElderlyViewModel.SETPASSWORD, edtSetPassword.text.toString())
            viewModel.putValue(ElderlyViewModel.CONFIRMPASSWORD, edtConfirmPassword.text.toString())
            viewModel.setAction(SignUpAction)
        }
    }
    fun configurePatientLogin(){
        val  patientLogin=Scene.getSceneForLayout(
            mSceneRoot as ViewGroup?,
            R.layout.layout_patient_login,
            requireContext()
        )
        patientLogin.enter()
        val edtPateintMail=mSceneRoot.findViewById<EditText>(R.id.edt_patient_email)
        val btn_patient_log_in=mSceneRoot.findViewById<Button>(R.id.btn_patient_log_in)
        btn_patient_log_in.setOnClickListener {
            viewModel.putValue(ElderlyViewModel.MAIL, edtPateintMail.text.toString())
            viewModel.setAction(AddGuardianAction)}

    }
    fun configureAddguardianScreen(){
        val addGuardian=Scene.getSceneForLayout(mSceneRoot as ViewGroup?,
            R.layout.layout_add_guardian,requireContext())
        addGuardian.enter()
        val edtGuardianCode=mSceneRoot.findViewById<EditText>(R.id.edt_guardian_code)
        val btn_add_gaurdian=mSceneRoot.findViewById<Button>(R.id.btn_add_gaurdian)
        btn_add_gaurdian.setOnClickListener {
            viewModel.putValue(ElderlyViewModel.GUARDIAN_CODE, edtGuardianCode.text.toString())
            viewModel.setAction(Action.DoneAction) }
    }
    fun configureUserTypeScreen(){
        val chooseUserType=Scene.getSceneForLayout(
            mSceneRoot as ViewGroup?,
            R.layout.layout_user_selection,
            requireContext()
        )
        chooseUserType.enter()
        val btn_choose_guardian=mSceneRoot.findViewById<Button>(R.id.btn_choose_guardian)
        btn_choose_guardian.setOnClickListener { viewModel.setAction(GuardianButtonAction) }
        val btn_choose_patient=mSceneRoot.findViewById<Button>(R.id.btn_choose_patient)
        btn_choose_patient.setOnClickListener { viewModel.setAction(PatientUserTypeAction) }
    }


    private var mTxtUsername: EditText?= null
    private var mTxtPassword: EditText?= null
    fun configureLoginScreen(){
        val existingUSer=Scene.getSceneForLayout(mSceneRoot as ViewGroup?,R.layout.guardian_login,requireContext())
        existingUSer.enter()
        val loginbtn=mSceneRoot.findViewById<Button>(R.id.btn_login_auth)

        mTxtUsername=mSceneRoot.findViewById<EditText>(R.id.edtEmail)
        mTxtPassword=mSceneRoot.findViewById<EditText>(R.id.edtPassword)
        loginbtn.setOnClickListener {
            viewModel.setAction(LogIn(mTxtUsername?.text.toString()?: "",mTxtPassword?.text.toString()?: ""))
        }
        mTxtUsername?.addTextChangedListener(textWatcherUsername)
    }

    val textWatcherUsername= object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                if(it.length<3){
                    mTxtUsername?.setError("Need more characters")
                    return
                }
            }
        }



        override fun afterTextChanged(s: Editable?) { }
    }


    private fun setState(states: States){
        if(states== States.user_type_state){
            configureUserTypeScreen()
        }
        else if(states== States.guardian_registration_personal_info_state){
            configureRegistrationNameEmailScreen()
        }
        else if(states== States.adding_guardian_contact_info_state){
            configureRegistrationContactAddressScreen()
        }
        else if(states== States.guardian_login_state){
            configureSetpasswordScreen()
        }
        else if(states== States.patient_login_state){
            configurePatientLogin()
        }
        else if(states== States.add_guardian_state){
            configureAddguardianScreen()
        }
        else if(states==States.log_in_state){
            configureLoginScreen()
        }
        else if(states==States.redirect_to_home_state){
            startPatientBio()
        }
        else if(states==States.signin_failed_state){
            mTxtUsername?.setError("Invalid Username or Password")
        }
        else if(states==States.add_patient_state){
            startAddPatient()
        }

        else if(states== States.kill_state){
            activity?.finish()
        }

    }


    private fun startAddPatient(){
        val intent=Intent(requireActivity(),AddPatientActivity::class.java)
        startActivity(intent)
    }


    private fun startPatientBio(){
//        val intent=Intent(requireActivity(), PatientListFragment::class.java)
//        startActivity(intent)
        val fragmentManager=getFragmentManager()
        val fragmentTransaction=fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.sceneRoot,  PatientListFragment())
        fragmentTransaction?.commit()
    }
    override fun fragmentBackPress(): Boolean {
        viewModel.setAction(Action.BackAction)
        return true;
    }


}


interface ICanHandleBackPress {
    fun fragmentBackPress(): Boolean
}
