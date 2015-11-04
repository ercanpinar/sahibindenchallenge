package challenge.sahibinden.com.sahibindenchallenge;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import challenge.sahibinden.com.sahibindenchallenge.model.Image;

/**
 * A list fragment representing a list of Images. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ImageDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ImageListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Long id);

    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Long id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ImageListFragment() {
    }
    public static HashMap<Long,Image> ITEM_MAP;
    private ArrayList<Image> imageArrayList;
    private Activity mActivity;
    private ImageListAdapter imageListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ITEM_MAP=new HashMap<>();
        mActivity=(ImageListActivity)getActivity();
        imageArrayList=new ArrayList<>();
        imageListAdapter=new ImageListAdapter(
                getActivity(),
                imageArrayList);
        setListAdapter(imageListAdapter);
        imageRequestMethod(1);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(imageArrayList.get(position).getId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        imageRequestMethod(offset);

    }
    public int pageFlag;
    public void imageRequestMethod(int page){
        pageFlag=page;
        final String URL = "https://api.500px.com/v1/photos?feature=popular&consumer_key=jlnyuEd6b8RFuvutw9yL7Xh5WuBepr3EqjjFE2ZN&page="+page;
        JsonObjectRequest jsonRequest = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getJSONArray("photos")!=null){

                                JSONArray imagesJsonArray= response.getJSONArray("photos");

                                for (int i = 0; i <imagesJsonArray.length() ; i++) {
                                    JSONObject jsonObject=imagesJsonArray.getJSONObject(i);
                                    JSONObject user= jsonObject.getJSONObject("user");
                                    JSONObject avatars = user.getJSONObject("avatars");
                                    Image image= new Image();
                                    image.setId(user.getLong("id"));
                                    image.setName(user.getString("username"));
                                    image.setUserAvatarUrlSmall(avatars.getJSONObject("small").getString("https"));
                                    image.setUserAvatarUrlBig(avatars.getJSONObject("large").getString("https"));
                                    image.setDescription(jsonObject.getString("description"));
                                    image.setNameImage(jsonObject.getString("name"));
                                    image.setUrlImage(jsonObject.getString("image_url"));
                                    imageArrayList.add(image);
                                    ITEM_MAP.put(user.getLong("id"),image);
                                }

                                imageListAdapter.notifyDataSetChanged();
                                createListViewEndlesListener(pageFlag);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.toString());
            }
        });

        Volley.newRequestQueue(mActivity).add(jsonRequest);
    }

    private void createListViewEndlesListener(int page) {
        if(page==1){
            getListView().setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    customLoadMoreDataFromApi(page);
                    return true;
                }
            });
        }
    }
}
