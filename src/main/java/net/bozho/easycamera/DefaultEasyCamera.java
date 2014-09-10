package net.bozho.easycamera;

import java.io.IOException;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusMoveCallback;
import android.hardware.Camera.FaceDetectionListener;
import android.os.Build;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

/**
 * The default implementation of EasyCamera 
 *
 */
public class DefaultEasyCamera implements EasyCamera {

    private Camera camera;
    private int id;
    /**
     * Gets access to the default camera
     * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#open%28%29">Camera.open()</a>
     */
    public static final EasyCamera open() {
        return new DefaultEasyCamera(Camera.open(), 0);
    }

    /**
     * Gets access to a specific camera
     * See <a href="http://developer.android.com/reference/android/hardware/Camera.html#open%28int%29">Camera.open(..)</a>
     */
    public static final EasyCamera open(int id) {
        return new DefaultEasyCamera(Camera.open(id), id);
    }
    
    private DefaultEasyCamera(Camera camera, int id) {
        this.camera = camera;
        this.id = id;
    }


    @Override
    public CameraActions startPreview(SurfaceHolder holder) throws IOException {
        if (holder == null) {
            throw new NullPointerException("You cannot start preview without a preview surface");
        }
        camera.setPreviewDisplay(holder);
        camera.startPreview();
        return new DefaultCameraActions(this);
    }

    @Override
    public CameraActions startPreview(SurfaceTexture texture) throws IOException {
        if (texture == null) {
            throw new NullPointerException("You cannot start preview without a preview texture");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            camera.setPreviewTexture(texture);
        } else {
            throw new IllegalStateException("Your Android version does not support this method.");
        }
        camera.startPreview();
        return new DefaultCameraActions(this);
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

	@Override
	public void alignCameraAndDisplayOrientation(WindowManager windowManager) {
	     Camera.CameraInfo info = new Camera.CameraInfo();
	     Camera.getCameraInfo(id, info);
	     int rotation = windowManager.getDefaultDisplay().getRotation();
	     int degrees = 0;
	     switch (rotation) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	     }

	     int result;
	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	         result = (info.orientation + degrees) % 360;
	         result = (360 - result) % 360;  // compensate the mirror
	     } else {  // back-facing
	         result = (info.orientation - degrees + 360) % 360;
	     }
	     camera.setDisplayOrientation(result);
	}
	
	@Override
	public boolean enableShutterSound(boolean enabled) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			return camera.enableShutterSound(enabled);
		}
		return false;
	}

	@Override
	public void setAutoFocusMoveCallback(AutoFocusMoveCallback cb) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			camera.setAutoFocusMoveCallback(cb);
		}
	}

	@Override
	public void setFaceDetectionListener(FaceDetectionListener listener) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			camera.setFaceDetectionListener(listener);
		}
	}

	@Override
	public void startFaceDetection() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			camera.startFaceDetection();
		}
	}

	@Override
	public void stopFaceDetection() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			camera.stopFaceDetection();
		}
	}
}
