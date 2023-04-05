package r42914lg.trykmm.domain.models.local

import r42914lg.trykmm.domain.remote.models.address.Address

data class CheckableAddress(
    val address: Address,
    var checked: Boolean
)

fun List<Address>.toCheckableList(): List<CheckableAddress> {
    return this.map {
        CheckableAddress(
            address = it,
            checked = false
        )
    }
}

data class AddressBsdListItem(
    val id: Int,
    val title: String,
    val details: String,
    var selectedFlag: Boolean
)

fun List<CheckableAddress>.toBsdList(): List<AddressBsdListItem> {
    return this.map {
        AddressBsdListItem(
            id = it.address.id,
            title = it.address.building.address ?: "null from back",
            details = it.address.number,
            selectedFlag = it.checked
        )
    }
}