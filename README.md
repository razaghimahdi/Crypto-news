# Crypto News

## Introduction:
Crypto News is an open-source Android Kotlin application that displays up-to-date news about cryptocurrencies. 
It is developed by Mahdi Razzaghi Ghaleh, with a base MVI architecture using use-cases. 
The app uses Retrofit to retrieve data from the network and Koin for dependency injection. 
It also uses Glide to load images.


## Features:
The app is built on an MVI architecture, which stands for Model-View-Intent. 
This makes it easy to separate the different components of the app and maintain a clean codebase.

The app has several features that users would love, including:

1. Paging: The app uses paging to load news items in batches, reducing the time it takes to load the app.

2. Filtering and Searching: Users can filter and search for news items using different criteria such as keyword, time, or source.

3. API Key: However, it's essential to note that you must add the API key in build.gradle for it to function correctly. 
To get the API key, you can visit `cryptonews-api`.com and follow the instructions provided.

| Main | Filtering |  
| :---: | :---: |
| ![](screenshots/1.jpg) | ![](screenshots/2.jpg)  |  


## Technology and Library Used:
To make the app fast, efficient, and user-friendly, several technologies and libraries were employed. These include:

1. Retrofit: Retrofit is used to retrieve data from the network. It is an HTTP client library for Android and Java that makes it easy to parse JSON data.

2. Koin: Koin is a dependency injection library that makes it easy to declare, inject, and manage dependencies in your app.

3. Glide: Glide is a fast and powerful image loading library that enables images to load seamlessly in the app.

## Conclusion:
The Crypto News Android Kotlin application is a simple yet powerful app that helps users stay informed about the latest crypto news. 
With its MVI architecture, paging, filtering, and searching features, the app provides a seamless and user-friendly experience. 
Additionally, with Retrofit, Koin, and Glide, the app loads fast and efficiently.

  