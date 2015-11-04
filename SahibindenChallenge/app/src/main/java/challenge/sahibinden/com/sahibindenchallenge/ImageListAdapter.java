package challenge.sahibinden.com.sahibindenchallenge;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import challenge.sahibinden.com.sahibindenchallenge.model.Image;

/**
 * Created by ercanpinar on 04/11/15.
 */
public class ImageListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Image>  mImageList;
    private Activity mActivity;
    public ImageListAdapter(Activity activity, ArrayList<Image> images) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mImageList = images;
        this.mActivity=activity;
    }

    @Override
    public int getCount() {
        if(mImageList!=null)
            return mImageList.size();
        else
            return 0;
    }

    @Override
    public Image getItem(int position) {
        return mImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item;

        item = mInflater.inflate(R.layout.item_image_list, null);
        TextView textView =
                (TextView) item.findViewById(R.id.name_textview);
        ImageView imageView =
                (ImageView) item.findViewById(R.id.icon_imageview);
        ImageView imageUserView =
                (ImageView) item.findViewById(R.id.icon_user_imageview);
        Image image = mImageList.get(position);

        textView.setText(image.getNameImage()+"/"+image.getName());
        Glide.with(mActivity)
                .load(image.getUrlImage())
                .into(imageView);
        Glide.with(mActivity)
                .load(image.getUserAvatarUrlSmall())
                .into(imageUserView);
        return item;
    }
}