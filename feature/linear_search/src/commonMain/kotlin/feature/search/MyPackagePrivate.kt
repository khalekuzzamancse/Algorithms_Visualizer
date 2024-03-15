package feature.search

@RequiresOptIn(level = RequiresOptIn.Level.ERROR, message = "Only to be used in MyPackage")
@Retention(AnnotationRetention.BINARY)
annotation class MyPackagePrivate