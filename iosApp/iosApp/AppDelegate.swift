//
//  AppDelegate.swift
//  iosApp
//
//  Created by Leonid Belyakov on 2/12/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import UserNotifications
import Shared


class AppDelegate: UIResponder, UIApplicationDelegate,
    UNUserNotificationCenterDelegate
{
    
    private let appStartupInformationProvider = KoinIosHelper.shared.get(
        objCProtocol: AppStartupInformationProvider.self
    ) as? AppStartupInformationProvider
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication
            .LaunchOptionsKey: Any]?
    ) -> Bool {
        // Assign the UNUserNotificationCenter delegate
        UNUserNotificationCenter.current().delegate = self
        return true
    }
    
    func userNotificationCenter(
          _ center: UNUserNotificationCenter,
          didReceive response: UNNotificationResponse,
          withCompletionHandler completionHandler: @escaping () -> Void
      ) {
          let userInfo = response.notification.request.content.userInfo
          if let launchedFromNotification = userInfo[NotificationsManager.companion.USER_INFO_LAUNCHED_KEY] as? Bool, launchedFromNotification {
              appStartupInformationProvider?.isLaunchedFromNotification = true
          }
          completionHandler()
      }
}
