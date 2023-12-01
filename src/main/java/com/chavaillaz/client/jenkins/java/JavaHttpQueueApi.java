package com.chavaillaz.client.jenkins.java;

import static com.chavaillaz.client.common.java.JavaHttpUtils.ofFormData;

import java.net.http.HttpClient;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.domain.queue.QueueItem;
import com.chavaillaz.client.jenkins.domain.queue.QueueItems;

/**
 * Implementation of {@link QueueApi} for Java HTTP.
 */
public class JavaHttpQueueApi extends AbstractJavaHttpClient implements QueueApi {

    /**
     * Creates a new queue client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpQueueApi(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<QueueItems> getQueueItems() {
        return sendAsync(requestBuilder(URL_QUEUE_LIST).GET(), QueueItems.class);
    }

    @Override
    public CompletableFuture<QueueItem> getQueueItem(long itemId) {
        return sendAsync(requestBuilder(URL_QUEUE_ITEM, itemId).GET(), QueueItem.class);
    }

    @Override
    public CompletableFuture<Void> cancelQueueItem(long itemId) {
        return sendAsync(requestBuilder(URL_QUEUE_ITEM_CANCEL, itemId)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .POST(ofFormData(Map.of("id", itemId))), Void.class);
    }

}
