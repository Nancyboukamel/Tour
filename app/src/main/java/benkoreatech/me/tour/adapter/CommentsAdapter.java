package benkoreatech.me.tour.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import benkoreatech.me.tour.R;
import benkoreatech.me.tour.objects.Comments;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsView>{
    List<Comments> comments;
    Context context;

    public CommentsAdapter(List<Comments> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public CommentsView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentrow, parent, false);
        return new CommentsAdapter.CommentsView(view);
    }

    @Override
    public void onBindViewHolder(CommentsView holder, int position) {
       Comments _comment=comments.get(position);
           if(_comment.getName()!=null && !_comment.getName().equalsIgnoreCase("")) {
               holder.username.setText(_comment.getName());
           }
           if(_comment.getRate()!=null && !_comment.getRate().equalsIgnoreCase("")) {
               holder.rating.setRating(Float.parseFloat(_comment.getRate()));
           }
           if(_comment.getComment()!=null && !_comment.getComment().equalsIgnoreCase("")) {
               holder.comment.setText(_comment.getComment());
           }
           if(_comment.getDate()!=null && !_comment.getDate().equalsIgnoreCase("")) {
               holder.date.setText(_comment.getDate());
           }
           if(_comment.getCommentTitle()!=null && !_comment.getCommentTitle().equalsIgnoreCase("")) {
               holder.title.setText(_comment.getCommentTitle());
           }

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentsView extends RecyclerView.ViewHolder {
        TextView username,date,title,comment;
        RatingBar rating;
        public CommentsView(View itemView) {
            super(itemView);
            username=(TextView) itemView.findViewById(R.id.username);
            date=(TextView) itemView.findViewById(R.id.date);
            title=(TextView) itemView.findViewById(R.id.title);
            comment=(TextView) itemView.findViewById(R.id.comment);
            rating=(RatingBar) itemView.findViewById(R.id.rating);
        }
    }

    public void Refresh(List<Comments> comments){
        this.comments=comments;
        notifyDataSetChanged();
    }
}
