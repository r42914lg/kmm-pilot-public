import SwiftUI
import MultiPlatformLibrary

@main
struct iOSApp: App {
    
    init() {
         SimpleServiceLocator.shared.setDriverFactory(driverFactory: DatabaseDriverFactory())
         SimpleServiceLocator.shared.setDataStore(dataStore: CreateDataStoreKt.createDataStore())
    }
    
	var body: some Scene {
		WindowGroup {
			AuthView()
		}
	}
}
