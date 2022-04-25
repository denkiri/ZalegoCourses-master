package com.denkiri.zalego.adapters;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.denkiri.zalego.DetailActivity;
import com.denkiri.zalego.MpesaActivity;
import com.denkiri.zalego.R;
import com.denkiri.zalego.models.Courses;
import com.denkiri.zalego.models.Student;

import com.denkiri.zalego.storage.PreferenceManager;
import java.util.List;
public class RegisteredCoursesAdapter extends RecyclerView.Adapter<RegisteredCoursesAdapter.ViewHolder> {
    private List<Courses> listItems;
    private Context context;
    private ProgressDialog dialog;
    final Student student = PreferenceManager.getInstance(context).getStudent();


    public RegisteredCoursesAdapter(List<Courses> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public ImageView image;
        public TextView name,period;
        public TextView price;
        public CardView card_view;
        public ViewHolder(View itemView ) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            name = (TextView) itemView.findViewById(R.id.textViewTitle);
            period = (TextView) itemView.findViewById(R.id.textPeriod);
            price = (TextView) itemView.findViewById(R.id.textViewPrice);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Courses listItem = listItems.get(position);
        Glide.with(context)
                .load(listItem.getImage())
                .into(holder.image);
        holder.name.setText(listItem.getCourseName());
        holder.period.setText(listItem.getDuration());
        holder.price.setText(String.valueOf(listItem.getPrice()));

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                final CharSequence[] dialogitem = {"View Course","Pay for Course"};
                builder.setTitle(listItem.getCourseName());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                                intent.putExtra("id", listItem.getId());
                                intent.putExtra("name",listItem.getCourseName());
                                intent.putExtra("description",listItem.getDescription());
                                intent.putExtra("price",listItem.getPrice());
                                intent.putExtra("campus",listItem.getCampus());
                                intent.putExtra("duration",listItem.getDuration());
                                intent.putExtra("rating",listItem.getRating());
                                intent.putExtra("image",listItem.getImage());
                                intent.putExtra("category_id",listItem.getCategory_id());
                                view.getContext().startActivity(intent);
                                break;
                            case 1 :
                                Intent intent2= new Intent(view.getContext(), MpesaActivity.class);
                                view.getContext().startActivity(intent2);


                                break;
                        }
                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}


