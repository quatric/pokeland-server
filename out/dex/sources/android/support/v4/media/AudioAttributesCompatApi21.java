package android.support.v4.media;

import android.media.AudioAttributes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: /Volumes/SSD/larsen/pokeland/apk/classes.dex */
@RequiresApi(MotionEventCompat.AXIS_WHEEL)
class AudioAttributesCompatApi21 {
    private static final String TAG = "AudioAttributesCompat";
    private static Method sAudioAttributesToLegacyStreamType;

    static final class Wrapper {
        private AudioAttributes mWrapped;

        private Wrapper(AudioAttributes audioAttributes) {
            this.mWrapped = audioAttributes;
        }

        public static Wrapper wrap(@NonNull AudioAttributes audioAttributes) {
            if (audioAttributes != null) {
                return new Wrapper(audioAttributes);
            }
            throw new IllegalArgumentException("AudioAttributesApi21.Wrapper cannot wrap null");
        }

        public AudioAttributes unwrap() {
            return this.mWrapped;
        }
    }

    AudioAttributesCompatApi21() {
    }

    public static int toLegacyStreamType(Wrapper wrapper) {
        AudioAttributes audioAttributesUnwrap = wrapper.unwrap();
        try {
            if (sAudioAttributesToLegacyStreamType == null) {
                sAudioAttributesToLegacyStreamType = AudioAttributes.class.getMethod("toLegacyStreamType", AudioAttributes.class);
            }
            return ((Integer) sAudioAttributesToLegacyStreamType.invoke(null, audioAttributesUnwrap)).intValue();
        } catch (ClassCastException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.w(TAG, "getLegacyStreamType() failed on API21+", e);
            return -1;
        }
    }
}
