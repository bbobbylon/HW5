# Threads
- Computers can perform operations concurrently.
- Operating systems on single-processor computers create the illusion of concurrent execution by rapidly switching between activities.
  - Time slicing

## Concurrent programming
In concurrent programming, there are two basic units of execution:
- Process: Generally has a complete, private set of basic run-time and  resources and its own memory space
- Threads: Exist within a process — every process has at least one.
  - Sometimes called lightweight processes


- Multithreaded execution is an essential feature of the Java platform.
  - Makes the UI more responsive
  - Allows for asynchronous or background processing


## Using Threads in Android
- One Approach:
  - Create a `Handler` object in your UI thread
  - Use worker threads to perform any required expensive operations
  - Post results either through a `Runnable` or a `Message`
  - Update the views on the UI thread as needed


- Another approach - use `AsyncTask`
  - Uses [Java Generics](https://docs.oracle.com/javase/tutorial/java/generics/index.html) which allow a type or method to operate on objects of various types
  - Must specify
    - the type of the parameters
    - the progress values
    - the final value of the task


## AsyncTask Methods
- `doInBackground() `   - this is the where you define the task
- `onPostExecute() `- called once your action is done.  Runs within the UI thread
- `onPreExecute()` - executes before the background task 
- `publishProgress()`  - used to publish updates on the UI thread while the background task is still running
- onProgressUpdate() - executes when progress is updated

## Lab Activity
- Add a private class to MainActivity that extends AsyncTask
  - This class will use a String containing a URL and update the text view
  - It will not keep track of progress
- Add the required unimplemented method `doInBackground() ` and add the code from the buttonClick method
  - Modify it so that it
    - Retrieves the URL from the parameter
    - Returns the string so that it can be processed in onPostExecute
- Add the `onPostExecute` method to set the text in the TextView
- Modify the `goButtonClick` method to create an instance of the DownloadData class and execute the task.
