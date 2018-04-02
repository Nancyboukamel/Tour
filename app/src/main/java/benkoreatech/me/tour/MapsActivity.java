package benkoreatech.me.tour;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import benkoreatech.me.tour.adapter.CityExpandableListView;
import benkoreatech.me.tour.adapter.CustomInfoWindowAdapter;
import benkoreatech.me.tour.adapter.TourList;
import benkoreatech.me.tour.adapter.ViewPagerAdapter;
import benkoreatech.me.tour.fragment.ContentType;
import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.objects.InfoWindowData;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.LocationBasedItem;
import benkoreatech.me.tour.objects.StayItem;
import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.categoryItem;
import benkoreatech.me.tour.objects.detailImageItem;
import benkoreatech.me.tour.utils.CityVolley;
import benkoreatech.me.tour.utils.LanguageSharedPreference;
import benkoreatech.me.tour.utils.LocationPreference;
import benkoreatech.me.tour.utils.SigninPreference;
import benkoreatech.me.tour.utils.VolleyApi;
import benkoreatech.me.tour.utils.areaBasedListVolley;
import benkoreatech.me.tour.utils.categortContentParse;
import benkoreatech.me.tour.utils.detailImageVolley;

public class MapsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener,View.OnClickListener,OnMapReadyCallback,TourSettings,TabLayout.OnTabSelectedListener,categoryInterface{

    private static final int PERMISSION_LOCATION_REQUEST_CODE = 100;
    private GoogleMap mMap;
    VolleyApi volleyApi;
    CityVolley cityVolley;
    TourSettings tourSettings=this;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView mCityList;
    private CityExpandableListView cityExpandableListView;
    Map<String, List<Item>> cityListData = new TreeMap<>();
    List<Item> itemsAreaCode=new ArrayList<>();
    LocationPreference locationPreference;
    private int lastCityExpandedPosition=-1;
    TabLayout toolbar;
    ViewPager viewPager;
    SlidingLayer slidingLayer2;
    SlidingLayer rightMenu;
    Marker clickedMarker;
    Menu menu;
    ImageView myLocation;
    LanguageSharedPreference languageSharedPreference;
    RelativeLayout activity_controller;
    SupportMapFragment mapFragment;
    RecyclerView list_view;
    categortContentParse categortContentParse;
    areaBasedListVolley areaBasedListVolley;
    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    LocationListener locationListener = this;
    SigninPreference signinPreference;
    public static final int Access_Location = 70;
    // Location updates intervals in sec
    float [] Markercolors;
    private static int UPDATE_INTERVAL = 60 * 1000; // 1min



    private int[] tabIcons = {
            R.drawable.nature,
            R.drawable.culture,
            R.drawable.sports,
            R.drawable.shopping,
            R.drawable.food,
            R.drawable.car,
            R.drawable.home
    };

