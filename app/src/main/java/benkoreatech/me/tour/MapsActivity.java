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

import com.bumptech.glide.util.Util;
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
import benkoreatech.me.tour.fragment.Festival;
import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.objects.FestivalItem;
import benkoreatech.me.tour.objects.InfoWindowData;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.LocationBasedItem;
import benkoreatech.me.tour.objects.StayItem;
import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.categoryItem;
import benkoreatech.me.tour.objects.detailImageItem;
import benkoreatech.me.tour.utils.CityVolley;
import benkoreatech.me.tour.utils.FestivalVolley;
import benkoreatech.me.tour.utils.LanguageSharedPreference;
import benkoreatech.me.tour.utils.LocationPreference;
import benkoreatech.me.tour.utils.SigninPreference;
import benkoreatech.me.tour.utils.Utils;
import benkoreatech.me.tour.utils.VolleyApi;
import benkoreatech.me.tour.utils.areaBasedListVolley;
import benkoreatech.me.tour.utils.categortContentParse;
import benkoreatech.me.tour.utils.detailImageVolley;

// The map activity

public class MapsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener, OnMapReadyCallback, TourSettings, TabLayout.OnTabSelectedListener, categoryInterface {


    private static final int PERMISSION_LOCATION_REQUEST_CODE = 100; // permission integer to differentiate between granted permissions
    private static final int PERMISSION_PHONE_CALL = 200;
    private GoogleMap mMap; // Google map
    VolleyApi volleyApi;  // volley api is used to fetch area codes
    CityVolley cityVolley; // city volley to fetch cities of every area code obtained in volley api
    TourSettings tourSettings = this; // Interface with 3 abstract methods to connect 2 independent class together
    private DrawerLayout mDrawerLayout; // Drawer layout for right navigation
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView mCityList; // Expandable list view because its 2 levels ex Seoul then when you open seoul you gonna see ( Youngsan, Itaewon...)
    private CityExpandableListView cityExpandableListView; // BaseExpandableListAdapter for the right menu ( adapter )
    Map<String, List<Item>> cityListData = new TreeMap<>();  // map with key string ex (seoul) anf List< Item > that is array list of all cities in seoul like youngsan ...
    List<Item> itemsAreaCode = new ArrayList<>(); // Item is a class with getters and setters method that contain name of area ex : youngsan and code 1123
    LocationPreference locationPreference; // Shared preference to store which place is picked by user so you wanna query according to the user choice ( example in dong-gu, daegu city --> areaCode=4&sigunguCode=4
    private int lastCityExpandedPosition = -1;
    TabLayout toolbar; // Tab layout ( Bottom menu for choosing : shopping, nature..)
    ViewPager viewPager; // Viewer pager ( Bottom menu for choosing : shopping, nature ..)
    SlidingLayer slidingLayer2; // Sliding Layer ( External library for bottom menu
    SlidingLayer rightMenu;// Sliding Layer ( External library for right menu
    Marker clickedMarker; // Marker " pin "
    Menu menu; // Menu where we put search icon and settings ( Sign out/ Map / List ..)
    ImageView myLocation;//Image View
    LanguageSharedPreference languageSharedPreference; // Language Shared Preference to save the language of device in order to query according to language
    RelativeLayout activity_controller; // Relative layout
    SupportMapFragment mapFragment; // Support map fragment for map
    RecyclerView list_view; // Recycler View
    categortContentParse categortContentParse; // Volley class to fetch the list of category codes according to cat 1, cat2 and cat3
    areaBasedListVolley areaBasedListVolley; // Volley class to get all places according to category code in specific area ( ex: resturant in myeongdong)
    GoogleApiClient mGoogleApiClient; // Google api client used to get current location and for tracking location ( GPS)
    private Location mLastLocation; // Last location known
    private LocationRequest mLocationRequest; // Location request
    LocationListener locationListener = this; // location listener interface to track location (onLocationchanged ...)
    SigninPreference signinPreference; // Sign in shared preference that contain user name and email of signed user used for logout and  query Urls according to specific user
    public static final int Access_Location = 70;  // Acccess location permission type
    // Location updates intervals in sec
    float[] Markercolors; // Marker colors array to choose random color for pin
    private static int UPDATE_INTERVAL = 60 * 1000; // 1min update interval for location change
    public boolean isFirstTime = true;
    String Phone = "";
    FestivalVolley festivalVolley;

