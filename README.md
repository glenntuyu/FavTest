# Favorite User App

FavTest is an application developed using Kotlin that provides a Github User App designed using Clean Architecture principles. It contains features such as home screen with infinite scrolling showing Github Users with search and favorite feature. You can search Github Users by username and add Github User to favorite by long-clicking the User Card. You can filter the list to only show Favorite Users. 

## Features

1. **Home Page / Search Page**
   - Displays list of Github Users with Infinite Scroll.
   - Search User by username.
   - Click User Card to move to User Detail.
   - Long Click User Card to add/remove to/from Favorite using Room Database.
   - Filter User to Favorite Only.

2. **Favorite User**
   - Displays list of Github Users using Room Database.
   - Search User by username.
   - Click User Card to move to User Detail.
   - Filter User to All Users.

3. **Movie Details**
   - Provide User Detail.

## Tech stack & Open-source libraries
1. **Clean Architecture**
Create a separation of concern between layers into presentation, domain, and data for a modular and scalable codebase.
   - **Presentation Layer**: Contains UI components and presentation logic.
   - **Domain Layer**: Defines use cases and business logic independent of UI.
   - **Data Layer**: Manages data sources, repositories, and external services.

2. **MVVM**
    - Architecture pattern used to create a separation of concern between Model, View, and ViewModel.

3. **Navigation Jetpack** 
    - Simplifies the implementation of navigation between screen (in this case fragments) in the app

4. **ViewModel** 
    - UI related data holder, lifecycle aware.

5. **Lifecycles**
    - Create a UI that automatically responds to lifecycle events.

6. **LiveData** 
    - Build data objects that notify views when the underlying database changes.

7. **Hilt** 
    - for dependency injection.

8. **Flow**
   - for managing data behind viewModel (use case, repository, etc)

9. **Kotlin Coroutines**
    - for managing background threads with simplified code and reducing needs for callbacks

10. **Glide** 
    - for image loading

11. **Retrofit2 & OkHttp3** 
    - to make REST requests to the web service integrated.

12. **Unit Test** 
    - for unit testing view model & PagingSource
    
13. **MVI**
    - for unidirectional data flow

## Open API
FavTest uses the Github API ("https://api.github.com/")

## Notes
- Owner's current coding style is the one in detail and favorite module
- Search is more focused on new tech stack  like MVI, Paging, and Flow
- Ideally FavoriteFragment should not be needed if `Add to Favorite` uses the same API. In this case, we can remove FavoriteFragment and reuse SearchFragment that hits the API with query parameter that will only return Favorite Users.
