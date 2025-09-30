import SwiftUI

extension Color {
    var isDarkColor: Bool {
#if canImport(UIKit)
        let uiColor = UIColor(self)
        var red: CGFloat = 0
        var green: CGFloat = 0
        var blue: CGFloat = 0
        var alpha: CGFloat = 0
        uiColor.getRed(&red, green: &green, blue: &blue, alpha: &alpha)
        let luminance = 0.299*red + 0.587*green + 0.114*blue
        return luminance < 0.5
#else
        return false // fallback for macOS if needed
#endif
    }
}

@MainActor
func hidekeyboard() {
    
    UIApplication
    .shared
    .sendAction(
        #selector(UIResponder.resignFirstResponder),
        to: nil, from: nil, for: nil)
    
}