    // list of drawables for bottom menu
    private int[] tabIcons = {
            R.drawable.nature,
            R.drawable.culture,
            R.drawable.sports,
            R.drawable.shopping,
            R.drawable.food,
            R.drawable.car,
            R.drawable.home,
            R.drawable.festival
    };

    // Since we have 7 tabs in bottom menu then we need 7 fragments but since all fragment in this case will handle same code i use single fragment with 7 instances
    ContentType nature, culture, leisure, shopping, cuisine, transportation, accomendation;
    Festival festival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // listener for map to know when its prepared and work
        mapFragment.getMapAsync(this);
        // declaring volley class by passing context and interface as parameters
        volleyApi = new VolleyApi(this, tourSettings);
        cityVolley = new CityVolley(this, tourSettings);
        // declaring shared preference
        locationPreference = new LocationPreference(this);
        // declaring views
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (TabLayout) findViewById(R.id.tabs);
        mCityList = (ExpandableListView) findViewById(R.id.city);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        slidingLayer2 = (SlidingLayer) findViewById(R.id.slidingLayer2);
        rightMenu = (SlidingLayer) findViewById(R.id.rightmenu);
        list_view = (RecyclerView) findViewById(R.id.list_view);
        myLocation = (ImageView) findViewById(R.id.mylocation);
        activity_controller = (RelativeLayout) findViewById(R.id.activity_controller);
        // S ign in shared preference declaration
        signinPreference = new SigninPreference(this);
        festivalVolley=new FestivalVolley(this,this);
        categortContentParse = new categortContentParse(this, this); // category content parse volley class
        areaBasedListVolley = new areaBasedListVolley(this, this); // area based list volley declaration
        languageSharedPreference = new LanguageSharedPreference(this); // language shared preference declaration

        // filling the array of marker colors for random selection
        Markercolors = Utils.getMarkerColors();

        // get the tool bar
        setLocationinToolbar();
        // get the default language from the phone
        String language = Locale.getDefault().getLanguage();
        // save this language in shared preference
        languageSharedPreference.save_language(language);
        // myLocation on click listener to listen for click when we need current location
        myLocation.setOnClickListener(this);
        setupDrawer(); // set up the drawer of right menu
        getCitiesData(); // get cities data in order to fill right menu
        setupViewPager(viewPager); // set up view pager for bottom menu
        toolbar.setupWithViewPager(viewPager); // set the tab layout with viewer pager
        setupTabIcons(); // set up the default tab icons
        toolbar.addOnTabSelectedListener(this); // tab on click listener in bottom menu
        buildGoogleApiClient(); // build the google api client
        createLocationRequest();
        // Here i do api call upon on Create to fill the nature spinner so i will not get empty one
        int code = 76; // nature
        // I prepare the Url to fetch the nature category code
        String categoryCodeURL = Constants.base_url + languageSharedPreference.getLanguage() + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&" + Constants.contentTypeId + "=" + code + Constants.json;
        categortContentParse.fetchData(categoryCodeURL, code, 1);

    }

    public void setLocationinToolbar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        String parentname = locationPreference.getParentName();
        String childname = locationPreference.getchildName();
        if (ab != null) {
            if (parentname != null && !parentname.equalsIgnoreCase("")) {
                if (childname != null && !childname.equalsIgnoreCase("")) {
                    String place = parentname + "," + childname;
                    ab.setTitle(place);
                } else {
                    ab.setTitle(parentname);
                }
            } else {
                ab.setTitle(""); // set tool bar title to empty
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == Access_Location) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // handle deny case
            } else {
                // if we already grant the fine location and coarse location permission we call for location update request
                // note that in Version M and up api 23 and up phone we have to grant or deny permission in phone with smaller version permissions is granted by default
                requestLocationUpdate();
            }
        } else if (requestCode == PERMISSION_PHONE_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Phone));
                    this.startActivity(intent);
                }

            }
        }

    }



