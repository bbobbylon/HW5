# JSON and REST Web Services

## JSON
- **J**ava**S**cript **O**bject **N**otation
- Lightweight format for storing and exchanging data
  - Human-readable text
  - Language independent
- Was originally specified by Douglas Crockford
- It is used primarily to transmit data between a server and web application  
  - An alternative to XML
- Built on two structures:
  - A collection of name/value pairs
  - An ordered list of values

### Syntax Rules
- JSON syntax is derived from JavaScript object notation syntax:
  - Data is in name/value pairs
  - Data is separated by commas
  - Curly braces hold objects
  - Square brackets hold arrays
- JSON values must be one of the following data types:
  - string
  - number
  - JSON object
  - array
  - boolean
  - null

### Examples
- [Book Example](http://cs.lewisu.edu/~howardcy/materials/php/books1.php)
- [Open Trivia Database](https://opentdb.com/api.php?amount=1&category=18&type=boolean)
- [NYT Bestsellers](http://api.nytimes.com/svc/books/v3/lists.json?api-key=42ff06dcd8c04a4cae037a10a43ffd4c&list=hardcover-fiction)
- For help parsing JSON data, install the [JSON Viewer](https://chrome.google.com/webstore/detail/json-viewer/gbmdgpbipfallnflgajpaliibnhdgobh) extension for Chrome

### Parsing JSON data
- Create a JSONObject: `JSONObject jObject = new JSONObject(result);`
- Retrieve a string: `String jsonString = jObject.getString("STRINGNAME");`
- Retrieve an integer: `int jsonInteger = jObject.getInt("INTEGERNAME");`
- Retrieve a JSONArray: `JSONArray jArray = jObject.getJSONArray("ARRAYNAME");`
- To get the items from the array

```java
JSONArray jArray = new JSONArray(jsonData);
for (int i = 0; i < jArray.length(); i++) {
    JSONObject jsonObject = jArray.getJSONObject(i);
    title = jsonObject.getString("title");
    isbn = jsonObject.getString("isbn");
    row = isbn + "\t" + title + "\n";
    results.append(row);
}
```

## REST Web Services
- Popular Web service model
  - Lightweight alternative to RPC and SOAP
- Uses The **RE**presentational **S**tate **T**ransfer Model
  - Design model for network applications
  - Platform independent
  - Language independent
  - Standards based
  - Runs on top of HTTP

### REST Design Principles
- Resource based: focus is on things not actions
- Stateless: interactions are stateless
  - Each request must contain all of the information required to complete it
- Cacheable

### URIs
- identify a resource not an action
- include everything needed to uniquely identify a resource
- should be intuitive
- To achieve these requirements, use a URI scheme that is similar to a directory structure

## Examples
- QuizApp
- NYT Bestsellers
  - This example uses the [OKHttp library](http://square.github.io/okhttp/)
  
 ## References
 - [Udacity - You Tube](https://www.youtube.com/watch?v=PKc8LVgsDA8)
 - [W3 Schools - JSON](https://www.w3schools.com/js/js_json_intro.asp)
