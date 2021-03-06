# Vimystry

Vimystry is an simple app displaying videos in a story

## Demo 

[Find the recorded demo here](art/demo.mp4)

## Features

There's a main screen with a full screen player. It immediately starts playing videos. Once ready
playback loops through all the medias indefinitely. Videos are ordered by creation order.

The UI displays current playing video’s information :

```
“likes” with short number formatting such as 1k/1m
“author” avatar
```

User can interact in the following ways:

```
Tap Right side of the screen to play next video
Tap Left side of the screen to play previous video
Long press anywhere and the video pauses
```

If the video is in a “paused state”, likes and author information disappear and only reappear once
the media is resumed

There are a “Loading” state during data load and an “Error” state if a failure occur

If you want to simulate an error in fetching data uncomment and show error screen, then uncomment this in [PostsGraphQLApi](shared/src/commonMain/kotlin/com/lduboscq/vimystry/remote/PostsGraphQLApi.kt) :

```
// throw NoBackendDataException()
```

Any update from the remote dataset result in the player being reloaded with new content (current
refresh is 5 min defined
in [PostsGraphQLApi](shared/src/commonMain/kotlin/com/lduboscq/vimystry/remote/PostsGraphQLApi.kt))

## Techs

- Kotlin min sdk 21
- Kotlin multiplatform library
- Configuration changes
- SOLID patterns
- MVVM architecture
- Coroutines (suspend functions, flow, scopes)
- ExoPlayer for the video streaming player
- Jetpack Compose for the ui
- Jetpack Compose tests ([ErrorScreenTest](androidApp/src/androidTest/kotlin/ErrorScreenTest.kt))
- Unit tests ([ShortenLikesTest](shared/src/androidTest/kotlin/com/lduboscq/vimystry/ShortenLikesTest.kt), [MainViewModelTest](androidApp/src/test/kotlin/MainViewModelTest.kt), [SortPostsByDateTest](androidApp/src/test/kotlin/SortPostsByDateTest.kt), [TestKoinGraph](androidApp/src/test/kotlin/TestKoinGraph.kt))
- Apollo GraphQL for the remote communication
- Koin for the multiplatform dependency injection framework
- Kotlin Coroutines (suspend function, flow, scopes)

## License

Vimystry is under MIT license, you can do almost anything you want like making and distributing closed source versions of the project. Find the license [here](./LICENSE).

## Project views 

<img align="left" src = "https://profile-counter.glitch.me/Vimystry/count.svg" alt ="Loading"> 