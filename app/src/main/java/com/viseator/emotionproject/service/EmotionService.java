package com.viseator.emotionproject.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.viseator.emotionproject.App;
import com.viseator.emotionproject.MainActivity;
import com.viseator.emotionproject.data.DaoSession;
import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.EmotionDataEntityDao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmotionService extends Service {

    private static final String TAG = "EmotionService";

    private EmotionServiceClient client;
    private EmotionDataEntityDao entityDao;
    private AlarmManager alarmManager;
    private Camera camera;
    private PhotoHolder photoHolder;
    private EmotionData data;

    private Handler mHandler;


    public class ServiceBinder extends Binder {
        public Service getService() {
            return EmotionService.this;
        }
        public void setHandler(Handler handler) {
            mHandler = handler;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Create Service");
        initialService();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        return super.onStartCommand(intent, flag, startId);
    }

    private EmotionDataEntityDao getEmotionDataEntityDao() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        return daoSession.getEmotionDataEntityDao();
    }

    private void initialService() {
        if (client == null) {
            client = new EmotionServiceRestClient("64130e72f5b34b19b7651c10e21703b4");
        }
        if (entityDao == null) {
            entityDao = getEmotionDataEntityDao();
            data = EmotionData.getInstance(entityDao);
        }
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        if (photoHolder == null) {
            photoHolder = new PhotoHolder();
        }
        IntentFilter intentFilter = new IntentFilter("com.viseator.emotionproject.mainservice");
        registerReceiver(mainServiceReceiver, intentFilter);
    }

    BroadcastReceiver mainServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive" + camera);
            if ("com.viseator.emotionproject.mainservice".equals(intent.getAction())) {
                setCamera();
                Log.d(TAG, "ALARMING!!!!");
            }
        }
    };

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "ServiceCamera");
    }

    private class PhotoHolder implements Camera.PictureCallback{
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            new SendRequest(bytes).execute();
        }
    }

    private class SendRequest extends AsyncTask<String, String, Boolean> {

        private byte[] bitmapBytes;
        private Gson gson;

        SendRequest(byte[] bitmapBytes) {
            this.bitmapBytes = bitmapBytes;
            gson = new Gson();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Log.d(TAG, "ready to send " + bitmapBytes.length);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Matrix matrix = new Matrix();
            matrix.postRotate(0);
            Bitmap proBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            proBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            String fileName = getDir().getPath() + File.pathSeparator + "test.jpg";
            Log.d(TAG, fileName);
            File file = new File(fileName);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(outputStream.toByteArray());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            List<RecognizeResult> resultList = null;

            try {
                resultList = EmotionService.this.client.recognizeImage(inputStream);
            } catch (EmotionServiceException | IOException e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }

            data.addEmotionData(resultList, System.currentTimeMillis());
            String result = gson.toJson(resultList);

            Message msg = new Message();
            msg.arg1 = 1;
            if (mHandler != null)
                mHandler.sendMessage(msg);

            Log.d("EmotionService", result);
            return Boolean.TRUE;
        }
    }

    private Camera openFacingFrontCamera() {
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        ;
        for (int camIdx = 0, cameraCount = Camera.getNumberOfCameras(); camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        return cam;
    }

    private void setCamera() {
        camera = openFacingFrontCamera();
        Camera.Parameters params = camera.getParameters();
        if (params.getMaxNumMeteringAreas() > 0){ // check that metering areas are supported
            Log.d(TAG, "ok");
            List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
            Rect areaRect1 = new Rect(-100, -100, 100, 100);    // specify an area in center of image
            meteringAreas.add(new Camera.Area(areaRect1, 1000)); // set weight to 60%
            params.setMeteringAreas(meteringAreas);
        }
        camera.setParameters(params);
        camera.startPreview();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        camera.takePicture(null, null, new PhotoHolder());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        camera.release();
    }

    public void startWork() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.viseator.emotionproject.mainservice");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, 0);
        // One minute
        Log.d(TAG, "alarmManager has been initialed");
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, 5000, 60000, pendingIntent);
    }

    public void stopWork() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.viseator.emotionproject.mainservice");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, 0);
        Log.d(TAG, "alarmManager has been stopped");
        alarmManager.cancel(pendingIntent);
    }
}
