package com.example.hifza.instantmet.transformImage;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by Fareeha on 2/1/2018.
 */

public class RoundedCornersTransform implements Transformation {

    public RoundedCornersTransform(final int radius, final int margin) {
        _radius = radius;
        _margin = margin;
        _key = "rounded(radius=" + radius + ", margin=" + margin + ")";
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(_margin, _margin, source.getWidth() - _margin, source.getHeight() - _margin),
                _radius, _radius, paint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return _key;
    }

    private final int _radius;
    private final int _margin;
    private final String _key;


}
