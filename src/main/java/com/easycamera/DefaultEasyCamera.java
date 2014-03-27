package com.easycamera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

public class DefaultEasyCamera implements EasyCamera {

    private Camera camera;

    public static final EasyCamera open() {
        return new DefaultEasyCamera(Camera.open());
    }

    public static final EasyCamera open(int id) {
        return new DefaultEasyCamera(Camera.open(id));
    }
    private DefaultEasyCamera(Camera camera) {
        this.camera = camera;
    }


    @Override
    public CameraActions startPreview(SurfaceHolder holder) throws IOException {
        camera.setPreviewDisplay(holder);
        return new DefaultCameraActions(camera);
    }

    @Override
    public CameraActions startPreview(SurfaceTexture texture) throws IOException {
        camera.setPreviewTexture(texture);
        return new DefaultCameraActions(camera);
    }

    @Override
    public void close() {
        camera.release();
    }

    @Override
    public void unlock() {
        camera.unlock();
    }

    @Override
    public void lock() {
        camera.lock();
    }

    @Override
    public void reconnect() throws IOException {
        camera.reconnect();
    }

    @Override
    public void stopPreview() {
        camera.stopPreview();
    }

    @Override
    public void setPreviewCallback(Camera.PreviewCallback cb) {
        camera.setPreviewCallback(cb);
    }

    @Override
    public void setOneShotPreviewCallback(Camera.PreviewCallback cb) {
        camera.setOneShotPreviewCallback(cb);
    }

    @Override
    public void setPreviewCallbackWithBuffer(Camera.PreviewCallback cb) {
        camera.setPreviewCallbackWithBuffer(cb);
    }

    @Override
    public void addCallbackBuffer(byte[] callbackBuffer) {
        camera.addCallbackBuffer(callbackBuffer);
    }

    @Override
    public void autoFocus(Camera.AutoFocusCallback cb) {
        camera.autoFocus(cb);
    }

    @Override
    public void cancelAutoFocus() {
        camera.cancelAutoFocus();
    }

    @Override
    public void startSmoothZoom(int value) {
        camera.startSmoothZoom(value);
    }

    @Override
    public void stopSmoothZoom() {
        camera.stopSmoothZoom();
    }

    @Override
    public void setDisplayOrientation(int degrees) {
        camera.setDisplayOrientation(degrees);
    }

    @Override
    public void setZoomChangeListener(Camera.OnZoomChangeListener listener) {
        camera.setZoomChangeListener(listener);
    }

    @Override
    public void setErrorCallback(Camera.ErrorCallback cb) {
        camera.setErrorCallback(cb);
    }

    @Override
    public void setParameters(Camera.Parameters parameters) {
        camera.setParameters(parameters);
    }

    @Override
    public Camera.Parameters getParameters() {
        return camera.getParameters();
    }

    @Override
    public Camera getRawCamera() {
        return camera;
    }
}
