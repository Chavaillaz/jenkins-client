package com.chavaillaz.client.jenkins.vertx;

import static com.chavaillaz.client.common.vertx.VertxUtils.formData;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.domain.queue.QueueItem;
import com.chavaillaz.client.jenkins.domain.queue.QueueItems;
import io.vertx.ext.web.client.WebClient;

/**
 * Implementation of {@link QueueApi} for Vert.x HTTP.
 */
public class VertxHttpQueueApi extends AbstractVertxHttpClient implements QueueApi {

    /**
     * Creates a new queue client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public VertxHttpQueueApi(WebClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<QueueItems> getQueueItems() {
        return handleAsync(requestBuilder(GET, URL_QUEUE_LIST).send(), QueueItems.class);
    }

    @Override
    public CompletableFuture<QueueItem> getQueueItem(long itemId) {
        return handleAsync(requestBuilder(GET, URL_QUEUE_ITEM, itemId).send(), QueueItem.class);
    }

    @Override
    public CompletableFuture<Void> cancelQueueItem(long itemId) {
        return handleAsync(requestBuilder(POST, URL_QUEUE_ITEM_CANCEL, itemId)
                .putHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .sendForm(formData(Map.of("id", itemId))), Void.class);
    }

}
