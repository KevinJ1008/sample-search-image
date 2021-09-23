# sample-search-image
Implement `Pixabay`'s `Search Image` API sample practice, and present in RecyclerView, also implement swap to list and grid feature when presenting result. Using `AutoCompleteTextView` to integrate with search history. Moreover, make fake API remote data source to show display mode by remote control, which could change to real API if we need.

* Module structure:
<br>![image](https://github.com/KevinJ1008/sample-search-image/blob/main/sample-currency-list-module-structure.drawio.png)<br>
  * `Base`: A module which provide base class or common utils.
  * `ImageLoader`: Provide image loader wrapper, which could change to another 3rd lib if we need.
  * `LocalClient`: Provide base local client interface and store all DB or related local client classes.
  * `RemoteClient`: Store all related remote data source classes.
  * `Widget`: Custom view related.
  * `TestCore`: Support baset test and provided test lib to prevent imort test lib dependently.
  * `app`: Implement main businees logic, including `View`, `ViewModel`, `Repository` and related `UseCase` classes.
  
* Apply libraries:
  * DI: `Koin`
  * Thread Control: `Coroutine`
  * RecyclerView: `Epoxy`
  * ImageLoader: `Glide`
  * Android Jetpack: `LiveData`, `ViewModel`, `Room`, `Navigation`
  * Request: `Retrofit`
  * Unit Test: `JUnit`, `MockK`, `Coroutine Test`
