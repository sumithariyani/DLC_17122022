
package com.theLearningcLub.model.videolist;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DatumVideoListModel {

    @SerializedName("vid_data")
    private List<VidDatumVideoListModel> mVidData;

    public List<VidDatumVideoListModel> getVidData() {
        return mVidData;
    }

    public void setVidData(List<VidDatumVideoListModel> vidData) {
        mVidData = vidData;
    }

}
