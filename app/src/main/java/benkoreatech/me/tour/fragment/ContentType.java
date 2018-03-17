package benkoreatech.me.tour.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.R;
import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.objects.categoryItem;


public class ContentType extends Fragment implements View.OnClickListener{

    AppCompatSpinner bigcategory,mediumSpinner,smallSpinner;
    List<categoryItem> bigItems=new ArrayList<>();
    List<categoryItem> mediumItems=new ArrayList<>();
    int code;
    TextView mainText,GO;
    categoryInterface categoryInterface;
    categoryItem BigItem,MediumItem,SmallItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contentid, container, false);
        bigcategory=(AppCompatSpinner) view.findViewById(R.id.spinner);
        mediumSpinner=(AppCompatSpinner) view.findViewById(R.id.mediumspinner);
        smallSpinner=(AppCompatSpinner) view.findViewById(R.id.smallspinner);
        mainText=(TextView) view.findViewById(R.id.mainText);
        GO=(TextView) view.findViewById(R.id.go);
        GO.setOnClickListener(this);
        return view;
    }

    public void FillBigSpinner(List<categoryItem> categoryItems, final int code, final categoryInterface categoryInterface){
        this.bigItems=categoryItems;
        this.code=code;
        this.categoryInterface=categoryInterface;
        categoryItems.add(0,new categoryItem("","",""));
        final List<String> BIGcategory=new ArrayList<>();
        for(categoryItem categoryItem1:categoryItems){
           BIGcategory.add(categoryItem1.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,BIGcategory);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        bigcategory.setAdapter(dataAdapter);
        bigcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BigItem=bigItems.get(position);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                if(categoryInterface!=null){
                    categoryInterface.onItemBigSelected(BigItem,code);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void FillMediumSpinner(List<categoryItem> categoryItems){
        this.mediumItems=categoryItems;
        categoryItems.add(0,new categoryItem("","",""));
        List<String> BIGcategory=new ArrayList<>();
        for(categoryItem categoryItem1:categoryItems){
            BIGcategory.add(categoryItem1.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,BIGcategory);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mediumSpinner.setAdapter(dataAdapter);

        mediumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               MediumItem= mediumItems.get(position);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                if(categoryInterface!=null){
                    categoryInterface.onItemMediumSelected(MediumItem,code);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void FillSmallSpinner(final List<categoryItem> categoryItems){
        categoryItems.add(0,new categoryItem("","",""));
        List<String> BIGcategory=new ArrayList<>();
        for(categoryItem categoryItem1:categoryItems){
            BIGcategory.add(categoryItem1.getName());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,BIGcategory);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smallSpinner.setAdapter(dataAdapter);
        smallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SmallItem=categoryItems.get(position);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setText(String text){
        mainText.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go:
                if(categoryInterface!=null){
                    categoryInterface.CloseSlider();
                    categoryInterface.PlotPins(BigItem,MediumItem,SmallItem,code);
                }
                break;
        }
    }
}
