import Foundation

@objc public class CapacitorForegroundService: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
