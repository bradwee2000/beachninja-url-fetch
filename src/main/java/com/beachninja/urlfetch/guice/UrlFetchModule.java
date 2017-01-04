package com.beachninja.urlfetch.guice;

import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.inject.AbstractModule;

/**
 * @author bradwee2000@gmail.com
 */
public class UrlFetchModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(URLFetchService.class).toInstance(URLFetchServiceFactory.getURLFetchService());
  }
}