// set tab icons of bottom menu
    private void setupTabIcons() {
        toolbar.getTabAt(0).setIcon(tabIcons[0]);
        toolbar.getTabAt(1).setIcon(tabIcons[1]);
        toolbar.getTabAt(2).setIcon(tabIcons[2]);
        toolbar.getTabAt(3).setIcon(tabIcons[3]);
        toolbar.getTabAt(4).setIcon(tabIcons[4]);
        toolbar.getTabAt(5).setIcon(tabIcons[5]);
        toolbar.getTabAt(6).setIcon(tabIcons[6]);
        toolbar.getTabAt(7).setIcon(tabIcons[7]);
    }

   // set viewer pager
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // declraing the fragments
        nature=new ContentType();
        culture=new ContentType();
        leisure=new ContentType();
        shopping=new ContentType();
        cuisine=new ContentType();
       transportation=new ContentType();
        accomendation=new ContentType();
        festival=new Festival(this);

        // adding the fragments to adapter
        adapter.addFragment(nature, "");
        adapter.addFragment(culture, "");
        adapter.addFragment(leisure, "");
        adapter.addFragment(shopping,"");
        adapter.addFragment(cuisine,"");
        adapter.addFragment(transportation,"");
        adapter.addFragment(accomendation,"");
        adapter.addFragment(festival,"");
        // populating the viewer pager with the adapter
        viewPager.setAdapter(adapter);
    }

    // set up drawer  of left menu ( Areas )
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

    // on Drawer Toggle : On drawer close and on Drawer open listerner
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    // on configuration changed
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    // when the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap; // we reference mMap to google map in order to user a public variable

        // checking if permission of access fine location and coarse location is granted or not
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true); // enable my location
            googleMap.getUiSettings().setMyLocationButtonEnabled(false); // set my location button to invisible because i dont want to use the default icon
        } else {
            // if permission not granted then you have to request this permission in M devices.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);
        }
        // when we click the info window for a marker listener
        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                // we get the tag of the clicked marker
                InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
                // if the tag is not null or its location based data is not null
                if(infoWindowData!=null && infoWindowData.getLocationBasedData()!=null) {
                    // we get the string data from this info window and we pass it to the dialog fragment as a parameter to later transform it using gson
                    final String data = infoWindowData.getLocationBasedData();
                    // Here how we open a dialog fragment from activity
                    FragmentManager fm = getSupportFragmentManager();
                    PlaceInfo placeInfo = PlaceInfo.newInstance("Some Title");
                    placeInfo.setStyle(android.app.DialogFragment.STYLE_NO_TITLE, R.style.MyTheme);
                    placeInfo.setLocationInfo(data); // we pass the data string to Place Info dialog fragment
                    placeInfo.show(fm, "activity_place_info");
                }

            }
        });
         // when we click on marker on map
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                clickedMarker=marker; // the clicked marker keep reference to it
                // animate the camera to this marker
                CameraPosition cameraPosition = new CameraPosition.Builder().bearing(30).zoom(13).target(marker.getPosition()).tilt(60).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
                // api call using latitude and longitude of clicked marker to get information about this marker with radius =0 in order to get info about only this marker
                String aSpecificPlace= Constants.base_url+languageSharedPreference.getLanguage()+Constants.locationBasedList+"?serviceKey="+Constants.server_key+"&numOfRows=1&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&listYN=Y&arrange=A&mapX="+marker.getPosition().longitude+"&mapY="+marker.getPosition().latitude+"&radius=0"+Constants.json;
                areaBasedListVolley.fetchData(aSpecificPlace,0,1);
                return true;
            }
        });

    }




    int i=0;
    // fill the city with list of items
    @Override
    public void FillCity(List<Item> items) {
        this.itemsAreaCode = items; // keep reference to list of items
        fetchData(itemsAreaCode.get(0)); // fetch the data for first item list according to sigungucode
    }

    @Override
    public void FillSubCity(List<Item> items,Item item) {
        // increment i
        i++;
        // ex add seoul to top of list
        items.add(0,item);
        // all it in map list with key item name (seoul) and value list of items (myeongdong, incheon...)
        cityListData.put(item.getName(),items);
        // fetch the second data
        fetchData(itemsAreaCode.get(i));
        // if i is equal to number of cities in area code = num of rows then stop looping and go to add city drawer to display the items
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

    @Override
    public void callPlace(String phonenumber) {
        this.Phone=phonenumber;
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("Call", " its permission is granted");
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber));
                startActivity(intent);
            } else {
                Log.d("Call", " its permission is not granted");
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSION_PHONE_CALL);
            }

    }

    private void addCityDrawer() {
        // pass the context, the items area code array list and map city list data to adapter
        cityExpandableListView = new CityExpandableListView(this,itemsAreaCode,cityListData);
        // set this adapter
        mCityList.setAdapter(cityExpandableListView);
        // when its expanded ( Listener )
        mCityList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                // to know when we need to collapse the group if another group is open so if we click on the child item its index not -1 so it gonna collapse
                if (lastCityExpandedPosition != -1 && groupPosition != lastCityExpandedPosition) {
                    mCityList.collapseGroup(lastCityExpandedPosition);
                }
                lastCityExpandedPosition = groupPosition;
            }
        });
        // when a child is clicked
        mCityList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // get the city at group position  example seoul
                Item city = itemsAreaCode.get(groupPosition);
                // if this city not null and is not empty
                if (city.getName() != null && !city.getName().equalsIgnoreCase("")) {
                    // get the code of the child item
                   Item child= cityListData.get(city.getName()).get(childPosition);
                    if (childPosition == 0) {
                        // if child position is 0 then save the sigungucode null in shared preference ( example top item is seoul and child item is seoul)
                        locationPreference.saveLocation(city.getName(),null,String.valueOf(city.getCode()), null);
                    } else {
                        // if the child position is not 0 then save the city code top item and the child code to shared oreference
                        locationPreference.saveLocation(city.getName(),child.getName(),String.valueOf(city.getCode()), String.valueOf(child.getCode()));

                    }
                    setLocationinToolbar();

                }
                // close the righ menu (sliding layer)
                rightMenu.closeLayer(true);
                // collapse this group mean close the group
                mCityList.collapseGroup(groupPosition);
