package benkoreatech.me.tour;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import benkoreatech.me.tour.adapter.CommentsAdapter;
import benkoreatech.me.tour.interfaces.placeInfoInterface;
import benkoreatech.me.tour.objects.Comments;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.objects.LocationBasedItem;
import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.detailCommonItem;
import benkoreatech.me.tour.objects.detailImageItem;
import benkoreatech.me.tour.objects.detailIntroItem;
import benkoreatech.me.tour.utils.CommentVolley;
import benkoreatech.me.tour.utils.Favorite_Volley;
import benkoreatech.me.tour.utils.LanguageSharedPreference;
import benkoreatech.me.tour.utils.SigninPreference;
import benkoreatech.me.tour.utils.Utils;
import benkoreatech.me.tour.utils.detailCommonVolley;
import benkoreatech.me.tour.utils.detailImageVolley;
import benkoreatech.me.tour.utils.detailIntroVolley;

public class PlaceInfo extends DialogFragment implements placeInfoInterface, OnMapReadyCallback, View.OnClickListener {
    Toolbar toolbar;
    TextView overview, title, direction, telephone, website, information_center, open_date, close_date, park_facility, available_season, available_time, experience_type, experience_age, accomcount;
    TextView used_time_culture, closed_date_culture, parking_facility, parking_fee, usefee, spendtime, scale, admitted_person, info_center;
    TextView open_period, closed_period, sport_parking_facility, sport_parking_fee, available_time_sport, reservation_guide, admission_fee, scale_sport, sport_experience_age, sport_persons, sport_info_center;
    TextView accomendation_capacity, benikia, checkin_time, checkout_time, check_cooking, foodplace, goodstay, hanok, infocenterlodging;
    TextView parkinglodging, pickup, roomcount, reservationlodging, reservartion_url, roomtype, scale_lodging, sub_facility;
    TextView fairday, infocentershopping, opendateshopping, opentime, parkingshopping, restdateshopping, restroom, saleitem, salesshopping, shopguide;
    detailCommonVolley detailCommonVolley;
    detailIntroVolley detailIntroVolley;
    detailImageVolley detailImageVolley;
    detailCommonItem detailCommonItem;
    RelativeLayout tourist, culture, sports, stay, shopping;
    LocationBasedItem locationBasedItem;
    areaBasedItem areaBasedItem;
    CarouselView carouselView;
    SupportMapFragment mapFragment;
    ImageView close, image;
    GoogleMap mMap;
    LatLng latLng;
    int code;
    EditText title_comment, comment;
    RatingBar ratingBar_comment;
    Button add_ur_comment;
    RecyclerView recyclerView_comments;
    CommentsAdapter commentsAdapter;
    CommentVolley commentVolley;
    float[] Markercolors;
    boolean isFavorite;
    SigninPreference signinPreference;
    Favorite_Volley favorite_volley;
    ImageView favorite;
    CardView card_view_third;
    LanguageSharedPreference languageSharedPreference;
    List<detailImageItem> detailImageItems = new ArrayList<>();
    TextView touristText;
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Log.d("DetailImage", "Here " + detailImageItems.get(position).getOriginimgurl());
            Picasso.with(getActivity().getApplicationContext()).load(detailImageItems.get(position).getOriginimgurl()).fit().centerCrop().into(imageView);

