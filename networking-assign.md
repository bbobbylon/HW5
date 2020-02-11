## Networking Assignment
In this assignment, you will create an app that retries an object from a web service.

### Requirements for this assignment:
- Data is retrieved from a web service, other than the ones used in the labs (NYT bestsellers, Open Trivia DB)
  - See list of potential web services below.  Note that there are many others, you do not have to chose from this list.
  - You will receive 0 pts. if you use any of the web services from the labs.
- Data is stored in a model object
- Data is displayed in the UI

###  Some Web Services
* [NYT Developers Network](https://developer.nytimes.com)
* [The Ticketmaster Developer Portal](https://developer.ticketmaster.com/products-and-docs/apis/getting-started/)
* [Jobs API](https://search.gov/developer/jobs.html)
* [Financial Data API](https://www.quandl.com/tools/api)
* [City of Chicago Data Portal](https://data.cityofchicago.org)
* [The Movie Database (TMDb)](https://www.themoviedb.org/documentation/api)
* [Accuweather](https://developer.accuweather.com/)
* [Open Chicago Transit Data](http://www.transitchicago.com/developers/)
* [Google APIs](https://developers.google.com/apis-explorer/)
* [Programmable Web API listing](https://www.programmableweb.com/apis/directory)
* **[A collective list of public JSON APIs for use in web development.](https://github.com/toddmotto/public-apis)**


### Grading

This assignment is worth **18 points**

Your assignment will be evaluated based on the following rubric:
- Contains an `AsyncTask` that downloads data from a web service: 5 pts.
- JSON data is correctly parsed: 4 pts.
- Includes a model class with appropriate constructor, getters and setters: 3 pts.
- Downloaded data is stored in a model object: 3 pts.
- Downloaded data is displayed in the user interface: 3 pts.
