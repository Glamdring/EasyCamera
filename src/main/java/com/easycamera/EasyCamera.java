package com.easycamera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * An interface to make working with the Android Camera easy.
 */
public interface EasyCamera { // TODO implements AutoCloseable {
    void close();

    void unlock();

    void lock();

    void reconnect() throws java.io.IOException;

    CameraActions startPreview(SurfaceHolder holder) throws IOException;

    CameraActions startPreview(SurfaceTexture texture) throws IOException;

    void stopPreview();

    void setPreviewCallback(Camera.PreviewCallback cb);

    void setOneShotPreviewCallback(Camera.PreviewCallback cb);

    void setPreviewCallbackWithBuffer(Camera.PreviewCallback cb);

    void addCallbackBuffer(byte[] callbackBuffer);

    void autoFocus(Camera.AutoFocusCallback cb);

    void cancelAutoFocus();

    void startSmoothZoom(int i);

    void stopSmoothZoom();

    void setDisplayOrientation(int i);

    void setZoomChangeListener(Camera.OnZoomChangeListener listener);

    void setErrorCallback(Camera.ErrorCallback cb);

    void setParameters(Camera.Parameters parameters);

    Camera.Parameters getParameters();

    Camera getRawCamera();

    public static interface CameraActions {
        void takePicture(Callbacks callbacks);
        void stopPreview();
    }

    public final class Callbacks {

        private ShutterCallback shutterCallback;
        private PictureCallback rawCallback;
        private PictureCallback postviewCallback;
        private PictureCallback jpegCallback;
        private boolean restartPreviewAfterCallbacks;

        public static Callbacks create() {
            return new Callbacks();
        }
        private Callbacks() {
            // private constructor to disable instantiation apart from the factory method
        }

        public Callbacks withShutterCallback(ShutterCallback shutterCallback) {
            this.shutterCallback = shutterCallback;
            return this;
        }

        public Callbacks withJpegCallback(PictureCallback jpegCallback) {
            this.jpegCallback = jpegCallback;
            return this;
        }

        public Callbacks withRawCallback(PictureCallback rawCallback) {
            this.rawCallback = rawCallback;
            return this;
        }

        public Callbacks withPostviewCallback(PictureCallback postviewCallback) {
            this.postviewCallback = postviewCallback;
            return this;
        }

        public Callbacks withRestartPreviewAfterCallbacks(boolean restart) {
            this.restartPreviewAfterCallbacks = restart;
            return this;
        }

        public ShutterCallback getShutterCallback() {
            return shutterCallback;
        }

        public PictureCallback getRawCallback() {
            return rawCallback;
        }

        public PictureCallback getPostviewCallback() {
            return postviewCallback;
        }

        public PictureCallback getJpegCallback() {
            return jpegCallback;
        }

        public boolean isRestartPreviewAfterCallbacks() {
            return restartPreviewAfterCallbacks;
        }
    }

    public static interface PictureCallback {
        void onPictureTaken(byte[] data, CameraActions actions);
    }

    public static interface ShutterCallback {
        void onShutter();
    }
}
