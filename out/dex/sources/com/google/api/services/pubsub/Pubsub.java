package com.google.api.services.pubsub;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.services.pubsub.model.AcknowledgeRequest;
import com.google.api.services.pubsub.model.CreateSnapshotRequest;
import com.google.api.services.pubsub.model.Empty;
import com.google.api.services.pubsub.model.ListSnapshotsResponse;
import com.google.api.services.pubsub.model.ListSubscriptionsResponse;
import com.google.api.services.pubsub.model.ListTopicSnapshotsResponse;
import com.google.api.services.pubsub.model.ListTopicSubscriptionsResponse;
import com.google.api.services.pubsub.model.ListTopicsResponse;
import com.google.api.services.pubsub.model.ModifyAckDeadlineRequest;
import com.google.api.services.pubsub.model.ModifyPushConfigRequest;
import com.google.api.services.pubsub.model.Policy;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PublishResponse;
import com.google.api.services.pubsub.model.PullRequest;
import com.google.api.services.pubsub.model.PullResponse;
import com.google.api.services.pubsub.model.SeekRequest;
import com.google.api.services.pubsub.model.SeekResponse;
import com.google.api.services.pubsub.model.SetIamPolicyRequest;
import com.google.api.services.pubsub.model.Snapshot;
import com.google.api.services.pubsub.model.Subscription;
import com.google.api.services.pubsub.model.TestIamPermissionsRequest;
import com.google.api.services.pubsub.model.TestIamPermissionsResponse;
import com.google.api.services.pubsub.model.Topic;
import com.google.api.services.pubsub.model.UpdateSnapshotRequest;
import com.google.api.services.pubsub.model.UpdateSubscriptionRequest;
import com.google.api.services.pubsub.model.UpdateTopicRequest;
import java.io.IOException;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
public class Pubsub extends AbstractGoogleJsonClient {
    public static final String DEFAULT_BASE_URL = "https://pubsub.googleapis.com/";
    public static final String DEFAULT_BATCH_PATH = "batch";
    public static final String DEFAULT_ROOT_URL = "https://pubsub.googleapis.com/";
    public static final String DEFAULT_SERVICE_PATH = "";

    public static final class Builder extends AbstractGoogleJsonClient.Builder {
        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
            super(httpTransport, jsonFactory, "https://pubsub.googleapis.com/", "", httpRequestInitializer, false);
            setBatchPath(Pubsub.DEFAULT_BATCH_PATH);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Pubsub build() {
            return new Pubsub(this);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setApplicationName(String str) {
            return (Builder) super.setApplicationName(str);
        }

        @Override // com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setBatchPath(String str) {
            return (Builder) super.setBatchPath(str);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
        }

