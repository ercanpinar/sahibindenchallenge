package challenge.sahibinden.com.sahibindenchallenge;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import challenge.sahibinden.com.sahibindenchallenge.model.Image;

/**
 * A fragment representing a single Image detail screen.
 * This fragment is either contained in a {@link ImageListActivity}
 * in two-pane mode (on tablets) or a {@link ImageDetailActivity}
 * on handsets.
 */
public class ImageDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Image mItem;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ImageDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ImageListFragment.ITEM_MAP.get(getArguments().getLong(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.user.username);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ImageView imageView= (ImageView) rootView.findViewById(R.id.image_imageview);
            if(mItem.image_url!=null)
                Glide.with(getActivity())
                        .load(mItem.image_url)
                        .into(imageView);
            ImageView imageUserView= (ImageView) rootView.findViewById(R.id.image_user_imageview);
            if(mItem.user.avatars.large.https!=null)
                Glide.with(getActivity())
                        .load(mItem.user.avatars.large.https)
                        .into(imageUserView);
            ((TextView) rootView.findViewById(R.id.image_name_textview)).setText(mItem.name + "  /  " + mItem.user.username);
            if(mItem.description!=null)
                ((TextView) rootView.findViewById(R.id.image_detail_textview)).setText(Html.fromHtml(mItem.description));
        }

        return rootView;
    }
}