            //imageView.setImageResource(sampleImages[position]);
        }
    };

    public static PlaceInfo newInstance(String title) {
        PlaceInfo frag = new PlaceInfo();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        frag.setCancelable(false);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_place_info, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        image = (ImageView) view.findViewById(R.id.image);
        favorite = (ImageView) view.findViewById(R.id.favorite);
        detailImageVolley = new detailImageVolley(getActivity(), this);
        fairday = (TextView) view.findViewById(R.id.fairday);
        close = (ImageView) view.findViewById(R.id.close);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        overview = (TextView) view.findViewById(R.id.overview);
        title = (TextView) view.findViewById(R.id.title);
        direction = (TextView) view.findViewById(R.id.direction);
        telephone = (TextView) view.findViewById(R.id.telephone);
        website = (TextView) view.findViewById(R.id.website);
        open_date = (TextView) view.findViewById(R.id.open_date);
        close_date = (TextView) view.findViewById(R.id.closed_date);
        touristText = (TextView) view.findViewById(R.id.TouristText);
        card_view_third=(CardView) view.findViewById(R.id.card_view_third);

//        infocentershopping = (TextView) view.findViewById(R.id.infocentershopping);
//        opendateshopping = (TextView) view.findViewById(R.id.opendateshopping);
//        opentime = (TextView) view.findViewById(R.id.opentime);
//        parkingshopping = (TextView) view.findViewById(R.id.parkingshopping);
//        restdateshopping = (TextView) view.findViewById(R.id.restdateshopping);
//        restroom = (TextView) view.findViewById(R.id.restroom);
//        saleitem = (TextView) view.findViewById(R.id.scaleitem);
//        salesshopping = (TextView) view.findViewById(R.id.saleshopping);
//        shopguide = (TextView) view.findViewById(R.id.shopguide);
//        open_period = (TextView) view.findViewById(R.id.open_period);
//        closed_period = (TextView) view.findViewById(R.id.closed_period);
//        shopping = (RelativeLayout) view.findViewById(R.id.shopping);
//        sport_parking_facility = (TextView) view.findViewById(R.id.sport_parking_facility);
//        sport_parking_fee = (TextView) view.findViewById(R.id.sport_parking_fee);
//        available_time_sport = (TextView) view.findViewById(R.id.available_time_sport);
//        reservation_guide = (TextView) view.findViewById(R.id.reservation_guide);
//        admission_fee = (TextView) view.findViewById(R.id.admission_fee);
//        scale_sport = (TextView) view.findViewById(R.id.scale_sport);
//        foodplace = (TextView) view.findViewById(R.id.foodplace);
//        roomtype = (TextView) view.findViewById(R.id.roomtype);
//        scale_lodging = (TextView) view.findViewById(R.id.scalelodging);
//        sub_facility = (TextView) view.findViewById(R.id.subfacility);
//        goodstay = (TextView) view.findViewById(R.id.goodstay);
//        hanok = (TextView) view.findViewById(R.id.hanok);
//        reservartion_url = (TextView) view.findViewById(R.id.reservationurl);
//        parkinglodging = (TextView) view.findViewById(R.id.parkinglodging);
//        pickup = (TextView) view.findViewById(R.id.pickup);
//        roomcount = (TextView) view.findViewById(R.id.roomcount);
//        reservationlodging = (TextView) view.findViewById(R.id.reservationlodging);
//        infocenterlodging = (TextView) view.findViewById(R.id.infocenterlodging);
//        accomendation_capacity = (TextView) view.findViewById(R.id.accomendation_capacity);
//        benikia = (TextView) view.findViewById(R.id.benikia);
//        checkin_time = (TextView) view.findViewById(R.id.checkintime);
//        checkout_time = (TextView) view.findViewById(R.id.checkouttime);
//        check_cooking = (TextView) view.findViewById(R.id.chkcooking);
//        sport_experience_age = (TextView) view.findViewById(R.id.sport_experience_age);
//        sport_persons = (TextView) view.findViewById(R.id.sport_persons);
//        sport_info_center = (TextView) view.findViewById(R.id.sport_info_center);
//        used_time_culture = (TextView) view.findViewById(R.id.used_time_culture);
//        closed_date_culture = (TextView) view.findViewById(R.id.closed_date_culture);
//        parking_facility = (TextView) view.findViewById(R.id.parking_facility);
//        parking_fee = (TextView) view.findViewById(R.id.parking_fee);
//        usefee = (TextView) view.findViewById(R.id.usefee);
//        spendtime = (TextView) view.findViewById(R.id.spendtime);
//        scale = (TextView) view.findViewById(R.id.scale);
//        admitted_person = (TextView) view.findViewById(R.id.admitted_person);
//        info_center = (TextView) view.findViewById(R.id.info_center);
//        culture = (RelativeLayout) view.findViewById(R.id.culture);
//        sports = (RelativeLayout) view.findViewById(R.id.sports);
//        stay = (RelativeLayout) view.findViewById(R.id.stay);
        add_ur_comment = (Button) view.findViewById(R.id.add_your_comment);
        title_comment = (EditText) view.findViewById(R.id.comment_title);
        comment = (EditText) view.findViewById(R.id.comment_comment);
        ratingBar_comment = (RatingBar) view.findViewById(R.id.comment_rating);
        recyclerView_comments = (RecyclerView) view.findViewById(R.id.comments);
        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_location);
        mapFragment.getMapAsync(this);
        signinPreference = new SigninPreference(getActivity());
        favorite_volley = new Favorite_Volley(getActivity(), this);
        detailCommonVolley = new detailCommonVolley(getActivity(), this);
        detailIntroVolley = new detailIntroVolley(getActivity(), this);
        languageSharedPreference = new LanguageSharedPreference(getActivity());
        carouselView.setImageListener(imageListener);
        // here we are checking if its already added to favorite or not
        // we prepare url + parameters and send api call (favorite volley)
        String url_Check_if_favorite = "";
        if (locationBasedItem != null) {
            url_Check_if_favorite = Constants.check_if_favorite + "?name=" + signinPreference.getUserEmail() + "&title=" + locationBasedItem.getTitle().trim();
        } else if (areaBasedItem != null) {
            url_Check_if_favorite = Constants.check_if_favorite + "?name=" + signinPreference.getUserEmail() + "&title=" + areaBasedItem.getTitle().trim();
        }
        favorite_volley.fetchData(url_Check_if_favorite, 3);


        Markercolors = Utils.getMarkerColors();

        String imageUrls = Constants.base_url + languageSharedPreference.getLanguage() + Constants.detailedImage + "?" + Constants.serviceKey + "=" + Constants.server_key + Constants.remain_url;
        String URL = Constants.base_url + languageSharedPreference.getLanguage() + Constants.detailCommon + "?" + Constants.serviceKey + "=" + Constants.server_key + Constants.remain_url;
        String detailIntroURL = Constants.base_url + languageSharedPreference.getLanguage() + Constants.detailIntro + "?" + Constants.serviceKey + "=" + Constants.server_key + Constants.remain_url;
