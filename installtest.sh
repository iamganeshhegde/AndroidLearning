alias fullBuildD='./gradlew packageDebugUniversalApk'
alias fullBuildR='./gradlew packageReleaseUniversalApk'
alias clean='./gradlew clean'
alias buildD='./gradlew assembleDebug'
alias buildR='./gradlew assembleRelease'
install_fulltest() {
    rm -rf /Users/ganeshhegde/AndroidStudioProjects/FirstComposeActivity/app/build/outputs/universal_apk/debug/app-debug-universal.apk
    fullBuildD
    installtest /Users/ganeshhegde/AndroidStudioProjects/FirstComposeActivity/app/build/outputs/universal_apk/debug/app-debug-universal.apk
}

installtest() {
    adb install -r -t $1
}
