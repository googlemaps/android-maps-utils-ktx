[![Maven Central](https://img.shields.io/maven-central/v/com.google.maps.android/maps-ktx)](https://maven-badges.herokuapp.com/maven-central/com.google.maps.android/maps-ktx)
![Release](https://github.com/googlemaps/android-maps-ktx/workflows/Release/badge.svg)
![Stable](https://img.shields.io/badge/stability-stable-green)
[![Tests/Build](https://github.com/googlemaps/android-maps-ktx/actions/workflows/test.yml/badge.svg)](https://github.com/googlemaps/android-maps-ktx/actions/workflows/test.yml)

![Contributors](https://img.shields.io/github/contributors/googlemaps/android-maps-ktx?color=green)
[![License](https://img.shields.io/github/license/googlemaps/android-maps-ktx?color=blue)][license]
[![StackOverflow](https://img.shields.io/stackexchange/stackoverflow/t/google-maps?color=orange&label=google-maps&logo=stackoverflow)](https://stackoverflow.com/questions/tagged/google-maps)
[![Discord](https://img.shields.io/discord/676948200904589322?color=6A7EC2&logo=discord&logoColor=ffffff)][Discord server]

# Maps Android KTX

## Description
This repository contains Kotlin extensions (KTX) for:
1. The [Maps SDK for Android][maps-sdk]
1. The [Maps SDK for Android Utility Library][amu]

It enables you to write more concise, idiomatic Kotlin. Each set of extensions can be used independently or together.

## Requirements

- [Sign up with Google Maps Platform]
- A Google Maps Platform [project] with the **Maps SDK for Android** enabled
- An [API key] associated with the project above
- Android API level 21+
- Kotlin-enabled project
- Kotlin coroutines

## Installation

```groovy
dependencies {

    // KTX for the Maps SDK for Android library
    implementation 'com.google.maps.android:maps-ktx:5.1.1'

    // KTX for the Maps SDK for Android Utility Library
    implementation 'com.google.maps.android:maps-utils-ktx:5.1.1'
}
```

## Usage

With this KTX library, you should be able to take advantage of several Kotlin language features such as extension functions, named parameters and default arguments, destructuring declarations, and coroutines.

### Sample App

<img src="https://developers.google.com/maps/documentation/android-sdk/images/utility-multilayer.png" width="150" align=right>

This repository includes a [demo app](app) that illustrates the use of this KTX library.

To run the demo app, you'll have to:

1. [Get a Maps API key](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
1. Create a file in the root directory called `secrets.properties` (this file should *NOT* be under version control to protect your API key)
1. Add a single line to `secrets.properties` that looks like `MAPS_API_KEY=YOUR_API_KEY`, where `YOUR_API_KEY` is the API key you obtained in the first step
1. Build and run

### Maps SDK KTX

#### Extension functions

Adding a `Marker`:

_Before_
```java
GoogleMap googleMap = // ...
LatLng sydney = new LatLng(-33.852, 151.211);
MarkerOptions markerOptions = new MarkerOptions()
    .position(Sydney)
    .title("Marker in Sydney");
Marker marker = googleMap.addMarker(markerOptions);
```

_After_
```kotlin
val googleMap = // ...
val sydney = LatLng(-33.852, 151.211)
val marker = googleMap.addMarker {
    position(sydney)
    title("Marker in Sydney")
}
```

#### Coroutines

Accessing a `GoogleMap` instance can be retrieved using coroutines vs. traditional the callback mechanism. The example here demonstrates how you can use this feature alongside with [Lifecycle-aware coroutine scopes][lifecycle] provided in Android’s Architecture Components. To use this, you'll need to add the following to your `build.gradle` dependencies:
`implementation 'androidx.lifecycle:lifecycle-runtime-ktx:<latest-version>'`

_Before_
```java
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

    mapFragment.getMapAsync(new OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Access GoogleMap instance here
        }
    });
}
```

_After_
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

    lifecycle.coroutineScope.launchWhenCreated {
        val googleMap = mapFragment?.awaitMap()
    }
}
```

#### Flow

Listing to camera events can be collected via [Kotlin Flow][kotlin-flow].

_Before_
```java
val googleMap = //...
googleMap.setOnCameraIdleListener = { //... }
googleMap.setOnCameraMoveCanceledListener { //... }
googleMap.setOnCameraMoveListener { //... }
googleMap.setOnCameraMoveStartedListener { //... }
```

_After_
```kotlin
// To be invoked within a coroutine scope
googleMap.cameraIdleEvents().collect { //... }
googleMap.cameraMoveCanceledEvents().collect { //... }
googleMap.cameraMoveEvents().collect { //... }
googleMap.cameraMoveStartedEvents().collect { //... }
```

### Maps SDK for Android Utilities KTX

#### Extension functions

Checking if a `LatLng` is contained within a `Polygon`:

_Before_
```java
Polygon polygon = // some polygon
LatLng latlng = // some latlng
boolean result = PolygonUtil.containsLocation(latlng, polygon.getPoints(), true);
```

_After_
```kotlin
val polygon: Polygon = // some polygon
val latlng: LatLng = // some latlng
val result: Boolean = polygon.contains(latLng)
```

#### Named parameters and default arguments

Creating a `GeoJsonLayer` object:

_Before_
```java
GeoJsonLayer layer = new GeoJsonLayer(
    map, 
    geoJsonFile, 
    null, 
    polygonManager, 
    null, 
    groundOverlayManager
);
```

_After_
```kotlin
val layer = geoJsonLayer(
    map = map,
    geoJsonFile = geoJsonFile,
    polygonManager = polygonManager,
    groundOverlayManager = groundOverlayManager
)
```

#### Destructuring Declarations

Destructuring properties of a `Point`:

_Before_
```java
Point point = new Point(1.0, 2.0);
double x = point.x;
double y = point.y;
```

_After_
```kotlin
val point = Point(1.0, 2.0)
val (x, y) = point
```

## Documentation

You can learn more about all the extensions provided by this library by reading the [documentation].

## Contributing

Contributions are welcome and encouraged! If you'd like to contribute, send us a [pull request] and refer to our [code of conduct] and [contributing guide].

## Terms of Service

This library uses Google Maps Platform services. Use of Google Maps Platform services through this library is subject to the Google Maps Platform [Terms of Service].

This library is not a Google Maps Platform Core Service. Therefore, the Google Maps Platform Terms of Service (e.g. Technical Support Services, Service Level Agreements, and Deprecation Policy) do not apply to the code in this library.

## Support

This library is offered via an open source [license]. It is not governed by the Google Maps Platform Support [Technical Support Services Guidelines, the SLA, or the [Deprecation Policy]. However, any Google Maps Platform services used by the library remain subject to the Google Maps Platform Terms of Service.

This library adheres to [semantic versioning] to indicate when backwards-incompatible changes are introduced. Accordingly, while the library is in version 0.x, backwards-incompatible changes may be introduced at any time.

If you find a bug, or have a feature request, please [file an issue] on GitHub. If you would like to get answers to technical questions from other Google Maps Platform developers, ask through one of our [developer community channels]. If you'd like to contribute, please check the [contributing guide].

You can also discuss this library on our [Discord server].

[lifecycle]: https://developer.android.com/topic/libraries/architecture/coroutines#lifecyclescope
[kotlin-flow]: https://kotlinlang.org/docs/reference/coroutines/flow.html

[API key]: https://developers.google.com/maps/documentation/android-sdk/get-api-key
[maps-sdk]: https://developers.google.com/maps/documentation/android-sdk
[documentation]: https://googlemaps.github.io/android-maps-ktx
[amu]: https://github.com/googlemaps/android-maps-utils

[code of conduct]: ?tab=coc-ov-file#readme
[contributing guide]: CONTRIBUTING.md
[Deprecation Policy]: https://cloud.google.com/maps-platform/terms
[developer community channels]: https://developers.google.com/maps/developer-community
[Discord server]: https://discord.gg/hYsWbmk
[file an issue]: https://github.com/googlemaps/android-maps-ktx/issues/new/choose
[license]: LICENSE
[project]: https://developers.google.com/maps/documentation/android-sdk/cloud-setup
[pull request]: https://github.com/googlemaps/android-maps-ktx/compare
[semantic versioning]: https://semver.org
[Sign up with Google Maps Platform]: https://console.cloud.google.com/google/maps-apis/start
[similar inquiry]: https://github.com/googlemaps/android-maps-ktx/issues
[SLA]: https://cloud.google.com/maps-platform/terms/sla
[Technical Support Services Guidelines]: https://cloud.google.com/maps-platform/terms/tssg
[Terms of Service]: https://cloud.google.com/maps-platform/terms
