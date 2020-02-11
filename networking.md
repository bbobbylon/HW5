# Networking

## Android SDK Networking Package
- java.net
- java.io
- android.net

## Communicating via a URL
- Use the Java `URL` class to create a URL object
- `URLConnection` objects represent a communications link between the application and a URL.
- The `URL` method `openConnection` returns a `URLConnection` object
- `HttpURLConnection` is used to send data over the web

## Reading from a URL
- Use `getInputStream()` to retrieve an input stream to read from the URL connection.
- Read from the stream using a `Scanner` object

## Networking Lab
- Goal: Fetch text from a web page  
- Open the NeworkLab project
- Review the layout and code
  - Note the click method is registered with the button in the XML layout


 **Code**
- In the 'goButtonClick' method, declare an `HttpURLConnection` object
- Inside of a [try](https://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html) block (several statement use methods that have [checked exceptions](https://www.geeksforgeeks.org/checked-vs-unchecked-exceptions-in-java/))
  - Create the URL from the string
  - Open the connection
  - Declare a scanner
  - Read from the stream and store the result in `jsonData`
- In a [finally](https://docs.oracle.com/javase/tutorial/essential/exceptions/finally.html) block disconnect the URL connection
- Set the text in the TextView


## Permissions
- In order to read from the Internet we have grant our application permission to access the Internet
- We do this by adding the Internet permission to the manifest
`<uses-permission android:name="android.permission.INTERNET" />`

## Try it out
**It fails…**
- The exception that causes the crash is `NetworkOnMainThreadException`.
- We cannot access network files unless we are on a separate thread.
