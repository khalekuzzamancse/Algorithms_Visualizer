// swift-tools-version: 6.1
import PackageDescription

let package = Package(
    name: "Features",
    platforms: [
        .iOS(.v17),
    ],
    products: [
        .library(
            name: "Features",
            targets: ["Features"]),
    ],
    dependencies: [
        // Assuming Core is local and part of the same workspace
        .package(path: "../Core")
    ],
    targets: [
        .target(
            name: "Features",
            dependencies: [
                .product(name: "CoreLib", package: "Core"),
            ]
        ),
    ]
)





//// swift-tools-version: 6.1
//import PackageDescription
//
//let package = Package(
//    name: "Features",
//    platforms: [
//          .iOS(.v17),
//         
//      ],
//    products: [
//        .library(
//            name: "Features",
//            targets: ["Features"]),
//    ],
//    targets: [
//        .target(name: "Features"),
//       
//    ]
//)
