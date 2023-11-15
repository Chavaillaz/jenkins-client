package com.chavaillaz.jenkins.client.java;

import static com.chavaillaz.jenkins.client.java.JavaHttpUtils.ofFormData;

import java.net.http.HttpClient;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jenkins.client.Authentication;
import com.chavaillaz.jenkins.client.QueueClient;
import com.chavaillaz.jenkins.domain.QueueItem;
import com.chavaillaz.jenkins.domain.QueueItems;

public class JavaHttpQueueClient extends AbstractJavaHttpClient implements QueueClient {

    /**
     * Creates a new queue client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpQueueClient(HttpClient client, String baseUrl, Authentication authentication) {
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
