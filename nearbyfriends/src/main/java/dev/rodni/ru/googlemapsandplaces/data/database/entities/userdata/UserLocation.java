package dev.rodni.ru.googlemapsandplaces.data.database.entities.userdata;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import org.threeten.bp.ZonedDateTime;

import java.util.Date;

//this class is parcelable because we need to pass this between the activities
@Entity(tableName = "user_location")
public class UserLocation implements Parcelable{

    @PrimaryKey(autoGenerate = false)
    private int id = 1;

    @Ignore
    @Embedded(prefix = "user_location_")
    private User user;
    @Embedded(prefix = "user_location_")
    private GeoPoint geo_point;
    @Embedded(prefix = "user_location_")
    private @ServerTimestamp
    Date timestamp;

    public UserLocation(User user, GeoPoint geo_point, Date timestamp) {
        this.user = user;
        this.geo_point = geo_point;
        this.timestamp = timestamp;
    }


    public UserLocation() {

    }

    @Ignore
    protected UserLocation(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<UserLocation> CREATOR = new Creator<UserLocation>() {
        @Override
        public UserLocation createFromParcel(Parcel in) {
            return new UserLocation(in);
        }

        @Override
        public UserLocation[] newArray(int size) {
            return new UserLocation[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GeoPoint getGeo_point() {
        return geo_point;
    }

    public void setGeo_point(GeoPoint geo_point) {
        this.geo_point = geo_point;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "user=" + user +
                ", geo_point=" + geo_point +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
    }
}
