package com.lg.gpuimageeffectsampleapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import jp.co.cyberagent.android.gpuimage.*;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener{


    GPUImageView imageView;
    SeekBar saturationseekbar, brightnessseekbar, contrastseekbar;
    Bitmap bitmap;

    GPUImageSaturationFilter gpuImageSaturationFilter = new GPUImageSaturationFilter();
    GPUImageBrightnessFilter gpuImageBrightnessFilter = new GPUImageBrightnessFilter();
    GPUImageContrastFilter gpuImageContrastFilter = new GPUImageContrastFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (GPUImageView) findViewById(R.id.gpuimage);
        saturationseekbar = (SeekBar) findViewById(R.id.saturationseekbar);
        brightnessseekbar = (SeekBar) findViewById(R.id.brightnessseekbar);
        contrastseekbar = (SeekBar) findViewById(R.id.contrastseekbar);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.manzara5);

        // Using GPUImage image processing
//        GPUImage gpuImage = new GPUImage(this);
//        gpuImage.setImage(bitmap);
//        gpuImage.setFilter(new GPUImageSaturationFilter(0.5f));
//        bitmap = gpuImage.getBitmapWithFilterApplied();

        // Display the processed image in ImageView

//        imageView.setFilter(new GPUImageSaturationFilter(0.5f));
        imageView.setImage(bitmap);

        saturationseekbar.setOnSeekBarChangeListener(this);
        brightnessseekbar.setOnSeekBarChangeListener(this);
        contrastseekbar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()){
            case R.id.saturationseekbar:
                gpuImageSaturationFilter.setSaturation(range(i, 0f, 2f));
                applyFiltersToBitmap();
                break;
            case R.id.brightnessseekbar:
                gpuImageBrightnessFilter.setBrightness(range(i, -1.0f, 1f));
                applyFiltersToBitmap();
                break;
            case R.id.contrastseekbar:
                gpuImageContrastFilter.setContrast(range(i, 0f, 4.0f));
                applyFiltersToBitmap();
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    protected float range(final int percentage, final float start, final float end) {

        return (end - start) * percentage / 100.0f + start;

    }

    protected void applyFiltersToBitmap() {
        GPUImageFilterGroup group = new GPUImageFilterGroup();
        group.addFilter(gpuImageSaturationFilter);
        group.addFilter(gpuImageBrightnessFilter);
        group.addFilter(gpuImageContrastFilter);

        imageView.setFilter(group);

        imageView.requestRender();
    }
}
