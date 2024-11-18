# Notes App

A simple Notes App built with Kotlin, using the Room Database for local storage, that allows users to manage notes, URLs, and passwords. This app supports features like creating, updating, deleting notes, adding images, searching, and managing URLs and passwords with various functionalities.

## Features

### 1. **Notes Management**
- **Create Note**: Users can create new notes with text and an optional image.
- **Update Note**: Users can update existing notes.
- **Long-Press Delete**: Notes can be deleted via a long press action.
- **Background Color Change**: Notes' background color can be changed dynamically.
- **Add Image**: Users can attach an image to each note.
- **Search Notes**: Search functionality to find notes based on keywords.

### 2. **URL Management**
- **Save URL**: Users can save URLs.
- **Long-Press Delete URL**: URLs can be deleted via a long press action.
- **Open URL**: Click a URL to open it in a browser.
- **Search URLs**: Search functionality to find saved URLs.

### 3. **Password Management**
- **Save Password**: Save passwords along with an associated ID.
- **Delete Password**: Passwords can be deleted.
- **Copy Password/ID**: Users can copy passwords and their associated IDs to the clipboard.
- **Search Passwords**: Search functionality to find saved passwords based on IDs or other details.

## Tech Stack

- **JAVA**
- **Room Database** for local storage
- **RecyclerView** for displaying notes, URLs, and passwords
- **Material Design** components

## Room Database Entities

- **NoteEntity**: Represents a note with a title, description, background color, image URI, and creation timestamp.
- **UrlEntity**: Represents a URL with a description and URL string.
- **PasswordEntity**: Represents a saved password with an associated ID and password string.

## Usage

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/xSunRayStudiox/Notes-App.git
    ```

2. **Open the Project** in Android Studio.

3. **Run the App** on an emulator or physical device.

4. **Create Notes**: Tap on the "Create" button to add a new note. You can also add an image to the note.

5. **Update Notes**: Tap on any existing note to edit it.

6. **Delete Notes**: Long-press on any note to delete it.

7. **Search Notes**: Use the search bar to find notes based on text content.

8. **Save and Manage URLs**: Add a URL by tapping on the "Save URL" button. You can click on saved URLs to open them in the browser. Long-press to delete URLs.

9. **Save and Manage Passwords**: Add passwords with associated IDs, and use the long-press action to delete. You can also copy passwords or IDs to the clipboard.

## Dependencies

- `Room`: For local database storage.
- `RecyclerView`: For displaying lists of notes, URLs, and passwords.
- `Glide`: For loading images into notes.
