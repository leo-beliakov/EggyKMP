import Foundation
import ActivityKit
import Shared

class LiveActivityManagerImpl: NSObject, LiveActivityManager {

    var isRunning: Bool {
        return !Activity<EggTimerActivityAttributes>.activities.isEmpty
    }

    func startLiveActivity(
        boilingTime: Int64,
        type: EggBoilingType
    ) {
        
        guard ActivityAuthorizationInfo().areActivitiesEnabled else {
            print("Live Activities are disabled.")
            return
        }

        let duration = TimeInterval(boilingTime / 1000) // convert Ms to Sec
        let attributes = EggTimerActivityAttributes(name: "Egg Timer")
        let contentState = EggTimerActivityAttributes.ContentState(
            startTime: Date(),
            endTime: Date().addingTimeInterval(duration),
            eggType: EggType(from: type)
        )

        let initialContent = ActivityContent(
            state: contentState,
            staleDate: nil
        )

        do {
            let _ = try Activity<EggTimerActivityAttributes>.request(
                attributes: attributes,
                content: initialContent,
                pushType: nil
            )
            print("Live Activity started.")
        } catch {
            print("Failed to start Live Activity: \(error)")
        }
    }

    func stopLiveActivity() {
        Task {
            for activity in Activity<EggTimerActivityAttributes>.activities {
                await activity.end(dismissalPolicy: .immediate)
            }
        }
    }
}
