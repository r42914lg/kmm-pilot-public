package r42914lg.trykmm.android.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.onEach
import r42914lg.trykmm.android.R
import r42914lg.trykmm.android.databinding.FragmentOnboardingBinding
import r42914lg.trykmm.android.utils.observeIn
import r42914lg.trykmm.domain.remote.models.phone.Phone
import r42914lg.trykmm.ui.PhoneViewModel.AuthenticationState
import r42914lg.trykmm.ui.PhoneViewModel
import kotlin.system.exitProcess

class OnboardingFragment : Fragment() {

    private lateinit var mBinding: FragmentOnboardingBinding
    private val phoneViewModel: PhoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitProcess(0)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startMainFragment()
    }

    private fun startMainFragment() {

        phoneViewModel.authenticationState.onEach {
            when (it) {
                AuthenticationState.StateInitial -> {
                    phoneViewModel.authenticateWithPhone(Phone("+023423423432423"))
                }
                AuthenticationState.StateValidPhone -> {
                    phoneViewModel.onSmsEntered("1234")
                }
                AuthenticationState.StateValidSms -> {
                    findNavController().navigate(R.id.mainFragment)
                }
                AuthenticationState.StateError -> {
                    Toast.makeText(requireContext(), "Authentication error...", Toast.LENGTH_LONG).show()
                }
            }
        }.observeIn(this)
    }
}