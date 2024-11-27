//
//  EggBoilingLiveActivityLiveActivity.swift
//  EggBoilingLiveActivity
//
//  Created by Leonid Belyakov on 22/11/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import ActivityKit
import WidgetKit
import SwiftUI

struct EggBoilingLiveActivityAttributes: ActivityAttributes {
    public struct ContentState: Codable, Hashable {
        // Dynamic stateful properties about your activity go here!
        var emoji: String
    }

    // Fixed non-changing properties about your activity go here!
    var name: String
}

struct EggBoilingLiveActivity: Widget {
    var body: some WidgetConfiguration {
        ActivityConfiguration(for: EggBoilingLiveActivityAttributes.self) { context in
            // Lock screen/banner UI goes here
            VStack {
                Text("Hello \(context.state.emoji)")
            }
            .activityBackgroundTint(Color.cyan)
            .activitySystemActionForegroundColor(Color.black)

        } dynamicIsland: { context in
            DynamicIsland {
                // Expanded UI goes here.  Compose the expanded UI through
                // various regions, like leading/trailing/center/bottom
                DynamicIslandExpandedRegion(.leading) {
                    Text("Leading")
                }
                DynamicIslandExpandedRegion(.trailing) {
                    Text("Trailing")
                }
                DynamicIslandExpandedRegion(.bottom) {
                    Text("Bottom \(context.state.emoji)")
                    // more content
                }
            } compactLeading: {
                Text("L")
            } compactTrailing: {
                Text("T \(context.state.emoji)")
            } minimal: {
                Text(context.state.emoji)
            }
            .widgetURL(URL(string: "http://www.apple.com"))
            .keylineTint(Color.red)
        }
    }
}

extension EggBoilingLiveActivityAttributes {
    fileprivate static var preview: EggBoilingLiveActivityAttributes {
        EggBoilingLiveActivityAttributes(name: "World")
    }
}

extension EggBoilingLiveActivityAttributes.ContentState {
    fileprivate static var smiley: EggBoilingLiveActivityAttributes.ContentState {
        EggBoilingLiveActivityAttributes.ContentState(emoji: "😀")
     }
     
     fileprivate static var starEyes: EggBoilingLiveActivityAttributes.ContentState {
         EggBoilingLiveActivityAttributes.ContentState(emoji: "🤩")
     }
}

#Preview("Notification", as: .content, using: EggBoilingLiveActivityAttributes.preview) {
    EggBoilingLiveActivity()
} contentStates: {
    EggBoilingLiveActivityAttributes.ContentState.smiley
    EggBoilingLiveActivityAttributes.ContentState.starEyes
}
