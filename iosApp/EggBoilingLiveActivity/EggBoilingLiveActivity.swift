//
//  EggTimerWidgetLiveActivity.swift
//  TestProject
//
//  Created by Leonid Belyakov on 19/11/2024.
//

import ActivityKit
import SwiftUI
import WidgetKit

struct EggBoilingLiveActivity: Widget {
    var body: some WidgetConfiguration {
        ActivityConfiguration(for: EggTimerActivityAttributes.self) { context in
            LockScreenTimerView(state: context.state)
        } dynamicIsland: { context in
            DynamicIsland {
                // Expanded Dynamic Island view
                DynamicIslandExpandedRegion(.center) {
                    VStack(spacing: 4) {  // Reduce spacing
                        Text("Egg Timer")
                            .font(.headline)
                            .multilineTextAlignment(.center)
                        Text(
                            timerInterval: context.state
                                .startTime...context.state.endTime,
                            countsDown: true
                        )
                        .font(.largeTitle)
                        .bold()
                        .multilineTextAlignment(.center)
                    }
                }
                DynamicIslandExpandedRegion(.bottom) {
                    Text("Your eggs will be ready soon!")
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                }
            } compactLeading: {
                // Compact leading label
                Text("Egg")
                    .font(.caption)
                    .bold()
            } compactTrailing: {
                // Compact trailing timer
                // https://stackoverflow.com/questions/66210592/widgetkit-timer-text-style-expands-it-to-fill-the-width-instead-of-taking-spa/76861806#76861806
                Text(
                    timerInterval: context.state
                        .startTime...context.state.endTime, countsDown: true
                )
                .monospacedDigit()
                .frame(width: 40)
            } minimal: {
                // Minimal Dynamic Island view
                Text(
                    timerInterval: context.state
                        .startTime...context.state.endTime, countsDown: true
                )
                .font(.caption2)
                .multilineTextAlignment(.center)
            }
        }
    }
}

struct LockScreenTimerView: View {
    let state: EggTimerActivityAttributes.ContentState

    var body: some View {
        VStack(spacing: 8) {  // Adjust spacing for a clean look
            Text("Egg Timer")
                .font(.headline)
                .bold()
                .multilineTextAlignment(.center)

            Text(
                timerInterval: state.startTime...state.endTime, countsDown: true
            )
            .font(.largeTitle)
            .bold()
            .monospacedDigit()  // Ensures numbers are evenly spaced
            .multilineTextAlignment(.center)
        }
        .padding()
        .frame(maxWidth: .infinity)  // Ensures alignment in Lock Screen widgets
    }
}
