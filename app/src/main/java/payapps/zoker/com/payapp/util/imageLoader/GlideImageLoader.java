package payapps.zoker.com.payapp.util.imageLoader;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.impl.AlbumImageLoader;

import java.io.File;

/**
 * Created by Administrator on 2017/4/21.
 */

public class GlideImageLoader implements AlbumImageLoader {
    @Override
    public void loadImage(ImageView imageView, String imagePath, int width, int height) {
        Glide.with(imageView.getContext())
                .load(new File(imagePath))
                .into(imageView);
    }
}
