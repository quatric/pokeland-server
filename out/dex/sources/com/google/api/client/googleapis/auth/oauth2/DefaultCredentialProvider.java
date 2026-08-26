package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Beta;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessControlException;
import java.util.Locale;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@Beta
class DefaultCredentialProvider extends SystemEnvironmentProvider {
    static final String APP_ENGINE_CREDENTIAL_CLASS = "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper";
    static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
    static final String CLOUD_SHELL_ENV_VAR = "DEVSHELL_CLIENT_PORT";
    static final String CREDENTIAL_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
    static final String HELP_PERMALINK = "https://developers.google.com/accounts/docs/application-default-credentials";
    static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";
    private GoogleCredential cachedCredential = null;
    private Environment detectedEnvironment = null;

    /* JADX INFO: renamed from: com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider$1 */
    static /* synthetic */ class C02801 {

        /* JADX INFO: renamed from: $SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment */
        static final /* synthetic */ int[] f273x52b60e20 = new int[Environment.values().length];

        static {
            try {
                f273x52b60e20[Environment.ENVIRONMENT_VARIABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f273x52b60e20[Environment.WELL_KNOWN_FILE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f273x52b60e20[Environment.APP_ENGINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f273x52b60e20[Environment.CLOUD_SHELL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f273x52b60e20[Environment.COMPUTE_ENGINE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static class ComputeGoogleCredential extends GoogleCredential {
        private static final String TOKEN_SERVER_ENCODED_URL = OAuth2Utils.getMetadataServerUrl() + "/computeMetadata/v1/instance/service-accounts/default/token";

        ComputeGoogleCredential(HttpTransport httpTransport, JsonFactory jsonFactory) {
            super(new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setTokenServerEncodedUrl(TOKEN_SERVER_ENCODED_URL));
        }

        @Override // com.google.api.client.googleapis.auth.oauth2.GoogleCredential, com.google.api.client.auth.oauth2.Credential
        protected TokenResponse executeRefreshToken() throws IOException {
            HttpRequest httpRequestBuildGetRequest = getTransport().createRequestFactory().buildGetRequest(new GenericUrl(getTokenServerEncodedUrl()));
            JsonObjectParser jsonObjectParser = new JsonObjectParser(getJsonFactory());
            httpRequestBuildGetRequest.setParser(jsonObjectParser);
            httpRequestBuildGetRequest.getHeaders().set("Metadata-Flavor", "Google");
            httpRequestBuildGetRequest.setThrowExceptionOnExecuteError(false);
            HttpResponse httpResponseExecute = httpRequestBuildGetRequest.execute();
            int statusCode = httpResponseExecute.getStatusCode();
            if (statusCode != 200) {
                if (statusCode == 404) {
                    throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", Integer.valueOf(statusCode)));
                }
                throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", Integer.valueOf(statusCode), httpResponseExecute.parseAsString()));
            }
            InputStream content = httpResponseExecute.getContent();
            if (content != null) {
                return (TokenResponse) jsonObjectParser.parseAndClose(content, httpResponseExecute.getContentCharset(), TokenResponse.class);
            }
            throw new IOException("Empty content from metadata token server request.");
        }
    }

    private enum Environment {
        UNKNOWN,
        ENVIRONMENT_VARIABLE,
        WELL_KNOWN_FILE,
        CLOUD_SHELL,
        APP_ENGINE,
        COMPUTE_ENGINE
    }

    DefaultCredentialProvider() {
    }

    private final Environment detectEnvironment(HttpTransport httpTransport) throws IOException {
        if (runningUsingEnvironmentVariable()) {
            return Environment.ENVIRONMENT_VARIABLE;
        }
        if (runningUsingWellKnownFile()) {
            return Environment.WELL_KNOWN_FILE;
        }
        if (useGAEStandardAPI()) {
            return Environment.APP_ENGINE;
        }
        if (runningOnCloudShell()) {
            return Environment.CLOUD_SHELL;
        }
        return OAuth2Utils.runningOnComputeEngine(httpTransport, this) ? Environment.COMPUTE_ENGINE : Environment.UNKNOWN;
    }

    private final GoogleCredential getAppEngineCredential(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        try {
            return (GoogleCredential) forName(APP_ENGINE_CREDENTIAL_CLASS).getConstructor(HttpTransport.class, JsonFactory.class).newInstance(httpTransport, jsonFactory);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", APP_ENGINE_CREDENTIAL_CLASS)), e));
        }
    }

    private GoogleCredential getCloudShellCredential(JsonFactory jsonFactory) {
        return new CloudShellCredential(Integer.parseInt(getEnv(CLOUD_SHELL_ENV_VAR)), jsonFactory);
    }

    private final GoogleCredential getComputeCredential(HttpTransport httpTransport, JsonFactory jsonFactory) {
        return new ComputeGoogleCredential(httpTransport, jsonFactory);
    }

    private GoogleCredential getCredentialUsingEnvironmentVariable(HttpTransport httpTransport, JsonFactory jsonFactory) throws Throwable {
        String env = getEnv(CREDENTIAL_ENV_VAR);
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(env);
                try {
                    GoogleCredential googleCredentialFromStream = GoogleCredential.fromStream(fileInputStream2, httpTransport, jsonFactory);
                    fileInputStream2.close();
                    return googleCredentialFromStream;
                } catch (IOException e) {
                    e = e;
                    throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Error reading credential file from environment variable %s, value '%s': %s", CREDENTIAL_ENV_VAR, env, e.getMessage())), e));
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    private GoogleCredential getCredentialUsingWellKnownFile(HttpTransport httpTransport, JsonFactory jsonFactory) throws Throwable {
        File wellKnownCredentialsFile = getWellKnownCredentialsFile();
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(wellKnownCredentialsFile);
                try {
                    GoogleCredential googleCredentialFromStream = GoogleCredential.fromStream(fileInputStream2, httpTransport, jsonFactory);
                    fileInputStream2.close();
                    return googleCredentialFromStream;
                } catch (IOException e) {
                    e = e;
                    fileInputStream = fileInputStream2;
                    throw new IOException(String.format("Error reading credential file from location %s: %s", wellKnownCredentialsFile, e.getMessage()));
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private final GoogleCredential getDefaultCredentialUnsynchronized(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        if (this.detectedEnvironment == null) {
            this.detectedEnvironment = detectEnvironment(httpTransport);
        }
        int i = C02801.f273x52b60e20[this.detectedEnvironment.ordinal()];
        if (i == 1) {
            return getCredentialUsingEnvironmentVariable(httpTransport, jsonFactory);
        }
        if (i == 2) {
            return getCredentialUsingWellKnownFile(httpTransport, jsonFactory);
        }
        if (i == 3) {
            return getAppEngineCredential(httpTransport, jsonFactory);
        }
        if (i == 4) {
            return getCloudShellCredential(jsonFactory);
        }
        if (i != 5) {
            return null;
        }
        return getComputeCredential(httpTransport, jsonFactory);
    }

    private final File getWellKnownCredentialsFile() {
        return new File(getProperty("os.name", "").toLowerCase(Locale.US).indexOf("windows") >= 0 ? new File(new File(getEnv("APPDATA")), CLOUDSDK_CONFIG_DIRECTORY) : new File(new File(getProperty("user.home", ""), ".config"), CLOUDSDK_CONFIG_DIRECTORY), WELL_KNOWN_CREDENTIALS_FILE);
    }

    private boolean runningOnCloudShell() {
        return getEnv(CLOUD_SHELL_ENV_VAR) != null;
    }

    private boolean runningUsingEnvironmentVariable() throws IOException {
        String env = getEnv(CREDENTIAL_ENV_VAR);
        if (env != null && env.length() != 0) {
            try {
                File file = new File(env);
                if (!file.exists() || file.isDirectory()) {
                    throw new IOException(String.format("Error reading credential file from environment variable %s, value '%s': File does not exist.", CREDENTIAL_ENV_VAR, env));
                }
                return true;
            } catch (AccessControlException unused) {
            }
        }
        return false;
    }

    private boolean runningUsingWellKnownFile() {
        try {
            return fileExists(getWellKnownCredentialsFile());
        } catch (AccessControlException unused) {
            return false;
        }
    }

    private boolean useGAEStandardAPI() {
        try {
            try {
                Field field = forName("com.google.appengine.api.utils.SystemProperty").getField("environment");
                return field.getType().getMethod("value", new Class[0]).invoke(field.get(null), new Object[0]) != null;
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", e.getMessage())), e));
            }
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    boolean fileExists(File file) {
        return file.exists() && !file.isDirectory();
    }

    Class<?> forName(String str) throws ClassNotFoundException {
        return Class.forName(str);
    }

    final GoogleCredential getDefaultCredential(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        synchronized (this) {
            if (this.cachedCredential == null) {
                this.cachedCredential = getDefaultCredentialUnsynchronized(httpTransport, jsonFactory);
            }
            if (this.cachedCredential == null) {
                throw new IOException(String.format("The Application Default Credentials are not available. They are available if running on Google App Engine, Google Compute Engine, or Google Cloud Shell. Otherwise, the environment variable %s must be defined pointing to a file defining the credentials. See %s for more information.", CREDENTIAL_ENV_VAR, HELP_PERMALINK));
            }
            return this.cachedCredential;
        }
    }

    String getProperty(String str, String str2) {
        return System.getProperty(str, str2);
    }
}
