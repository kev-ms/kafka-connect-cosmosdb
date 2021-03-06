package com.microsoft.azure.cosmosdb.kafka.connect.source;

import com.microsoft.azure.cosmosdb.kafka.connect.BooleanSetting;
import com.microsoft.azure.cosmosdb.kafka.connect.NumericSetting;
import com.microsoft.azure.cosmosdb.kafka.connect.Setting;
import com.microsoft.azure.cosmosdb.kafka.connect.SettingDefaults;
import com.microsoft.azure.cosmosdb.kafka.connect.Settings;
import org.apache.commons.collections4.ListUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Contains settings for the CosmosDB Kafka Source Connector
 */
public class SourceSettings extends Settings {
    private String postProcessor;
    private boolean startFromBeginning;
    private final List<Setting> sourceSettings = Arrays.asList(
            //Add all source settings here:
            new NumericSetting(PREFIX + ".task.timeout", "The max number of milliseconds the source task will use to read documents before sending them to Kafka.",
                    "Task Timeout", SettingDefaults.TASK_TIMEOUT, this::setTaskTimeout, this::getTaskTimeout),
            new NumericSetting(PREFIX + ".task.buffer.size", "The max size the container of documents (in bytes) the source task will buffer before sending them to Kafka.",
                    "Task reader buffer size", SettingDefaults.TASK_BUFFER_SIZE, this::setTaskBufferSize, this::getTaskBufferSize),
            new NumericSetting(PREFIX + ".task.batch.size","The max number of documents the source task will buffer before sending them to Kafka.",
                    "Task batch size", SettingDefaults.TASK_BATCH_SIZE, this::setTaskBatchSize, this::getTaskBatchSize),
            new NumericSetting(PREFIX + ".task.poll.interval","The default polling interval in milliseconds that a source task polls for changes.",
                    "Task poll interval", SettingDefaults.TASK_POLL_INTERVAL, this::setTaskPollInterval, this::getTaskPollInterval),
            new Setting(Settings.PREFIX + ".containers", "A comma delimited list of source container names.",
                    "Container Names List", this::setContainerList, this::getContainerList),
            new Setting(Settings.PREFIX + ".source.post-processor", "Comma-separated list of Source Post-Processor class names to use for post-processing.",
                    "Source post-processor", this::setPostProcessor, this::getPostProcessor),
            new Setting(Settings.PREFIX + ".assigned.container", "The Cosmos DB Feed Container assigned to the task.",
                    "Assigned Container", this::setAssignedContainer, this::getAssignedContainer),
            new Setting(Settings.PREFIX + ".worker.name", "The Cosmos DB worker name.",
                    "Worker name", this::setWorkerName, this::getWorkerName),
            new BooleanSetting(Settings.PREFIX + ".changefeed.startFromBeginning", "Whether the change feed should start from beginning.",
                    "Change Feed start from beginning", SourceSettingDefaults.CHANGE_FEED_START_FROM_BEGINNING, this::setStartFromBeginning, this::isStartFromBeginning)

    );


    private String workerName;

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerName() {
        return this.workerName;
    }

    private String assignedContainer;

    public String getAssignedContainer() {
        return this.assignedContainer;
    }

    public void setAssignedContainer(String assignedPartitions) {
        this.assignedContainer = assignedPartitions;
    }

    public String getPostProcessor() {
        return this.postProcessor;
    }

    public void setPostProcessor(String postProcessor) {
        this.postProcessor = postProcessor;
    }

    public boolean isStartFromBeginning() { return this.startFromBeginning; }

    public void setStartFromBeginning(boolean startFromBeginning) { this.startFromBeginning = startFromBeginning;  }

    @Override
    protected List<Setting> getAllSettings() {
        return ListUtils.union(super.getAllSettings(), sourceSettings);
    }

}
