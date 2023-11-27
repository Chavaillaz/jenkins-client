package com.chavaillaz.client.jenkins.api;

import static java.util.Optional.empty;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.domain.QueueItem;
import com.chavaillaz.client.jenkins.domain.QueueItems;

public interface QueueClient {

    String URL_QUEUE_LIST = "queue/api/json";
    String URL_QUEUE_ITEM = "queue/item/{0,number,#}/api/json";
    String URL_QUEUE_ITEM_CANCEL = "queue/cancelItem";

    /**
     * Gets the list of queue items.
     *
     * @return A {@link CompletableFuture} with the queue items
     */
    CompletableFuture<QueueItems> getQueueItems();

    /**
     * Gets a specific queue item.
     * Queue items are builds that have been scheduled to run, but are waiting for a slot.
     * You can poll the queue item that corresponds to a build to detect whether the build is still pending or is executing.
     *
     * @param queueId The queue identifier
     * @return A {@link CompletableFuture} with the queue item
     */
    CompletableFuture<QueueItem> getQueueItem(long queueId);

    /**
     * Gets a specific queue item as an {@link Optional}.
     *
     * @param queueId The queue identifier
     * @return A {@link CompletableFuture} with the queue item
     * @see #getQueueItem(long)
     */
    default CompletableFuture<Optional<QueueItem>> getQueueItemOptional(long queueId) {
        return getQueueItem(queueId)
                .thenApply(Optional::of)
                .exceptionally(exception -> empty());
    }

    /**
     * Cancels a queue item before it gets built.
     *
     * @param id The queue identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> cancelQueueItem(long id);

}
