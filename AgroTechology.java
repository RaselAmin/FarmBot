package com.androquad.shobujekattor.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androquad.shobujekattor.R;

/**
 * Created by sujon on 06/04/2016.
 */
public class AgroTechology extends Fragment {
ListView classLv, projuktiLv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agro_technology, container, false);

        final String[] district = {"কীটতত্ত্ব","কৃষি যন্ত্রপাতি","পাহাড়ি কৃষি","বীজ প্রযুক্তি","শস্য সংগ্রহোত্তর প্রযুক্তি","সরেজমিন গবেষণা","সেচ ও পানি ব্যবস্থাপনা"};
        final String[] sreni0 = {"পেস্টিসাইড টক্সিলজি","সমন্বিত বালাইদমন ব্যবস্থাপনা"};
        final String[] sreni1 = {"আম পাড়া ও শোধন যন্ত্র","আলু উত্তোলন ও গ্রেডিং যন্ত্র","ইনক্লাইন্ড প্লেট সিডার","বেড প্লান্টার",
                "শস্য কর্তন যন্ত্র","শস্য ঝাড়াই যন্ত্র","শস্য মাড়াই যন্ত্র","সেচ ও পানি ব্যবস্থাপনা","হাই স্পীড রোটারি টিলার","হাইব্রিড ড্রায়ার"};
        final String[] sreni2 = {"ঝাড় শিম","তলোয়ার শিম","ধনিয়া পাতা","বসত বাড়িতে সবজি চাষ","লেবু"};
        final String[] sreni3 = {"গম","চালকুমড়া","চীনাবাদাম","ভুট্টা"};
        final String[] sreni4 = {"শস্য সংগ্রহোত্তর প্রযুক্তি"};
        final String[] sreni5 = {"ফারমিং সিস্টেম প্রযুক্তি","বাগান এর নকশা","বারি চুলা","সবজি চাষ এ সার এর ব্যবহার","সবজির উৎপাদন প্রযুক্তি","সবজির পোকা দমন"};
        final String[] sreni6 = {"অগভীর নলকূপ এর পানি বিভাজন যন্ত্র","কাঁঠাল চাষ এ সেচ পদ্ধতি","ফারটিগেসান পদ্ধতি","ফারটিগেসান পদ্ধতি টমেটো চাষ","সার মিশ্রিত পানি সেচ পদ্ধতি","সেচ বাস্থাপনা"};



        classLv = (ListView) view.findViewById(R.id.classLv);
        projuktiLv = (ListView) view.findViewById(R.id.projuktiLv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, district);
        classLv.setAdapter(adapter);

        classLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni0);
                        projuktiLv.setAdapter(adapter0);
                        break;
                    case 1:
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni1);
                        projuktiLv.setAdapter(adapter1);
                        break;
                    case 2:
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni2);
                        projuktiLv.setAdapter(adapter2);
                        break;
                    case 3:
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni3);
                        projuktiLv.setAdapter(adapter3);
                        break;
                    case 4:
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni4);
                        projuktiLv.setAdapter(adapter4);
                        break;
                    case 5:
                        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni5);
                        projuktiLv.setAdapter(adapter5);
                        break;
                    case 6:
                        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sreni6);
                        projuktiLv.setAdapter(adapter6);
                        break;


                }
            }
        });


        return view;
    }
}
