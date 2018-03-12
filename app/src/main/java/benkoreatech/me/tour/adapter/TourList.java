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
        holder.place_name.setText(areaBasedItem.getTitle());
        holder.date.setText(areaBasedItem.getTel());
        if(areaBasedItem.getFirstimage()!=null) {
            Picasso.with(context).load(areaBasedItem.getFirstimage()).fit().centerCrop().into(holder.image);
        }
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
        ImageView image;
        TextView place_name,date;
        View view;
        public TourView(android.view.View view) {
            super(view);
            this.view=view;
            image=(ImageView) view.findViewById(R.id.image);
            place_name=(TextView) view.findViewById(R.id.place_name);
            date=(TextView) view.findViewById(R.id.date);
        }
    }

}
