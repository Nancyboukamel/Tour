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
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.interfaces.placeInfoInterface;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.objects.LocationBasedItem;
import benkoreatech.me.tour.objects.detailCommonItem;
import benkoreatech.me.tour.objects.detailImageItem;
import benkoreatech.me.tour.objects.detailIntroItem;
import benkoreatech.me.tour.utils.detailCommonVolley;
import benkoreatech.me.tour.utils.detailImageVolley;
import benkoreatech.me.tour.utils.detailIntroVolley;

public class PlaceInfo extends AppCompatActivity implements placeInfoInterface {
Toolbar toolbar;
TextView overview,title,direction,telephone,website,information_center,open_date,close_date,park_facility,available_season,available_time,experience_type,experience_age,accomcount;
TextView used_time_culture,closed_date_culture,parking_facility,parking_fee,usefee,spendtime,scale,admitted_person,info_center;
TextView open_period,closed_period,sport_parking_facility,sport_parking_fee,available_time_sport,reservation_guide,admission_fee,scale_sport,sport_experience_age,sport_persons,sport_info_center;
TextView accomendation_capacity,benikia,checkin_time,checkout_time,check_cooking,foodplace,goodstay,hanok,infocenterlodging;
TextView parkinglodging,pickup,roomcount,reservationlodging,reservartion_url,roomtype,scale_lodging,sub_facility;
TextView fairday,infocentershopping,opendateshopping,opentime,parkingshopping,restdateshopping,restroom,saleitem,salesshopping,shopguide;
detailCommonVolley detailCommonVolley;
detailIntroVolley detailIntroVolley;
detailImageVolley detailImageVolley;
detailCommonItem detailCommonItem;
RelativeLayout tourist,culture,sports,stay,shopping;
LocationBasedItem locationBasedItem;
CarouselView carouselView;
List<detailImageItem> detailImageItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        carouselView=(CarouselView) findViewById(R.id.carouselView);
        fairday=(TextView) findViewById(R.id.fairday);
        infocentershopping=(TextView) findViewById(R.id.infocentershopping);
        opendateshopping=(TextView) findViewById(R.id.opendateshopping);
        opentime=(TextView) findViewById(R.id.opentime);
        parkingshopping=(TextView)findViewById(R.id.parkingshopping);
        restdateshopping=(TextView) findViewById(R.id.restdateshopping);
        restroom=(TextView) findViewById(R.id.restroom);
        saleitem=(TextView) findViewById(R.id.scaleitem);
        salesshopping=(TextView) findViewById(R.id.saleshopping);
        shopguide=(TextView) findViewById(R.id.shopguide);
        open_period=(TextView) findViewById(R.id.open_period);
        closed_period=(TextView) findViewById(R.id.closed_period);
        shopping=(RelativeLayout) findViewById(R.id.shopping);
        sport_parking_facility=(TextView) findViewById(R.id.sport_parking_facility);
        sport_parking_fee=(TextView) findViewById(R.id.sport_parking_fee);
        available_time_sport=(TextView) findViewById(R.id.available_time_sport);
        reservation_guide=(TextView) findViewById(R.id.reservation_guide);
        admission_fee=(TextView) findViewById(R.id.admission_fee);
        scale_sport=(TextView) findViewById(R.id.scale_sport);
        foodplace=(TextView) findViewById(R.id.foodplace);
        roomtype=(TextView) findViewById(R.id.roomtype);
        scale_lodging=(TextView) findViewById(R.id.scalelodging);
        sub_facility=(TextView) findViewById(R.id.subfacility);
        goodstay=(TextView) findViewById(R.id.goodstay);
        hanok=(TextView) findViewById(R.id.hanok);
        reservartion_url=(TextView) findViewById(R.id.reservationurl);
        parkinglodging=(TextView) findViewById(R.id.parkinglodging);
        pickup=(TextView) findViewById(R.id.pickup);
        roomcount=(TextView) findViewById(R.id.roomcount);
        reservationlodging=(TextView) findViewById(R.id.reservationlodging);
        infocenterlodging=(TextView) findViewById(R.id.infocenterlodging);
        accomendation_capacity=(TextView) findViewById(R.id.accomendation_capacity);
        benikia=(TextView) findViewById(R.id.benikia);
        checkin_time=(TextView) findViewById(R.id.checkintime);
        checkout_time=(TextView) findViewById(R.id.checkouttime);
        check_cooking=(TextView) findViewById(R.id.chkcooking);
        sport_experience_age=(TextView) findViewById(R.id.sport_experience_age);
        sport_persons=(TextView) findViewById(R.id.sport_persons);
        sport_info_center=(TextView) findViewById(R.id.sport_info_center);
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
        stay=(RelativeLayout) findViewById(R.id.stay);
        detailCommonVolley=new detailCommonVolley(this,this);
        detailIntroVolley=new detailIntroVolley(this,this);
        detailImageVolley=new detailImageVolley(this,this);
        String data=getIntent().getStringExtra("data");
        carouselView.setImageListener(imageListener);
        if(data!=null){
            Gson gson=new Gson();
            locationBasedItem=gson.fromJson(data,LocationBasedItem.class);
            Log.d("HeroJongi","Picture is "+locationBasedItem.getFirstimage());
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
            String imageUrls="http://api.visitkorea.or.kr/openapi/service/rest/EngService/detailImage?serviceKey=9opMOuXLGj2h16CybYD9T5qTds4376qAZO4VG9qNuHKrm1d%2FfCPfUoBPDOkfQiZKB%2BidiHynuWwbnVUHgrinJw%3D%3D&numOfRows=10&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId="+locationBasedItem.getContentid()+"&imageYN=Y"+Constants.json;
            detailImageVolley.fetchData(imageUrls);
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
                   // experience age ............ expagerangeleports
                   if(detailIntroItem.getExpagerangeleports()!=null && !detailIntroItem.getExpagerangeleports().equalsIgnoreCase("")){
                       String time="<b> Experience age: </b>"+detailIntroItem.getExpagerangeleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       sport_experience_age.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       sport_experience_age.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getAccomcountleports()!=null && !detailIntroItem.getAccomcountleports().equalsIgnoreCase("")){
                       String time="<b> Number of addmitted persons: </b>"+detailIntroItem.getAccomcountleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       sport_persons.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       sport_persons.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getInfocenterleports()!=null && !detailIntroItem.getInfocenterleports().equalsIgnoreCase("")){
                       String time="<b> Information center: </b>"+detailIntroItem.getInfocenterleports();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       sport_info_center.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       sport_info_center.setVisibility(View.GONE);
                   }
                   break;
               case 80:
                   stay.setVisibility(View.VISIBLE);
                   if(detailIntroItem.getAccomcountlodging()!=null && !detailIntroItem.getAccomcountlodging().equalsIgnoreCase("")){
                       String time="<b> Accomendation capacity: </b>"+detailIntroItem.getAccomcountlodging();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       accomendation_capacity.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       accomendation_capacity.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getBenikia()!=null && !detailIntroItem.getBenikia().equalsIgnoreCase("") && !detailIntroItem.getBenikia().equalsIgnoreCase("0")){
                       String time="<b> Benikia: </b>"+detailIntroItem.getAccomcountlodging();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       benikia.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       benikia.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getCheckintime()!=null && !detailIntroItem.getCheckintime().equalsIgnoreCase("")){
                       String time="<b> Check in time: </b>"+detailIntroItem.getCheckintime();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       checkin_time.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       checkin_time.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getCheckouttime()!=null && !detailIntroItem.getCheckouttime().equalsIgnoreCase("")){
                       String time="<b> Check out time: </b>"+detailIntroItem.getCheckouttime();
                       SpannableString text = new SpannableString(Html.fromHtml(time));
                       checkout_time.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       checkout_time.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getChkcooking()!=null && !detailIntroItem.getChkcooking().equalsIgnoreCase("")){
                       String checkcooking="<b> Cooking available check : </b>"+detailIntroItem.getChkcooking();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                      check_cooking.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       check_cooking.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getFoodplace()!=null && !detailIntroItem.getFoodplace().equalsIgnoreCase("")){
                       String checkcooking="<b> Food place: </b>"+detailIntroItem.getFoodplace();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       foodplace.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else {
                       foodplace.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getGoodstay()!=null && !detailIntroItem.getGoodstay().equalsIgnoreCase("") && !detailIntroItem.getGoodstay().equalsIgnoreCase("0")){
                       String checkcooking="<b> Good stay: </b>"+detailIntroItem.getGoodstay();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       goodstay.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       goodstay.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getHanok()!=null && !detailIntroItem.getHanok().equalsIgnoreCase("") && !detailIntroItem.getHanok().equalsIgnoreCase("0")){
                       String checkcooking="<b> Hanok: </b>"+detailIntroItem.getHanok();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       hanok.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else {
                       hanok.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getInfocenterlodging()!=null && !detailIntroItem.getInfocenterlodging().equalsIgnoreCase("")){
                       String checkcooking="<b> Information center: </b>"+detailIntroItem.getInfocenterlodging();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       infocenterlodging.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       infocenterlodging.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getParkinglodging()!=null && !detailIntroItem.getParkinglodging().equalsIgnoreCase("")){
                       String checkcooking="<b> Parking facility: </b>"+detailIntroItem.getParkinglodging();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       parkinglodging.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       parkinglodging.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getPickup()!=null && !detailIntroItem.getPickup().equalsIgnoreCase("")){
                       String checkcooking="<b> Pickup service: </b>"+detailIntroItem.getPickup();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       pickup.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       pickup.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getRoomcount()!=null && !detailIntroItem.getRoomcount().equalsIgnoreCase("")){
                       String checkcooking="<b> Number of guest room: </b>"+detailIntroItem.getRoomcount();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                      roomcount.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       roomcount.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getReservationlodging()!=null && !detailIntroItem.getReservationlodging().equalsIgnoreCase("")){
                       String checkcooking="<b> Reservation guide: </b>"+detailIntroItem.getReservationlodging();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       reservationlodging.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       reservationlodging.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getReservationurl()!=null && !detailIntroItem.getReservationurl().equalsIgnoreCase("")){
                       String checkcooking="<b> Reservartion guide homepage: </b>"+detailIntroItem.getReservationurl();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       reservartion_url.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                      reservartion_url.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getRoomtype()!=null && !detailIntroItem.getRoomtype().equalsIgnoreCase("")){
                       String checkcooking="<b> Guest room type: </b>"+detailIntroItem.getRoomtype();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       roomtype.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       roomtype.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getScale()!=null && !detailIntroItem.getScale().equalsIgnoreCase("")){
                       String checkcooking="<b> Scale: </b>"+detailIntroItem.getScale();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       scale.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       scale.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getSubfacility()!=null && !detailIntroItem.getSubfacility().equalsIgnoreCase("")){
                       String checkcooking="<b> Sub facility: </b>"+detailIntroItem.getSubfacility();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       sub_facility.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       sub_facility.setVisibility(View.GONE);
                   }
                   break;
               case 79:
                   shopping.setVisibility(View.VISIBLE);
                   if(detailIntroItem.getFairday()!=null && !detailIntroItem.getFairday().equalsIgnoreCase("")){
                       String checkcooking="<b> Fair day: </b>"+detailIntroItem.getFairday();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       fairday.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       fairday.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getInfocentershopping()!=null && !detailIntroItem.getInfocentershopping().equalsIgnoreCase("")){
                       String checkcooking="<b> Information center: </b>"+detailIntroItem.getInfocentershopping();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       infocentershopping.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       infocentershopping.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getOpendateshopping()!=null && !detailIntroItem.getOpendateshopping().equalsIgnoreCase("")){
                       String checkcooking="<b> Opening day: </b>"+detailIntroItem.getOpendateshopping();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       opendateshopping.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       opendateshopping.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getOpentime()!=null && !detailIntroItem.getOpentime().equalsIgnoreCase("")){
                       String checkcooking="<b> Business hour: </b>"+detailIntroItem.getOpentime();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       opentime.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       opentime.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getParkingshopping()!=null && !detailIntroItem.getParkingshopping().equalsIgnoreCase("")){
                       String checkcooking="<b> Parking facility: </b>"+detailIntroItem.getParkingshopping();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       parkingshopping.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       parkingshopping.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getRestdateshopping()!=null && detailIntroItem.getRestdateshopping().equalsIgnoreCase("")){
                       String checkcooking="<b> Closed day: </b>"+detailIntroItem.getRestdateshopping();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       restdateshopping.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       restdateshopping.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getRestroom()!=null && !detailIntroItem.getRestroom().equalsIgnoreCase("")){
                       String checkcooking="<b> Rest room information: </b>"+detailIntroItem.getRestroom();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       restroom.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       restroom.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getSaleitem()!=null && !detailIntroItem.getSaleitem().equalsIgnoreCase("")){
                       String checkcooking="<b> Sale Item: </b>"+detailIntroItem.getRestroom();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       saleitem.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       saleitem.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getScaleshopping()!=null && !detailIntroItem.getScaleshopping().equalsIgnoreCase("")){
                       String checkcooking="<b> Scale: </b>"+detailIntroItem.getScaleshopping();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       salesshopping.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       salesshopping.setVisibility(View.GONE);
                   }
                   if(detailIntroItem.getShopguide()!=null && !detailIntroItem.getShopguide().equalsIgnoreCase("")){
                       String checkcooking="<b> Store guidance: </b>"+detailIntroItem.getShopguide();
                       SpannableString text = new SpannableString(Html.fromHtml(checkcooking));
                       shopguide.setText(text, TextView.BufferType.SPANNABLE);
                   }
                   else{
                       shopguide.setVisibility(View.GONE);
                   }
                   break;
           }

       }
    }

    @Override
    public void setImages(final List<detailImageItem>detailImageItems) {
        Log.d("DetailImage"," Array size "+detailImageItems.size());
        this.detailImageItems=detailImageItems;
        carouselView.setPageCount(detailImageItems.size());

    //    carouselView.setPageCount(detailImageItems.size());
//        carouselView.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                Log.d("DetailImage"," Image "+detailImageItems.get(position).getOriginimgurl());
//               Picasso.with(PlaceInfo.this).load(detailImageItems.get(position).getOriginimgurl()).into(imageView);
//            }
//        });
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Log.d("DetailImage","Here "+detailImageItems.get(position).getOriginimgurl());
            Picasso.with(getApplicationContext()).load(detailImageItems.get(position).getSmallimageurl()).fit().centerCrop().into(imageView);

            //imageView.setImageResource(sampleImages[position]);
        }
    };


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