    ContentType nature,culture,leisure,shopping,cuisine,transportation,accomendation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        volleyApi=new VolleyApi(this,tourSettings);
        cityVolley=new CityVolley(this,tourSettings);
        locationPreference=new LocationPreference(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar=(TabLayout) findViewById(R.id.tabs);
        mCityList=(ExpandableListView) findViewById(R.id.city);
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        slidingLayer2=(SlidingLayer) findViewById(R.id.slidingLayer2);
        rightMenu=(SlidingLayer) findViewById(R.id.rightmenu);
        list_view=(RecyclerView) findViewById(R.id.list_view);
        myLocation=(ImageView) findViewById(R.id.mylocation);
        signinPreference=new SigninPreference(this);
        activity_controller=(RelativeLayout) findViewById(R.id.activity_controller);
        categortContentParse=new categortContentParse(this,this);
        areaBasedListVolley=new areaBasedListVolley(this,this);
        languageSharedPreference=new LanguageSharedPreference(this);

        Markercolors=new float[]{BitmapDescriptorFactory.HUE_RED,BitmapDescriptorFactory.HUE_ORANGE,BitmapDescriptorFactory.HUE_YELLOW,BitmapDescriptorFactory.HUE_GREEN,
        BitmapDescriptorFactory.HUE_CYAN,BitmapDescriptorFactory.HUE_AZURE,BitmapDescriptorFactory.HUE_BLUE,BitmapDescriptorFactory.HUE_VIOLET,BitmapDescriptorFactory.HUE_MAGENTA,
        BitmapDescriptorFactory.HUE_ROSE};

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setTitle("");
        }
        String language= Locale.getDefault().getLanguage();
        languageSharedPreference.save_language(language);
        myLocation.setOnClickListener(this);
        setupDrawer();
        getCitiesData();
        setupViewPager(viewPager);
        toolbar.setupWithViewPager(viewPager);
        setupTabIcons();
        toolbar.addOnTabSelectedListener(this);
        buildGoogleApiClient();
       createLocationRequest();
        int code=76;
        String categoryCodeURL = Constants.base_url+languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
        categortContentParse.fetchData(categoryCodeURL,code,1);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == Access_Location) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // handle deny case
            } else {
                requestLocationUpdate();
            }
        }


    }




    private void setupTabIcons() {
        toolbar.getTabAt(0).setIcon(tabIcons[0]);
        toolbar.getTabAt(1).setIcon(tabIcons[1]);
        toolbar.getTabAt(2).setIcon(tabIcons[2]);
        toolbar.getTabAt(3).setIcon(tabIcons[3]);
        toolbar.getTabAt(4).setIcon(tabIcons[4]);
        toolbar.getTabAt(5).setIcon(tabIcons[5]);
        toolbar.getTabAt(6).setIcon(tabIcons[6]);
    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        nature=new ContentType();
        culture=new ContentType();
        leisure=new ContentType();
        shopping=new ContentType();
        cuisine=new ContentType();
       transportation=new ContentType();
        accomendation=new ContentType();

        adapter.addFragment(nature, "");
        adapter.addFragment(culture, "");
        adapter.addFragment(leisure, "");
        adapter.addFragment(shopping,"");
        adapter.addFragment(cuisine,"");
        adapter.addFragment(transportation,"");
        adapter.addFragment(accomendation,"");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);
        }
        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
                if(infoWindowData!=null && infoWindowData.getLocationBasedData()!=null) {
                    final String data = infoWindowData.getLocationBasedData();
                    FragmentManager fm = getSupportFragmentManager();
                    PlaceInfo placeInfo = PlaceInfo.newInstance("Some Title");
                    placeInfo.setStyle(android.app.DialogFragment.STYLE_NO_TITLE, R.style.MyTheme);
                    placeInfo.setLocationInfo(data);
                    placeInfo.show(fm, "activity_place_info");
                }

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                clickedMarker=marker;
                CameraPosition cameraPosition = new CameraPosition.Builder().bearing(30).zoom(13).target(marker.getPosition()).tilt(60).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
                String aSpecificPlace= Constants.base_url+languageSharedPreference.getLanguage()+Constants.locationBasedList+"?serviceKey="+Constants.server_key+"&numOfRows=1&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&listYN=Y&arrange=A&mapX="+marker.getPosition().longitude+"&mapY="+marker.getPosition().latitude+"&radius=0"+Constants.json;
                areaBasedListVolley.fetchData(aSpecificPlace,0,1);
                return true;
            }
        });

    }




    int i=0;
    @Override
    public void FillCity(List<Item> items) {
        this.itemsAreaCode = items;
        fetchData(itemsAreaCode.get(0));
    }

    @Override
    public void FillSubCity(List<Item> items,Item item) {
       for(Item item1:items){
           Log.d("HeroJongi"," ITEM IS "+item1.getName());
       }
        i++;
        // add seoul to yop of list
        items.add(0,item);
        cityListData.put(item.getName(),items);
        fetchData(itemsAreaCode.get(i));
        if(i==itemsAreaCode.size()-1){
            // now we can fill the extandable list view\
          addCityDrawer();
        }

    }

    @Override
    public void onListItemClicked(areaBasedItem areaBasedItem) {
        FragmentManager fm = getSupportFragmentManager();
        PlaceInfo placeInfo = PlaceInfo.newInstance("Some Title");
        placeInfo.setStyle(android.app.DialogFragment.STYLE_NO_TITLE, R.style.MyTheme);
        placeInfo.setLocationInfoItem(areaBasedItem);
        placeInfo.show(fm, "activity_place_info");

    }

    private void addCityDrawer() {
        cityExpandableListView = new CityExpandableListView(this,itemsAreaCode,cityListData);
        mCityList.setAdapter(cityExpandableListView);
        mCityList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastCityExpandedPosition != -1 && groupPosition != lastCityExpandedPosition) {
                    mCityList.collapseGroup(lastCityExpandedPosition);
                }
                lastCityExpandedPosition = groupPosition;
            }
        });
        mCityList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Item city = itemsAreaCode.get(groupPosition);
                if (city.getName() != null && !city.getName().equalsIgnoreCase("")) {
                    int provience = cityListData.get(city.getName()).get(childPosition).getCode();
                    if (childPosition == 0) {
                        locationPreference.saveLocation(String.valueOf(city.getCode()), null);
                    } else {
                        locationPreference.saveLocation(String.valueOf(city.getCode()), String.valueOf(provience));
                    }

                }
                rightMenu.closeLayer(true);
