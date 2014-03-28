package com.easycamera;

import android.hardware.Camera;

/**
 * Default implementation of CameraActions
 */
public class DefaultCameraActions implements EasyCamera.CameraActions {

    private EasyCamera camera;
    DefaultCameraActions(EasyCamera camera) {
        this.camera = camera;
    }

    @Override
    public void takePicture(final EasyCamera.Callbacks callbacks) {
        // wrap EasyCamera callbacks in raw callbacks, if specified. Otherwise pass null
        camera.getRawCamera().takePicture(rawCallbackOrNull(callbacks.getShutterCallback()), rawCallbackOrNull(callbacks.getRawCallback()), rawCallbackOrNull(callbacks.getPostviewCallback()), new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                if (callbacks.getJpegCallback() != null) {
                    callbacks.getJpegCallback().onPictureTaken(data, DefaultCameraActions.this); //TODO is this the right CameraActions?
                }
                if (callbacks.isRestartPreviewAfterCallbacks()) {
                    camera.startPreview();
                }
            }
        });
    }

    @Override
    public EasyCamera getCamera() {
        return camera;
    }

    private Camera.PictureCallback rawCallbackOrNull(final EasyCamera.PictureCallback callback) {
        if (callback != null) {
            return new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    callback.onPictureTaken(data, DefaultCameraActions.this); //TODO is this the right CameraActions?
                }
            };
        }
        return null;
    }

    private Camera.ShutterCallback rawCallbackOrNull(final EasyCamera.ShutterCallback callback) {
        if (callback != null) {
            return new Camera.ShutterCallback() {
                @Override
                public void onShutter() {
                    callback.onShutter();
                }
            };
        }
        return null;
    }
}
