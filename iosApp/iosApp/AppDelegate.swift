//
//  AppDelegate.swift
//  iosApp
//
//  Created by Leonid Belyakov on 2/12/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import UIKit
import UserNotifications

class AppDelegate: UIResponder, UIApplicationDelegate,
    UNUserNotificationCenterDelegate
{

    private let appStartupInformationProvider =
        KoinIosHelper.shared.get(
            objCProtocol: AppStartupInformationProvider.self
        ) as? AppStartupInformationProvider

    private let logger =
        KoinIosHelper.shared.get(
            objCClass: EggyLogger.self
        ) as? EggyLogger

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication
            .LaunchOptionsKey: Any]?
    ) -> Bool {
        setupLifecycleObservers()
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
        if let launchedFromNotification = userInfo[
            NotificationsManager.companion.USER_INFO_LAUNCHED_KEY] as? Bool,
            launchedFromNotification
        {
            appStartupInformationProvider?.isLaunchedFromNotification = true
        }
        completionHandler()
    }

    func setupLifecycleObservers() {
        let notificationCenter = NotificationCenter.default

        notificationCenter.addObserver(
            forName: UIApplication.didBecomeActiveNotification,
            object: nil,
            queue: OperationQueue.main
        ) { [weak self] _ in
            self?.logger?.i(throwable: nil, tag: "") { "App is in foreground" }
        }

        notificationCenter.addObserver(
            forName: UIApplication.didEnterBackgroundNotification,
            object: nil,
            queue: OperationQueue.main
        ) { [weak self] _ in
            self?.logger?.i(throwable: nil, tag: "") { "App is in background" }
        }
    }
}
