package benkoreatech.me.tour;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import benkoreatech.me.tour.interfaces.placeInfoInterface;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.objects.LocationBasedItem;
import benkoreatech.me.tour.objects.detailCommonItem;
import benkoreatech.me.tour.objects.detailIntroItem;
import benkoreatech.me.tour.utils.detailCommonVolley;
import benkoreatech.me.tour.utils.detailIntroVolley;

public class PlaceInfo extends AppCompatActivity implements placeInfoInterface {
ImageView place_image;
Toolbar toolbar;
TextView overview,title,direction,telephone,website,information_center,open_date,close_date,park_facility,available_season,available_time,experience_type,experience_age,accomcount;
TextView used_time_culture,closed_date_culture,parking_facility,parking_fee,usefee,spendtime,scale,admitted_person,info_center;
TextView open_period,closed_period,sport_parking_facility,sport_parking_fee,available_time_sport,reservation_guide,attend_person,admission_fee,scale_sport,sport_experience_age,sport_persons,sport_info_center;
detailCommonVolley detailCommonVolley;
detailIntroVolley detailIntroVolley;
detailCommonItem detailCommonItem;
RelativeLayout tourist,culture,sports;
LocationBasedItem locationBasedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        open_period=(TextView) findViewById(R.id.open_period);
        closed_period=(TextView) findViewById(R.id.closed_period);
        sport_parking_facility=(TextView) findViewById(R.id.sport_parking_facility);
        sport_parking_fee=(TextView) findViewById(R.id.sport_parking_fee);
        available_time_sport=(TextView) findViewById(R.id.available_time_sport);
        reservation_guide=(TextView) findViewById(R.id.reservation_guide);
        attend_person=(TextView) findViewById(R.id.attend_person);
        admission_fee=(TextView) findViewById(R.id.admission_fee);
        scale_sport=(TextView) findViewById(R.id.scale_sport);
        sport_experience_age=(TextView) findViewById(R.id.sport_experience_age);
        sport_persons=(TextView) findViewById(R.id.sport_persons);
        sport_info_center=(TextView) findViewById(R.id.sport_info_center);
        place_image=(ImageView) findViewById(R.id.backdrop);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        overview=(TextView) findViewById(R.id.overview);
        title=(TextView) findViewById(R.id.title);
        direction=(TextView) findViewById(R.id.direction);
        telephone=(TextView) findViewById(R.id.telephone);
        website=(TextView) findViewById(R.id.website);
        open_date=(TextView) findViewById(R.id.open_date);
        close_date=(TextView) findViewById(R.id.closed_date);
        park_facility=(TextView) findViewById(R.id.park_facility);
        information_center=(TextView) findViewById(R.id.information_center);
        available_season=(TextView) findViewById(R.id.available_season);
        available_time=(TextView) findViewById(R.id.available_time);
        experience_age=(TextView) findViewById(R.id.experience_age);
        experience_type=(TextView) findViewById(R.id.experience_guide);
        accomcount=(TextView) findViewById(R.id.accomcount);
        tourist=(RelativeLayout) findViewById(R.id.tourist);
        used_time_culture=(TextView) findViewById(R.id.used_time_culture);
        closed_date_culture=(TextView) findViewById(R.id.closed_date_culture);
        parking_facility=(TextView) findViewById(R.id.parking_facility);
        parking_fee=(TextView) findViewById(R.id.parking_fee);
        usefee=(TextView) findViewById(R.id.usefee);
        spendtime=(TextView) findViewById(R.id.spendtime);
        scale=(TextView) findViewById(R.id.scale);
        admitted_person=(TextView) findViewById(R.id.admitted_person);
        info_center=(TextView) findViewById(R.id.info_center);
        culture=(RelativeLayout) findViewById(R.id.culture);
        sports=(RelativeLayout) findViewById(R.id.sports);
        detailCommonVolley=new detailCommonVolley(this,this);
        detailIntroVolley=new detailIntroVolley(this,this);
        String data=getIntent().getStringExtra("data");
        if(data!=null){
            Gson gson=new Gson();
            locationBasedItem=gson.fromJson(data,LocationBasedItem.class);
            Log.d("HeroJongi","Picture is "+locationBasedItem.getFirstimage());
            if(locationBasedItem.getFirstimage()!=null && !locationBasedItem.getFirstimage().equalsIgnoreCase("")){
                Picasso.with(this).load(locationBasedItem.getFirstimage()).resize(350, 300).onlyScaleDown().centerCrop().into(place_image);
            }
            if(locationBasedItem.getTitle()!=null && !locationBasedItem.getTitle().equalsIgnoreCase("")){
                toolbar.setTitle(locationBasedItem.getTitle());
            }
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

            String URL="http://api.visitkorea.or.kr/openapi/service/rest/EngService/detailCommon?serviceKey=9opMOuXLGj2h16CybYD9T5qTds4376qAZO4VG9qNuHKrm1d%2FfCPfUoBPDOkfQiZKB%2BidiHynuWwbnVUHgrinJw%3D%3D&numOfRows=10&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId="+locationBasedItem.getContentid()+"&defaultYN=Y&addrinfoYN=Y&overviewYN=Y&transGuideYN=Y&addrinfoYN=Y"+ Constants.json;
            detailCommonVolley.fetchData(URL);
            String detailIntroURL="http://api.visitkorea.or.kr/openapi/service/rest/EngService/detailIntro?serviceKey=9opMOuXLGj2h16CybYD9T5qTds4376qAZO4VG9qNuHKrm1d%2FfCPfUoBPDOkfQiZKB%2BidiHynuWwbnVUHgrinJw%3D%3D&numOfRows=10&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId="+locationBasedItem.getContentid()+"&contentTypeId="+locationBasedItem.getContenttypeid()+"&introYN=Y"+Constants.json;
            Log.d("HeroJongi","DetailIntro URL "+detailIntroURL);
            detailIntroVolley.fetchData(detailIntroURL);
        }

    }

    @Override
    public void details(detailCommonItem detailCommonItem) {
        if (detailCommonItem != null) {
            this.detailCommonItem=detailCommonItem;
            if (detailCommonItem.getOverview() != null && !detailCommonItem.getOverview().equalsIgnoreCase("'")) {
                String Overview = detailCommonItem.getOverview();
                SpannableString text = new SpannableString(Html.fromHtml(Overview));
                overview.setText(text, TextView.BufferType.SPANNABLE);
            }
            else {
                overview.setVisibility(View.GONE);
            }

            title.setText(getType(detailCommonItem.getContenttypeid()));
            if (detailCommonItem.getTel() != null && !detailCommonItem.getTel().equalsIgnoreCase("")) {
                String tel = "<b> Tel: </b>" + detailCommonItem.getTel();
                SpannableString text = new SpannableString(Html.fromHtml(tel));
                telephone.setText(text, TextView.BufferType.SPANNABLE);
            }
            else{
                telephone.setVisibility(View.GONE);
            }
            if (detailCommonItem.getHomepage() != null && !detailCommonItem.getHomepage().equalsIgnoreCase("")) {
                String _website = "<b> Website: </b>" + detailCommonItem.getHomepage();
                website.setText(Html.fromHtml(_website));
                website.setLinkTextColor(Color.BLUE);
                website.setMovementMethod(LinkMovementMethod.getInstance());

            }
            else {
                website.setVisibility(View.GONE);
            }
            if (detailCommonItem.getDirections() != null && !detailCommonItem.getDirections().equalsIgnoreCase("")) {
                String _direction = "<b> Direction: </b>" + detailCommonItem.getDirections();
                SpannableString text = new SpannableString(Html.fromHtml(_direction));
                direction.setText(text, TextView.BufferType.SPANNABLE);
            }
            else {
                direction.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void detailsIntro(detailIntroItem detailIntroItem) {
       if(detailIntroItem!=null){
           String id=locationBasedItem.getContenttypeid();
           Log.d("HeroJongi","Here "+id);
           int Int_id=Integer.parseInt(id);
           switch(Int_id){
               case 76:
                   Log.d("HeroJongi","Infocenter "+ detailIntroItem.getInfocenter());
                   tourist.setVisibility(View.VISIBLE);
                   if(detailIntroItem.getInfocenter()!=null && !detailIntroItem.getInfocenter().equalsIgnoreCase("")){
                       String _information_center="<b> Information Center:</b> <br/> <br/>"+detailIntroItem.getInfocenter();
                       SpannableString text = new SpannableString(Html.fromHtml(_information_center));
                       information_center.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       information_center.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getOpendate()!=null && !detailIntroItem.getOpendate().equalsIgnoreCase("")){
                       String openDate="<b> Opening day: </b>"+detailIntroItem.getOpendate();
                       SpannableString text = new SpannableString(Html.fromHtml(openDate));
                       open_date.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       open_date.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getRestdate()!=null && !detailIntroItem.getRestdate().equalsIgnoreCase("")){
                       String ClosedDate="<b> Closed day: </b>"+detailIntroItem.getRestdate();
                       SpannableString text = new SpannableString(Html.fromHtml(ClosedDate));
                       close_date.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       close_date.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getParking()!=null && !detailIntroItem.getParking().equalsIgnoreCase("")){
                       String ClosedDate="<b> Parking Facility: </b>"+detailIntroItem.getParking();
                       SpannableString text = new SpannableString(Html.fromHtml(ClosedDate));
                       park_facility.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       park_facility.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getUseseason()!=null && !detailIntroItem.getUseseason().equalsIgnoreCase("")){
                       String availableseason="<b> Available Season: </b>"+detailIntroItem.getUseseason();
                       SpannableString text = new SpannableString(Html.fromHtml(availableseason));
                       available_season.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       available_season.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getUsetime()!=null && !detailIntroItem.getUsetime().equalsIgnoreCase("")){
                       String availabletime="<b> Available Time: </b>"+detailIntroItem.getUsetime();
                       SpannableString text = new SpannableString(Html.fromHtml(availabletime));
                       available_time.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       available_time.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getExpagerange()!=null && !detailIntroItem.getExpagerange().equalsIgnoreCase("")){
                       String experienceage="<b> Experience age: </b>"+detailIntroItem.getExpagerange();
                       SpannableString text = new SpannableString(Html.fromHtml(experienceage));
                       experience_age.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       experience_age.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getExpguide()!=null && !detailIntroItem.getExpguide().equalsIgnoreCase("")){
                       String experienceguide="<b> Experience guide: </b>"+detailIntroItem.getExpguide();
                       SpannableString text = new SpannableString(Html.fromHtml(experienceguide));
                       experience_type.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       experience_type.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getAccomcount()!=null && !detailIntroItem.getAccomcount().equalsIgnoreCase("")){
                       String nbperson="<b> Number of persons to be admitted: </b>"+detailIntroItem.getAccomcount();
                       SpannableString text = new SpannableString(Html.fromHtml(nbperson));
                       accomcount.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       accomcount.setVisibility(View.GONE);
                   }
                   break;

               case 78:
                   culture.setVisibility(View.VISIBLE);
                if(detailIntroItem.getUsetime()!=null && !detailIntroItem.getUsetime().equalsIgnoreCase("")){
                    String time="<b> Available time: </b>"+detailIntroItem.getUsetime();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    used_time_culture.setText(text, TextView.BufferType.SPANNABLE);
                }
                else {
                    used_time_culture.setVisibility(View.GONE);
                }
                if(detailIntroItem.getRestdateculture()!=null && !detailIntroItem.getRestdateculture().equalsIgnoreCase("")){
                    String time="<b> Closed day: </b>"+detailIntroItem.getRestdateculture();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    closed_date_culture.setText(text, TextView.BufferType.SPANNABLE);
                }
                else{
                    closed_date_culture.setVisibility(View.GONE);
                }
                if(detailIntroItem.getParkingculture()!=null && !detailIntroItem.getParkingculture().equalsIgnoreCase("")){
                    String time="<b> Parking facility: </b>"+detailIntroItem.getParkingculture();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    parking_facility.setText(text, TextView.BufferType.SPANNABLE);
                }
                else{
                    parking_facility.setVisibility(View.GONE);
                }
                if(detailIntroItem.getParkingfee()!=null && !detailIntroItem.getParkingfee().equalsIgnoreCase("")){
                    String time="<b> Parking fee: </b>"+detailIntroItem.getParkingfee();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    parking_fee.setText(text, TextView.BufferType.SPANNABLE);
                }
                else{
                    parking_fee.setVisibility(View.GONE);
                }
                if(detailIntroItem.getUsefee()!=null && !detailIntroItem.getUsefee().equalsIgnoreCase("")){
                    String time="<b> Use fee: </b>"+detailIntroItem.getUsefee();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    usefee.setText(text, TextView.BufferType.SPANNABLE);
                }
                else {
                    usefee.setVisibility(View.GONE);
                }
                if(detailIntroItem.getSpendtime()!=null && !detailIntroItem.getSpendtime().equalsIgnoreCase("")){
                    String time="<b> Spend time: </b>"+detailIntroItem.getSpendtime();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    spendtime.setText(text, TextView.BufferType.SPANNABLE);
                }
                else {
                    spendtime.setVisibility(View.GONE);
                }
                if(detailIntroItem.getScale()!=null && !detailIntroItem.getScale().equalsIgnoreCase("")){
                    String time="<b> Scale: </b>"+detailIntroItem.getScale();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    scale.setText(text, TextView.BufferType.SPANNABLE);
                }
                else{
                    scale.setVisibility(View.GONE);
                }
                if(detailIntroItem.getAccomcountculture()!=null && !detailIntroItem.getAccomcountculture().equalsIgnoreCase("")){
                    String time="<b> Number of admitted persons: </b>"+detailIntroItem.getAccomcountculture();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    admitted_person.setText(text, TextView.BufferType.SPANNABLE);
                }
                else {
                    admitted_person.setVisibility(View.GONE);
                }
                if(detailIntroItem.getInfocenterculture()!=null && !detailIntroItem.getInfocenterculture().equalsIgnoreCase("")){
                    String time="<b> Information center: </b>"+detailIntroItem.getInfocenterculture();
                    SpannableString text = new SpannableString(Html.fromHtml(time));
                    admitted_person.setText(text, TextView.BufferType.SPANNABLE);
                }
                else {
                    info_center.setVisibility(View.GONE);
                }
                   break;
               case 85:

                   break;
               case 75:
                   sports.setVisibility(View.VISIBLE);
                   if(detailIntroItem.getOpenperiod()!=null && !detailIntroItem.getOpenperiod().equalsIgnoreCase("")){
                       String time="<b> Open period: </b>"+detailIntroItem.getOpenperiod();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       open_period.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       open_period.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getRestdateleports()!=null && !detailIntroItem.getRestdateleports().equalsIgnoreCase("")){
                       String time="<b> Closed period: </b>"+detailIntroItem.getRestdateleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       closed_period.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       closed_period.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getParkingleports()!=null && !detailIntroItem.getParkingleports().equalsIgnoreCase("")){
                       String time="<b> Parking facility: </b>"+detailIntroItem.getParkingleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       sport_parking_facility.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       sport_parking_facility.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getParkingfeeleports()!=null && !detailIntroItem.getParkingfeeleports().equalsIgnoreCase("")){
                       String time="<b> Parking fee: </b>"+detailIntroItem.getParkingfeeleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       sport_parking_fee.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       sport_parking_fee.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getUsetimeleports()!=null && !detailIntroItem.getUsetimeleports().equalsIgnoreCase("")){
                       String time="<b> Available time: </b>"+detailIntroItem.getUsetimeleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       available_time_sport.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       available_time_sport.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getReservation()!=null && !detailIntroItem.getReservation().equalsIgnoreCase("")){
                       String time="<b> Reservation Guide: </b>"+detailIntroItem.getReservation();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       reservation_guide.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       reservation_guide.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getAccomcountleports()!=null && !detailIntroItem.getAccomcountleports().equalsIgnoreCase("")){
                       String time="<b> Number of admitted persons: </b>"+detailIntroItem.getAccomcountleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       attend_person.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       attend_person.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getUsefeeleports()!=null &&!detailIntroItem.getUsefeeleports().equalsIgnoreCase("")){
                       String time="<b> Admission fee: </b>"+detailIntroItem.getUsefeeleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       admission_fee.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else {
                       admission_fee.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getScaleleports()!=null &&!detailIntroItem.getScaleleports().equalsIgnoreCase("")){
                       String time="<b> Scale: </b>"+detailIntroItem.getScaleleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       scale_sport.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       scale_sport.setVisibility(View.GONE);
                   }
                   // experience age ............
                   break;
           }

       }
    }


    public String  getType(String value){
        String type="";
        int Value=Integer.parseInt(value);
        switch (Value) {
            case 76:
                type="Nature";
                break;
            case 78:
                type="Culture/Art/History";
                break;
            case 75:
                type="Leisure/Sports";
                break;
            case 80:
                type="Accomendation";
                break;
            case 79:
                type="Shopping";
                break;
            case 77:
                type="Transportation";
                break;
            case 82:
                type="Cuisine";
                break;
        }

        return type;
    }
}
