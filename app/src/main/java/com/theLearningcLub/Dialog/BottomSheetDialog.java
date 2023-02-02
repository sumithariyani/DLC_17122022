package com.theLearningcLub.Dialog;

import static com.theLearningcLub.HomeActivity.position;
import static com.theLearningcLub.HomeActivity.s_free_status;
import static com.theLearningcLub.HomeActivity.s_pack_id;
import static com.theLearningcLub.HomeActivity.s_pack_type;
import static com.theLearningcLub.HomeActivity.video_date;
import static com.theLearningcLub.HomeActivity.video_name;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.theLearningcLub.HomeActivity;
import com.theLearningcLub.R;
import com.theLearningcLub.VideoActivity;
import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    Button course_button;
    AppCompatImageView ivVideoImage;
   public static AppCompatTextView tvVideoName,tvDateVideo;

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,
                container, false);

        course_button = v.findViewById(R.id.course_button);
        ivVideoImage = v.findViewById(R.id.ivVideoImage);
        tvVideoName = v.findViewById(R.id.tvVideoName);
        tvDateVideo = v.findViewById(R.id.tvDateVideo);
        tvVideoName.setText(video_name);
        tvDateVideo.setText(video_date);
        Glide.with(getContext()).load(HomeActivity.videoimage
                .replace("http://","https://")).into(ivVideoImage);

        course_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> videolist = new ArrayList<>();
                ArrayList<String> videotitle = new ArrayList<>();
                for (int i = 0; i < HomeActivity.purachase_package_video_modelslist.size(); i++) {

//                    Log.e("TAG", "doInBackground??????: "+i );
                    if(HomeActivity.stvideo_id.equals(HomeActivity.purachase_package_video_modelslist.get(i).getVideo_id())){
                        position=i;
                        Log.e("TAG", "doInBackground??????: "+i );
//                                    Toast.makeText(HomeActivity.this, "one "+position, Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e("TAG", "doInBackground??????:>>>> "+i );
                    }
                    if (s_free_status.equals("0")) {
                        if (HomeActivity.purachase_package_video_modelslist.get(i).getIs_free().equals("0")) {
                        } else {
                            videolist.add(HomeActivity.purachase_package_video_modelslist.get(i).getVideo());
                            videotitle.add(HomeActivity.purachase_package_video_modelslist.get(i).getVideo_title());
                        }
                    } else {
                        videolist.add(HomeActivity.purachase_package_video_modelslist.get(i).getVideo());
                        videotitle.add(HomeActivity.purachase_package_video_modelslist.get(i).getVideo_title());
                    }
                }
                int viewvdideo=Integer.parseInt(HomeActivity.purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;

                if (s_free_status.equals("0")){
                    if (HomeActivity.purachase_package_video_modelslist.get(position).getIs_free().equals("0")){
                        Toast.makeText(getContext(), "To watch this amazing video, please purchase the package.", Toast.LENGTH_SHORT).show();
                    }else if (HomeActivity.purachase_package_video_modelslist.get(position).getIs_free().equals("1")){
//                        Toast.makeText(getContext(), "three"+position, Toast.LENGTH_SHORT).show();

                        Intent in = new Intent(getContext(),VideoActivity.class);
                        in.putExtra("URI",HomeActivity.purachase_package_video_modelslist.get(position).getVideo());
                        in.putExtra("VIDEO_TITLE",HomeActivity.purachase_package_video_modelslist.get(position).getVideo_title());
                        in.putExtra("VIDEO_DESC",HomeActivity.purachase_package_video_modelslist.get(position).getVideo_desc());
                        in.putExtra("s_pack_id",s_pack_id);
                        in.putExtra("s_pack_name","");
                        in.putExtra("VIDEO_ID",HomeActivity.purachase_package_video_modelslist.get(position).getVideo_id());
                        in.putExtra("is_free",s_free_status);
                        in.putExtra("videoType","1");
                        in.putExtra("from",s_pack_type);
                        in.putExtra("position",position);
                        in.putExtra("viewduration",viewvdideo);
                        in.putStringArrayListExtra("videoArrayList", videolist);
                        in.putStringArrayListExtra("titleArrayList", videotitle);

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("ARRAYLIST", HomeActivity.purachase_package_video_modelslist);
                        in.putExtras(bundle);
                        startActivity(in);
                    }
                }else {
//                    Toast.makeText(getContext(), "three1."+position, Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(getContext(),VideoActivity.class);
                    in.putExtra("URI",HomeActivity.purachase_package_video_modelslist.get(position).getVideo());
                    in.putExtra("VIDEO_TITLE",HomeActivity.purachase_package_video_modelslist.get(position).getVideo_title());
                    in.putExtra("VIDEO_DESC",HomeActivity.purachase_package_video_modelslist.get(position).getVideo_desc());
                    in.putExtra("s_pack_id",s_pack_id);
                    in.putExtra("s_pack_name","");
                    in.putExtra("VIDEO_ID",HomeActivity.purachase_package_video_modelslist.get(position).getVideo_id());
                    in.putExtra("is_free",s_free_status);
                    in.putExtra("videoType","1");
                    in.putExtra("from",s_pack_type);
                    in.putExtra("position",position);
                    in.putExtra("viewduration",viewvdideo);
                    in.putStringArrayListExtra("videoArrayList", videolist);
                    in.putStringArrayListExtra("titleArrayList", videotitle);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("ARRAYLIST", HomeActivity.purachase_package_video_modelslist);
                    in.putExtras(bundle);
                    startActivity(in);
                }
                dismiss();
            }
        });
        return v;
    }



}

