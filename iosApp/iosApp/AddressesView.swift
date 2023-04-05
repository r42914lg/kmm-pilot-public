import SwiftUI
import MultiPlatformLibrary
import MultiPlatformLibrarySwift
import mokoMvvmFlowSwiftUI
import Combine

struct AuthView: View {
    @ObservedObject var viewModel: PhoneViewModel = PhoneViewModel()
    @State var message = "Onboarding screen"
    @State var isPresenting = false
    
    var body: some View {
        Group {
            Text("\(message)")
            NavigationView {
                VStack {
                    Button("Proceed") { isPresenting = true }
                    NavigationLink(destination: AddressesView(), isActive: $isPresenting) {
                        EmptyView()
                    }
                }
            }
        }.onReceive(createPublisher(viewModel.authenticationState)) { authState in
            let authStateKs = PhoneViewModelAuthenticationStateKs(authState)
            switch(authStateKs) {
            case .stateInitial:
                self.message = "initial received"
                viewModel.authenticateWithPhone(phone: Phone(phone: "+0734284832764"))
                break
            case .stateValidPhone:
                self.message = "valid phone received"
                viewModel.onSmsEntered(sms: "1234")
                break
            case .stateValidSms:
                self.message = "valid sms received"
                break
            case .stateError:
                self.message = "error received"
                break
            @unknown default:
                self.message = "unexpected state received"
                break
            }
        }
    }
}

struct AuthView_Previews: PreviewProvider {
    static var previews: some View {
        AddressesView()
    }
}

struct AddressesView: View {
    @ObservedObject var viewModel: AddressViewModel = AddressViewModel()
    @State var message = String("Addresses screen")
    
	var body: some View {
        Group {
            Text("\(message)")
        }.onReceive(createPublisher(viewModel.addressState)) { addrState in
            let addrStateKs = AddressViewModelAddressStateKs(addrState)
            switch(addrStateKs) {
            case .stateLoading:
                self.message = "Loading addresses..."
                break
            case .stateError:
                self.message = "Error while loading addresses"
                break
            case .stateContent:
                let content = addrStateKs.sealed as! AddressViewModelAddressStateStateContent
                let list = content.addrList
                var txtStr = ""
                for (index, item) in list.enumerated() {
                    txtStr += "\(index) \(item.address.number)\n"
                }
                self.message = "Addresses loaded: " + "\n" + txtStr
                break
            @unknown default:
                self.message = "unexpected state received"
                break
            }
        }
	}
}

struct AddressesView_Previews: PreviewProvider {
	static var previews: some View {
        AddressesView()
	}
}
