// swift-tools-version: 6.1
import PackageDescription

let package = Package(
    name: "Core",
    platforms: [
          .iOS(.v17),
         
      ],
    products: [
        .library(
            name: "CoreLib",
            targets: ["CoreUI","CoreLanguage"]),
    ],
    targets: [
        .target(name: "CoreUI",path: "Sources/UI"),
        .target(name: "CoreLanguage",path: "Sources/Language"),
    ]
)
