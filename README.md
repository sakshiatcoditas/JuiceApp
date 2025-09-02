# Juice Tracker App

A modern **Juice Tracker** Android application built using **Jetpack Compose**, XML layouts, and following the **MVVM architecture**. This app allows users to add, edit, and delete juice entries while keeping track of juice details like name, description, rating, and color.

---

## Features

- **Add Juice Entries:** Users can add new juice entries with name, description, color, and rating.  
- **Edit & Delete:** Modify existing entries or delete them directly from the list.  
- **Color Selection:** Choose juice color from a dropdown menu.  
- **Rating System:** Rate juices using a rating bar.  
- **RecyclerView Display:** Displays all juices in a clean and scrollable list.  
- **Navigation Component:** Seamless navigation between screens using Jetpack Navigation.  
- **MVVM Architecture:** Separation of concerns with `ViewModel`, `Repository`, and `UI` layers.  
- **Kotlin Coroutines & Flow:** Observes live data streams for real-time updates.  
- **BottomSheetDialogFragment:** Easy data entry using modern bottom sheet UI.


## Architecture & Libraries Used

- **MVVM (Model-View-ViewModel):**  
  - `JuiceViewModel` handles UI logic.  
  - `Repository` manages data operations.
    
- **Jetpack Components:**  
  - `ViewModel`, `LiveData`, `Navigation Component`.  
  - `RecyclerView` with `ListAdapter` for efficient lists.
  - 
- **XML & Compose Interoperability:** Combines modern Compose UI elements with traditional XML layouts.  



