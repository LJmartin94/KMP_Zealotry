import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init(){
        DependencyInjectionKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
