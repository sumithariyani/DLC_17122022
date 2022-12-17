package com.theLearningcLub.fragment;

import static com.theLearningcLub.HomeActivity.bottomSheetDashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

import com.theLearningcLub.AgentReportActivity;
import com.theLearningcLub.Fragment_Aboutus;
import com.theLearningcLub.Fragment_Privacy;
import com.theLearningcLub.HelpAndSupportActivity;
import com.theLearningcLub.HomeActivity;
import com.theLearningcLub.LoginActivity;
import com.theLearningcLub.ProfileActivity;
import com.theLearningcLub.QuizActivity;
import com.theLearningcLub.R;
import com.theLearningcLub.ShareActivity;
import com.theLearningcLub.utils.BaseFragment;

public class MoreFragment extends BaseFragment implements View.OnClickListener{

    LinearLayout llMyAccount,llMyPackages,llQuiz,llShare,llAbout,llPrivacy,llHelp,llAgent,llLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

//        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);
//        bottomSheetDashboard.getMenu().getItem(3).setChecked(true);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.more));
        HomeActivity.iv_menu_main.setVisibility(View.VISIBLE);
        HomeActivity.ivBack.setVisibility(View.GONE);
//        HomeActivity.ivNotification.setVisibility(View.VISIBLE);
        HomeActivity.rlCart.setVisibility(View.VISIBLE);
        HomeActivity.ivWallet.setVisibility(View.VISIBLE);

        llMyAccount = view.findViewById(R.id.llMyAccount);
        llMyPackages = view.findViewById(R.id.llMyPackages);
        llQuiz = view.findViewById(R.id.llQuiz);
        llShare = view.findViewById(R.id.llShare);
        llAbout = view.findViewById(R.id.llAbout);
        llPrivacy = view.findViewById(R.id.llPrivacy);
        llHelp = view.findViewById(R.id.llHelp);
        llAgent = view.findViewById(R.id.llAgent);
        llLogout = view.findViewById(R.id.llLogout);

        llMyAccount.setOnClickListener(this);
        llMyPackages.setOnClickListener(this);
        llQuiz.setOnClickListener(this);
        llShare.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llPrivacy.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llAgent.setOnClickListener(this);
        llLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llMyAccount){
            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new ProfileActivity(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
//            sessionManager.setfragmentclick("true");

        }else if (view.getId() == R.id.llMyPackages){

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new MyPackagesActivity(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
            HomeActivity.tvUserHello.setText(getResources().getString(R.string.my_packages));
            HomeActivity.iv_menu_main.setVisibility(View.GONE);
            HomeActivity.ivBack.setVisibility(View.VISIBLE);
//            HomeActivity.ivNotification.setVisibility(View.VISIBLE);
            HomeActivity.rlCart.setVisibility(View.VISIBLE);

//            if(sessionManager.getclick().equals("false")) {
//                sessionManager.setclick("true");
//                sessionManager.setfragmentclick("true");
//            }else{
//                Toast.makeText(mContext, "you have not purchased any packages.", Toast.LENGTH_SHORT).show();
//            }


        }else if (view.getId() == R.id.llQuiz){

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new QuizActivity(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
            HomeActivity.tvUserHello.setText(getResources().getString(R.string.quiz));

//            if(sessionManager.getclick().equals("false")) {
//                sessionManager.setclick("true");
//                sessionManager.setfragmentclick("true");
//            }else{
//                Toast.makeText(mContext, "No quiz available", Toast.LENGTH_SHORT).show();
//            }
        }else if (view.getId() == R.id.llShare){

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new ShareActivity(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
//            sessionManager.setIscheck("0");
//            sessionManager.setfragmentclick("true");
            HomeActivity.tvUserHello.setText(getResources().getString(R.string.share));

        }else if (view.getId() == R.id.llAbout){

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new Fragment_Aboutus(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
            HomeActivity.tvUserHello.setText(getResources().getString(R.string.about_us));
            HomeActivity.iv_menu_main.setVisibility(View.GONE);
            HomeActivity.ivBack.setVisibility(View.VISIBLE);
//            HomeActivity.ivNotification.setVisibility(View.GONE);
            HomeActivity.rlCart.setVisibility(View.GONE);
//            sessionManager.setfragmentclick("true");

        }else if (view.getId() == R.id.llPrivacy){

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new Fragment_Privacy(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
            HomeActivity.tvUserHello.setText(getResources().getString(R.string.privacy_policy));
            HomeActivity.iv_menu_main.setVisibility(View.GONE);
            HomeActivity.ivBack.setVisibility(View.VISIBLE);
//            HomeActivity.ivNotification.setVisibility(View.GONE);
            HomeActivity.rlCart.setVisibility(View.GONE);
//            sessionManager.setfragmentclick("true");

        }else if (view.getId() == R.id.llHelp){

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new HelpAndSupportActivity(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
//            sessionManager.setfragmentclick("true");

        }else if (view.getId() == R.id.llAgent){

            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new AgentReportActivity(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
//            sessionManager.setfragmentclick("true");

        }else if (view.getId() == R.id.llLogout){
            logoutAlertBox();
        }
    }

    private void logoutAlertBox(){
        //will create a view of our custom dialog layout
        View alertCustomdialog = LayoutInflater.from(mContext).inflate(R.layout.dialog_box,null);
        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);
        AppCompatButton btnYes = alertCustomdialog.findViewById(R.id.btnYes);
        AppCompatButton btnNo = alertCustomdialog.findViewById(R.id.btnNo);
        TextView tvMsg = alertCustomdialog.findViewById(R.id.tvMsg);
        tvMsg.setText(mContext.getResources().getString(R.string.logout_massage));

        final AlertDialog dialog = alert.create();
        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //finally show the dialog box in android all
        dialog.show();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.clearSession();
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                mContext.finish();
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}