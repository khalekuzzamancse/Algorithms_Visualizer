// swift-tools-version: 6.1
import PackageDescription

let package = Package(
    name: "NearByMessenger",
    platforms: [
        .iOS(.v17)
    ],
    products: [
        Product.library(
            name: "NearByMessenger",
            targets: ["NearByMessenger"]
        ),
        Product.library(
            name: "FeaureProfile",
            targets: ["Profile"]
        )
    ],
    dependencies: [
        // Sibling Core package
        .package(path: "../Core")
       
    ],
    targets: [
        .target(
            name: "NearByMessenger",
            dependencies: [
                .product(name: "CoreLib", package: "Core"),
                "Navigation"
            ]
        ),
        .target(
            name: "Navigation",
            dependencies: [
                .product(name: "CoreLib", package: "Core"),
            ],
            path: "Sources/Feature/Navigation"
        ),
        .target(
            name: "Profile",
            dependencies: [
                .product(name: "CoreLib", package: "Core"),
            ],
            path: "Sources/Feature/Profile"
        ),
        .target(
            name: "Home",
            dependencies: [
                .product(name: "CoreLib", package: "Core"),
            
            ],
            path: "Sources/Feature/Home"
        )
    ]
)

