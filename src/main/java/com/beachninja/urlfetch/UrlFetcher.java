package com.beachninja.urlfetch;

import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Wrapper for Google App Engine's URLFetchService.
 * @author bradwee2000@gmail.com
 */
@Singleton
public class UrlFetcher {

  private final URLFetchService urlFetchService;

  @Inject
  public UrlFetcher(final URLFetchService urlFetchService) {
    this.urlFetchService = urlFetchService;
  }

  public UrlFetchBuilder connect(final String url) {
    return new UrlFetchBuilder(urlFetchService, url);
  }
}
