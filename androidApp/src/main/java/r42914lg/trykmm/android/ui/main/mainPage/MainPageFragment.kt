package r42914lg.trykmm.android.ui.main.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.onEach
import r42914lg.trykmm.android.R
import r42914lg.trykmm.android.databinding.FragmentMainPageBinding
import r42914lg.trykmm.android.utils.observeIn
import r42914lg.trykmm.ui.AddressViewModel.AddressState
import r42914lg.trykmm.ui.AddressViewModel

class MainPageFragment : Fragment() {

    private lateinit var mBinding: FragmentMainPageBinding

    private val addressViewModel: AddressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainPageBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAddressObserver()
    }


    private fun setUpAddressObserver() {
        addressViewModel.addressState
            .onEach {
                when (it) {
                    AddressState.StateLoading -> {
                        mBinding.loading.visibility = View.VISIBLE
                    }
                    is AddressState.StateContent -> {
                        mBinding.loading.visibility = View.INVISIBLE

                        mBinding.vAddressMore1.setOnClickListener {
                            openListAddress()
                        }
                        var selectedFlat = it.addrList[0]
                        for (element in it.addrList) {
                            if (element.checked) {
                                selectedFlat = element
                                break
                            }
                        }
                        mBinding.tvMainAddress.text = selectedFlat.address.building.address
                        mBinding.tvMainFlatNumber.text = selectedFlat.address.number
                    }
                    AddressState.StateError -> {
                        mBinding.loading.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "Error while loading...",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
            .observeIn(this)
    }

    private fun openListAddress() {
        findNavController().navigate(R.id.listAddressBsd)
    }
}