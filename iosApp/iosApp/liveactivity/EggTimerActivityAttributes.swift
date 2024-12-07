//
//  EggTimerActivityAttributes 2.swift
//  TestProject
//
//  Created by Leonid Belyakov on 19/11/2024.
//

import ActivityKit
import Foundation
import Shared

struct EggTimerActivityAttributes: ActivityAttributes {
    public struct ContentState: Codable, Hashable {
        var startTime: Date
        var endTime: Date
        var eggType: EggType

        var eggImageName: String {
            eggType.imageName
        }
    }

    var name: String
}

enum EggType: String, Codable, Hashable {
    case soft = "Soft"
    case medium = "Medium"
    case hard = "Hard"

    var imageName: String {
        switch self {
        case .soft:
            return "notification_egg_soft"
        case .medium:
            return "notification_egg_medium"
        case .hard:
            return "notification_egg_hard"
        }
    }

    init(from kotlinEnum: EggBoilingType) {
        switch kotlinEnum {
        case .soft:
            self = .soft
        case .medium:
            self = .medium
        case .hard:
            self = .hard
        default:
            self = .medium
        }
    }
}
