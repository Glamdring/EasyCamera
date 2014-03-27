package com.easycamera;

import android.hardware.Camera;

public class DefaultCameraActions implements EasyCamera.CameraActions {

    private Camera camera;
    DefaultCameraActions(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void takePicture(final EasyCamera.Callbacks callbacks) {
        // wrap EasyCamera callbacks in raw callbacks, if specified. Otherwise pass null
        camera.takePicture(rawCallbackOrNull(callbacks.getShutterCallback()), rawCallbackOrNull(callbacks.getRawCallback()), rawCallbackOrNull(callbacks.getPostviewCallback()), new Camera.PictureCallback() {
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

    @Override
    public void stopPreview() {
        camera.stopPreview();
    }
}
