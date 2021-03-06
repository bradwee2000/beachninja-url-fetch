package com.beachninja.urlfetch;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author bradwee2000@gmail.com
 */
public class UrlFetchBuilderTest {

  private URLFetchService urlFetchService;
  private Future<HTTPResponse> futureResponse;

  private UrlFetchBuilder urlFetchBuilder;

  @Before
  public void before() {
    urlFetchService = mock(URLFetchService.class);
    futureResponse = mock(Future.class);

    when(urlFetchService.fetchAsync(any(HTTPRequest.class))).thenReturn(futureResponse);

    urlFetchBuilder = new UrlFetchBuilder(urlFetchService, "http://localhost:8080");
  }

  @Test
  public void testGetParamsAsBytes_shouldConvertParamsToBytes() {
    final byte[] payload = "&data1=value1&data2=value2".getBytes();

    assertThat(urlFetchBuilder
        .data("data1", "value1")
        .data("data2", "value2").getParamsAsBytes()).isEqualTo(payload);
  }

  @Test
  public void testAddData_shouldAddDataToRequest() {
    final Map<String, String> params = urlFetchBuilder.data("data1", "value1").data("data2", "value2").getParams();

    assertThat(params.get("data1")).isEqualTo("value1");
    assertThat(params.get("data2")).isEqualTo("value2");
  }

  @Test
  public void testPost_shouldRequestPost() {
    final ArgumentCaptor<HTTPRequest> requestCaptor = ArgumentCaptor.forClass(HTTPRequest.class);
    final Future<HTTPResponse> response = urlFetchBuilder.data("data", "value").post();

    assertThat(response).isEqualTo(futureResponse);

    verify(urlFetchService).fetchAsync(requestCaptor.capture());

    final HTTPRequest request = requestCaptor.getValue();
    assertThat(request.getMethod()).isEqualTo(HTTPMethod.POST);
    assertThat(request.getPayload()).isEqualTo("&data=value".getBytes());
    assertThat(request.getURL().toString()).isEqualTo("http://localhost:8080");
  }

  @Test
  public void testGet_shouldRequestGet() {
    final ArgumentCaptor<HTTPRequest> requestCaptor = ArgumentCaptor.forClass(HTTPRequest.class);
    final Future<HTTPResponse> response = urlFetchBuilder.data("data", "value").get();

    assertThat(response).isEqualTo(futureResponse);

    verify(urlFetchService).fetchAsync(requestCaptor.capture());

    final HTTPRequest request = requestCaptor.getValue();
    assertThat(request.getMethod()).isEqualTo(HTTPMethod.GET);
    assertThat(request.getPayload()).isEqualTo("&data=value".getBytes());
    assertThat(request.getURL().toString()).isEqualTo("http://localhost:8080");
  }
}
