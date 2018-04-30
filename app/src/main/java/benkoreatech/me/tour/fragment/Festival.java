package benkoreatech.me.tour.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import benkoreatech.me.tour.R;
import benkoreatech.me.tour.interfaces.categoryInterface;

public class Festival extends Fragment  {

    DatePicker finishDate,startDate;
    TextView Go;
    String startD,endD;
    categoryInterface  categoryInterface;

    public Festival(benkoreatech.me.tour.interfaces.categoryInterface categoryInterface) {
        this.categoryInterface = categoryInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.festival, container, false);
        finishDate=(DatePicker) view.findViewById(R.id.finishDate);
        startDate=(DatePicker) view.findViewById(R.id.startDate);
        Go=(TextView) view.findViewById(R.id.go);
        Date c = Calendar.getInstance().getTime();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(c);
        final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        startD = df.format(c);
        endD=df.format(c);
        startDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar calendar=new GregorianCalendar();
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.YEAR,year);
                startD=df.format(calendar.getTime());
            }
        });
        finishDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar calendar=new GregorianCalendar();
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.YEAR,year);
                endD=df.format(calendar.getTime());
            }
        });
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoryInterface!=null){
                  categoryInterface.setStartEndDate(startD,endD);
                }
            }
        });
        return view;
    }



}
