package dev.rodni.ru.googlemapsandplaces.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.rodni.ru.googlemapsandplaces.R;
import dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata.User;
import dev.rodni.ru.googlemapsandplaces.ui.IProfile;
import dev.rodni.ru.googlemapsandplaces.ui.avaterpicker.ImageListFragment;


public class ProfileFragment extends DaggerFragment implements View.OnClickListener, IProfile {
    private static final String TAG = "ProfileFragment";

    @Inject @Named("app_user") User userSingleton;

    //widgets
    private CircleImageView mAvatarImage;

    //vars
    private ImageListFragment mImageListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrieveProfileImage();
    }

    private void retrieveProfileImage(){
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.cartman_cop)
                .placeholder(R.drawable.cartman_cop);

        int avatar = 0;
        try{
            //avatar = Integer.parseInt(((UserProvider)getApplicationContext()).getUser().getAvatar());
            avatar = Integer.parseInt(userSingleton.getAvatar());
        }catch (NumberFormatException e){
            Log.e(TAG, "retrieveProfileImage: no avatar image. Setting default. " + e.getMessage() );
        }

        Glide.with(ProfileFragment.this)
                .setDefaultRequestOptions(requestOptions)
                .load(avatar)
                .into(mAvatarImage);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAvatarImage = view.findViewById(R.id.image_choose_avatar);

        view.findViewById(R.id.image_choose_avatar).setOnClickListener(this);
        view.findViewById(R.id.text_choose_avatar).setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onImageSelected(int resource) {

        // remove the image selector fragment
        //getSupportFragmentManager().beginTransaction()
        //        .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up)
        //        .remove(mImageListFragment)
        //        .commit();

        // display the image
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.cartman_cop)
                .error(R.drawable.cartman_cop);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(resource)
                .into(mAvatarImage);

        // update the client and database
        //User user = userApp;
        //user.setAvatar(String.valueOf(resource));
        userSingleton.setAvatar(String.valueOf(resource));

        FirebaseFirestore.getInstance()
                .collection(getString(R.string.collection_users))
                .document(FirebaseAuth.getInstance().getUid())
                .set(userSingleton);
    }
}
