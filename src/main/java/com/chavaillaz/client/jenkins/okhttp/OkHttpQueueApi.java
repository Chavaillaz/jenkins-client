package com.chavaillaz.client.jenkins.okhttp;

import static com.chavaillaz.client.common.okhttp.OkHttpUtils.ofFormData;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.domain.queue.QueueItem;
import com.chavaillaz.client.jenkins.domain.queue.QueueItems;
import okhttp3.OkHttpClient;

/**
 * Implementation of {@link QueueApi} for OkHttp.
 */
public class OkHttpQueueApi extends AbstractOkHttpClient implements QueueApi {

    /**
     * Creates a new queue client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public OkHttpQueueApi(OkHttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<QueueItems> getQueueItems() {
        return sendAsync(requestBuilder(URL_QUEUE_LIST).get(), QueueItems.class);
    }

    @Override
    public CompletableFuture<QueueItem> getQueueItem(long itemId) {
        return sendAsync(requestBuilder(URL_QUEUE_ITEM, itemId).get(), QueueItem.class);
    }

    @Override
    public CompletableFuture<Void> cancelQueueItem(long itemId) {
        return sendAsync(requestBuilder(URL_QUEUE_ITEM_CANCEL, itemId)
                .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .post(ofFormData(Map.of("id", itemId))), Void.class);
    }

}
