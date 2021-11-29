package com.leandro.instagram.commom.component;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MediaHelper {
    private static WeakReference<MediaHelper> INSTANCE;
    private Activity activity;
    private Fragment fragment;
    private static final int GALLERY_CODE = 2;
    private static final int CAMERA_CODE = 1;
    private Uri mCropImageUri;
    private CropImageView cropImageView;
    private OnImageCroppedListener listener;
    private Uri mSavedImageUri;


    public static MediaHelper getInstace(Activity activity) {
        if (INSTANCE == null) {
            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setActivity(activity);
        }else if (INSTANCE.get() == null){
            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setActivity(activity);
        }

        return INSTANCE.get();

    }

    public static MediaHelper getInstace(Fragment fragment) {
        if (INSTANCE == null) {
            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setFragment(fragment);
        }else if (INSTANCE.get() == null){
            MediaHelper mediaHelper = new MediaHelper();
            INSTANCE = new WeakReference<>(mediaHelper);
            INSTANCE.get().setFragment(fragment);
        }
        return INSTANCE.get();

    }

    public MediaHelper cropView(CropImageView cropImageView) {
        this.cropImageView = cropImageView;

        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setOnCropImageCompleteListener(((view, result) -> {
            Uri uri = result.getUri();
            if (uri != null && listener != null) {
                listener.onImageCropped(uri);
            }
        }));
        return this;
    }

    public MediaHelper listener(OnImageCroppedListener listener) {
        this.listener = listener;
        return this;
    }

    private Context getContext() {
        if (fragment != null && fragment.getActivity() != null)
            return fragment.getActivity();
        return activity;
    }

    public void chooserGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, GALLERY_CODE);
    }

    public void chooserCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
            }
            if (photoFile != null) {
                mCropImageUri = FileProvider.getUriForFile(getContext(), "com.leandro.instagram.fileprovider", photoFile);

                SharedPreferences myPrefs = getContext().getSharedPreferences("camera_image", 0);
                SharedPreferences.Editor edit = myPrefs.edit();
                edit.putString("url", mCropImageUri.toString());
                edit.apply();

                intent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImageUri);
                activity.startActivityForResult(intent, CAMERA_CODE);
            }
        }
    }


    private void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void onActivityResult(int requestCode, int requestResult, Intent data) {
        SharedPreferences myprefs = getContext().getSharedPreferences("camera_image", 0);
        String url = myprefs.getString("url", null);

        if (mCropImageUri == null && url != null)
            mCropImageUri = Uri.parse(url);

        if (requestCode == CAMERA_CODE && requestResult == RESULT_OK) {
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), mCropImageUri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activity != null)
                        activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
                    else
                        fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
                }
            } else {
                listener.onImagePicked(mCropImageUri);
            }
        } else if (requestCode == GALLERY_CODE && requestResult == RESULT_OK) {
            mCropImageUri = CropImage.getPickImageResultUri(getContext(), data);
            listener.onImagePicked(mCropImageUri);
        }
    }

    public void cropImage(CropImageView cropImageView) {
        File getImage = getContext().getExternalCacheDir();
        if (getImage != null) {
            mSavedImageUri = Uri.fromFile(new File(getImage.getPath(), System.currentTimeMillis() + ".jpg"));
        }
        cropImageView.saveCroppedImageAsync(mSavedImageUri);
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storeDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storeDir);
    }

    public boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public Camera getCameraInstace() {
        Camera camera = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && getContext() != null
                    && getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (activity != null)
                    activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, 300);
                else
                    fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, 300);
            }
            camera = Camera.open();
        } catch (Exception e) {

        }

        return camera;
    }

    public Uri saveCameraFile(byte[] data) {
        File pictureFile = createImageFile(true);
        if (pictureFile == null) {
            Log.d("teste 2 foto", " error createinf media file, check storage permission");
            return null;
        }
        File outputMediaFile = null;
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);

            ExifInterface exif = new ExifInterface(pictureFile.toString());
            Log.d("teste", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
            if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")) {
                realImage = rotate(realImage, 90);
            } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")) {
                realImage = rotate(realImage, 270);
            } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")) {
                realImage = rotate(realImage, 180);
            } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")) {
                realImage = rotate(realImage, 90);
            }

            realImage.compress(Bitmap.CompressFormat.JPEG, 100,fos);
            fos.close();

            Matrix matrix = new Matrix();
            outputMediaFile = createImageFile(false);
            if (outputMediaFile == null){
                Log.d("teste","Error creaing media file, check storage permissions");
                return null;
            }

            Log.i("Teste", realImage.getWidth() + " x " +realImage.getHeight());
            Bitmap result = Bitmap.createBitmap(realImage,0,0,
                    realImage.getWidth(),
                    realImage.getWidth(),matrix,true);

            fos = new FileOutputStream(outputMediaFile);
            result.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();


        } catch (FileNotFoundException e) {
            Log.e("Teste", e.getLocalizedMessage(),e);
        } catch (IOException e) {
            Log.e("Teste", e.getLocalizedMessage(),e);
        }
        return Uri.fromFile(outputMediaFile);
    }

    private static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setRotate(degree);

        return Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);
    }


    private File createImageFile(boolean temp) {
        if (getContext() == null) return null;

        File mediaStorageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (mediaStorageDir != null && !mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                Log.d("teste foto", " FAIL createImageFile: ");
                return null;
            }
        }
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + (temp ? "TEMP_" : "IMG_") + timestamp + ".jpg");
    }


    public interface OnImageCroppedListener {
        void onImageCropped(Uri uri);

        void onImagePicked(Uri uri);
    }

}
