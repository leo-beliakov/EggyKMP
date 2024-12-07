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
                    HStack(spacing: 8) {
                        Image(context.state.eggImageName)  // Dynamic egg image
                            .resizable()
                            .scaledToFit()
                            .frame(height: 40)

                        VStack(alignment: .leading, spacing: 4) {
                            Text("Egg Timer")
                                .font(.headline)
                                .multilineTextAlignment(.center)

                            Text(
                                "Boiling \(context.state.eggType)-boiled eggs"
                            )
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                        }
                        Text(
                            timerInterval: context.state
                                .startTime...context.state.endTime,
                            countsDown: true
                        )
                        .font(.largeTitle)
                        .bold()
                        .monospacedDigit()
                        .frame(width: 90)
                        .multilineTextAlignment(.trailing)
                    }
                }
            } compactLeading: {
                // Compact leading icon
                Image(context.state.eggImageName)  // Dynamic egg image
                    .resizable()
                    .scaledToFit()
                    .frame(width: 20, height: 20)
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
        HStack(alignment: .center, spacing: 16) {
            // Egg Image on the Left
            Image(state.eggImageName)
                .resizable()
                .scaledToFit()
                .frame(height: 100)
                .frame(width: 100)

            // Text Section on the right
            VStack(alignment: .center, spacing: 4) {
                Text("Egg Timer")
                    .font(.headline)
                    .bold()

                Text("Boiling \(state.eggType.rawValue)-boiled eggs")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .layoutPriority(1)

                Text(
                    timerInterval: state.startTime...state.endTime,
                    countsDown: true
                )
                .font(.largeTitle)
                .bold()
                .monospacedDigit()
                .multilineTextAlignment(.center)
            }.frame(maxWidth: .infinity)
        }
        .padding()
        .frame(maxWidth: .infinity)
    }
}