//                mDrawerLayout.closeDrawer(GravityCompat.END);
                return false;
            }
        });

   }


    public void fetchData(Object item){
        if(item instanceof Item) {
            Item _item=(Item) item;
            // prepare url with the area code of the following item to get the sub cities then call again fetch Data
            String areaCodeURL = Constants.base_url +languageSharedPreference.getLanguage()+ Constants.areaCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&" + Constants.areaCode + "=" + _item.getCode() + Constants.json;
            cityVolley.fetchData(areaCodeURL, _item);
        }

    }



    // get cities data
    public void getCitiesData() {
        String areaCodeURL= Constants.base_url+languageSharedPreference.getLanguage()+Constants.areaCode+"?serviceKey="+Constants.server_key+"&numOfRows=17&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest"+Constants.json;
        volleyApi.fetchData(areaCodeURL,Constants.areaCode,null);
    }

    // on tab selected get the index of this tap and according to it send api call to fetch category code
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
        // call the volley class category content parse and fetch the data
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
              // Fill First spinner
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
            // send cat1 and prepare the url
            String URLCategoryItems=Constants.base_url +languageSharedPreference.getLanguage()+ Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=40&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+"&cat1="+categoryItem.getCode()+Constants.json;
            categortContentParse.fetchData(URLCategoryItems,code,2);
        }
    }


    // same thing as big category you have to full the data in spinner and then on item selected take medium Item and make api call by using cat 1 and cat2 so you gonna get small category
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
    public void setPins(List<FestivalItem> festivalItems) {
        Log.d("HeroJongi"," SIze "+festivalItems.size());
        if (mMap != null && festivalItems.size() > 0) {
            mMap.clear();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (FestivalItem areaBasedItem : festivalItems) {
                if (areaBasedItem != null && areaBasedItem.getMapy() != null && areaBasedItem.getMapx() != null && !areaBasedItem.getMapx().equalsIgnoreCase("0") && !areaBasedItem.getMapy().equalsIgnoreCase("0")) {
                    LatLng latLng = new LatLng(Double.parseDouble(areaBasedItem.getMapy()), Double.parseDouble(areaBasedItem.getMapx()));
                    float color = Markercolors[new Random().nextInt(Markercolors.length)];
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(color)));
                    builder.include(marker.getPosition());
                }
            }
        }
    }

    @Override
    public void setListareaBasedItems(List<areaBasedItem> areaBasedItems) {
        if(areaBasedItems!=null && areaBasedItems.size()>0) {
            Log.d("HeroJongi"," set List area based items ");
            TourList tourList = new TourList(areaBasedItems, MapsActivity.this,this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
            list_view.setLayoutManager(mLayoutManager);
            list_view.setItemAnimator(new DefaultItemAnimator());
            list_view.setAdapter(tourList);
        }
    }

    @Override
    public void setStartEndDate(String startDate, String endDate) {
        if(startDate!=null && endDate!=null && !startDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase("")){
            slidingLayer2.closeLayer(true);
            String url=Constants.base_url+languageSharedPreference.getLanguage()+Constants.searchFestival+"?"+Constants.serviceKey+"="+Constants.server_key+
                    "&numOfRows=10&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&listYN=Y&arrange=A&eventStartDate="+startDate+"&eventEndDate="+endDate+Constants.json;
            festivalVolley.fetchData(url);
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
        if(isFirstTime){
            isFirstTime=false;
            ZoomtoMyCurrentLocation();
        }
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
