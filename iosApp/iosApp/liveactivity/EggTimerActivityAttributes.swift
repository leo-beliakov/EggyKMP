//
//  EggTimerActivityAttributes 2.swift
//  TestProject
//
//  Created by Leonid Belyakov on 19/11/2024.
//


import ActivityKit
import Foundation

struct EggTimerActivityAttributes: ActivityAttributes {
    public struct ContentState: Codable, Hashable {
         var startTime: Date
         var endTime: Date
     }

    var name: String
}
