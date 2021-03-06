/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.action.admin.cluster.snapshots.restore;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.MasterNodeOperationRequestBuilder;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.internal.InternalClusterAdminClient;
import org.elasticsearch.common.settings.Settings;

import java.util.Map;

/**
 * Restore snapshot request builder
 */
public class RestoreSnapshotRequestBuilder extends MasterNodeOperationRequestBuilder<RestoreSnapshotRequest, RestoreSnapshotResponse, RestoreSnapshotRequestBuilder> {

    /**
     * Constructs new restore snapshot request builder
     *
     * @param clusterAdminClient cluster admin client
     */
    public RestoreSnapshotRequestBuilder(ClusterAdminClient clusterAdminClient) {
        super((InternalClusterAdminClient) clusterAdminClient, new RestoreSnapshotRequest());
    }

    /**
     * Constructs new restore snapshot request builder with specified repository and snapshot names
     *
     * @param clusterAdminClient cluster admin client
     * @param repository         reposiory name
     * @param name               snapshot name
     */
    public RestoreSnapshotRequestBuilder(ClusterAdminClient clusterAdminClient, String repository, String name) {
        super((InternalClusterAdminClient) clusterAdminClient, new RestoreSnapshotRequest(repository, name));
    }


    /**
     * Sets snapshot name
     *
     * @param snapshot snapshot name
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setSnapshot(String snapshot) {
        request.snapshot(snapshot);
        return this;
    }

    /**
     * Sets repository name
     *
     * @param repository repository name
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setRepository(String repository) {
        request.repository(repository);
        return this;
    }

    /**
     * Sets the list of indices that should be restored from snapshot
     * <p/>
     * The list of indices supports multi-index syntax. For example: "+test*" ,"-test42" will index all indices with
     * prefix "test" except index "test42". Aliases are not supported. An empty list or {"_all"} will restore all open
     * indices in the snapshot.
     *
     * @param indices list of indices
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setIndices(String... indices) {
        request.indices(indices);
        return this;
    }

    /**
     * Specifies what type of requested indices to ignore and how to deal with wildcard expressions.
     * For example indices that don't exist.
     *
     * @param indicesOptions the desired behaviour regarding indices to ignore and wildcard indices expressions
     * @return this request
     */
    public RestoreSnapshotRequestBuilder setIndicesOptions(IndicesOptions indicesOptions) {
        request.indicesOptions(indicesOptions);
        return this;
    }


    /**
     * Sets rename pattern that should be applied to restored indices.
     * <p/>
     * Indices that match the rename pattern will be renamed according to {@link #setRenameReplacement(String)}. The
     * rename pattern is applied according to the {@link java.util.regex.Matcher#appendReplacement(StringBuffer, String)}
     * The request will fail if two or more indices will be renamed into the same name.
     *
     * @param renamePattern rename pattern
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setRenamePattern(String renamePattern) {
        request.renamePattern(renamePattern);
        return this;
    }

    /**
     * Sets rename replacement
     * <p/>
     * See {@link #setRenamePattern(String)} for more information.
     *
     * @param renameReplacement rename replacement
     * @return
     */
    public RestoreSnapshotRequestBuilder setRenameReplacement(String renameReplacement) {
        request.renameReplacement(renameReplacement);
        return this;
    }


    /**
     * Sets repository-specific restore settings.
     * <p/>
     * See repository documentation for more information.
     *
     * @param settings repository-specific snapshot settings
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setSettings(Settings settings) {
        request.settings(settings);
        return this;
    }

    /**
     * Sets repository-specific restore settings.
     * <p/>
     * See repository documentation for more information.
     *
     * @param settings repository-specific snapshot settings
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setSettings(Settings.Builder settings) {
        request.settings(settings);
        return this;
    }

    /**
     * Sets repository-specific restore settings in JSON, YAML or properties format
     * <p/>
     * See repository documentation for more information.
     *
     * @param source repository-specific snapshot settings
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setSettings(String source) {
        request.settings(source);
        return this;
    }

    /**
     * Sets repository-specific restore settings
     * <p/>
     * See repository documentation for more information.
     *
     * @param source repository-specific snapshot settings
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setSettings(Map<String, Object> source) {
        request.settings(source);
        return this;
    }

    /**
     * If this parameter is set to true the operation will wait for completion of restore process before returning.
     *
     * @param waitForCompletion if true the operation will wait for completion
     * @return this builder
     */
    public RestoreSnapshotRequestBuilder setWaitForCompletion(boolean waitForCompletion) {
        request.waitForCompletion(waitForCompletion);
        return this;
    }

    /**
     * If set to true the restore procedure will restore global cluster state.
     * <p/>
     * The global cluster state includes persistent settings and index template definitions.
     *
     * @param restoreGlobalState true if global state should be restored from the snapshot
     * @return this request
     */
    public RestoreSnapshotRequestBuilder setRestoreGlobalState(boolean restoreGlobalState) {
        request.includeGlobalState(restoreGlobalState);
        return this;
    }

    @Override
    protected void doExecute(ActionListener<RestoreSnapshotResponse> listener) {
        ((ClusterAdminClient) client).restoreSnapshot(request, listener);
    }
}
