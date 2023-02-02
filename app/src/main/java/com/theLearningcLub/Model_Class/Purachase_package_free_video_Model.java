package com.theLearningcLub.Model_Class;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP on 6/7/2019.
 */

public class Purachase_package_free_video_Model implements Parcelable {

    String video_id;
    String video_title;
    String video_desc;
    String video;
    String video_date;
    String video_image;
    String is_free;
    String videoview_Time;
    String videototal_duration;

    protected Purachase_package_free_video_Model(Parcel in) {
        video_id = in.readString();
        video_title = in.readString();
        video_desc = in.readString();
        video = in.readString();
        video_date = in.readString();
        video_image = in.readString();
        is_free = in.readString();
        videoview_Time = in.readString();
        videototal_duration = in.readString();
    }

    public static final Creator<Purachase_package_free_video_Model> CREATOR = new Creator<Purachase_package_free_video_Model>() {
        @Override
        public Purachase_package_free_video_Model createFromParcel(Parcel in) {
            return new Purachase_package_free_video_Model(in);
        }

        @Override
        public Purachase_package_free_video_Model[] newArray(int size) {
            return new Purachase_package_free_video_Model[size];
        }
    };

    public Purachase_package_free_video_Model(String video_id, String video_title, String video_desc, String video, String video_date, String video_image, String is_free, String videoview_Time, String videototal_duration) {
        this.video_id = video_id;
        this.video_title = video_title;
        this.video_desc = video_desc;
        this.video = video;
        this.video_date = video_date;
        this.video_image = video_image;
        this.is_free = is_free;
        this.videoview_Time = videoview_Time;
        this.videototal_duration = videototal_duration;
    }

    public String getVideoview_Time() {
        return videoview_Time;
    }

    public void setVideoview_Time(String videoview_Time) {
        this.videoview_Time = videoview_Time;
    }

    public String getVideototal_duration() {
        return videototal_duration;
    }

    public void setVideototal_duration(String videototal_duration) {
        this.videototal_duration = videototal_duration;
    }




    public String getVideo_image() {
        return video_image;
    }

    public void setVideo_image(String video_image) {
        this.video_image = video_image;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideo_date() {
        return video_date;
    }

    public void setVideo_date(String video_date) {
        this.video_date = video_date;
    }


    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(video_id);
        parcel.writeString(video_title);
        parcel.writeString(video_desc);
        parcel.writeString(video);
        parcel.writeString(video_date);
        parcel.writeString(video_image);
        parcel.writeString(is_free);
        parcel.writeString(videoview_Time);
        parcel.writeString(videototal_duration);
    }
}
