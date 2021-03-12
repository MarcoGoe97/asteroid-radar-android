### Prerequisites

An API key from NASA is required. You can get it from https://api.nasa.gov/

Search for the following line in the build.gradle(:app) file:
```
it.buildConfigField 'String', 'API_KEY', '"XXXXXX"'
```
where "XXXXXX" is replaced with your actual API key.

You will not be able to build or use the app until this is done.