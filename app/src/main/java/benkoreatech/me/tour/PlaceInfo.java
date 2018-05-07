package benkoreatech.me.tour;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
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

import java.net.URL;
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
import benkoreatech.me.tour.objects.Favorites;
import benkoreatech.me.tour.objects.FestivalItem;
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
    private static final int PERMISSION_PHONE_CALL = 200;
    Toolbar toolbar;
    TextView overview, title, direction, telephone, website,open_date, close_date;
    TextView fairday;
    detailCommonVolley detailCommonVolley;
    detailIntroVolley detailIntroVolley;
    detailImageVolley detailImageVolley;
    detailCommonItem detailCommonItem;
    LocationBasedItem locationBasedItem;
    areaBasedItem areaBasedItem;
    FestivalItem festivalItem;
    Favorites favoriteItem;
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
    NestedScrollView nestedScrollView;
    LanguageSharedPreference languageSharedPreference;
    List<detailImageItem> detailImageItems = new ArrayList<>();
    TextView touristText;
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Log.d("DetailImage", "Here " + detailImageItems.get(position).getOriginimgurl());
            Picasso.with(getActivity().getApplicationContext()).load(detailImageItems.get(position).getOriginimgurl()).fit().centerCrop().into(imageView);
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
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setImageListener(imageListener);

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
        add_ur_comment = (Button) view.findViewById(R.id.add_your_comment);
        title_comment = (EditText) view.findViewById(R.id.comment_title);
        comment = (EditText) view.findViewById(R.id.comment_comment);
        ratingBar_comment = (RatingBar) view.findViewById(R.id.comment_rating);
        recyclerView_comments = (RecyclerView) view.findViewById(R.id.comments);
        nestedScrollView=(NestedScrollView) view.findViewById(R.id.nested);
        mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_location);
        mapFragment.getMapAsync(this);
        signinPreference = new SigninPreference(getActivity());
        favorite_volley = new Favorite_Volley(getActivity(), this);
        detailCommonVolley = new detailCommonVolley(getActivity(), this);
        detailIntroVolley = new detailIntroVolley(getActivity(), this);
        languageSharedPreference = new LanguageSharedPreference(getActivity());

        String imageUrls = Constants.base_url + languageSharedPreference.getLanguage() + Constants.detailedImage + "?" + Constants.serviceKey + "=" + Constants.server_key + Constants.remain_url;
        String URL = Constants.base_url + languageSharedPreference.getLanguage() + Constants.detailCommon + "?" + Constants.serviceKey + "=" + Constants.server_key + Constants.remain_url;
        String detailIntroURL = Constants.base_url + languageSharedPreference.getLanguage() + Constants.detailIntro + "?" + Constants.serviceKey + "=" + Constants.server_key + Constants.remain_url;



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
        if(festivalItem!=null && festivalItem.getTitle()!=null && !festivalItem.getTitle().equalsIgnoreCase("")){
            toolbar.setTitle(festivalItem.getTitle());
            imageUrls += festivalItem.getContentid() + "&imageYN=Y" + Constants.json;
            URL += festivalItem.getContentid() + "&defaultYN=Y&addrinfoYN=Y&overviewYN=Y&transGuideYN=Y&addrinfoYN=Y" + Constants.json;
            detailIntroURL += festivalItem.getContentid() + "&contentTypeId=" + festivalItem.getContenttypeid() + "&introYN=Y" + Constants.json;
        }
        if(favoriteItem!=null && favoriteItem.getTitle()!=null && !favoriteItem.getTitle().equalsIgnoreCase("")){
            toolbar.setTitle(favoriteItem.getTitle());
            imageUrls += favoriteItem.getContenttypeid() + "&imageYN=Y" + Constants.json;
            URL += favoriteItem.getContenttypeid()  + "&defaultYN=Y&addrinfoYN=Y&overviewYN=Y&transGuideYN=Y&addrinfoYN=Y" + Constants.json;
            detailIntroURL += favoriteItem.getContenttypeid()  + "&contentTypeId=" +favoriteItem.getContenttypeid()+ "&introYN=Y" + Constants.json;
        }
        Log.d("NANCY"," Image URLS "+imageUrls);
        Log.d("NANCY"," URLS "+URL);
        Log.d("NANCY"," detail Intro URL "+detailIntroURL);
        detailImageVolley.fetchData(imageUrls);
        detailCommonVolley.fetchData(URL);
        detailIntroVolley.fetchData(detailIntroURL);

        String url_Check_if_favorite = "";
        if (locationBasedItem != null) {
            url_Check_if_favorite = Constants.check_if_favorite + "?name=" + signinPreference.getUserEmail() + "&title=" + locationBasedItem.getTitle().trim();
        } else if (areaBasedItem != null) {
            url_Check_if_favorite = Constants.check_if_favorite + "?name=" + signinPreference.getUserEmail() + "&title=" + areaBasedItem.getTitle().trim();
        }
        if(festivalItem!=null){
            url_Check_if_favorite = Constants.check_if_favorite + "?name=" + signinPreference.getUserEmail() + "&title=" + festivalItem.getTitle().trim();
        }
        if(favoriteItem!=null){
            url_Check_if_favorite = Constants.check_if_favorite + "?name=" + signinPreference.getUserEmail() + "&title=" + favoriteItem.getTitle().trim();
        }
        favorite_volley.fetchData(url_Check_if_favorite, 3);


        Markercolors = Utils.getMarkerColors();

        nestedScrollView.scrollTo(0, 0);
        title_comment.clearFocus();

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

    public void setLocationInfoItem(Object object) {
        if(object instanceof areaBasedItem) {
            this.areaBasedItem = (areaBasedItem) object;
        }
        if(object instanceof FestivalItem){
            this.festivalItem=(FestivalItem)object;
        }
        if(object instanceof Favorites){
            this.favoriteItem=(Favorites)object;
        }

    }

    @Override
    public void details(final detailCommonItem detailCommonItem) {
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
                String tel = "<b>"+getResources().getString(R.string.telephone)+"</b> " + detailCommonItem.getTel();
                SpannableString text = new SpannableString(Html.fromHtml(tel));
                telephone.setText(text, TextView.BufferType.SPANNABLE);
                telephone.setTextColor(getResources().getColor(R.color.blue));
                telephone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Log.d("Call", " its permission is granted");
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + detailCommonItem.getTel()));
                            startActivity(intent);
                        } else {
                            Log.d("Call", " its permission is not granted");
                            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSION_PHONE_CALL);
                        }
                    }
                });
            } else {
                telephone.setVisibility(View.GONE);
            }
            if (detailCommonItem.getHomepage() != null && !detailCommonItem.getHomepage().equalsIgnoreCase("")) {
                String _website = "<b>"+getResources().getString(R.string.website)+"</b> " + detailCommonItem.getHomepage();
                website.setText(Html.fromHtml(_website));
                website.setLinkTextColor(Color.BLUE);
                website.setMovementMethod(LinkMovementMethod.getInstance());

            } else {
                website.setVisibility(View.GONE);
            }
            if (detailCommonItem.getDirections() != null && !detailCommonItem.getDirections().equalsIgnoreCase("")) {
                String _direction = "<b>"+getResources().getString(R.string.direction)+" </b> " + detailCommonItem.getDirections();
                SpannableString text = new SpannableString(Html.fromHtml(_direction));
                direction.setText(text, TextView.BufferType.SPANNABLE);
            } else {
                direction.setVisibility(View.GONE);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
         if (requestCode == PERMISSION_PHONE_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + detailCommonItem.getTel()));
                    this.startActivity(intent);
                }

            }
        }

    }

    @Override
    public void detailsIntro(detailIntroItem detailIntroItem) {
        if (detailIntroItem != null) {
            String id = "";
            if (locationBasedItem != null) {
                id = locationBasedItem.getContenttypeid();
            }
            if (areaBasedItem != null) {
                id = areaBasedItem.getContenttypeid();
            }
            if(festivalItem!=null){
                id=festivalItem.getContenttypeid();
            }
            if(favoriteItem!=null){
                id=favoriteItem.getContenttypeid();
            }
            // 79 80 75 78
            String touristinfo = "";
            int Int_id = Integer.parseInt(id);
            Log.d("SPECA", " Code " + Int_id);
            if(Int_id==76 || Int_id==12){
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
                    touristinfo += "<b> "+getResources().getString(R.string.opening_day)+" </b> " + openday+"<br/>";
                }
                if (closedday != null && !closedday.equalsIgnoreCase("")) {
                    touristinfo += "<br/><br/><b>"+getResources().getString(R.string.closed_day)+" </b> " + closedday+"<br/>";
                }
                if (Park != null && !Park.equalsIgnoreCase("")) {
                    touristinfo += "<br/><br/><b>"+getResources().getString(R.string.parking_facility)+" </b> " + Park+"<br/>";
                }
                if(useseason!=null && !useseason.equalsIgnoreCase("")){
                    touristinfo=  "<br/><br/><b>"+getResources().getString(R.string.available_season)+" </b> " + useseason+"<br/>";
                }
                if(useTime!=null && !useTime.equalsIgnoreCase("")){
                    touristinfo="<br/><br/><b>"+getResources().getString(R.string.available_time)+" </b> " + useTime+"<br/>";
                }
                if(expage!=null && !expage.equalsIgnoreCase("")){
                    touristinfo+="<br/><br/><b>"+getResources().getString(R.string.experience_age)+"</b> "+expage+"<br/>";
                }
                if(expguide!=null && !expguide.equalsIgnoreCase("")){
                    touristinfo+="<br/><br/><b>"+getResources().getString(R.string.experience_guide)+" </b> "+expguide+"<br/>";
                }
                if(accomaccount!=null && !accomaccount.equalsIgnoreCase("")){
                    touristinfo+="<br/><br/><b>"+getResources().getString(R.string.number_of_person_to_be_admitted)+" </b> "+accomaccount+"<br/>";
                }
                if (informationcenter != null && !informationcenter.equalsIgnoreCase("")) {
                    touristinfo += "<br/><br/><b>"+getResources().getString(R.string.information_center)+"</b><br/>" + informationcenter+"<br/>";
                }
            }
            else if(Int_id==78 || Int_id==14){
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
                    touristinfo+= "<b>"+getResources().getString(R.string.available_time)+" </b> " +useTime+"<br/>";
                }
                if (restDay != null && !restDay.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> "+getResources().getString(R.string.closed_day)+" </b> " + restDay+"<br/>";
                }
                if (parkingCulture!= null && !parkingCulture.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> "+getResources().getString(R.string.parking_facility)+"</b> " + parkingCulture+"<br/>";
                }
                if (parking_Fee!= null && !parking_Fee.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> "+getResources().getString(R.string.parking_fee)+" </b> " +parking_Fee+"<br/>";
                }
                if (useFee != null && !useFee.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.use_fee)+" </b> " + useFee+"<br/>";
                }
                if (spendTime!= null && !spendTime.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> "+getResources().getString(R.string.spend_time)+" </b> " + detailIntroItem.getSpendtime()+"<br/>";
                }
                if (scale!= null && !scale.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b> "+getResources().getString(R.string.scale)+" </b> " + scale+"<br/>";

                }
                if (accomculture!= null && !accomculture.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.number_of_person_to_be_admitted)+"</b> " + accomculture+"<br/>";
                }
                if (infocenter!= null && !infocenter.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.information_center)+" </b><br/>" +infocenter+"<br/>";
                }
            }
            if(Int_id==75 || Int_id==28){
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
                    touristinfo += "<b>"+getResources().getString(R.string.open_period)+"</b> " + openPeriod+"<br/>";
                }
                if (restDate!= null && !restDate.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.closed_period)+"</b> " + restDate+"<br/>";
                }
                if (parking != null && !parking.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.parking_facility)+"</b> "  +parking+"<br/>";
                }
                if (parkingFee != null && !parkingFee.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.parking_fee)+"</b> " + parkingFee+"<br/>";
                }
                if (useTime!= null && !useTime.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.available_time)+"</b> " + useTime+"<br/>";
                }
                if (reservation!= null && !reservation.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.reservation_guide)+"</b> " +reservation+"<br/>";

                }
                if (useFee!= null && !useFee.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b> "+getResources().getString(R.string.admission_fee)+"</b> " +useFee+"<br/>";
                }
                if (scale!= null && !scale.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.scale)+"</b> " + scale+"<br/>";
                }
                if (expagerangesports != null && !expagerangesports.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.experience_age)+"</b> " + expagerangesports+"<br/>";
                }
                if (accomleports != null && !accomleports.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.number_of_person_to_be_admitted)+"</b> " +accomleports+"<br/>";
                }
                if (infoCenter!= null && !infoCenter.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.information_center)+"</b><br/>" + infoCenter+"<br/>";
                }
            }
            if(Int_id==79 || Int_id==38){

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
                    touristinfo += "<b>"+getResources().getString(R.string.fair_day)+"</b> " + fairDay+"<br/>";
                }
                if (checkCooking!= null && !checkCooking.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.information_center)+"</b> " + checkCooking+"<br/>";
                }
                if (opendate!= null && !opendate.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.open_date)+"</b> " + opendate+"<br/>";
                }
                if (openTime!= null && !openTime.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.business_hour)+"</b> " +openTime+"<br/>";
                }
                if (parking != null && !parking.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.parking_facility)+"</b> " + parking+"<br/>";
                }
                if (restdate!= null && restdate.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.closed_day)+"" + restdate+"<br/>";
                }
                if (restRoom!= null && !restRoom.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.restroom_information)+"</b> " + restRoom+"<br/>";
                }
                if (saleItem!= null && !saleItem.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.sale_item)+"</b> " + saleItem+"<br/>";
                }
                if (scale!= null && !scale.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.scale)+"</b> " + scale+"<br/>";
                }
                if (shopGuide!= null && !shopGuide.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.store_guidance)+"</b><br/>" +shopGuide+"<br/>";
                }
            }
            if(Int_id==80 || Int_id==32){
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
                    touristinfo += "<b>"+getResources().getString(R.string.accommodation_capacity)+"</b> " + accomCount+"<br/>";
                }
                if (benikia!= null && !benikia.equalsIgnoreCase("") && !benikia.equalsIgnoreCase("0")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.benikia)+"</b> " + benikia+"<br/>";
                }
                if (checkInTime!= null && !checkInTime.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.checkin_time)+"</b> " + checkInTime+"<br/>";
                }
                if (checkOutTime!= null && !checkOutTime.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.checkout_time)+"</b> " + checkOutTime+"<br/>";
                }
                if (checkcooking!= null && !checkcooking.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.cooking_available_check)+"</b>" + checkcooking+"<br/>";
                }
                if (foodplace!= null && !foodplace.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.food_place)+"</b> " + foodplace+"<br/>";
                }
                if (goodStay!= null && !goodStay.equalsIgnoreCase("")) {
                    touristinfo+= "<br/><b>"+getResources().getString(R.string.good_stay)+"</b> " + goodStay+"<br/>";
                }
                if (hanok!= null && !hanok.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.hanok)+"</b> " + hanok+"<br/><br/>";
                }
                if (infoCenter!= null && !infoCenter.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.information_center)+"</b> " +infoCenter+"<br/>";
                }
                if (parking!= null && !parking.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.parking_facility)+"</b> " + parking+"<br/>";
                }
                if (pickup!= null && !pickup.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.pickup_service)+"</b> " + pickup+"<br/>";
                }
                if (roomCount!= null && !roomCount.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.number_of_guest_room)+"</b> " + roomCount+"<br/>";
                }
                if (reservation!= null && !reservation.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.reservation_guide)+"</b> " + reservation+"<br/>";
                }
                if (reservationURL!= null && !reservationURL.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.reservation_guide_homepage)+"</b> " + reservationURL+"<br/>";
                }
                if (roomType!= null && !roomType.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.guest_room_type)+"</b> " +roomType+"<br/>";
                }
                if (scale!= null && !scale.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.scale)+"</b> " + scale+"<br/>";
                }
                if (subfacility!= null && !subfacility.equalsIgnoreCase("")) {
                    touristinfo += "<br/><b>"+getResources().getString(R.string.sub_facility)+"</b> " + subfacility+"<br/>";
                }
            }
            if(Int_id==82 || Int_id==39){
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
                    touristinfo+="<b>"+getResources().getString(R.string.main_dishes)+"</b> "+firstMenu+"<br/>";
                }
                if(openDateFood!=null && !openDateFood.equalsIgnoreCase("")){
                    touristinfo+="<br/> <br>"+getResources().getString(R.string.open_date)+"</b>"+openDateFood+"<br/>";
                }
                if(openTimeFood!=null && !openTimeFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.business_hour)+"</b> "+openTimeFood+"<br/>";
                }
                if(parkingFood!=null && !parkingFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.parking_facility)+"</b> "+parkingFood+"<br/>";
                }
                if(reservationFood!=null && !reservationFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.reservation_guide)+"</b> "+reservationFood+"<br/>";
                }
                if(restDateFood!=null && !reservationFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.closed_day)+"</b> "+restDateFood+"<br/>";
                }
                if(scaleFood!=null && !scaleFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.scale)+"</b> "+scaleFood+"<br/>";
                }
                if(seat!=null && !seat.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.seat_number)+"</b> "+seat+"<br/>";
                }
                if(smoking!=null && !smoking.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.smoking)+"</b> "+smoking+"<br/>";
                }
                if(treatMenu!=null && !treatMenu.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.treat_menu)+"</b> "+treatMenu+"<br/>";
                }
                if(infoCenterFood!=null && !infoCenterFood.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.information_center)+"<b> "+infoCenterFood+"<br/>";
                }
            }
            if(Int_id==77 || Int_id==25){
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
                    touristinfo+="<b>"+getResources().getString(R.string.credit_card)+"</b>"+checkCreditcard+"<br/>";
                }
                if(conven!=null && !conven.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.facilities)+"</b>"+conven+"<br/>";
                }
                if(disableFacility!=null && !disableFacility.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.disable_facility)+"</b>"+disableFacility+"<br/>";
                }
                if(infoCenter!=null && !infoCenter.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.information_center)+"</b>"+infoCenter+"<br/>";
                }
                if(infoCenterTraffic!=null && !infoCenterTraffic.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.contact_us)+"</b>"+infoCenterTraffic+"<br/>";
                }
                if(mainRoute!=null && !mainRoute.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.main_route)+"</b>"+mainRoute+"<br/>";
                }
                if(operationTimeTraffic!=null && !operationTimeTraffic.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.operation_time)+" </b>"+operationTimeTraffic+"<br/>";
                }
                if(parkingTraffic!=null && !parkingTraffic.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.parking_facility)+"</b>"+parkingTraffic+"<br/>";
                }
                if(restRoomTrafic!=null && !restRoomTrafic.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.rest_room)+"</b>"+restRoomTrafic+"<br/>";
                }
                if(shipInfo!=null && !shipInfo.equalsIgnoreCase("")){
                    touristinfo+="<br/><b>"+getResources().getString(R.string.ship_information)+" </b>"+shipInfo+"<br/>";
                }
            }
            if(Int_id==85 || Int_id==15){
                String ageLimit=detailIntroItem.getAgelimit();
                String bookingPlace=detailIntroItem.getBookingplace();
                String discountinfofestival=detailIntroItem.getDiscountinfofestival();
                String event_end_day=detailIntroItem.getEventenddate();
                String event_home_page=detailIntroItem.getEventhomepage();
                String event_place=detailIntroItem.getEventplace();
                String event_start_date=detailIntroItem.getEventstartdate();
                String festival_grade=detailIntroItem.getFestivalgrade();
                String place_info=detailIntroItem.getPlaceinfo();
                String place_time=detailIntroItem.getPlaytime();
                String program=detailIntroItem.getProgram();
                String spendtimefestival=detailIntroItem.getSpendtimefestival();
                String sponsor1=detailIntroItem.getSponsor1();
                String sponsor1Tel=detailIntroItem.getSponsor1tel();
                String sponsor2=detailIntroItem.getSponsor2();
                String sponsor2Tel=detailIntroItem.getSponsor2tel();
                String subevent=detailIntroItem.getSubevent();
                String useTimeFestival=detailIntroItem.getUsetimefestival();

                if(ageLimit!=null && !ageLimit.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.age_limit)+"</b>"+ageLimit+"<br/>";
                }
                if(bookingPlace!=null && !bookingPlace.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.booking_place)+"</b>"+bookingPlace+"<br/>";
                }
                if(discountinfofestival!=null && !discountinfofestival.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.discount_info)+"</b>"+discountinfofestival+"<br/>";
                }
                if(event_end_day!=null && !event_end_day.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.event_end_day)+"</b>"+event_end_day+"<br/>";
                }
                if(event_home_page!=null && !event_home_page.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.event_home_page)+"</b>"+event_home_page+"<br/>";
                }
                if(event_place!=null && !event_place.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.event_place)+"</b>"+event_place+"<br/>";
                }
                if(event_start_date!=null && !event_start_date.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.event_start_date)+"</b>"+event_start_date+"<br/>";
                }
                if(festival_grade!=null && !festival_grade.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.festival_class)+"</b>"+festival_grade+"<br/>";
                }
                if(place_info!=null && !place_info.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.event_location)+"</b>"+place_info+"<br/>";
                }
                if(place_time!=null && !place_time.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.play_time)+"</b>"+place_time+"<br/>";
                }
                if(program!=null && !program.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.program)+"</b>"+program+"<br/>";
                }
                if(spendtimefestival!=null && !spendtimefestival.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.time_required)+"</b>"+spendtimefestival+"<br/>";
                }
                if(sponsor1!=null && !sponsor1.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.sponser_info)+"</b>"+sponsor1+"<br/>";
                }
                if(sponsor1Tel!=null && !sponsor1Tel.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.sponser_contact_nb)+"</b>"+sponsor1Tel+"<br/>";
                }
                if(sponsor2!=null && !sponsor2.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.sponser2)+"</b>"+sponsor2+"<br/>";
                }
                if(sponsor2Tel!=null && !sponsor2Tel.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.sponser2tel)+"</b>"+sponsor2Tel+"<br/>";
                }
                if(subevent!=null && !subevent.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.sub_event)+"</b>"+subevent+"<br/>";
                }
                if(useTimeFestival!=null && !useTimeFestival.equalsIgnoreCase("")){
                    touristinfo+="<b>"+getResources().getString(R.string.use_fee)+"</b>"+useTimeFestival+"<br/>";
                }
            }
            // to here only
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
        else if(festivalItem!=null){
            imageUrl=festivalItem.getFirstimage();
        }
        else if(favoriteItem!=null){
            imageUrl=favoriteItem.getPicture();
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
        else if(festivalItem!=null){
            url += "?mapX=" + festivalItem.getMapx() + "&mapY=" +festivalItem.getMapy();
        }
        else if(favoriteItem!=null){
            url += "?mapX=" + favoriteItem.getMapX()+ "&mapY=" + favoriteItem.getMapY();
        }
        Log.d("HeroJongi", " URL get comments " + url);
        commentVolley.fetchData(url, 1);
    }

    public String getType(String value) {
        String type = "";
        int Value = Integer.parseInt(value);
        // 79 80 75 78
        switch (Value) {
            case 76: case 12:
                type =getResources().getString(R.string.nature);
                break;
            case 78: case 14:
                type = getResources().getString(R.string.culture);
                break;
            case 75: case 28:
                type = getResources().getString(R.string.leisure);
                break;
            case 80: case 32:
                type = getResources().getString(R.string.accomendation);
                break;
            case 79: case 38:
                type = getResources().getString(R.string.shopping);
                break;
            case 77:case 25:
                type = getResources().getString(R.string.transportation);
                break;
            case 82:case 39:
                type = getResources().getString(R.string.cuisine);
                break;
            case 85:case 15:
                type=getResources().getString(R.string.festival);
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
        else if(festivalItem!=null){
            latLng = new LatLng(Double.parseDouble(festivalItem.getMapy()), Double.parseDouble(festivalItem.getMapx()));
            code = Integer.parseInt(festivalItem.getContenttypeid());
        }
        else if(favoriteItem!=null){
            latLng = new LatLng(Double.parseDouble(favoriteItem.getMapY()), Double.parseDouble(favoriteItem.getMapX()));
            code = Integer.parseInt(favoriteItem.getContenttypeid());
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
            try {
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.map_location)).commit();
            }
            catch (Exception e){

            }
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
                if(festivalItem!=null){
                    mapX=festivalItem.getMapx();
                    mapY=festivalItem.getMapy();
                }
                if(favoriteItem!=null){
                    mapX=favoriteItem.getMapX();
                    mapY=favoriteItem.getMapY();
                }
                String url = Constants.post_comment + "?name=" + signinPreference.getUsername() + "&date=" + today + "&commentTitle=" + commentTitle + "&comment=" + Comment + "&rate=" + rating + "&mapX=" + mapX + "&mapY=" + mapY;
                Log.d("HeroJongi", "here url post comment " + url);
                commentVolley.fetchData(url, 2);
                break;
            case R.id.favorite:
                if (!isFavorite) {
                    String Url = Constants.add_to_favorite + "?name=" + signinPreference.getUserEmail();
                    if (areaBasedItem != null) {
                        Url += "&mapX=" + areaBasedItem.getMapx() + "&mapY=" + areaBasedItem.getMapy() + "&contentTypeId=" + areaBasedItem.getContenttypeid() + "&title=" + areaBasedItem.getTitle()+"&Tel="+areaBasedItem.getTel()+"&Address="+areaBasedItem.getAddr1()+"&Picture="+areaBasedItem.getFirstimage();
                        if(areaBasedItem.getFirstimage2()==null){
                            Url+="&Picture2="+areaBasedItem.getFirstimage();
                        }
                        else{
                            Url+="&Picture2="+areaBasedItem.getFirstimage2();
                        }
                    } else if (locationBasedItem != null) {
                        Url += "&mapX=" + locationBasedItem.getMapx() + "&mapY=" + locationBasedItem.getMapy() + "&contentTypeId=" + locationBasedItem.getContenttypeid() + "&title=" + locationBasedItem.getTitle()+"&Tel="+locationBasedItem.getTel()+"&Address="+locationBasedItem.getAddr1()+"&Picture="+locationBasedItem.getFirstimage();
                        if(locationBasedItem.getFirstimage1()==null){
                            Url+="&Picture2="+locationBasedItem.getFirstimage();
                        }
                        else{
                            Url+="&Picture2="+locationBasedItem.getFirstimage1();
                        }
                    }
                    else if(festivalItem!=null){
                        Url += "&mapX=" + festivalItem.getMapx() + "&mapY=" + festivalItem.getMapy() + "&contentTypeId=" + festivalItem.getContenttypeid() + "&title=" + festivalItem.getTitle()+"&Tel="+festivalItem.getTel()+"&Address="+festivalItem.getAddr1()+"&Picture="+festivalItem.getFirstimage();
                        if(festivalItem.getFirstimage2()==null){
                            Url+="&Picture2="+festivalItem.getFirstimage();
                        }
                        else{
                            Url+="&Picture2="+festivalItem.getFirstimage2();
                        }
                    }
                    else if(favoriteItem!=null){
                        Url += "&mapX=" + favoriteItem.getMapX() + "&mapY=" + favoriteItem.getMapY() + "&contentTypeId=" + favoriteItem.getContenttypeid() + "&title=" +favoriteItem.getTitle()+"&Tel="+favoriteItem.getTel()+"&Address="+favoriteItem.getAddress()+"&Picture="+favoriteItem.getPicture();
                        if(favoriteItem.getPicture2()==null){
                            Url+="&Picture2="+favoriteItem.getPicture();
                        }
                        else{
                            Url+="&Picture2="+favoriteItem.getPicture2();
                        }
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
                    else if(festivalItem!=null){
                        Url += "&title=" + festivalItem.getTitle();
                    }
                    else if(favoriteItem!=null){
                        Url += "&title=" + favoriteItem.getTitle();
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