        public Builder setPubsubRequestInitializer(PubsubRequestInitializer pubsubRequestInitializer) {
            return (Builder) super.setGoogleClientRequestInitializer((GoogleClientRequestInitializer) pubsubRequestInitializer);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setRootUrl(String str) {
            return (Builder) super.setRootUrl(str);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setServicePath(String str) {
            return (Builder) super.setServicePath(str);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setSuppressAllChecks(boolean z) {
            return (Builder) super.setSuppressAllChecks(z);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setSuppressPatternChecks(boolean z) {
            return (Builder) super.setSuppressPatternChecks(z);
        }

        @Override // com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder, com.google.api.client.googleapis.services.AbstractGoogleClient.Builder
        public Builder setSuppressRequiredParameterChecks(boolean z) {
            return (Builder) super.setSuppressRequiredParameterChecks(z);
        }
    }

    public class Projects {

        public class Snapshots {

            public class Create extends PubsubRequest<Snapshot> {
                private static final String REST_PATH = "v1/{+name}";
                private final Pattern NAME_PATTERN;

                @Key
                private String name;

                protected Create(String str, CreateSnapshotRequest createSnapshotRequest) {
                    super(Pubsub.this, HttpMethods.PUT, REST_PATH, createSnapshotRequest, Snapshot.class);
                    this.NAME_PATTERN = Pattern.compile("^projects/[^/]+/snapshots/[^/]+$");
                    this.name = (String) Preconditions.checkNotNull(str, "Required parameter name must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                }

                public String getName() {
                    return this.name;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Create set(String str, Object obj) {
                    return (Create) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> set$Xgafv2(String str) {
                    return (Create) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setAccessToken2(String str) {
                    return (Create) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setAlt2(String str) {
                    return (Create) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setCallback2(String str) {
                    return (Create) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setFields2(String str) {
                    return (Create) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setKey2(String str) {
                    return (Create) super.setKey2(str);
                }

                public Create setName(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                    }
                    this.name = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setOauthToken2(String str) {
                    return (Create) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setPrettyPrint2(Boolean bool) {
                    return (Create) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setQuotaUser2(String str) {
                    return (Create) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setUploadProtocol2(String str) {
                    return (Create) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType, reason: merged with bridge method [inline-methods] */
                public PubsubRequest<Snapshot> setUploadType2(String str) {
                    return (Create) super.setUploadType2(str);
                }
            }

            public class Delete extends PubsubRequest<Empty> {
                private static final String REST_PATH = "v1/{+snapshot}";
                private final Pattern SNAPSHOT_PATTERN;

                @Key
                private String snapshot;

                protected Delete(String str) {
                    super(Pubsub.this, HttpMethods.DELETE, REST_PATH, null, Empty.class);
                    this.SNAPSHOT_PATTERN = Pattern.compile("^projects/[^/]+/snapshots/[^/]+$");
                    this.snapshot = (String) Preconditions.checkNotNull(str, "Required parameter snapshot must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SNAPSHOT_PATTERN.matcher(str).matches(), "Parameter snapshot must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                }

                public String getSnapshot() {
                    return this.snapshot;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Delete set(String str, Object obj) {
                    return (Delete) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Empty> set$Xgafv2(String str) {
                    return (Delete) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Empty> setAccessToken2(String str) {
                    return (Delete) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Empty> setAlt2(String str) {
                    return (Delete) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Empty> setCallback2(String str) {
                    return (Delete) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Empty> setFields2(String str) {
                    return (Delete) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Empty> setKey2(String str) {
                    return (Delete) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Empty> setOauthToken2(String str) {
                    return (Delete) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Empty> setPrettyPrint2(Boolean bool) {
                    return (Delete) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Empty> setQuotaUser2(String str) {
                    return (Delete) super.setQuotaUser2(str);
                }

                public Delete setSnapshot(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SNAPSHOT_PATTERN.matcher(str).matches(), "Parameter snapshot must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                    }
                    this.snapshot = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Empty> setUploadProtocol2(String str) {
                    return (Delete) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Empty> setUploadType2(String str) {
                    return (Delete) super.setUploadType2(str);
                }
            }

            public class Get extends PubsubRequest<Snapshot> {
                private static final String REST_PATH = "v1/{+snapshot}";
                private final Pattern SNAPSHOT_PATTERN;

                @Key
                private String snapshot;

                protected Get(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, Snapshot.class);
                    this.SNAPSHOT_PATTERN = Pattern.compile("^projects/[^/]+/snapshots/[^/]+$");
                    this.snapshot = (String) Preconditions.checkNotNull(str, "Required parameter snapshot must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SNAPSHOT_PATTERN.matcher(str).matches(), "Parameter snapshot must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public String getSnapshot() {
                    return this.snapshot;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Get set(String str, Object obj) {
                    return (Get) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Snapshot> set$Xgafv2(String str) {
                    return (Get) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Snapshot> setAccessToken2(String str) {
                    return (Get) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Snapshot> setAlt2(String str) {
                    return (Get) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Snapshot> setCallback2(String str) {
                    return (Get) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Snapshot> setFields2(String str) {
                    return (Get) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Snapshot> setKey2(String str) {
                    return (Get) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Snapshot> setOauthToken2(String str) {
                    return (Get) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Snapshot> setPrettyPrint2(Boolean bool) {
                    return (Get) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Snapshot> setQuotaUser2(String str) {
                    return (Get) super.setQuotaUser2(str);
                }

                public Get setSnapshot(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SNAPSHOT_PATTERN.matcher(str).matches(), "Parameter snapshot must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                    }
                    this.snapshot = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Snapshot> setUploadProtocol2(String str) {
                    return (Get) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Snapshot> setUploadType2(String str) {
                    return (Get) super.setUploadType2(str);
                }
            }

            public class GetIamPolicy extends PubsubRequest<Policy> {
                private static final String REST_PATH = "v1/{+resource}:getIamPolicy";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected GetIamPolicy(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, Policy.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/snapshots/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public GetIamPolicy set(String str, Object obj) {
                    return (GetIamPolicy) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Policy> set$Xgafv2(String str) {
                    return (GetIamPolicy) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Policy> setAccessToken2(String str) {
                    return (GetIamPolicy) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Policy> setAlt2(String str) {
                    return (GetIamPolicy) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Policy> setCallback2(String str) {
                    return (GetIamPolicy) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Policy> setFields2(String str) {
                    return (GetIamPolicy) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Policy> setKey2(String str) {
                    return (GetIamPolicy) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Policy> setOauthToken2(String str) {
                    return (GetIamPolicy) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Policy> setPrettyPrint2(Boolean bool) {
                    return (GetIamPolicy) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Policy> setQuotaUser2(String str) {
                    return (GetIamPolicy) super.setQuotaUser2(str);
                }

                public GetIamPolicy setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Policy> setUploadProtocol2(String str) {
                    return (GetIamPolicy) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Policy> setUploadType2(String str) {
                    return (GetIamPolicy) super.setUploadType2(str);
                }
            }

            public class List extends PubsubRequest<ListSnapshotsResponse> {
                private static final String REST_PATH = "v1/{+project}/snapshots";
                private final Pattern PROJECT_PATTERN;

                @Key
                private Integer pageSize;

                @Key
                private String pageToken;

                @Key
                private String project;

                protected List(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, ListSnapshotsResponse.class);
                    this.PROJECT_PATTERN = Pattern.compile("^projects/[^/]+$");
                    this.project = (String) Preconditions.checkNotNull(str, "Required parameter project must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.PROJECT_PATTERN.matcher(str).matches(), "Parameter project must conform to the pattern ^projects/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public Integer getPageSize() {
                    return this.pageSize;
                }

                public String getPageToken() {
                    return this.pageToken;
                }

                public String getProject() {
                    return this.project;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public List set(String str, Object obj) {
                    return (List) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<ListSnapshotsResponse> set$Xgafv2(String str) {
                    return (List) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<ListSnapshotsResponse> setAccessToken2(String str) {
                    return (List) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<ListSnapshotsResponse> setAlt2(String str) {
                    return (List) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<ListSnapshotsResponse> setCallback2(String str) {
                    return (List) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<ListSnapshotsResponse> setFields2(String str) {
                    return (List) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<ListSnapshotsResponse> setKey2(String str) {
                    return (List) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<ListSnapshotsResponse> setOauthToken2(String str) {
                    return (List) super.setOauthToken2(str);
                }

                public List setPageSize(Integer num) {
                    this.pageSize = num;
                    return this;
                }

                public List setPageToken(String str) {
                    this.pageToken = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<ListSnapshotsResponse> setPrettyPrint2(Boolean bool) {
                    return (List) super.setPrettyPrint2(bool);
                }

                public List setProject(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.PROJECT_PATTERN.matcher(str).matches(), "Parameter project must conform to the pattern ^projects/[^/]+$");
                    }
                    this.project = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<ListSnapshotsResponse> setQuotaUser2(String str) {
                    return (List) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<ListSnapshotsResponse> setUploadProtocol2(String str) {
                    return (List) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<ListSnapshotsResponse> setUploadType2(String str) {
                    return (List) super.setUploadType2(str);
                }
            }

            public class Patch extends PubsubRequest<Snapshot> {
                private static final String REST_PATH = "v1/{+name}";
                private final Pattern NAME_PATTERN;

                @Key
                private String name;

                protected Patch(String str, UpdateSnapshotRequest updateSnapshotRequest) {
                    super(Pubsub.this, HttpMethods.PATCH, REST_PATH, updateSnapshotRequest, Snapshot.class);
                    this.NAME_PATTERN = Pattern.compile("^projects/[^/]+/snapshots/[^/]+$");
                    this.name = (String) Preconditions.checkNotNull(str, "Required parameter name must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                }

                public String getName() {
                    return this.name;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Patch set(String str, Object obj) {
                    return (Patch) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Snapshot> set$Xgafv2(String str) {
                    return (Patch) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Snapshot> setAccessToken2(String str) {
                    return (Patch) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Snapshot> setAlt2(String str) {
                    return (Patch) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Snapshot> setCallback2(String str) {
                    return (Patch) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Snapshot> setFields2(String str) {
                    return (Patch) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Snapshot> setKey2(String str) {
                    return (Patch) super.setKey2(str);
                }

                public Patch setName(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                    }
                    this.name = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Snapshot> setOauthToken2(String str) {
                    return (Patch) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Snapshot> setPrettyPrint2(Boolean bool) {
                    return (Patch) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Snapshot> setQuotaUser2(String str) {
                    return (Patch) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Snapshot> setUploadProtocol2(String str) {
                    return (Patch) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Snapshot> setUploadType2(String str) {
                    return (Patch) super.setUploadType2(str);
                }
            }

            public class SetIamPolicy extends PubsubRequest<Policy> {
                private static final String REST_PATH = "v1/{+resource}:setIamPolicy";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected SetIamPolicy(String str, SetIamPolicyRequest setIamPolicyRequest) {
                    super(Pubsub.this, "POST", REST_PATH, setIamPolicyRequest, Policy.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/snapshots/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public SetIamPolicy set(String str, Object obj) {
                    return (SetIamPolicy) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Policy> set$Xgafv2(String str) {
                    return (SetIamPolicy) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Policy> setAccessToken2(String str) {
                    return (SetIamPolicy) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Policy> setAlt2(String str) {
                    return (SetIamPolicy) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Policy> setCallback2(String str) {
                    return (SetIamPolicy) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Policy> setFields2(String str) {
                    return (SetIamPolicy) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Policy> setKey2(String str) {
                    return (SetIamPolicy) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Policy> setOauthToken2(String str) {
                    return (SetIamPolicy) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Policy> setPrettyPrint2(Boolean bool) {
                    return (SetIamPolicy) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Policy> setQuotaUser2(String str) {
                    return (SetIamPolicy) super.setQuotaUser2(str);
                }

                public SetIamPolicy setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Policy> setUploadProtocol2(String str) {
                    return (SetIamPolicy) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Policy> setUploadType2(String str) {
                    return (SetIamPolicy) super.setUploadType2(str);
                }
            }

            public class TestIamPermissions extends PubsubRequest<TestIamPermissionsResponse> {
                private static final String REST_PATH = "v1/{+resource}:testIamPermissions";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected TestIamPermissions(String str, TestIamPermissionsRequest testIamPermissionsRequest) {
                    super(Pubsub.this, "POST", REST_PATH, testIamPermissionsRequest, TestIamPermissionsResponse.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/snapshots/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public TestIamPermissions set(String str, Object obj) {
                    return (TestIamPermissions) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<TestIamPermissionsResponse> set$Xgafv2(String str) {
                    return (TestIamPermissions) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<TestIamPermissionsResponse> setAccessToken2(String str) {
                    return (TestIamPermissions) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<TestIamPermissionsResponse> setAlt2(String str) {
                    return (TestIamPermissions) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<TestIamPermissionsResponse> setCallback2(String str) {
                    return (TestIamPermissions) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<TestIamPermissionsResponse> setFields2(String str) {
                    return (TestIamPermissions) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<TestIamPermissionsResponse> setKey2(String str) {
                    return (TestIamPermissions) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<TestIamPermissionsResponse> setOauthToken2(String str) {
                    return (TestIamPermissions) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<TestIamPermissionsResponse> setPrettyPrint2(Boolean bool) {
                    return (TestIamPermissions) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<TestIamPermissionsResponse> setQuotaUser2(String str) {
                    return (TestIamPermissions) super.setQuotaUser2(str);
                }

                public TestIamPermissions setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/snapshots/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<TestIamPermissionsResponse> setUploadProtocol2(String str) {
                    return (TestIamPermissions) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<TestIamPermissionsResponse> setUploadType2(String str) {
                    return (TestIamPermissions) super.setUploadType2(str);
                }
            }

            public Snapshots() {
            }

            public Create create(String str, CreateSnapshotRequest createSnapshotRequest) throws IOException {
                Create create = new Create(str, createSnapshotRequest);
                Pubsub.this.initialize(create);
                return create;
            }

            public Delete delete(String str) throws IOException {
                Delete delete = new Delete(str);
                Pubsub.this.initialize(delete);
                return delete;
            }

            public Get get(String str) throws IOException {
                Get get = new Get(str);
                Pubsub.this.initialize(get);
                return get;
            }

            public GetIamPolicy getIamPolicy(String str) throws IOException {
                GetIamPolicy getIamPolicy = new GetIamPolicy(str);
                Pubsub.this.initialize(getIamPolicy);
                return getIamPolicy;
            }

            public List list(String str) throws IOException {
                List list = new List(str);
                Pubsub.this.initialize(list);
                return list;
            }

            public Patch patch(String str, UpdateSnapshotRequest updateSnapshotRequest) throws IOException {
                Patch patch = new Patch(str, updateSnapshotRequest);
                Pubsub.this.initialize(patch);
                return patch;
            }

            public SetIamPolicy setIamPolicy(String str, SetIamPolicyRequest setIamPolicyRequest) throws IOException {
                SetIamPolicy setIamPolicy = new SetIamPolicy(str, setIamPolicyRequest);
                Pubsub.this.initialize(setIamPolicy);
                return setIamPolicy;
            }

            public TestIamPermissions testIamPermissions(String str, TestIamPermissionsRequest testIamPermissionsRequest) throws IOException {
                TestIamPermissions testIamPermissions = new TestIamPermissions(str, testIamPermissionsRequest);
                Pubsub.this.initialize(testIamPermissions);
                return testIamPermissions;
            }
        }

        public class Subscriptions {

            public class Acknowledge extends PubsubRequest<Empty> {
                private static final String REST_PATH = "v1/{+subscription}:acknowledge";
                private final Pattern SUBSCRIPTION_PATTERN;

                @Key
                private String subscription;

                protected Acknowledge(String str, AcknowledgeRequest acknowledgeRequest) {
                    super(Pubsub.this, "POST", REST_PATH, acknowledgeRequest, Empty.class);
                    this.SUBSCRIPTION_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.subscription = (String) Preconditions.checkNotNull(str, "Required parameter subscription must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getSubscription() {
                    return this.subscription;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Acknowledge set(String str, Object obj) {
                    return (Acknowledge) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Empty> set$Xgafv2(String str) {
                    return (Acknowledge) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Empty> setAccessToken2(String str) {
                    return (Acknowledge) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Empty> setAlt2(String str) {
                    return (Acknowledge) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Empty> setCallback2(String str) {
                    return (Acknowledge) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Empty> setFields2(String str) {
                    return (Acknowledge) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Empty> setKey2(String str) {
                    return (Acknowledge) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Empty> setOauthToken2(String str) {
                    return (Acknowledge) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Empty> setPrettyPrint2(Boolean bool) {
                    return (Acknowledge) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Empty> setQuotaUser2(String str) {
                    return (Acknowledge) super.setQuotaUser2(str);
                }

                public Acknowledge setSubscription(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.subscription = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Empty> setUploadProtocol2(String str) {
                    return (Acknowledge) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Empty> setUploadType2(String str) {
                    return (Acknowledge) super.setUploadType2(str);
                }
            }

            public class Create extends PubsubRequest<Subscription> {
                private static final String REST_PATH = "v1/{+name}";
                private final Pattern NAME_PATTERN;

                @Key
                private String name;

                protected Create(String str, Subscription subscription) {
                    super(Pubsub.this, HttpMethods.PUT, REST_PATH, subscription, Subscription.class);
                    this.NAME_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.name = (String) Preconditions.checkNotNull(str, "Required parameter name must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getName() {
                    return this.name;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Create set(String str, Object obj) {
                    return (Create) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Subscription> set$Xgafv2(String str) {
                    return (Create) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Subscription> setAccessToken2(String str) {
                    return (Create) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Subscription> setAlt2(String str) {
                    return (Create) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Subscription> setCallback2(String str) {
                    return (Create) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Subscription> setFields2(String str) {
                    return (Create) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Subscription> setKey2(String str) {
                    return (Create) super.setKey2(str);
                }

                public Create setName(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.name = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Subscription> setOauthToken2(String str) {
                    return (Create) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Subscription> setPrettyPrint2(Boolean bool) {
                    return (Create) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Subscription> setQuotaUser2(String str) {
                    return (Create) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Subscription> setUploadProtocol2(String str) {
                    return (Create) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Subscription> setUploadType2(String str) {
                    return (Create) super.setUploadType2(str);
                }
            }

            public class Delete extends PubsubRequest<Empty> {
                private static final String REST_PATH = "v1/{+subscription}";
                private final Pattern SUBSCRIPTION_PATTERN;

                @Key
                private String subscription;

                protected Delete(String str) {
                    super(Pubsub.this, HttpMethods.DELETE, REST_PATH, null, Empty.class);
                    this.SUBSCRIPTION_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.subscription = (String) Preconditions.checkNotNull(str, "Required parameter subscription must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getSubscription() {
                    return this.subscription;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Delete set(String str, Object obj) {
                    return (Delete) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Empty> set$Xgafv2(String str) {
                    return (Delete) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Empty> setAccessToken2(String str) {
                    return (Delete) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Empty> setAlt2(String str) {
                    return (Delete) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Empty> setCallback2(String str) {
                    return (Delete) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Empty> setFields2(String str) {
                    return (Delete) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Empty> setKey2(String str) {
                    return (Delete) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Empty> setOauthToken2(String str) {
                    return (Delete) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Empty> setPrettyPrint2(Boolean bool) {
                    return (Delete) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Empty> setQuotaUser2(String str) {
                    return (Delete) super.setQuotaUser2(str);
                }

                public Delete setSubscription(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.subscription = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Empty> setUploadProtocol2(String str) {
                    return (Delete) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Empty> setUploadType2(String str) {
                    return (Delete) super.setUploadType2(str);
                }
            }

            public class Get extends PubsubRequest<Subscription> {
                private static final String REST_PATH = "v1/{+subscription}";
                private final Pattern SUBSCRIPTION_PATTERN;

                @Key
                private String subscription;

                protected Get(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, Subscription.class);
                    this.SUBSCRIPTION_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.subscription = (String) Preconditions.checkNotNull(str, "Required parameter subscription must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public String getSubscription() {
                    return this.subscription;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Get set(String str, Object obj) {
                    return (Get) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Subscription> set$Xgafv2(String str) {
                    return (Get) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Subscription> setAccessToken2(String str) {
                    return (Get) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Subscription> setAlt2(String str) {
                    return (Get) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Subscription> setCallback2(String str) {
                    return (Get) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Subscription> setFields2(String str) {
                    return (Get) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Subscription> setKey2(String str) {
                    return (Get) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Subscription> setOauthToken2(String str) {
                    return (Get) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Subscription> setPrettyPrint2(Boolean bool) {
                    return (Get) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Subscription> setQuotaUser2(String str) {
                    return (Get) super.setQuotaUser2(str);
                }

                public Get setSubscription(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.subscription = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Subscription> setUploadProtocol2(String str) {
                    return (Get) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Subscription> setUploadType2(String str) {
                    return (Get) super.setUploadType2(str);
                }
            }

            public class GetIamPolicy extends PubsubRequest<Policy> {
                private static final String REST_PATH = "v1/{+resource}:getIamPolicy";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected GetIamPolicy(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, Policy.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public GetIamPolicy set(String str, Object obj) {
                    return (GetIamPolicy) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Policy> set$Xgafv2(String str) {
                    return (GetIamPolicy) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Policy> setAccessToken2(String str) {
                    return (GetIamPolicy) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Policy> setAlt2(String str) {
                    return (GetIamPolicy) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Policy> setCallback2(String str) {
                    return (GetIamPolicy) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Policy> setFields2(String str) {
                    return (GetIamPolicy) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Policy> setKey2(String str) {
                    return (GetIamPolicy) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Policy> setOauthToken2(String str) {
                    return (GetIamPolicy) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Policy> setPrettyPrint2(Boolean bool) {
                    return (GetIamPolicy) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Policy> setQuotaUser2(String str) {
                    return (GetIamPolicy) super.setQuotaUser2(str);
                }

                public GetIamPolicy setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Policy> setUploadProtocol2(String str) {
                    return (GetIamPolicy) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Policy> setUploadType2(String str) {
                    return (GetIamPolicy) super.setUploadType2(str);
                }
            }

            public class List extends PubsubRequest<ListSubscriptionsResponse> {
                private static final String REST_PATH = "v1/{+project}/subscriptions";
                private final Pattern PROJECT_PATTERN;

                @Key
                private Integer pageSize;

                @Key
                private String pageToken;

                @Key
                private String project;

                protected List(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, ListSubscriptionsResponse.class);
                    this.PROJECT_PATTERN = Pattern.compile("^projects/[^/]+$");
                    this.project = (String) Preconditions.checkNotNull(str, "Required parameter project must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.PROJECT_PATTERN.matcher(str).matches(), "Parameter project must conform to the pattern ^projects/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public Integer getPageSize() {
                    return this.pageSize;
                }

                public String getPageToken() {
                    return this.pageToken;
                }

                public String getProject() {
                    return this.project;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public List set(String str, Object obj) {
                    return (List) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<ListSubscriptionsResponse> set$Xgafv2(String str) {
                    return (List) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<ListSubscriptionsResponse> setAccessToken2(String str) {
                    return (List) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<ListSubscriptionsResponse> setAlt2(String str) {
                    return (List) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<ListSubscriptionsResponse> setCallback2(String str) {
                    return (List) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<ListSubscriptionsResponse> setFields2(String str) {
                    return (List) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<ListSubscriptionsResponse> setKey2(String str) {
                    return (List) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<ListSubscriptionsResponse> setOauthToken2(String str) {
                    return (List) super.setOauthToken2(str);
                }

                public List setPageSize(Integer num) {
                    this.pageSize = num;
                    return this;
                }

                public List setPageToken(String str) {
                    this.pageToken = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<ListSubscriptionsResponse> setPrettyPrint2(Boolean bool) {
                    return (List) super.setPrettyPrint2(bool);
                }

                public List setProject(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.PROJECT_PATTERN.matcher(str).matches(), "Parameter project must conform to the pattern ^projects/[^/]+$");
                    }
                    this.project = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<ListSubscriptionsResponse> setQuotaUser2(String str) {
                    return (List) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<ListSubscriptionsResponse> setUploadProtocol2(String str) {
                    return (List) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<ListSubscriptionsResponse> setUploadType2(String str) {
                    return (List) super.setUploadType2(str);
                }
            }

            public class ModifyAckDeadline extends PubsubRequest<Empty> {
                private static final String REST_PATH = "v1/{+subscription}:modifyAckDeadline";
                private final Pattern SUBSCRIPTION_PATTERN;

                @Key
                private String subscription;

                protected ModifyAckDeadline(String str, ModifyAckDeadlineRequest modifyAckDeadlineRequest) {
                    super(Pubsub.this, "POST", REST_PATH, modifyAckDeadlineRequest, Empty.class);
                    this.SUBSCRIPTION_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.subscription = (String) Preconditions.checkNotNull(str, "Required parameter subscription must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getSubscription() {
                    return this.subscription;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public ModifyAckDeadline set(String str, Object obj) {
                    return (ModifyAckDeadline) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Empty> set$Xgafv2(String str) {
                    return (ModifyAckDeadline) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Empty> setAccessToken2(String str) {
                    return (ModifyAckDeadline) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Empty> setAlt2(String str) {
                    return (ModifyAckDeadline) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Empty> setCallback2(String str) {
                    return (ModifyAckDeadline) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Empty> setFields2(String str) {
                    return (ModifyAckDeadline) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Empty> setKey2(String str) {
                    return (ModifyAckDeadline) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Empty> setOauthToken2(String str) {
                    return (ModifyAckDeadline) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Empty> setPrettyPrint2(Boolean bool) {
                    return (ModifyAckDeadline) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Empty> setQuotaUser2(String str) {
                    return (ModifyAckDeadline) super.setQuotaUser2(str);
                }

                public ModifyAckDeadline setSubscription(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.subscription = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Empty> setUploadProtocol2(String str) {
                    return (ModifyAckDeadline) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Empty> setUploadType2(String str) {
                    return (ModifyAckDeadline) super.setUploadType2(str);
                }
            }

            public class ModifyPushConfig extends PubsubRequest<Empty> {
                private static final String REST_PATH = "v1/{+subscription}:modifyPushConfig";
                private final Pattern SUBSCRIPTION_PATTERN;

                @Key
                private String subscription;

                protected ModifyPushConfig(String str, ModifyPushConfigRequest modifyPushConfigRequest) {
                    super(Pubsub.this, "POST", REST_PATH, modifyPushConfigRequest, Empty.class);
                    this.SUBSCRIPTION_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.subscription = (String) Preconditions.checkNotNull(str, "Required parameter subscription must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getSubscription() {
                    return this.subscription;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public ModifyPushConfig set(String str, Object obj) {
                    return (ModifyPushConfig) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Empty> set$Xgafv2(String str) {
                    return (ModifyPushConfig) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Empty> setAccessToken2(String str) {
                    return (ModifyPushConfig) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Empty> setAlt2(String str) {
                    return (ModifyPushConfig) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Empty> setCallback2(String str) {
                    return (ModifyPushConfig) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Empty> setFields2(String str) {
                    return (ModifyPushConfig) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Empty> setKey2(String str) {
                    return (ModifyPushConfig) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Empty> setOauthToken2(String str) {
                    return (ModifyPushConfig) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Empty> setPrettyPrint2(Boolean bool) {
                    return (ModifyPushConfig) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Empty> setQuotaUser2(String str) {
                    return (ModifyPushConfig) super.setQuotaUser2(str);
                }

                public ModifyPushConfig setSubscription(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.subscription = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Empty> setUploadProtocol2(String str) {
                    return (ModifyPushConfig) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Empty> setUploadType2(String str) {
                    return (ModifyPushConfig) super.setUploadType2(str);
                }
            }

            public class Patch extends PubsubRequest<Subscription> {
                private static final String REST_PATH = "v1/{+name}";
                private final Pattern NAME_PATTERN;

                @Key
                private String name;

                protected Patch(String str, UpdateSubscriptionRequest updateSubscriptionRequest) {
                    super(Pubsub.this, HttpMethods.PATCH, REST_PATH, updateSubscriptionRequest, Subscription.class);
                    this.NAME_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.name = (String) Preconditions.checkNotNull(str, "Required parameter name must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getName() {
                    return this.name;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Patch set(String str, Object obj) {
                    return (Patch) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Subscription> set$Xgafv2(String str) {
                    return (Patch) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Subscription> setAccessToken2(String str) {
                    return (Patch) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Subscription> setAlt2(String str) {
                    return (Patch) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Subscription> setCallback2(String str) {
                    return (Patch) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Subscription> setFields2(String str) {
                    return (Patch) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Subscription> setKey2(String str) {
                    return (Patch) super.setKey2(str);
                }

                public Patch setName(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.name = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Subscription> setOauthToken2(String str) {
                    return (Patch) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Subscription> setPrettyPrint2(Boolean bool) {
                    return (Patch) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Subscription> setQuotaUser2(String str) {
                    return (Patch) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Subscription> setUploadProtocol2(String str) {
                    return (Patch) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Subscription> setUploadType2(String str) {
                    return (Patch) super.setUploadType2(str);
                }
            }

            public class Pull extends PubsubRequest<PullResponse> {
                private static final String REST_PATH = "v1/{+subscription}:pull";
                private final Pattern SUBSCRIPTION_PATTERN;

                @Key
                private String subscription;

                protected Pull(String str, PullRequest pullRequest) {
                    super(Pubsub.this, "POST", REST_PATH, pullRequest, PullResponse.class);
                    this.SUBSCRIPTION_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.subscription = (String) Preconditions.checkNotNull(str, "Required parameter subscription must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getSubscription() {
                    return this.subscription;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Pull set(String str, Object obj) {
                    return (Pull) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<PullResponse> set$Xgafv2(String str) {
                    return (Pull) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<PullResponse> setAccessToken2(String str) {
                    return (Pull) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<PullResponse> setAlt2(String str) {
                    return (Pull) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<PullResponse> setCallback2(String str) {
                    return (Pull) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<PullResponse> setFields2(String str) {
                    return (Pull) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<PullResponse> setKey2(String str) {
                    return (Pull) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<PullResponse> setOauthToken2(String str) {
                    return (Pull) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<PullResponse> setPrettyPrint2(Boolean bool) {
                    return (Pull) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<PullResponse> setQuotaUser2(String str) {
                    return (Pull) super.setQuotaUser2(str);
                }

                public Pull setSubscription(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.subscription = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<PullResponse> setUploadProtocol2(String str) {
                    return (Pull) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<PullResponse> setUploadType2(String str) {
                    return (Pull) super.setUploadType2(str);
                }
            }

            public class Seek extends PubsubRequest<SeekResponse> {
                private static final String REST_PATH = "v1/{+subscription}:seek";
                private final Pattern SUBSCRIPTION_PATTERN;

                @Key
                private String subscription;

                protected Seek(String str, SeekRequest seekRequest) {
                    super(Pubsub.this, "POST", REST_PATH, seekRequest, SeekResponse.class);
                    this.SUBSCRIPTION_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.subscription = (String) Preconditions.checkNotNull(str, "Required parameter subscription must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getSubscription() {
                    return this.subscription;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Seek set(String str, Object obj) {
                    return (Seek) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<SeekResponse> set$Xgafv2(String str) {
                    return (Seek) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<SeekResponse> setAccessToken2(String str) {
                    return (Seek) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<SeekResponse> setAlt2(String str) {
                    return (Seek) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<SeekResponse> setCallback2(String str) {
                    return (Seek) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<SeekResponse> setFields2(String str) {
                    return (Seek) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<SeekResponse> setKey2(String str) {
                    return (Seek) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<SeekResponse> setOauthToken2(String str) {
                    return (Seek) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<SeekResponse> setPrettyPrint2(Boolean bool) {
                    return (Seek) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<SeekResponse> setQuotaUser2(String str) {
                    return (Seek) super.setQuotaUser2(str);
                }

                public Seek setSubscription(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.SUBSCRIPTION_PATTERN.matcher(str).matches(), "Parameter subscription must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.subscription = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<SeekResponse> setUploadProtocol2(String str) {
                    return (Seek) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<SeekResponse> setUploadType2(String str) {
                    return (Seek) super.setUploadType2(str);
                }
            }

            public class SetIamPolicy extends PubsubRequest<Policy> {
                private static final String REST_PATH = "v1/{+resource}:setIamPolicy";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected SetIamPolicy(String str, SetIamPolicyRequest setIamPolicyRequest) {
                    super(Pubsub.this, "POST", REST_PATH, setIamPolicyRequest, Policy.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public SetIamPolicy set(String str, Object obj) {
                    return (SetIamPolicy) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Policy> set$Xgafv2(String str) {
                    return (SetIamPolicy) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Policy> setAccessToken2(String str) {
                    return (SetIamPolicy) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Policy> setAlt2(String str) {
                    return (SetIamPolicy) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Policy> setCallback2(String str) {
                    return (SetIamPolicy) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Policy> setFields2(String str) {
                    return (SetIamPolicy) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Policy> setKey2(String str) {
                    return (SetIamPolicy) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Policy> setOauthToken2(String str) {
                    return (SetIamPolicy) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Policy> setPrettyPrint2(Boolean bool) {
                    return (SetIamPolicy) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Policy> setQuotaUser2(String str) {
                    return (SetIamPolicy) super.setQuotaUser2(str);
                }

                public SetIamPolicy setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Policy> setUploadProtocol2(String str) {
                    return (SetIamPolicy) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Policy> setUploadType2(String str) {
                    return (SetIamPolicy) super.setUploadType2(str);
                }
            }

            public class TestIamPermissions extends PubsubRequest<TestIamPermissionsResponse> {
                private static final String REST_PATH = "v1/{+resource}:testIamPermissions";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected TestIamPermissions(String str, TestIamPermissionsRequest testIamPermissionsRequest) {
                    super(Pubsub.this, "POST", REST_PATH, testIamPermissionsRequest, TestIamPermissionsResponse.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/subscriptions/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public TestIamPermissions set(String str, Object obj) {
                    return (TestIamPermissions) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<TestIamPermissionsResponse> set$Xgafv2(String str) {
                    return (TestIamPermissions) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<TestIamPermissionsResponse> setAccessToken2(String str) {
                    return (TestIamPermissions) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<TestIamPermissionsResponse> setAlt2(String str) {
                    return (TestIamPermissions) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<TestIamPermissionsResponse> setCallback2(String str) {
                    return (TestIamPermissions) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<TestIamPermissionsResponse> setFields2(String str) {
                    return (TestIamPermissions) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<TestIamPermissionsResponse> setKey2(String str) {
                    return (TestIamPermissions) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<TestIamPermissionsResponse> setOauthToken2(String str) {
                    return (TestIamPermissions) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<TestIamPermissionsResponse> setPrettyPrint2(Boolean bool) {
                    return (TestIamPermissions) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<TestIamPermissionsResponse> setQuotaUser2(String str) {
                    return (TestIamPermissions) super.setQuotaUser2(str);
                }

                public TestIamPermissions setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/subscriptions/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<TestIamPermissionsResponse> setUploadProtocol2(String str) {
                    return (TestIamPermissions) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<TestIamPermissionsResponse> setUploadType2(String str) {
                    return (TestIamPermissions) super.setUploadType2(str);
                }
            }

            public Subscriptions() {
            }

            public Acknowledge acknowledge(String str, AcknowledgeRequest acknowledgeRequest) throws IOException {
                Acknowledge acknowledge = new Acknowledge(str, acknowledgeRequest);
                Pubsub.this.initialize(acknowledge);
                return acknowledge;
            }

            public Create create(String str, Subscription subscription) throws IOException {
                Create create = new Create(str, subscription);
                Pubsub.this.initialize(create);
                return create;
            }

            public Delete delete(String str) throws IOException {
                Delete delete = new Delete(str);
                Pubsub.this.initialize(delete);
                return delete;
            }

            public Get get(String str) throws IOException {
                Get get = new Get(str);
                Pubsub.this.initialize(get);
                return get;
            }

            public GetIamPolicy getIamPolicy(String str) throws IOException {
                GetIamPolicy getIamPolicy = new GetIamPolicy(str);
                Pubsub.this.initialize(getIamPolicy);
                return getIamPolicy;
            }

            public List list(String str) throws IOException {
                List list = new List(str);
                Pubsub.this.initialize(list);
                return list;
            }

            public ModifyAckDeadline modifyAckDeadline(String str, ModifyAckDeadlineRequest modifyAckDeadlineRequest) throws IOException {
                ModifyAckDeadline modifyAckDeadline = new ModifyAckDeadline(str, modifyAckDeadlineRequest);
                Pubsub.this.initialize(modifyAckDeadline);
                return modifyAckDeadline;
            }

            public ModifyPushConfig modifyPushConfig(String str, ModifyPushConfigRequest modifyPushConfigRequest) throws IOException {
                ModifyPushConfig modifyPushConfig = new ModifyPushConfig(str, modifyPushConfigRequest);
                Pubsub.this.initialize(modifyPushConfig);
                return modifyPushConfig;
            }

            public Patch patch(String str, UpdateSubscriptionRequest updateSubscriptionRequest) throws IOException {
                Patch patch = new Patch(str, updateSubscriptionRequest);
                Pubsub.this.initialize(patch);
                return patch;
            }

            public Pull pull(String str, PullRequest pullRequest) throws IOException {
                Pull pull = new Pull(str, pullRequest);
                Pubsub.this.initialize(pull);
                return pull;
            }

            public Seek seek(String str, SeekRequest seekRequest) throws IOException {
                Seek seek = new Seek(str, seekRequest);
                Pubsub.this.initialize(seek);
                return seek;
            }

            public SetIamPolicy setIamPolicy(String str, SetIamPolicyRequest setIamPolicyRequest) throws IOException {
                SetIamPolicy setIamPolicy = new SetIamPolicy(str, setIamPolicyRequest);
                Pubsub.this.initialize(setIamPolicy);
                return setIamPolicy;
            }

            public TestIamPermissions testIamPermissions(String str, TestIamPermissionsRequest testIamPermissionsRequest) throws IOException {
                TestIamPermissions testIamPermissions = new TestIamPermissions(str, testIamPermissionsRequest);
                Pubsub.this.initialize(testIamPermissions);
                return testIamPermissions;
            }
        }

        public class Topics {

            public class Create extends PubsubRequest<Topic> {
                private static final String REST_PATH = "v1/{+name}";
                private final Pattern NAME_PATTERN;

                @Key
                private String name;

                protected Create(String str, Topic topic) {
                    super(Pubsub.this, HttpMethods.PUT, REST_PATH, topic, Topic.class);
                    this.NAME_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                    this.name = (String) Preconditions.checkNotNull(str, "Required parameter name must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                }

                public String getName() {
                    return this.name;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Create set(String str, Object obj) {
                    return (Create) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Topic> set$Xgafv2(String str) {
                    return (Create) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Topic> setAccessToken2(String str) {
                    return (Create) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Topic> setAlt2(String str) {
                    return (Create) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Topic> setCallback2(String str) {
                    return (Create) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Topic> setFields2(String str) {
                    return (Create) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Topic> setKey2(String str) {
                    return (Create) super.setKey2(str);
                }

                public Create setName(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }
                    this.name = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Topic> setOauthToken2(String str) {
                    return (Create) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Topic> setPrettyPrint2(Boolean bool) {
                    return (Create) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Topic> setQuotaUser2(String str) {
                    return (Create) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Topic> setUploadProtocol2(String str) {
                    return (Create) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Topic> setUploadType2(String str) {
                    return (Create) super.setUploadType2(str);
                }
            }

            public class Delete extends PubsubRequest<Empty> {
                private static final String REST_PATH = "v1/{+topic}";
                private final Pattern TOPIC_PATTERN;

                @Key
                private String topic;

                protected Delete(String str) {
                    super(Pubsub.this, HttpMethods.DELETE, REST_PATH, null, Empty.class);
                    this.TOPIC_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                    this.topic = (String) Preconditions.checkNotNull(str, "Required parameter topic must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                }

                public String getTopic() {
                    return this.topic;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Delete set(String str, Object obj) {
                    return (Delete) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Empty> set$Xgafv2(String str) {
                    return (Delete) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Empty> setAccessToken2(String str) {
                    return (Delete) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Empty> setAlt2(String str) {
                    return (Delete) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Empty> setCallback2(String str) {
                    return (Delete) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Empty> setFields2(String str) {
                    return (Delete) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Empty> setKey2(String str) {
                    return (Delete) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Empty> setOauthToken2(String str) {
                    return (Delete) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Empty> setPrettyPrint2(Boolean bool) {
                    return (Delete) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Empty> setQuotaUser2(String str) {
                    return (Delete) super.setQuotaUser2(str);
                }

                public Delete setTopic(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }
                    this.topic = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Empty> setUploadProtocol2(String str) {
                    return (Delete) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Empty> setUploadType2(String str) {
                    return (Delete) super.setUploadType2(str);
                }
            }

            public class Get extends PubsubRequest<Topic> {
                private static final String REST_PATH = "v1/{+topic}";
                private final Pattern TOPIC_PATTERN;

                @Key
                private String topic;

                protected Get(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, Topic.class);
                    this.TOPIC_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                    this.topic = (String) Preconditions.checkNotNull(str, "Required parameter topic must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public String getTopic() {
                    return this.topic;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Get set(String str, Object obj) {
                    return (Get) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Topic> set$Xgafv2(String str) {
                    return (Get) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Topic> setAccessToken2(String str) {
                    return (Get) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Topic> setAlt2(String str) {
                    return (Get) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Topic> setCallback2(String str) {
                    return (Get) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Topic> setFields2(String str) {
                    return (Get) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Topic> setKey2(String str) {
                    return (Get) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Topic> setOauthToken2(String str) {
                    return (Get) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Topic> setPrettyPrint2(Boolean bool) {
                    return (Get) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Topic> setQuotaUser2(String str) {
                    return (Get) super.setQuotaUser2(str);
                }

                public Get setTopic(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }
                    this.topic = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Topic> setUploadProtocol2(String str) {
                    return (Get) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Topic> setUploadType2(String str) {
                    return (Get) super.setUploadType2(str);
                }
            }

            public class GetIamPolicy extends PubsubRequest<Policy> {
                private static final String REST_PATH = "v1/{+resource}:getIamPolicy";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected GetIamPolicy(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, Policy.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public GetIamPolicy set(String str, Object obj) {
                    return (GetIamPolicy) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Policy> set$Xgafv2(String str) {
                    return (GetIamPolicy) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Policy> setAccessToken2(String str) {
                    return (GetIamPolicy) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Policy> setAlt2(String str) {
                    return (GetIamPolicy) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Policy> setCallback2(String str) {
                    return (GetIamPolicy) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Policy> setFields2(String str) {
                    return (GetIamPolicy) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Policy> setKey2(String str) {
                    return (GetIamPolicy) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Policy> setOauthToken2(String str) {
                    return (GetIamPolicy) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Policy> setPrettyPrint2(Boolean bool) {
                    return (GetIamPolicy) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Policy> setQuotaUser2(String str) {
                    return (GetIamPolicy) super.setQuotaUser2(str);
                }

                public GetIamPolicy setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Policy> setUploadProtocol2(String str) {
                    return (GetIamPolicy) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Policy> setUploadType2(String str) {
                    return (GetIamPolicy) super.setUploadType2(str);
                }
            }

            public class List extends PubsubRequest<ListTopicsResponse> {
                private static final String REST_PATH = "v1/{+project}/topics";
                private final Pattern PROJECT_PATTERN;

                @Key
                private Integer pageSize;

                @Key
                private String pageToken;

                @Key
                private String project;

                protected List(String str) {
                    super(Pubsub.this, "GET", REST_PATH, null, ListTopicsResponse.class);
                    this.PROJECT_PATTERN = Pattern.compile("^projects/[^/]+$");
                    this.project = (String) Preconditions.checkNotNull(str, "Required parameter project must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.PROJECT_PATTERN.matcher(str).matches(), "Parameter project must conform to the pattern ^projects/[^/]+$");
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpRequest buildHttpRequestUsingHead() throws IOException {
                    return super.buildHttpRequestUsingHead();
                }

                @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                public HttpResponse executeUsingHead() throws IOException {
                    return super.executeUsingHead();
                }

                public Integer getPageSize() {
                    return this.pageSize;
                }

                public String getPageToken() {
                    return this.pageToken;
                }

                public String getProject() {
                    return this.project;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public List set(String str, Object obj) {
                    return (List) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<ListTopicsResponse> set$Xgafv2(String str) {
                    return (List) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<ListTopicsResponse> setAccessToken2(String str) {
                    return (List) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<ListTopicsResponse> setAlt2(String str) {
                    return (List) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<ListTopicsResponse> setCallback2(String str) {
                    return (List) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<ListTopicsResponse> setFields2(String str) {
                    return (List) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<ListTopicsResponse> setKey2(String str) {
                    return (List) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<ListTopicsResponse> setOauthToken2(String str) {
                    return (List) super.setOauthToken2(str);
                }

                public List setPageSize(Integer num) {
                    this.pageSize = num;
                    return this;
                }

                public List setPageToken(String str) {
                    this.pageToken = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<ListTopicsResponse> setPrettyPrint2(Boolean bool) {
                    return (List) super.setPrettyPrint2(bool);
                }

                public List setProject(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.PROJECT_PATTERN.matcher(str).matches(), "Parameter project must conform to the pattern ^projects/[^/]+$");
                    }
                    this.project = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<ListTopicsResponse> setQuotaUser2(String str) {
                    return (List) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<ListTopicsResponse> setUploadProtocol2(String str) {
                    return (List) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<ListTopicsResponse> setUploadType2(String str) {
                    return (List) super.setUploadType2(str);
                }
            }

            public class Patch extends PubsubRequest<Topic> {
                private static final String REST_PATH = "v1/{+name}";
                private final Pattern NAME_PATTERN;

                @Key
                private String name;

                protected Patch(String str, UpdateTopicRequest updateTopicRequest) {
                    super(Pubsub.this, HttpMethods.PATCH, REST_PATH, updateTopicRequest, Topic.class);
                    this.NAME_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                    this.name = (String) Preconditions.checkNotNull(str, "Required parameter name must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                }

                public String getName() {
                    return this.name;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Patch set(String str, Object obj) {
                    return (Patch) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Topic> set$Xgafv2(String str) {
                    return (Patch) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Topic> setAccessToken2(String str) {
                    return (Patch) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Topic> setAlt2(String str) {
                    return (Patch) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Topic> setCallback2(String str) {
                    return (Patch) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Topic> setFields2(String str) {
                    return (Patch) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Topic> setKey2(String str) {
                    return (Patch) super.setKey2(str);
                }

                public Patch setName(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.NAME_PATTERN.matcher(str).matches(), "Parameter name must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }
                    this.name = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Topic> setOauthToken2(String str) {
                    return (Patch) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Topic> setPrettyPrint2(Boolean bool) {
                    return (Patch) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Topic> setQuotaUser2(String str) {
                    return (Patch) super.setQuotaUser2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Topic> setUploadProtocol2(String str) {
                    return (Patch) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Topic> setUploadType2(String str) {
                    return (Patch) super.setUploadType2(str);
                }
            }

            public class Publish extends PubsubRequest<PublishResponse> {
                private static final String REST_PATH = "v1/{+topic}:publish";
                private final Pattern TOPIC_PATTERN;

                @Key
                private String topic;

                protected Publish(String str, PublishRequest publishRequest) {
                    super(Pubsub.this, "POST", REST_PATH, publishRequest, PublishResponse.class);
                    this.TOPIC_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                    this.topic = (String) Preconditions.checkNotNull(str, "Required parameter topic must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                }

                public String getTopic() {
                    return this.topic;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public Publish set(String str, Object obj) {
                    return (Publish) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<PublishResponse> set$Xgafv2(String str) {
                    return (Publish) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<PublishResponse> setAccessToken2(String str) {
                    return (Publish) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<PublishResponse> setAlt2(String str) {
                    return (Publish) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<PublishResponse> setCallback2(String str) {
                    return (Publish) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<PublishResponse> setFields2(String str) {
                    return (Publish) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<PublishResponse> setKey2(String str) {
                    return (Publish) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<PublishResponse> setOauthToken2(String str) {
                    return (Publish) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<PublishResponse> setPrettyPrint2(Boolean bool) {
                    return (Publish) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<PublishResponse> setQuotaUser2(String str) {
                    return (Publish) super.setQuotaUser2(str);
                }

                public Publish setTopic(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }
                    this.topic = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<PublishResponse> setUploadProtocol2(String str) {
                    return (Publish) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<PublishResponse> setUploadType2(String str) {
                    return (Publish) super.setUploadType2(str);
                }
            }

            public class SetIamPolicy extends PubsubRequest<Policy> {
                private static final String REST_PATH = "v1/{+resource}:setIamPolicy";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected SetIamPolicy(String str, SetIamPolicyRequest setIamPolicyRequest) {
                    super(Pubsub.this, "POST", REST_PATH, setIamPolicyRequest, Policy.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public SetIamPolicy set(String str, Object obj) {
                    return (SetIamPolicy) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<Policy> set$Xgafv2(String str) {
                    return (SetIamPolicy) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<Policy> setAccessToken2(String str) {
                    return (SetIamPolicy) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<Policy> setAlt2(String str) {
                    return (SetIamPolicy) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<Policy> setCallback2(String str) {
                    return (SetIamPolicy) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<Policy> setFields2(String str) {
                    return (SetIamPolicy) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<Policy> setKey2(String str) {
                    return (SetIamPolicy) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<Policy> setOauthToken2(String str) {
                    return (SetIamPolicy) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<Policy> setPrettyPrint2(Boolean bool) {
                    return (SetIamPolicy) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<Policy> setQuotaUser2(String str) {
                    return (SetIamPolicy) super.setQuotaUser2(str);
                }

                public SetIamPolicy setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<Policy> setUploadProtocol2(String str) {
                    return (SetIamPolicy) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<Policy> setUploadType2(String str) {
                    return (SetIamPolicy) super.setUploadType2(str);
                }
            }

            public class Snapshots {

                public class List extends PubsubRequest<ListTopicSnapshotsResponse> {
                    private static final String REST_PATH = "v1/{+topic}/snapshots";
                    private final Pattern TOPIC_PATTERN;

                    @Key
                    private Integer pageSize;

                    @Key
                    private String pageToken;

                    @Key
                    private String topic;

                    protected List(String str) {
                        super(Pubsub.this, "GET", REST_PATH, null, ListTopicSnapshotsResponse.class);
                        this.TOPIC_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                        this.topic = (String) Preconditions.checkNotNull(str, "Required parameter topic must be specified.");
                        if (Pubsub.this.getSuppressPatternChecks()) {
                            return;
                        }
                        Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }

                    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                    public HttpRequest buildHttpRequestUsingHead() throws IOException {
                        return super.buildHttpRequestUsingHead();
                    }

                    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                    public HttpResponse executeUsingHead() throws IOException {
                        return super.executeUsingHead();
                    }

                    public Integer getPageSize() {
                        return this.pageSize;
                    }

                    public String getPageToken() {
                        return this.pageToken;
                    }

                    public String getTopic() {
                        return this.topic;
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                    public List set(String str, Object obj) {
                        return (List) super.set(str, obj);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: set$Xgafv */
                    public PubsubRequest<ListTopicSnapshotsResponse> set$Xgafv2(String str) {
                        return (List) super.set$Xgafv2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setAccessToken */
                    public PubsubRequest<ListTopicSnapshotsResponse> setAccessToken2(String str) {
                        return (List) super.setAccessToken2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setAlt */
                    public PubsubRequest<ListTopicSnapshotsResponse> setAlt2(String str) {
                        return (List) super.setAlt2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setCallback */
                    public PubsubRequest<ListTopicSnapshotsResponse> setCallback2(String str) {
                        return (List) super.setCallback2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setFields */
                    public PubsubRequest<ListTopicSnapshotsResponse> setFields2(String str) {
                        return (List) super.setFields2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setKey */
                    public PubsubRequest<ListTopicSnapshotsResponse> setKey2(String str) {
                        return (List) super.setKey2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setOauthToken */
                    public PubsubRequest<ListTopicSnapshotsResponse> setOauthToken2(String str) {
                        return (List) super.setOauthToken2(str);
                    }

                    public List setPageSize(Integer num) {
                        this.pageSize = num;
                        return this;
                    }

                    public List setPageToken(String str) {
                        this.pageToken = str;
                        return this;
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setPrettyPrint */
                    public PubsubRequest<ListTopicSnapshotsResponse> setPrettyPrint2(Boolean bool) {
                        return (List) super.setPrettyPrint2(bool);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setQuotaUser */
                    public PubsubRequest<ListTopicSnapshotsResponse> setQuotaUser2(String str) {
                        return (List) super.setQuotaUser2(str);
                    }

                    public List setTopic(String str) {
                        if (!Pubsub.this.getSuppressPatternChecks()) {
                            Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                        }
                        this.topic = str;
                        return this;
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setUploadProtocol */
                    public PubsubRequest<ListTopicSnapshotsResponse> setUploadProtocol2(String str) {
                        return (List) super.setUploadProtocol2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setUploadType */
                    public PubsubRequest<ListTopicSnapshotsResponse> setUploadType2(String str) {
                        return (List) super.setUploadType2(str);
                    }
                }

                public Snapshots() {
                }

                public List list(String str) throws IOException {
                    List list = new List(str);
                    Pubsub.this.initialize(list);
                    return list;
                }
            }

            public class Subscriptions {

                public class List extends PubsubRequest<ListTopicSubscriptionsResponse> {
                    private static final String REST_PATH = "v1/{+topic}/subscriptions";
                    private final Pattern TOPIC_PATTERN;

                    @Key
                    private Integer pageSize;

                    @Key
                    private String pageToken;

                    @Key
                    private String topic;

                    protected List(String str) {
                        super(Pubsub.this, "GET", REST_PATH, null, ListTopicSubscriptionsResponse.class);
                        this.TOPIC_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                        this.topic = (String) Preconditions.checkNotNull(str, "Required parameter topic must be specified.");
                        if (Pubsub.this.getSuppressPatternChecks()) {
                            return;
                        }
                        Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }

                    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                    public HttpRequest buildHttpRequestUsingHead() throws IOException {
                        return super.buildHttpRequestUsingHead();
                    }

                    @Override // com.google.api.client.googleapis.services.AbstractGoogleClientRequest
                    public HttpResponse executeUsingHead() throws IOException {
                        return super.executeUsingHead();
                    }

                    public Integer getPageSize() {
                        return this.pageSize;
                    }

                    public String getPageToken() {
                        return this.pageToken;
                    }

                    public String getTopic() {
                        return this.topic;
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                    public List set(String str, Object obj) {
                        return (List) super.set(str, obj);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: set$Xgafv */
                    public PubsubRequest<ListTopicSubscriptionsResponse> set$Xgafv2(String str) {
                        return (List) super.set$Xgafv2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setAccessToken */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setAccessToken2(String str) {
                        return (List) super.setAccessToken2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setAlt */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setAlt2(String str) {
                        return (List) super.setAlt2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setCallback */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setCallback2(String str) {
                        return (List) super.setCallback2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setFields */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setFields2(String str) {
                        return (List) super.setFields2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setKey */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setKey2(String str) {
                        return (List) super.setKey2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setOauthToken */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setOauthToken2(String str) {
                        return (List) super.setOauthToken2(str);
                    }

                    public List setPageSize(Integer num) {
                        this.pageSize = num;
                        return this;
                    }

                    public List setPageToken(String str) {
                        this.pageToken = str;
                        return this;
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setPrettyPrint */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setPrettyPrint2(Boolean bool) {
                        return (List) super.setPrettyPrint2(bool);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setQuotaUser */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setQuotaUser2(String str) {
                        return (List) super.setQuotaUser2(str);
                    }

                    public List setTopic(String str) {
                        if (!Pubsub.this.getSuppressPatternChecks()) {
                            Preconditions.checkArgument(this.TOPIC_PATTERN.matcher(str).matches(), "Parameter topic must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                        }
                        this.topic = str;
                        return this;
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setUploadProtocol */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setUploadProtocol2(String str) {
                        return (List) super.setUploadProtocol2(str);
                    }

                    @Override // com.google.api.services.pubsub.PubsubRequest
                    /* JADX INFO: renamed from: setUploadType */
                    public PubsubRequest<ListTopicSubscriptionsResponse> setUploadType2(String str) {
                        return (List) super.setUploadType2(str);
                    }
                }

                public Subscriptions() {
                }

                public List list(String str) throws IOException {
                    List list = new List(str);
                    Pubsub.this.initialize(list);
                    return list;
                }
            }

            public class TestIamPermissions extends PubsubRequest<TestIamPermissionsResponse> {
                private static final String REST_PATH = "v1/{+resource}:testIamPermissions";
                private final Pattern RESOURCE_PATTERN;

                @Key
                private String resource;

                protected TestIamPermissions(String str, TestIamPermissionsRequest testIamPermissionsRequest) {
                    super(Pubsub.this, "POST", REST_PATH, testIamPermissionsRequest, TestIamPermissionsResponse.class);
                    this.RESOURCE_PATTERN = Pattern.compile("^projects/[^/]+/topics/[^/]+$");
                    this.resource = (String) Preconditions.checkNotNull(str, "Required parameter resource must be specified.");
                    if (Pubsub.this.getSuppressPatternChecks()) {
                        return;
                    }
                    Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                }

                public String getResource() {
                    return this.resource;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest, com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest, com.google.api.client.googleapis.services.AbstractGoogleClientRequest, com.google.api.client.util.GenericData
                public TestIamPermissions set(String str, Object obj) {
                    return (TestIamPermissions) super.set(str, obj);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: set$Xgafv */
                public PubsubRequest<TestIamPermissionsResponse> set$Xgafv2(String str) {
                    return (TestIamPermissions) super.set$Xgafv2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAccessToken */
                public PubsubRequest<TestIamPermissionsResponse> setAccessToken2(String str) {
                    return (TestIamPermissions) super.setAccessToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setAlt */
                public PubsubRequest<TestIamPermissionsResponse> setAlt2(String str) {
                    return (TestIamPermissions) super.setAlt2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setCallback */
                public PubsubRequest<TestIamPermissionsResponse> setCallback2(String str) {
                    return (TestIamPermissions) super.setCallback2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setFields */
                public PubsubRequest<TestIamPermissionsResponse> setFields2(String str) {
                    return (TestIamPermissions) super.setFields2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setKey */
                public PubsubRequest<TestIamPermissionsResponse> setKey2(String str) {
                    return (TestIamPermissions) super.setKey2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setOauthToken */
                public PubsubRequest<TestIamPermissionsResponse> setOauthToken2(String str) {
                    return (TestIamPermissions) super.setOauthToken2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setPrettyPrint */
                public PubsubRequest<TestIamPermissionsResponse> setPrettyPrint2(Boolean bool) {
                    return (TestIamPermissions) super.setPrettyPrint2(bool);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setQuotaUser */
                public PubsubRequest<TestIamPermissionsResponse> setQuotaUser2(String str) {
                    return (TestIamPermissions) super.setQuotaUser2(str);
                }

                public TestIamPermissions setResource(String str) {
                    if (!Pubsub.this.getSuppressPatternChecks()) {
                        Preconditions.checkArgument(this.RESOURCE_PATTERN.matcher(str).matches(), "Parameter resource must conform to the pattern ^projects/[^/]+/topics/[^/]+$");
                    }
                    this.resource = str;
                    return this;
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadProtocol */
                public PubsubRequest<TestIamPermissionsResponse> setUploadProtocol2(String str) {
                    return (TestIamPermissions) super.setUploadProtocol2(str);
                }

                @Override // com.google.api.services.pubsub.PubsubRequest
                /* JADX INFO: renamed from: setUploadType */
                public PubsubRequest<TestIamPermissionsResponse> setUploadType2(String str) {
                    return (TestIamPermissions) super.setUploadType2(str);
                }
            }

            public Topics() {
            }

            public Create create(String str, Topic topic) throws IOException {
                Create create = new Create(str, topic);
                Pubsub.this.initialize(create);
                return create;
            }

            public Delete delete(String str) throws IOException {
                Delete delete = new Delete(str);
                Pubsub.this.initialize(delete);
                return delete;
            }

            public Get get(String str) throws IOException {
                Get get = new Get(str);
                Pubsub.this.initialize(get);
                return get;
            }

            public GetIamPolicy getIamPolicy(String str) throws IOException {
                GetIamPolicy getIamPolicy = new GetIamPolicy(str);
                Pubsub.this.initialize(getIamPolicy);
                return getIamPolicy;
            }

            public List list(String str) throws IOException {
                List list = new List(str);
                Pubsub.this.initialize(list);
                return list;
            }

            public Patch patch(String str, UpdateTopicRequest updateTopicRequest) throws IOException {
                Patch patch = new Patch(str, updateTopicRequest);
                Pubsub.this.initialize(patch);
                return patch;
            }

            public Publish publish(String str, PublishRequest publishRequest) throws IOException {
                Publish publish = new Publish(str, publishRequest);
                Pubsub.this.initialize(publish);
                return publish;
            }

            public SetIamPolicy setIamPolicy(String str, SetIamPolicyRequest setIamPolicyRequest) throws IOException {
                SetIamPolicy setIamPolicy = new SetIamPolicy(str, setIamPolicyRequest);
                Pubsub.this.initialize(setIamPolicy);
                return setIamPolicy;
            }

            public Snapshots snapshots() {
                return new Snapshots();
            }

            public Subscriptions subscriptions() {
                return new Subscriptions();
            }

            public TestIamPermissions testIamPermissions(String str, TestIamPermissionsRequest testIamPermissionsRequest) throws IOException {
                TestIamPermissions testIamPermissions = new TestIamPermissions(str, testIamPermissionsRequest);
                Pubsub.this.initialize(testIamPermissions);
                return testIamPermissions;
            }
        }

        public Projects() {
        }

        public Snapshots snapshots() {
            return new Snapshots();
        }

        public Subscriptions subscriptions() {
            return new Subscriptions();
        }

        public Topics topics() {
            return new Topics();
        }
    }

    static {
        Preconditions.checkState(GoogleUtils.MAJOR_VERSION.intValue() == 1 && GoogleUtils.MINOR_VERSION.intValue() >= 15, "You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.25.0 of the Cloud Pub/Sub API library.", GoogleUtils.VERSION);
    }

    public Pubsub(HttpTransport httpTransport, JsonFactory jsonFactory, HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(httpTransport, jsonFactory, httpRequestInitializer));
    }

    Pubsub(Builder builder) {
        super(builder);
    }

    @Override // com.google.api.client.googleapis.services.AbstractGoogleClient
    protected void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        super.initialize(abstractGoogleClientRequest);
    }

    public Projects projects() {
        return new Projects();
    }
}
