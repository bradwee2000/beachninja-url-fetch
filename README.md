# beachninja-url-fetch
Easy-to-use wrapper for Google App Engine's URLFetchServiceFactory.

## Usage Example

```java
final UrlFetcher urlFetcher = new UrlFetcher(URLFetchServiceFactory.getURLFetchService());

final Future<HTTPResponse> futureResponse = urlFetcher.connect("https://sample.link.com")
  .data("firstname", "John")
  .data("lastname", "Doe")
  .post()
```