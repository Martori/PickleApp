# PickleApp
This is a demo app that uses the  [Rick and morty api](https://rickandmortyapi.com/) to display a list of the characters of the show and their details.
This project uses:
- Clean architecture
- SOLID principles
- MVVM pattern 
- Repository pattern
- Unit testing
- Jetpack compose
- Dependency injection
- Image caching

## External Dependencies
This project uses some external dependencies. Most are standard in the industry, some are major libraries:
- **Coil:** for image display
- **Koin:** for dependency injection
- **Retrofit:**, Retrofit converters and OKHttp:++ for network requests
- **mockk:** for Mocking in the tests

## Test
This project includes unit tests for some classes in each layer as a Sample of how to test them. The most intersting or complex class in each layer is the one that has been tested.

## Future work and discussion points
This are some of potential areas to be improved or discussed when talking about the project:
- Extracting interfaces for view states, actions and viewmodels
- Adding an additional cache for character details
- Changing pagination implementation
- UI testing
