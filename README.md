EasyCamera
==========

Wrapper around the android Camera class that simplifies its usage (<a href="http://techblog.bozho.net/?p=1380&preview=true">read more</a> about the process)

Usage:

```java

EasyCamera camera = DefaultEasyCamera.open();
CameraActions actions = camera.startPreview(surface);
PictureCallback callback = new PictureCallback() {
    public void onPictureTaken(byte[] data, CameraActions actions) {
        // store picture
    }
}
actions.takePicture(Callbacks.create().withJpegCallback(callback))
```

By default, preview stops when a picture is taken. If you want to restart preview, specify `.withRestartPreviewAfterCallbacks(true)` on the `Callbacks` object

If you need the `android.hardware.Camera` object, get it via `camera.getRawCamera()`
