package com.easycamera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * An interface to make working with the Android Camera easy.
 * 
 * Usage:
 * 
 * <pre>
 * {@code
 * EasyCamera camera = DefaultEasyCamera.open();
 * CameraActions actions = camera.startPreview(surface)
 * PictureCallback jpegCallback = new PictureCallback() {
 *     public void onPictureTaken(byte[] data, CameraActions actions) {
 *        // store picture
 *     }
 * }
 * actions.takePicture(Callbacks.withJpegCallback(jpegCallback));
 * }
 * </pre>
 * 
 * Remember to invoke camera.close() when you are done using the camera -
 * this can be done in the callback, or in an inPause(..) method of an Activity
 */
public interface EasyCamera { // TODO implements AutoCloseable {
	/**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#release%28%29">Camera.release()</a>
	 */
    void close();

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#unlock%28%29">Camera.unlock()</a>
	 */
    void unlock();

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#lock%28%29">Camera.lock()</a>
	 */
    void lock();

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#reconnect%28%29">Camera.reconnect()</a>
	 */
    void reconnect() throws java.io.IOException;

    /**
	 * Convenient method that combines two steps required before taking pictures:
	 * <a href="http://developer.android.com/reference/android/hardware/Camera.html#setPreviewDisplay%28android.view.SurfaceHolder%29">Setting surface</a>
	 * <a href="http://developer.android.com/reference/android/hardware/Camera.html#startPreview%28%29">Starting preview</a>
	 */
    CameraActions startPreview(SurfaceHolder holder) throws IOException;

    /**
	 * Convenient method that combines two steps required before taking pictures:
	 * <a href="http://developer.android.com/reference/android/hardware/Camera.html#setPreviewTexture%28android.graphics.SurfaceTexture%29">Setting texture</a>
	 * <a href="http://developer.android.com/reference/android/hardware/Camera.html#startPreview%28%29">Starting preview</a>
	 */
    CameraActions startPreview(SurfaceTexture texture) throws IOException;

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#stopPreview%28%29">Camera.stopPreview()</a>
	 */
    void stopPreview();

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#setPreviewCallback%28android.hardware.Camera.PreviewCallback%29">Camera.setPreviewCallback(..)</a>
	 */
    void setPreviewCallback(Camera.PreviewCallback cb);

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#setOneShotPreviewCallback%28android.hardware.Camera.PreviewCallback%29">Camera.setOneShotPreviewCallback(..)</a>
	 */
    void setOneShotPreviewCallback(Camera.PreviewCallback cb);

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#setPreviewCallbackWithBuffer%28android.hardware.Camera.PreviewCallback%29">Camera.setPreviewCallbackWithBuffer(..)</a>
	 */
    void setPreviewCallbackWithBuffer(Camera.PreviewCallback cb);

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#addCallbackBuffer%28byte[]%29">Camera.addCallbackBuffer(..)</a>
	 */
    void addCallbackBuffer(byte[] callbackBuffer);

	/**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#autoFocus%28android.hardware.Camera.AutoFocusCallback%29">Camera.autoFocus(..)</a>
	 */
    void autoFocus(Camera.AutoFocusCallback cb);

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#cancelAutoFocus%28%29">Camera.cancelAutoFocus()</a>
	 */
    void cancelAutoFocus();

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#startSmoothZoom%28int%29">Camera.startSmoothZoom(..)</a>
	 */
    void startSmoothZoom(int value);

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#stopSmoothZoom%28%29">Camera.stopSmoothZoom()</a>
	 */
    void stopSmoothZoom();

    /**
	 * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#startSmoothZoom%28int%29">Camera.startSmoothZoom(..)</a>
	 */
    void setDisplayOrientation(int degrees);

    /**
     * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#setZoomChangeListener%28android.hardware.Camera.OnZoomChangeListener%29">Camera.setZoomChangeListener(..)</a>
     */
    void setZoomChangeListener(Camera.OnZoomChangeListener listener);

    /**
     * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#setErrorCallback%28android.hardware.Camera.ErrorCallback%29">Camera.setErrorCallback(..)</a>
     */
    void setErrorCallback(Camera.ErrorCallback cb);

    /**
     * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#setParameters%28android.hardware.Camera.Parameters%29">Camera.setParameters(..)</a> 
     */
    void setParameters(Camera.Parameters parameters);

    /**
     * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#getParameters%28%29">Camera.getParameters(..)</a> 
     */
    Camera.Parameters getParameters();

    /**
     * Get the underlying android Camera object
     * @return raw camera object
     */
    Camera getRawCamera();

    public static interface CameraActions {
        void takePicture(Callbacks callbacks);
        EasyCamera getCamera();
    }

    /**
	 * Builder-like class that encapsulates picture callbacks. All the callbacks
	 * are optional (though normally you'd need at least one to be able to
	 * actually do anything meaningful)
	 * 
	 */
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

    /**
     * See <a href="http://developer.android.com/reference/android/hardware/Camera.PictureCallback.html">Camera.PictureCallback</a> 
     */
    public static interface PictureCallback {
        void onPictureTaken(byte[] data, CameraActions actions);
    }

    /**
     * See <a href="http://developer.android.com/reference/android/hardware/Camera.ShutterCallback.html">Camera.ShutterCallback</a> 
     */
    public static interface ShutterCallback {
        void onShutter();
    }
}
