package com.szhrnet.dotcom.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.szhrnet.dotcom.R;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * Created by lk on 2017/4/5 0005.
 * Picasso图片加载类
 */
public class PicassoUtils implements ImageLoader {

  /** 图片加载不使用https验证 */
  public static void initPicassoWithoutSecurity(Context context, OkHttpClient client) {

    Picasso build = new Picasso.Builder(context).downloader(new OkHttp3Downloader(client))
        .listener(new Picasso.Listener() {
          @Override public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
            exception.printStackTrace();
          }
        })
        .build();
    Picasso.setSingletonInstance(build);
  }

  public static void LoadImage(Context context, String path, ImageView imageView) {
    if (TextUtils.isEmpty(path)) return;
    Picasso.with(context).load(path).into(imageView);
  }

  public static void LoadImage(Context context, File file, ImageView imageView) {
    Picasso.with(context).load(file).into(imageView);
  }

  public static void LoadImage(Context context, int path, ImageView imageView) {
    Picasso.with(context).load(path).into(imageView);
  }

  /**
   * 加载圆形图片
   */
  public static void LoadCircleImage(Context context, String path, ImageView imageView) {
    if (TextUtils.isEmpty(path)) return;
    if (checkImgType(path)) {
      Picasso.with(context).load(path).transform(new CircleTransform()).into(imageView);
    } else {
      Picasso.with(context).load(path).into(imageView);
    }
  }

  /**
   * 加载圆形图片
   */
  public static void LoadCircleImage(Context context, int path, ImageView imageView) {
    Picasso.with(context).load(path).transform(new CircleTransform()).into(imageView);
  }

  /**
   * 加载圆形图片,自定义大小
   */
  public static void LoadCircleImage(Context context, int path, ImageView imageView, int size) {
    int i = DensityUtils.dp2px(size);
    Picasso.with(context).load(path).resize(i, i).transform(new CircleTransform()).into(imageView);
  }

  /**
   * 加载圆形图片,自定义大小
   */
  public static void LoadCircleImage(Context context, String path, ImageView imageView, int size) {
    if (TextUtils.isEmpty(path)) return;
    int i = DensityUtils.dp2px(size);
    if (checkImgType(path)) {
      Picasso.with(context)
          .load(path)
          .resize(i, i)
          .transform(new CircleTransform())
          .into(imageView);
    } else {
      Picasso.with(context).load(path).resize(i, i).into(imageView);
    }
  }

  /**
   * 加载圆形带边框图片
   *
   * @param border dp值
   */
  public static void LoadCircleStrokeImage(Context context, String path, ImageView imageView,
      int border, int borderColor) {
    if (TextUtils.isEmpty(path)) return;
    if (checkImgType(path)) {
      int borderWidth = DensityUtils.dp2px(border);
      Picasso.with(context)
          .load(path)
          .transform(new CircleStrokeTransform(borderWidth, borderColor))
          .into(imageView);
    } else {
      Picasso.with(context).load(path).into(imageView);
    }
  }

  /**
   * 加载圆形带边框图片
   *
   * @param border dp值
   */
  public static void LoadCircleStrokeImage(Context context, int resId, ImageView imageView,
      int border, int borderColor) {
    int borderWidth = DensityUtils.dp2px(border);
    Picasso.with(context)
        .load(resId)
        .transform(new CircleStrokeTransform(borderWidth, borderColor))
        .into(imageView);
  }

  /**
   * 加载圆角图片
   */
  public static void LoadRoundImage(Context context, File file, float roundRadius,
      ImageView imageView) {
    Picasso.with(context).load(file).transform(new RoundTransform(roundRadius)).into(imageView);
  }

  /**
   * 加载圆角图片
   */
  public static void LoadRoundImage(Context context, String path, int roundRadius,
      ImageView imageView) {
    if (TextUtils.isEmpty(path)) return;
    if (checkImgType(path)) {
      Picasso.with(context).load(path).transform(new RoundTransform(roundRadius)).into(imageView);
    } else {
      Picasso.with(context).load(path).into(imageView);
    }
  }

  /**
   * 加载圆角图片
   */
  public static void LoadRoundImage(Context context, int path, float roundRadius,
      ImageView imageView) {
    Picasso.with(context).load(path).transform(new RoundTransform(roundRadius)).into(imageView);
  }

  @Override public void displayImage(Activity activity, String path, ImageView imageView, int width,
      int height) {
    if (TextUtils.isEmpty(path)) return;
    Picasso.with(activity)
        .load(Uri.fromFile(new File(path)))
        .placeholder(R.mipmap.default_image)
        .error(R.mipmap.default_image)
        .resize(width, height)
        .centerInside()
        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
        .into(imageView);
  }

  @Override public void clearMemoryCache() {

  }

  //--------------------------------------------------

  /**
   * 设置圆形头像
   */
  public static class CircleTransform implements Transformation {
    @Override public Bitmap transform(Bitmap source) {
      int size = Math.min(source.getWidth(), source.getHeight());

      int x = (source.getWidth() - size) / 2;
      int y = (source.getHeight() - size) / 2;

      Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
      if (squaredBitmap != source) {
        source.recycle();
      }

      Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

      Canvas canvas = new Canvas(bitmap);
      Paint paint = new Paint();
      BitmapShader shader =
          new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
      paint.setShader(shader);
      paint.setAntiAlias(true);
      float r = size / 2f;
      canvas.drawCircle(r, r, r, paint);

      squaredBitmap.recycle();
      return bitmap;
    }

    @Override public String key() {
      return "circle";
    }
  }

  /**
   * 圆形带边框图片加载
   */
  public static class CircleStrokeTransform implements Transformation {

    private int mBorderWidth = 4;  //边框宽度
    private @ColorInt int mBorderColor = R.color.white;  //边框颜色

    public CircleStrokeTransform(int mBorderWidth, @ColorInt int mBorderColor) {

      this.mBorderColor = mBorderColor;
      this.mBorderWidth = mBorderWidth;
    }

    @Override public Bitmap transform(Bitmap source) {
      int size = Math.min(source.getWidth(), source.getHeight());

      int x = (source.getWidth() - size) / 2;
      int y = (source.getHeight() - size) / 2;

      Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
      if (squaredBitmap != source) {
        source.recycle();
      }

      Bitmap bitmap = Bitmap.createBitmap(size, size,
          source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888);

      Canvas canvas = new Canvas(bitmap);
      //绘制圆形
      Paint paint = new Paint();
      BitmapShader shader =
          new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
      paint.setShader(shader);
      paint.setAntiAlias(true);

      //绘制边框
      Paint mBorderPaint = new Paint();
      mBorderPaint.setStyle(Paint.Style.STROKE);
      mBorderPaint.setStrokeWidth(mBorderWidth);
      mBorderPaint.setColor(mBorderColor);
      mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
      mBorderPaint.setAntiAlias(true);

      //将边框和圆形画到canvas上
      float r = size / 2f;
      float r1 = (size - 2 * mBorderWidth) / 2f;
      canvas.drawCircle(r, r, r1, paint);
      canvas.drawCircle(r, r, r1, mBorderPaint);

      squaredBitmap.recycle();
      return bitmap;
    }

    @Override public String key() {
      return "Circle-stroke";
    }
  }

  /**
   * 绘制圆角
   */
  private static class RoundTransform implements Transformation {
    private float radius;//圆角值

    public RoundTransform(float radius) {
      this.radius = radius;
    }

    @Override public String key() {
      return "round : radius = " + radius;
    }

    @Override public Bitmap transform(Bitmap source) {
      int widthLight = source.getWidth();
      int heightLight = source.getHeight();

      Bitmap output =
          Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

      Canvas canvas = new Canvas(output);
      Paint paintColor = new Paint();
      paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

      RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

      canvas.drawRoundRect(rectF, radius, radius, paintColor);

      Paint paintImage = new Paint();
      paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
      canvas.drawBitmap(source, 0, 0, paintImage);
      source.recycle();
      return output;
    }
  }

  /**
   * 不能使用圆形加载gif图片
   */
  private static boolean checkImgType(String path) {
    if (TextUtils.isEmpty(path)) {
      return false;
    }
    if (path.endsWith("gif") || path.endsWith("GIF")) {
      return false;
    }
    return true;
  }
}