//        Log.d("HeroJongi", "DetailIntro URL " + detailIntroURL);


        if (locationBasedItem != null && locationBasedItem.getTitle() != null && !locationBasedItem.getTitle().equalsIgnoreCase("")) {
            toolbar.setTitle(locationBasedItem.getTitle());
            imageUrls += locationBasedItem.getContentid() + "&imageYN=Y" + Constants.json;
            URL += locationBasedItem.getContentid() + "&defaultYN=Y&addrinfoYN=Y&overviewYN=Y&transGuideYN=Y&addrinfoYN=Y" + Constants.json;
            detailIntroURL += locationBasedItem.getContentid() + "&contentTypeId=" + locationBasedItem.getContenttypeid() + "&introYN=Y" + Constants.json;

        }
        if (areaBasedItem != null && areaBasedItem.getTitle() != null && !areaBasedItem.getTitle().equalsIgnoreCase("")) {
            toolbar.setTitle(areaBasedItem.getTitle());
            imageUrls += areaBasedItem.getContentid() + "&imageYN=Y" + Constants.json;
            URL += areaBasedItem.getContentid() + "&defaultYN=Y&addrinfoYN=Y&overviewYN=Y&transGuideYN=Y&addrinfoYN=Y" + Constants.json;
            detailIntroURL += areaBasedItem.getContentid() + "&contentTypeId=" + areaBasedItem.getContenttypeid() + "&introYN=Y" + Constants.json;
        }
        detailImageVolley.fetchData(imageUrls);
        detailCommonVolley.fetchData(URL);
        detailIntroVolley.fetchData(detailIntroURL);

        commentVolley = new CommentVolley(getActivity(), this);

        fetchPlaceComments();

        close.setOnClickListener(this);
        favorite.setOnClickListener(this);
        add_ur_comment.setOnClickListener(this);
    }

    public void setLocationInfo(String data) {
        if (data != null) {
            Gson gson = new Gson();
            locationBasedItem = gson.fromJson(data, LocationBasedItem.class);
        }

    }

    public void setLocationInfoItem(areaBasedItem areaBasedItem) {
        this.areaBasedItem = areaBasedItem;
    }

    @Override
    public void details(detailCommonItem detailCommonItem) {
        if (detailCommonItem != null) {
            this.detailCommonItem = detailCommonItem;
            if (detailCommonItem.getOverview() != null && !detailCommonItem.getOverview().equalsIgnoreCase("'")) {
                String Overview = detailCommonItem.getOverview();
                SpannableString text = new SpannableString(Html.fromHtml(Overview));
                overview.setText(text, TextView.BufferType.SPANNABLE);
            } else {
                overview.setVisibility(View.GONE);
            }

            title.setText(getType(detailCommonItem.getContenttypeid()));
            if (detailCommonItem.getTel() != null && !detailCommonItem.getTel().equalsIgnoreCase("")) {
                String tel = "<b> Tel: </b>" + detailCommonItem.getTel();
                SpannableString text = new SpannableString(Html.fromHtml(tel));
                telephone.setText(text, TextView.BufferType.SPANNABLE);
            } else {
                telephone.setVisibility(View.GONE);
            }
            if (detailCommonItem.getHomepage() != null && !detailCommonItem.getHomepage().equalsIgnoreCase("")) {
                String _website = "<b> Website: </b>" + detailCommonItem.getHomepage();
                website.setText(Html.fromHtml(_website));
                website.setLinkTextColor(Color.BLUE);
                website.setMovementMethod(LinkMovementMethod.getInstance());

            } else {
                website.setVisibility(View.GONE);
            }
            if (detailCommonItem.getDirections() != null && !detailCommonItem.getDirections().equalsIgnoreCase("")) {
                String _direction = "<b> Direction: </b>" + detailCommonItem.getDirections();
                SpannableString text = new SpannableString(Html.fromHtml(_direction));
                direction.setText(text, TextView.BufferType.SPANNABLE);
            } else {
                direction.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void detailsIntro(detailIntroItem detailIntroItem) {
        Log.d("SPECA", " DetialIntro " + detailIntroItem.toString());
        if (detailIntroItem != null) {
            String id = "";
            if (locationBasedItem != null) {
                id = locationBasedItem.getContenttypeid();
            }
            if (areaBasedItem != null) {
                id = areaBasedItem.getContenttypeid();
            }
            // 79 80 75 78
            String touristinfo = "";
            int Int_id = Integer.parseInt(id);
            Log.d("SPECA", " Code " + Int_id);
            if(Int_id==76){
                Log.d("SPECA"," HERE ");
                String informationcenter = detailIntroItem.getInfocenter();
                String openday = detailIntroItem.getOpendate();
                String closedday = detailIntroItem.getRestdate();
                String Park = detailIntroItem.getParking();
                String useseason = detailIntroItem.getUseseason();
                String useTime = detailIntroItem.getUsetime();
                String expage = detailIntroItem.getExpagerange();
                String expguide = detailIntroItem.getExpguide();
                String accomaccount = detailIntroItem.getAccomcount();

                Log.d("SPECA"," opendate "+openday+" closeday "+closedday+" park "+Park+" useseason "+useseason+" useTime "+useTime+" exppage "+expage+" expguide "+expguide+" accom "+accomaccount);

                if (openday != null && !openday.equalsIgnoreCase("")) {
                    touristinfo += "<b> Opening day: </b> " + openday;
                }
                if (closedday != null && !closedday.equalsIgnoreCase("")) {
                    touristinfo += "<br/><br/><b> Closed day: </b> " + closedday;
                }
                if (Park != null && !Park.equalsIgnoreCase("")) {
                    touristinfo += "<br/><br/><b> Parking Facility: </b> " + Park;
                }
                if(useseason!=null && !useseason.equalsIgnoreCase("")){
                  touristinfo=  "<br/><br/><b> Available Season: </b> " + useseason;
                }
                if(useTime!=null && !useTime.equalsIgnoreCase("")){
                    touristinfo="<br/><br/><b> Available Time: </b> " + useTime;
                }
                if(expage!=null && !expage.equalsIgnoreCase("")){
                    touristinfo+="<br/><br/><b> Experience age: </b> "+expage;
                }
                if(expguide!=null && !expguide.equalsIgnoreCase("")){
                    touristinfo+="<br/><br/><b> Experience guide: </b> "+expguide;
                }
                if(accomaccount!=null && !accomaccount.equalsIgnoreCase("")){
                    touristinfo+="<br/><br/><b> Number of persons to be admitted: </b> "+accomaccount;
                }
                if (informationcenter != null && !informationcenter.equalsIgnoreCase("")) {
                    touristinfo += "<br/><br/><b> Information Center:</b><br/>" + informationcenter;
                }
            }
             else if(Int_id==78){
                String useTime=detailIntroItem.getUsetime();
                String restDay=detailIntroItem.getRestdate();
                String parkingCulture=detailIntroItem.getParkingculture();
                String parking_Fee=detailIntroItem.getParkingfee();
                String useFee=detailIntroItem.getUsefee();
                String spendTime=detailIntroItem.getSpendtime();
                String scale=detailIntroItem.getScale();
                String accomculture=detailIntroItem.getAccomcountculture();
                String infocenter=detailIntroItem.getInfocenterculture();

                Log.d("SPECA",useTime+" "+restDay+" "+parkingCulture+" "+parking_Fee+" "+useFee+" "+spendTime+" "+scale+" "+accomculture+" "+infocenter);

                if (useTime!=null && !useTime.equalsIgnoreCase("")) {
                   touristinfo+= "<b> Available time: </b> " +useTime;
                }
                if (restDay != null && !restDay.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> Closed day: </b> " + restDay;
                }
                if (parkingCulture!= null && !parkingCulture.equalsIgnoreCase("")) {
                  touristinfo+= "<br/><b> Parking facility: </b> " + parkingCulture;
                }
                if (parking_Fee!= null && !parking_Fee.equalsIgnoreCase("")) {
                   touristinfo+= "<br/><b> Parking fee: </b> " +parking_Fee;
                }
                if (useFee != null && !useFee.equalsIgnoreCase("")) {
                   touristinfo+= "<br/><b> Use fee: </b> " + useFee;
                }
                if (spendTime!= null && !spendTime.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> Spend time: </b> " + detailIntroItem.getSpendtime();
                }
                if (scale!= null && !scale.equalsIgnoreCase("")) {
                 touristinfo+= "<br/><b> Scale: </b> " + scale;

                }
                if (accomculture!= null && !accomculture.equalsIgnoreCase("")) {
                   touristinfo+= "<br/><b> Number of admitted persons: </b> " + accomculture;
                }
                if (infocenter!= null && !infocenter.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> Information center: </b><br/>" +infocenter;
                }
            }
            if(Int_id==75){
                 String openPeriod=detailIntroItem.getOpenperiod();
                 String restDate=detailIntroItem.getRestdateleports();
                 String parking=detailIntroItem.getParkingleports();
                 String parkingFee=detailIntroItem.getParkingfeeleports();
                 String useTime=detailIntroItem.getUsetimeleports();
                 String reservation=detailIntroItem.getReservation();
                 String useFee=detailIntroItem.getUsefeeleports();
                 String scale=detailIntroItem.getScaleleports();
                String expagerangesports=detailIntroItem.getExpagerangeleports();
                String accomleports=detailIntroItem.getAccomcountleports();
                String infoCenter=detailIntroItem.getInfocenterleports();

                if (openPeriod!= null && !openPeriod.equalsIgnoreCase("")) {
                    touristinfo += "<b> Open period: </b> " + openPeriod+"<br/>";
                }
                if (restDate!= null && !restDate.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> Closed period: </b> " + restDate+"<br/>";
                }
                if (parking != null && !parking.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> Parking facility: </b> "  +parking+"<br/>";
                }
                if (parkingFee != null && !parkingFee.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Parking fee: </b> " + parkingFee+"<br/>";
                }
                if (useTime!= null && !useTime.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> Available time: </b> " + useTime+"<br/>";
                }
                if (reservation!= null && !reservation.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Reservation Guide: </b> " +reservation+"<br/>";

                }
                if (useFee!= null && !useFee.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Admission fee: </b> " +useFee+"<br/>";
                }
                if (scale!= null && !scale.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Scale: </b> " + scale+"<br/>";
                }
                if (expagerangesports != null && !expagerangesports.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Experience age: </b> " + expagerangesports+"<br/>";
                }
                if (accomleports != null && !accomleports.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Number of addmitted persons: </b> " +accomleports+"<br/>";
                }
                if (infoCenter!= null && !infoCenter.equalsIgnoreCase("")) {
                  touristinfo+= "<br/><b> Information center: </b><br/>" + infoCenter+"<br/>";
                }
            }
            if(Int_id==79){

                String fairDay=detailIntroItem.getFairday();
                String checkCooking=detailIntroItem.getInfocentershopping();
                String opendate=detailIntroItem.getOpendateshopping();
                String openTime=detailIntroItem.getOpentime();
                String parking=detailIntroItem.getParkingshopping();
                String restdate=detailIntroItem.getRestdateshopping();
                String restRoom=detailIntroItem.getRestroom();
                String saleItem=detailIntroItem.getSaleitem();
                String scale=detailIntroItem.getScaleshopping();
                String shopGuide=detailIntroItem.getShopguide();

                if (fairDay!= null && !fairDay.equalsIgnoreCase("")) {
                   touristinfo += "<b> Fair day: </b> " + fairDay+"<br/>";
                }
                if (checkCooking!= null && !checkCooking.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Information center: </b> " + checkCooking+"<br/>";
                }
                if (opendate!= null && !opendate.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> Opening day: </b> " + opendate+"<br/>";
                }
                if (openTime!= null && !openTime.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> Business hour: </b> " +openTime+"<br/>";
                }
                if (parking != null && !parking.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Parking facility: </b> " + parking+"<br/>";
                }
                if (restdate!= null && restdate.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Closed day: </b> " + restdate+"<br/>";
                }
                if (restRoom!= null && !restRoom.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Rest room information: </b> " + restRoom+"<br/>";
                }
                if (saleItem!= null && !saleItem.equalsIgnoreCase("")) {
                  touristinfo+= "<br/><b> Sale Item: </b> " + saleItem+"<br/>";
                }
                if (scale!= null && !scale.equalsIgnoreCase("")) {
                  touristinfo += "<br/><b> Scale: </b> " + scale+"<br/>";
                }
                if (shopGuide!= null && !shopGuide.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Store guidance: </b><br/>" +shopGuide+"<br/>";
                }
            }
            if(Int_id==80){
                String accomCount=detailIntroItem.getAccomcountlodging();
                String benikia=detailIntroItem.getBenikia();
                String checkInTime=detailIntroItem.getCheckintime();
                String checkOutTime=detailIntroItem.getCheckouttime();
                String checkcooking=detailIntroItem.getChkcooking();
                String foodplace= detailIntroItem.getFoodplace();
                String goodStay=detailIntroItem.getGoodstay();
                String hanok=detailIntroItem.getHanok();
                String infoCenter=detailIntroItem.getInfocenterlodging();
                String parking=detailIntroItem.getParkinglodging();
                String pickup=detailIntroItem.getPickup();
                String roomCount=detailIntroItem.getRoomcount();
                String reservation=detailIntroItem.getReservationlodging();
                String reservationURL=detailIntroItem.getReservationurl();
                String roomType=detailIntroItem.getRoomtype();
                String scale=detailIntroItem.getScale();
                String subfacility=detailIntroItem.getSubfacility();

                if (accomCount!= null && !accomCount.equalsIgnoreCase("")) {
                    touristinfo += "<b> Accomendation capacity: </b> " + accomCount+"<br/>";
                }
                if (benikia!= null && !benikia.equalsIgnoreCase("") && !benikia.equalsIgnoreCase("0")) {
                    touristinfo += "<br/><b> Benikia: </b> " + benikia+"<br/>";
                }
                if (checkInTime!= null && !checkInTime.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Check in time: </b> " + checkInTime+"<br/>";
                }
                if (checkOutTime!= null && !checkOutTime.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Check out time: </b> " + checkOutTime+"<br/>";
                }
                if (checkcooking!= null && !checkcooking.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Cooking available check : </b>" + checkcooking+"<br/>";
                }
                if (foodplace!= null && !foodplace.equalsIgnoreCase("")) {
                   touristinfo+= "<br/><b> Food place: </b> " + foodplace+"<br/>";
                }
                if (goodStay!= null && !goodStay.equalsIgnoreCase("")) {
                   touristinfo+= "<br/><b> Good stay: </b> " + goodStay+"<br/>";
                }
                if (hanok!= null && !hanok.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Hanok: </b> " + hanok+"<br/>";
                }
                if (infoCenter!= null && !infoCenter.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Information center: </b> " +infoCenter+"<br/>";
                }
                if (parking!= null && !parking.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Parking facility: </b> " + parking+"<br/>";
                }
                if (pickup!= null && !pickup.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Pickup service: </b> " + pickup+"<br/>";
                }
                if (roomCount!= null && !roomCount.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Number of guest room: </b> " + roomCount+"<br/>";
                }
                if (reservation!= null && !reservation.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Reservation guide: </b> " + reservation+"<br/>";
                }
                if (reservationURL!= null && !reservationURL.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Reservartion guide homepage: </b> " + reservationURL+"<br/>";
                }
                if (roomType!= null && !roomType.equalsIgnoreCase("")) {
                  touristinfo += "<br/><b> Guest room type: </b> " +roomType+"<br/>";
                }
                if (scale!= null && !scale.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> Scale: </b> " + scale+"<br/>";
                }
                if (subfacility!= null && !subfacility.equalsIgnoreCase("")) {
                   touristinfo += "<br/><b> Sub facility: </b> " + subfacility+"<br/>";
                }
            }
            if(Int_id==82){
                String firstMenu=detailIntroItem.getFirstmenu();
                String infoCenterFood=detailIntroItem.getInfocenterfood();
                String openDateFood=detailIntroItem.getOpendatefood();
                String openTimeFood=detailIntroItem.getOpentimefood();
                String parkingFood=detailIntroItem.getParkingfood();
                String reservationFood=detailIntroItem.getReservationfood();
                String restDateFood=detailIntroItem.getRestdatefood();
                String scaleFood=detailIntroItem.getScalefood();
                String seat=detailIntroItem.getSeat();
                String smoking=detailIntroItem.getSmoking();
                String treatMenu=detailIntroItem.getTreatmenu();

                if(firstMenu!=null && !firstMenu.equalsIgnoreCase("")){
                    touristinfo+="<b> Main dishes: </b> "+firstMenu+"<br/>";
                }
                if(openDateFood!=null && !openDateFood.equalsIgnoreCase("")){
                    touristinfo+="<br/> <br> Open date: </b>"+openDateFood+"<br/>";
                }
                if(openTimeFood!=null && !openTimeFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b> Business hours: </b> "+openTimeFood+"<br/>";
                }
                if(parkingFood!=null && !parkingFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Parking facility: </b> "+parkingFood+"<br/>";
                }
                if(reservationFood!=null && !reservationFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b> Reservation guide: </b> "+reservationFood+"<br/>";
                }
                if(restDateFood!=null && !reservationFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b> Closed day: </b> "+restDateFood+"<br/>";
                }
                if(scaleFood!=null && !scaleFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Scale: </b> "+scaleFood+"<br/>";
                }
                if(seat!=null && !seat.equalsIgnoreCase("")){
                    touristinfo+="<br/><b> Seat number: </b> "+seat+"<br/>";
                }
                if(smoking!=null && !smoking.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Smoking</b> "+smoking+"<br/>";
                }
                if(treatMenu!=null && !treatMenu.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Treat menu: </b> "+treatMenu+"<br/>";
                }
                if(infoCenterFood!=null && !infoCenterFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Information center <b> "+infoCenterFood+"<br/>";
                }
            }
            if(Int_id==77){
                String checkCreditcard=detailIntroItem.getChkcreditcardtraffic();
                String conven=detailIntroItem.getConven();
                String disableFacility=detailIntroItem.getDisablefacility();
                String infoCenter=detailIntroItem.getForeignerinfocenter();
                String infoCenterTraffic=detailIntroItem.getInfocentertraffic();
                String mainRoute=detailIntroItem.getMainroute();
                String operationTimeTraffic=detailIntroItem.getOperationtimetraffic();
                String parkingTraffic=detailIntroItem.getParkingtraffic();
                String restRoomTrafic=detailIntroItem.getRestroomtraffic();
                String shipInfo=detailIntroItem.getShipinfo();

                if(checkCreditcard!=null && !checkCreditcard.equalsIgnoreCase("")){
                    touristinfo+="<b>Credit card: </b>"+checkCreditcard+"<br/>";
                }
                if(conven!=null && !conven.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Facilities: </b>"+conven+"<br/>";
                }
                if(disableFacility!=null && !disableFacility.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Disable facility: </b>"+disableFacility+"<br/>";
                }
                if(infoCenter!=null && !infoCenter.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Information center: </b>"+infoCenter+"<br/>";
                }
                if(infoCenterTraffic!=null && !infoCenterTraffic.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Contact us: </b>"+infoCenterTraffic+"<br/>";
                }
                if(mainRoute!=null && !mainRoute.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Main route: </b>"+mainRoute+"<br/>";
                }
                if(operationTimeTraffic!=null && !operationTimeTraffic.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Operation time: </b>"+operationTimeTraffic+"<br/>";
                }
                if(parkingTraffic!=null && !parkingTraffic.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Parking facility: </b>"+parkingTraffic+"<br/>";
                }
                if(restRoomTrafic!=null && !restRoomTrafic.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Rest room: </b>"+restRoomTrafic+"<br/>";
                }
                if(shipInfo!=null && !shipInfo.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>Ship information: </b>"+shipInfo+"<br/>";
                }
            }
            if(touristinfo!=null && !touristinfo.equalsIgnoreCase("")) {
                card_view_third.setVisibility(View.VISIBLE);
                SpannableString text = new SpannableString(Html.fromHtml(touristinfo));

                touristText.setText(text);
            }
            else{
                card_view_third.setVisibility(View.GONE);
            }
           }
        }


    @Override
    public void setImages(final List<detailImageItem> detailImageItems) {
        Log.d("DetailImage", " Array size " + detailImageItems.size());
        this.detailImageItems = detailImageItems;
        carouselView.setPageCount(detailImageItems.size());
    }

    @Override
    public void setImagesIfNoExist() {
        String imageUrl = null;
        if (locationBasedItem != null) {
            imageUrl = locationBasedItem.getFirstimage();
        } else if (areaBasedItem != null) {
            imageUrl = areaBasedItem.getFirstimage();
        }
        Log.d("HeroJongi", " image " + imageUrl);
        image.setVisibility(View.VISIBLE);
        carouselView.setVisibility(View.GONE);
        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            Picasso.with(getActivity()).load(imageUrl).fit().into(image);
        }
    }

    @Override
    public void addToFavorite() {
        isFavorite = true;
        favorite.setBackgroundResource(R.drawable.starfull);
    }

    @Override
    public void RemoveFromFavorite() {
        isFavorite = false;
        favorite.setBackgroundResource(R.drawable.star);
    }

    @Override
    public void setListofComments(List<Comments> comments) {
        Collections.sort(comments, new Comparator<Comments>() {
            @Override
            public int compare(Comments o1, Comments o2) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                int compare = 0;
                try {
                    Date date1 = format.parse(o1.getDate());
                    Date date2 = format.parse(o2.getDate());
                    compare = date2.compareTo(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return compare;
            }
        });
        if (commentsAdapter == null) {
            commentsAdapter = new CommentsAdapter(comments, getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
            recyclerView_comments.setLayoutManager(linearLayoutManager);
            recyclerView_comments.setItemAnimator(new DefaultItemAnimator());
            recyclerView_comments.setAdapter(commentsAdapter);
        } else {
            commentsAdapter.Refresh(comments);
        }
    }

    @Override
    public void fetchPlaceComments() {
        String url = Constants.get_all_comments;
        comment.setText("");
        title_comment.setText("");
        ratingBar_comment.setRating(0);
        if (areaBasedItem != null) {
            url += "?mapX=" + areaBasedItem.getMapx() + "&mapY=" + areaBasedItem.getMapy();
        } else if (locationBasedItem != null) {
            url += "?mapX=" + locationBasedItem.getMapx() + "&mapY=" + locationBasedItem.getMapy();
        }
        Log.d("HeroJongi", " URL get comments " + url);
        commentVolley.fetchData(url, 1);
    }

    public String getType(String value) {
        String type = "";
        int Value = Integer.parseInt(value);
        // 79 80 75 78
        switch (Value) {
            case 76:
                type = "Nature";
                break;
            case 78:
                type = "Culture/Art/History";
                break;
            case 75:
                type = "Leisure/Sports";
                break;
            case 80:
                type = "Accomendation";
                break;
            case 79:
                type = "Shopping";
                break;
            case 77:
                type = "Transportation";
                break;
            case 82:
                type = "Cuisine";
                break;
        }

        return type;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);

        // locate marker on map
        if (locationBasedItem != null) {
            latLng = new LatLng(Double.parseDouble(locationBasedItem.getMapy()), Double.parseDouble(locationBasedItem.getMapx()));
            code = Integer.parseInt(locationBasedItem.getContenttypeid());
        } else if (areaBasedItem != null) {
            latLng = new LatLng(Double.parseDouble(areaBasedItem.getMapy()), Double.parseDouble(areaBasedItem.getMapx()));
            code = Integer.parseInt(areaBasedItem.getContenttypeid());
        }
        float color = Markercolors[new Random().nextInt(Markercolors.length)];

        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(color)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(10).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("HeroJongi", " on stop");
        if (mMap != null) {
            mapFragment.onDestroyView();
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.map_location)).commit();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_your_comment:
                String Comment = comment.getText().toString();
                String commentTitle = title_comment.getText().toString();
                String rating = String.valueOf(ratingBar_comment.getRating());
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                String today = formatter.format(currentTime);
                String mapX = "", mapY = "";
                if (areaBasedItem != null) {
                    mapX = areaBasedItem.getMapx();
                    mapY = areaBasedItem.getMapy();
                } else if (locationBasedItem != null) {
                    mapX = locationBasedItem.getMapx();
                    mapY = locationBasedItem.getMapy();
                }
                String url = Constants.post_comment + "?name=" + signinPreference.getUsername() + "&date=" + today + "&commentTitle=" + commentTitle + "&comment=" + Comment + "&rate=" + rating + "&mapX=" + mapX + "&mapY=" + mapY;
                Log.d("HeroJongi", "here url post comment " + url);
                commentVolley.fetchData(url, 2);
                break;
            case R.id.favorite:
                if (!isFavorite) {
                    String Url = Constants.add_to_favorite + "?name=" + signinPreference.getUserEmail();
                    if (areaBasedItem != null) {
                        Url += "&mapX=" + areaBasedItem.getMapx() + "&mapY=" + areaBasedItem.getMapy() + "&contentTypeId=" + areaBasedItem.getContenttypeid() + "&title=" + areaBasedItem.getTitle();
                    } else if (locationBasedItem != null) {
                        Url += "&mapX=" + locationBasedItem.getMapx() + "&mapY=" + locationBasedItem.getMapy() + "&contentTypeId=" + locationBasedItem.getContenttypeid() + "&title=" + locationBasedItem.getTitle();
                    }
                    Log.d("HeroJongi", "Add To Favorite URL is " + Url);
                    favorite_volley.fetchData(Url, 0);
                } else {
                    String Url = Constants.remove_favorite + "?name=" + signinPreference.getUserEmail();
                    if (areaBasedItem != null) {
                        Url += "&title=" + areaBasedItem.getTitle();
                    } else if (locationBasedItem != null) {
                        Url += "&title=" + locationBasedItem.getTitle();
                    }
                    Log.d("HeroJongi", "Add To Favorite URL is " + Url);
                    favorite_volley.fetchData(Url, 1);
                }
                break;
            case R.id.close:
                dismiss();
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                //do your stuff
                Log.d("HeroJongi", " on back ");
                dismiss();
            }
        };
    }
}
