EasyCamera
==========

Wrapper around the android Camera class that simplifies its usage (<a href="http://techblog.bozho.net/?p=1380">read more</a> about the process)

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

How to import in maven:

	<dependency>
        <groupId>net.bozho.easycamera</groupId>
        <artifactId>easycamera</artifactId>
        <version>0.0.1</version>
        <classifier>aar</classifier>
    </dependency>