//                Log.d("HeroJongi"," Selected Item"+selectedItem);
                mCityList.collapseGroup(groupPosition);
//                mDrawerLayout.closeDrawer(GravityCompat.END);
                return false;
            }
        });

   }


    public void fetchData(Object item){
        if(item instanceof Item) {
            Item _item=(Item) item;
            String areaCodeURL = Constants.base_url +languageSharedPreference.getLanguage()+ Constants.areaCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&" + Constants.areaCode + "=" + _item.getCode() + Constants.json;
            cityVolley.fetchData(areaCodeURL, _item);
        }

    }




    public void getCitiesData() {
        String areaCodeURL= Constants.base_url+languageSharedPreference.getLanguage()+Constants.areaCode+"?serviceKey="+Constants.server_key+"&numOfRows=17&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest"+Constants.json;
        volleyApi.fetchData(areaCodeURL,Constants.areaCode,null);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position=tab.getPosition();
        int code=0;
        String categoryCodeURL="";
        slidingLayer2.openLayer(true);
        switch (position){
            case 0:
               code=76;
               categoryCodeURL = Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 1:
                code=78;
                categoryCodeURL= Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 2:
                code=75;
                categoryCodeURL= Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 3:
                code=79;
                categoryCodeURL= Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 4:
                code=82;
                categoryCodeURL= Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 5:
                code=77;
                categoryCodeURL= Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 6:
                code=80;
                categoryCodeURL= Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;

        }
        categortContentParse.fetchData(categoryCodeURL,code,1);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void BigCategory(List<categoryItem> categoryItems,int code) {
        if(categoryItems!=null && categoryItems.size()>0){
      switch (code) {
          case 76:
              nature.setText("Nature");
              nature.FillBigSpinner(categoryItems, code,this);
              break;
          case 78:
              culture.setText("Culture/Art/History");
              culture.FillBigSpinner(categoryItems, code,this);
              break;
          case 75:
              leisure.setText("Leisure/Sports");
              leisure.FillBigSpinner(categoryItems, code,this);
              break;
          case 80:
              accomendation.setText("Accomendation");
              accomendation.FillBigSpinner(categoryItems, code,this);
              break;
          case 79:
              shopping.setText("Shopping");
              shopping.FillBigSpinner(categoryItems, code,this);
              break;
          case 77:
              transportation.setText("Transportation");
              transportation.FillBigSpinner(categoryItems, code,this);
              break;
          case 82:
              cuisine.setText("Cuisine");
              cuisine.FillBigSpinner(categoryItems, code,this);
              break;
      }
      }
    }

    @Override
    public void onItemBigSelected(categoryItem categoryItem,int code) {
        if(categoryItem!=null && categoryItem.getName()!=null && !categoryItem.getName().equalsIgnoreCase("")){
            String URLCategoryItems=Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=40&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+"&cat1="+categoryItem.getCode()+Constants.json;
            categortContentParse.fetchData(URLCategoryItems,code,2);
        }
    }

    @Override
    public void MediumCategory(List<categoryItem> categoryItems,int code) {
        if(categoryItems!=null && categoryItems.size()>0){
            switch (code) {
                case 76:
                    nature.FillMediumSpinner(categoryItems);
                    break;
                case 78:
                    culture.FillMediumSpinner(categoryItems);
                    break;
                case 75:
                    leisure.FillMediumSpinner(categoryItems);
                    break;
                case 80:
                    accomendation.FillMediumSpinner(categoryItems);
                    break;
                case 79:
                    shopping.FillMediumSpinner(categoryItems);
                    break;
                case 77:
                    transportation.FillMediumSpinner(categoryItems);
                    break;
                case 82:
                    cuisine.FillMediumSpinner(categoryItems);
                    break;
            }
        }
    }

    @Override
    public void onItemMediumSelected(categoryItem categoryItem, int code) {
        if(categoryItem!=null && categoryItem.getName()!=null && !categoryItem.getName().equalsIgnoreCase("")){
            String URLCategoryItems=Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=40&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+"&cat1="+categoryItem.getCode().substring(0,3)+"&cat2="+categoryItem.getCode()+Constants.json;
            categortContentParse.fetchData(URLCategoryItems,code,3);
        }
    }

    @Override
    public void SmallCategory(List<categoryItem> categoryItems, int code) {
        if(categoryItems!=null && categoryItems.size()>0){
            switch (code) {
                case 76:
                    nature.FillSmallSpinner(categoryItems);
                    break;
                case 78:
                    culture.FillSmallSpinner(categoryItems);
                    break;
                case 75:
                    leisure.FillSmallSpinner(categoryItems);
                    break;
                case 80:
                    accomendation.FillSmallSpinner(categoryItems);
                    break;
                case 79:
                    shopping.FillSmallSpinner(categoryItems);
                    break;
                case 77:
                    transportation.FillSmallSpinner(categoryItems);
                    break;
                case 82:
                    cuisine.FillSmallSpinner(categoryItems);
                    break;
            }
        }
    }

    @Override
    public void CloseSlider() {
        slidingLayer2.closeLayer(true);
    }

    @Override
    public void PlotPins(categoryItem BigItem, categoryItem MediumItem, categoryItem SmallItem, int code) {
        String areaCode = "", SigunguCode = "";
        if (locationPreference != null) {
            areaCode = locationPreference.getAreaCode();
            SigunguCode = locationPreference.getSigungCode();
            String areabasedList = Constants.base_url +languageSharedPreference.getLanguage()+ Constants.areaBasedList + "?serviceKey=" + Constants.server_key + "&numOfRows=400&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&";

            if (areaCode != null && !areaCode.equalsIgnoreCase("")) {
                areabasedList += Constants.areaCode + "=" + areaCode;
            }
            if (SigunguCode != null && !SigunguCode.equalsIgnoreCase("")) {
                areabasedList += "&" + Constants.sigunguCode + "=" + SigunguCode;
            }
            if (BigItem != null && BigItem.getCode() != null && !BigItem.getCode().equalsIgnoreCase("")) {
                String cat1 = BigItem.getCode();
                areabasedList += "&cat1=" + cat1;
            }
            if (MediumItem != null && MediumItem.getCode() != null && !MediumItem.getCode().equalsIgnoreCase("")) {
                String cat2 = MediumItem.getCode();
                areabasedList += "&cat2=" + cat2;
            }
            if (SmallItem != null && SmallItem.getCode() != null && !SmallItem.getCode().equalsIgnoreCase("")) {
                String cat3 = SmallItem.getCode();
                areabasedList += "&cat3=" + cat3;
            }
            areabasedList += Constants.json;
            Log.d("HeroJongi","URL "+areabasedList);
            areaBasedListVolley.fetchData(areabasedList, code,0);
        }
    }

    @Override
    public void setPins(List<areaBasedItem> areaBasedItems, int code) {
        Log.d("HeroJongi"," array size "+areaBasedItems.size());
        if(mMap!=null && areaBasedItems.size()>0){
            mMap.clear();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for(areaBasedItem areaBasedItem:areaBasedItems) {
                if (areaBasedItem!=null && areaBasedItem.getMapy()!=null && areaBasedItem.getMapx()!=null && !areaBasedItem.getMapx().equalsIgnoreCase("0") && !areaBasedItem.getMapy().equalsIgnoreCase("0")) {
                    LatLng latLng = new LatLng(Double.parseDouble(areaBasedItem.getMapy()),Double.parseDouble(areaBasedItem.getMapx()));
                  float color=  Markercolors[new Random().nextInt(Markercolors.length)];
                    Marker marker=mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(color)));
                    builder.include(marker.getPosition());
                }

    }

    Log.d("HeroJongi","on loop end");
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
        }
    }

    @Override
    public void setPinInfo(List<LocationBasedItem> locationBasedItems) {
        if(locationBasedItems.size()>0 && mMap!=null && clickedMarker!=null){
            Log.d("HeroJongi"," on Fetch Info completed "+locationBasedItems.size()+"  "+locationBasedItems.get(0).toString());
            CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this,MapsActivity.this);
            mMap.setInfoWindowAdapter(adapter);
            InfoWindowData info = new InfoWindowData();
            Gson gson=new Gson();
            String data=gson.toJson(locationBasedItems.get(0));
            info.setLocationBasedData(data);
            clickedMarker.setTag(info);
            clickedMarker.showInfoWindow();
        }
    }

    @Override
    public void setListareaBasedItems(List<areaBasedItem> areaBasedItems) {
        if(areaBasedItems!=null && areaBasedItems.size()>0) {
            Log.d("HeroJongi"," set List area based items ");
            TourList tourList = new TourList(areaBasedItems, MapsActivity.this,this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            list_view.setLayoutManager(mLayoutManager);
            list_view.setItemAnimator(new DefaultItemAnimator());
            list_view.setAdapter(tourList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_place_info, menu);
        this.menu=menu;


        MenuItem map = menu.findItem(R.id.action_map);
        MenuItem list=menu.findItem(R.id.action_list);
        MenuItem signout=menu.findItem(R.id.signout);

        SpannableString s = new SpannableString(map.getTitle());
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 0, s.length(), 0);
        map.setTitle(s);

        SpannableString sa = new SpannableString(list.getTitle());
        sa.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 0, sa.length(), 0);
        list.setTitle(sa);

        SpannableString _signout = new SpannableString(signout.getTitle());
        _signout.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 0, _signout.length(), 0);
        signout.setTitle(_signout);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (id){
            case R.id.action_list:
               list_view.setVisibility(View.VISIBLE);
                activity_controller.setVisibility(View.GONE);
                break;
            case R.id.action_map:
                list_view.setVisibility(View.GONE);
                activity_controller.setVisibility(View.VISIBLE);
                break;
            case R.id.signout:
                signinPreference.isSignin(false,null,null);
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.mylocation:
                 ZoomtoMyCurrentLocation();
                break;
        }
    }

    public void ZoomtoMyCurrentLocation() {
        if (mMap != null && mLastLocation!=null) {
            LatLng coordinate = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
            mMap.animateCamera(location);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Resuming the periodic location updates
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void startLocationUpdates() {
        Log.d("HeroJongi", "Here 1234");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Access_Location);
        } else {
            requestLocationUpdate();
        }

    }

    public void requestLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("HeroJongi ", "requestLocationUpdate");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
        }

    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        // mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }



    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            String keyword = URLEncoder.encode(query, "UTF-8");
          //  http://api.visitkorea.or.kr/openapi/service/rest/EngService/searchKeyword?serviceKey=[Authentication Key]&numOfRows=10&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&listYN=Y&arrange=A&keyword=%EA%B0%95%EC%9B%90
            String areaCode = "", SigunguCode = "";
            if (locationPreference != null) {
                areaCode = locationPreference.getAreaCode();
                SigunguCode = locationPreference.getSigungCode();

                String searchKeywordURL= Constants.base_url+languageSharedPreference.getLanguage()+Constants.searchKeyword+"?serviceKey="+Constants.server_key+"&numOfRows=400&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&listYN=Y&arrange=A&keyword="+keyword+"&";
               // areaBasedListVolley.fetchData(searchKeywordURL,0,2);

                if (areaCode != null && !areaCode.equalsIgnoreCase("")) {
                    searchKeywordURL += Constants.areaCode + "=" + areaCode;
                }
                if (SigunguCode != null && !SigunguCode.equalsIgnoreCase("")) {
                    searchKeywordURL += "&" + Constants.sigunguCode + "=" + SigunguCode;
                }
                searchKeywordURL += Constants.json;
                 areaBasedListVolley.fetchData(searchKeywordURL,0,0);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return true;
    }



    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
