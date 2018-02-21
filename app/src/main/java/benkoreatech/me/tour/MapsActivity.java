package benkoreatech.me.tour;

import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import benkoreatech.me.tour.adapter.CityExpandableListView;
import benkoreatech.me.tour.adapter.ViewPagerAdapter;
import benkoreatech.me.tour.fragment.ContentType;
import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.interfaces.categoryInterface;
import benkoreatech.me.tour.objects.Constants;
import benkoreatech.me.tour.objects.Item;
import benkoreatech.me.tour.objects.StayItem;
import benkoreatech.me.tour.objects.areaBasedItem;
import benkoreatech.me.tour.objects.categoryItem;
import benkoreatech.me.tour.utils.CityVolley;
import benkoreatech.me.tour.utils.LocationPreference;
import benkoreatech.me.tour.utils.VolleyApi;
import benkoreatech.me.tour.utils.areaBasedListVolley;
import benkoreatech.me.tour.utils.categortContentParse;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,TourSettings,TabLayout.OnTabSelectedListener,categoryInterface {

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
    categortContentParse categortContentParse;
    areaBasedListVolley areaBasedListVolley;
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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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
        categortContentParse=new categortContentParse(this,this);
        areaBasedListVolley=new areaBasedListVolley(this,this);
        setupDrawer();
        getCitiesData();
        setupViewPager(viewPager);
        toolbar.setupWithViewPager(viewPager);
        setupTabIcons();
        toolbar.addOnTabSelectedListener(this);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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
            String areaCodeURL = Constants.base_url + Constants.areaCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&" + Constants.areaCode + "=" + _item.getCode() + Constants.json;
            cityVolley.fetchData(areaCodeURL, _item);
        }

    }




    public void getCitiesData() {
        String areaCodeURL= Constants.base_url+Constants.areaCode+"?serviceKey="+Constants.server_key+"&numOfRows=17&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest"+Constants.json;
        volleyApi.fetchData(areaCodeURL,Constants.areaCode,null);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position=tab.getPosition();
        int code=0;
        String categoryCodeURL="";
        switch (position){
            case 0:
               code=76;
               categoryCodeURL = Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 1:
                code=78;
                categoryCodeURL= Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 2:
                code=75;
                categoryCodeURL= Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 3:
                code=79;
                categoryCodeURL= Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 4:
                code=82;
                categoryCodeURL= Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 5:
                code=77;
                categoryCodeURL= Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
                break;
            case 6:
                code=80;
                categoryCodeURL= Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=25&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+Constants.json;
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
            String URLCategoryItems=Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=40&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+"&cat1="+categoryItem.getCode()+Constants.json;
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
            String URLCategoryItems=Constants.base_url + Constants.categoryCode + "?serviceKey=" + Constants.server_key + "&numOfRows=40&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&"+Constants.contentTypeId+"="+code+"&cat1="+categoryItem.getCode().substring(0,3)+"&cat2="+categoryItem.getCode()+Constants.json;
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
            String areabasedList = Constants.base_url + Constants.areaBasedList + "?serviceKey=" + Constants.server_key + "&numOfRows=400&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&";

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
            areaBasedListVolley.fetchData(areabasedList, code);
        }
    }

    @Override
    public void setPins(List<areaBasedItem> areaBasedItems, int code) {
        if(mMap!=null && areaBasedItems.size()>0){
            mMap.clear();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for(areaBasedItem areaBasedItem:areaBasedItems) {
                if (areaBasedItem!=null && areaBasedItem.getMapy()!=null && areaBasedItem.getMapx()!=null && !areaBasedItem.getMapx().equalsIgnoreCase("0") && !areaBasedItem.getMapy().equalsIgnoreCase("0")) {
                    LatLng latLng = new LatLng(Double.parseDouble(areaBasedItem.getMapy()),Double.parseDouble(areaBasedItem.getMapx()));
                    MarkerOptions markerOpts = new MarkerOptions().position(latLng);
                    if(code==76) {
                        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.nature));
                    }
                    else if(code==78){
                        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.culture));
                    }
                    else if(code==75){
                        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.sports));
                    }
                    else if(code==80){
                        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.home));
                    }
                   else if(code==79){
                        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.shopping));
                    }
                    else if(code==77){
                        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.car));
                    }
                    else if(code==82){
                        markerOpts.icon(BitmapDescriptorFactory.fromResource(R.drawable.food));
                    }
                    Marker marker=mMap.addMarker(markerOpts);
                    builder.include(marker.getPosition());
                }
    }
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200));
        }
    }
}
