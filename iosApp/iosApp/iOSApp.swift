import SwiftUI
import Shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    init() {
        KoinIosHelper.shared.doInitKoin(
            createLiveActivityManager: {
                return LiveActivityManagerImpl()
            }
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
