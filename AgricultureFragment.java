package com.androquad.shobujekattor.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.androquad.shobujekattor.R;


public class AgricultureFragment extends android.app.Fragment {

    ListView classLv, fosholLv;
    int curPos;

    public AgricultureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agriculture, container, false);

        final String[] district = {"কন্দাল ফসল ", "ডাল ফসল ", "তৈলবীজ", "দানা ফসল", "ফল", "ফুল", "মসলা", "সবজি ফসল"};
        final String[] sreni0 = {"আলু", "কচু", "মিষ্টি আলু", "মুখীকচু"};
        final String[] sreni1 = {"খেসারী", "ছোলা", "ফেলন", "মসুর", "মাসকলাই", "মুগ"};
        final String[] sreni2 = {"গর্জন তিল", "চীনাবাদাম", "তিল", "তিসি", "সরিষা", "সূর্যমুখী"};
        final String[] sreni3 = {"কাউন", "গম", "চীনা","ট্রিটিক্যালি","ভুট্টা"};
        final String[] sreni4 = {"আঁশফল", "আনারস", "আম", "আমলকি", "আমড়া", "কমলা","কাঁঠাল", "কামরাঙ্গা", "কুল", "জামরুল",
                "তেঁতুল", "তৈকর", "নারিকেল", "নাশপাতি","পেঁপে", "পেঁয়ারা", "বাতাবিলেবু", "লটকন","লিচু","লেবু"};
        final String[] sreni5 = {"অর্কিড", "চন্দ্রমল্লিকা"};
        final String[] sreni6 = {"আদা", "কালজিরা", "গোল মরিচ", "ধনিয়া", "পেঁয়াজ", "মরিচ", "মেথি","রসুন","হলুদ"};
        final String[] sreni7 = {"করলা","চালকুমড়া","টমেটো","পটল","পুঁইশাক","ফুলকপি","বরবটি","বাঁধাকপি","লাউ","লালশাক","লেটুস","শিম"};
        final int [] imgList ={R.drawable.alo,R.drawable.kochu,R.drawable.mistalo,R.drawable.mukhikochu,R.drawable.khesari,R.drawable.chula,R.drawable.felon,R.drawable.mosor};

        //-----ALERT DIALOG PART 1-----
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("X", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //....
            }
        }).setNegativeButton(" ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //....
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater DialogInflater = getActivity().getLayoutInflater();
        final View dialogLayout = DialogInflater.inflate(R.layout.dialog_product, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //---to show the window as per the image dimensions;
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(), imgList[curPos]);

                float imageWidthInPX = (float) image.getWidth();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float) icon.getHeight() / (float) icon.getWidth()));
                image.setLayoutParams(layoutParams);
            }
        });
        //------------------------





        classLv = (ListView) view.findViewById(R.id.classLv);
        fosholLv = (ListView) view.findViewById(R.id.fosholLv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, district);
        classLv.setAdapter(adapter);

        classLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni0);
                        fosholLv.setAdapter(adapter0);
                        fosholLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                //-----ALERT DIALOG PART 2-----
                                curPos = pos;
                                ImageView img = (ImageView) dialogLayout.findViewById(R.id.goProDialogImage);
                                img.setImageResource(imgList[pos]);
                                dialog.show();
                                //------------------------
                            }
                        });
                        break;
                    case 1:
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni1);
                        fosholLv.setAdapter(adapter1);
                        break;
                    case 2:
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni2);
                        fosholLv.setAdapter(adapter2);
                        break;
                    case 3:
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni3);
                        fosholLv.setAdapter(adapter3);
                        break;
                    case 4:
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni4);
                        fosholLv.setAdapter(adapter4);
                        break;
                    case 5:
                        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni5);
                        fosholLv.setAdapter(adapter5);
                        break;
                    case 6:
                        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni6);
                        fosholLv.setAdapter(adapter6);
                        break;
                    case 7:
                        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni7);
                        fosholLv.setAdapter(adapter7);
                        break;

                }
            }
        });



        return view;
    }


}
