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
import benkoreatech.me.tour.objects.areaBasedItem;
import de.hdodenhof.circleimageview.CircleImageView;

public class TourList extends RecyclerView.Adapter<TourList.TourView>{


    List<areaBasedItem> areaBasedItems;
    Context context;
    TourSettings tourSettings;

    public TourList(List<areaBasedItem> areaBasedItems, Context context, TourSettings tourSettings) {
        this.areaBasedItems=areaBasedItems;
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
        final areaBasedItem areaBasedItem=areaBasedItems.get(position);
        holder.title.setText(areaBasedItem.getTitle());
        holder.address.setText(areaBasedItem.getAddr1());
        if(areaBasedItem.getFirstimage()!=null) {
            Picasso.with(context).load(areaBasedItem.getFirstimage2()).fit().centerCrop().into(holder.circleImageView);
        }
        holder.call.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(areaBasedItem.getTel()!=null && !areaBasedItem.getTel().equalsIgnoreCase("")){
                    if(tourSettings!=null){
                        tourSettings.callPlace(areaBasedItem.getTel());
                    }
                }
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(tourSettings!=null){
                 tourSettings.onListItemClicked(areaBasedItems.get(position));
             }
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaBasedItems.size();
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
