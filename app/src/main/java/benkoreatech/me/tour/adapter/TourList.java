package benkoreatech.me.tour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import benkoreatech.me.tour.R;
import benkoreatech.me.tour.interfaces.TourSettings;
import benkoreatech.me.tour.objects.Favorites;
import benkoreatech.me.tour.objects.FestivalItem;
import benkoreatech.me.tour.objects.areaBasedItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class TourList extends RecyclerView.Adapter<TourList.TourView>{


    List<Object> objects;
    Context context;
    TourSettings tourSettings;

    public TourList(List<Object> objects, Context context, TourSettings tourSettings) {
        this.objects=objects;
        this.context = context;
        this.tourSettings=tourSettings;
    }

    @Override
    public TourList.TourView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new TourList.TourView(itemView);
    }

    @Override
    public void onBindViewHolder(TourList.TourView holder, final int position) {
        final Object object=objects.get(position);
        if(object instanceof areaBasedItem) {
           final areaBasedItem areaBasedItem=(areaBasedItem)object;
            holder.title.setText(areaBasedItem.getTitle());
            holder.address.setText(areaBasedItem.getAddr1());
            if (areaBasedItem.getFirstimage2() != null) {
                Picasso.with(context).load(areaBasedItem.getFirstimage2()).fit().centerCrop().into(holder.circleImageView);
            }

        }
        if(object instanceof FestivalItem){
            FestivalItem festivalItem=(FestivalItem) object;
            holder.title.setText(festivalItem.getTitle());
            holder.address.setText(festivalItem.getAddr1());
            if (festivalItem.getFirstimage2() != null) {
                Picasso.with(context).load(festivalItem.getFirstimage2()).fit().centerCrop().into(holder.circleImageView);
            }
        }
        if(object instanceof Favorites){
            Favorites favorites=(Favorites)object;
            holder.title.setText(favorites.getTitle());
            holder.address.setText(favorites.getAddress());
            if (favorites.getPicture2() != null) {
                Picasso.with(context).load(favorites.getPicture2()).fit().centerCrop().into(holder.circleImageView);
            }
        }

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Object object=objects.get(position);
                if(object instanceof areaBasedItem) {
                    areaBasedItem areaBasedItem=(benkoreatech.me.tour.objects.areaBasedItem)object;
                    if (areaBasedItem.getTel() != null && !areaBasedItem.getTel().equalsIgnoreCase("")) {
                        if (tourSettings != null) {
                            tourSettings.callPlace(areaBasedItem.getTel());
                        }
                    }
                }
                if(object instanceof FestivalItem){
                    FestivalItem festivalItem=(FestivalItem) object;
                    if (festivalItem.getTel() != null && !festivalItem.getTel().equalsIgnoreCase("")) {
                        if (tourSettings != null) {
                            tourSettings.callPlace(festivalItem.getTel());
                        }
                    }
                }
                if(object instanceof Favorites) {
                    Favorites favorites = (Favorites) object;
                    if (favorites.getTel() != null && !favorites.getTel().equalsIgnoreCase("")) {
                        if (tourSettings != null) {
                            tourSettings.callPlace(favorites.getTel());
                        }
                    }
                }
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tourSettings != null) {
                    final Object object=objects.get(position);
                    if(object instanceof areaBasedItem) {
                        final areaBasedItem areaBasedItem = (areaBasedItem) object;
                        tourSettings.onListItemClicked(areaBasedItem);
                    }
                    if(object instanceof FestivalItem){
                        final FestivalItem festivalItem=(FestivalItem)object;
                        tourSettings.onListItemClicked(festivalItem);
                    }
                    if(object instanceof Favorites){
                        final Favorites favorites=(Favorites) object;
                        tourSettings.onListItemClicked(favorites);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    public class TourView extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView title,address;
        ImageView call;
        View view;
        public TourView(android.view.View view) {
            super(view);
            this.view=view;
            circleImageView=(CircleImageView) view.findViewById(R.id.place_photo);
            title=(TextView) view.findViewById(R.id.title);
            address=(TextView) view.findViewById(R.id.address);
            call=(ImageView) view.findViewById(R.id.call);
        }
    }

}
