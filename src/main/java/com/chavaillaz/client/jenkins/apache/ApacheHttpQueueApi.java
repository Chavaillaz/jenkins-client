package com.chavaillaz.client.jenkins.apache;

import static com.chavaillaz.client.common.apache.ApacheHttpUtils.formData;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.domain.queue.QueueItem;
import com.chavaillaz.client.jenkins.domain.queue.QueueItems;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

/**
 * Implementation of {@link QueueApi} for Apache HTTP.
 */
public class ApacheHttpQueueApi extends AbstractApacheHttpClient implements QueueApi {

    /**
     * Creates a new queue client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public ApacheHttpQueueApi(CloseableHttpAsyncClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<QueueItems> getQueueItems() {
        return sendAsync(requestBuilder(get(), URL_QUEUE_LIST), QueueItems.class);
    }

    @Override
    public CompletableFuture<QueueItem> getQueueItem(long itemId) {
        return sendAsync(requestBuilder(get(), URL_QUEUE_ITEM, itemId), QueueItem.class);
    }

    @Override
    public CompletableFuture<Void> cancelQueueItem(long itemId) {
        return sendAsync(requestBuilder(post(), URL_QUEUE_ITEM_CANCEL, itemId)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .addParameters(formData(Map.of("id", itemId))), Void.class);
    }

}
