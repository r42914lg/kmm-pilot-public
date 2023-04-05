package r42914lg.trykmm.android.ui.main.mainPage.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.onEach
import r42914lg.trykmm.android.R
import r42914lg.trykmm.android.core.ui.base.BottomSheetTransparentOpacityDialog
import r42914lg.trykmm.android.databinding.BsdListAddressBinding
import r42914lg.trykmm.android.utils.observeIn
import r42914lg.trykmm.domain.models.local.CheckableAddress
import r42914lg.trykmm.domain.models.local.toBsdList
import r42914lg.trykmm.ui.AddressViewModel.AddressState
import r42914lg.trykmm.ui.AddressViewModel

class ListAddressBsd : BottomSheetTransparentOpacityDialog() {

    private lateinit var mBinding: BsdListAddressBinding
    private lateinit var adapter: ListAddressAdapter

    private val addressViewModel: AddressViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = BsdListAddressBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onOpacityChanged(alpha: Float) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)
        setUpAddressObserver()
    }

    private fun setUpAddressObserver() {
        addressViewModel.addressState
            .onEach {
                when (it) {
                    AddressState.StateLoading -> {}
                    AddressState.StateError -> {}
                    is AddressState.StateContent -> {
                        setUpAdapter(it.addrList)
                        openBsd(mBinding.bsdListAddress, 0.8f)
                    }
                }
            }
            .observeIn(this)
    }

    private fun setUpAdapter(addrList: List<CheckableAddress>) {
        mBinding.rvAddress.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = ListAddressAdapter {
            addressViewModel.onAddressSelected(it.id)
            dismiss()
        }

        mBinding.rvAddress.adapter = adapter
        mBinding.rvAddress.addItemDecoration(ListAddressDecorator(requireContext(), R.drawable.divider_list_address))

        adapter.setItems(addrList.toBsdList())
    }

}