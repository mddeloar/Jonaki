package com.example.mddeloarhossain.jonaki;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.ContextMenu;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.List;

        import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MD. DELOAR HOSSAIN on 01-May-19.
 */

public class AdapterBloodDonorProfileShow extends RecyclerView.Adapter<AdapterBloodDonorProfileShow.MyViewHolder>  {

    //****For Filtering here we use " implements Filterable " ****//
    private Context context;
    private List<AdapterBloodDonorsShowUpload> uploadList;
    //private List<AdapterBloodDonorsShowUpload> uploadList1;//****For Filtering ****//
    //private OnItemClickListener listener;

    public AdapterBloodDonorProfileShow(Context context, List<AdapterBloodDonorsShowUpload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
        ///uploadList1 = new ArrayList<>(uploadList);//****For Filtering****//
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_blood_donor_profile, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AdapterBloodDonorsShowUpload uploadWithDetails = uploadList.get(position);
        holder.name.setText(uploadWithDetails.getName());
        holder.city.setText(uploadWithDetails.getCity());
        holder.location.setText(uploadWithDetails.getLocation());
        holder.bloodgroup.setText(uploadWithDetails.getBloodgroup());
        Picasso.with(context)
                .load(uploadWithDetails.getImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView name, location, city, bloodgroup;
        //CircleImageView imageView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextViewId);
            city = itemView.findViewById(R.id.districtTextViewId);
            location = itemView.findViewById(R.id.areaTextViewId);
            bloodgroup = itemView.findViewById(R.id.bloodGroupTextViewId);
            imageView = itemView.findViewById(R.id.profile_image);

            //itemView.setOnClickListener(this);
            //itemView.setOnCreateContextMenuListener(this);
        }

        /*@Override
        public void onClick(View view) {

            if(listener != null){
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION){

                    listener.OnItemClick(position);
                }

            }

        }*/

        /*@Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.setHeaderTitle("Choose an action");
            MenuItem doAnyTask = contextMenu.add(Menu.NONE, 1, 1, "do any task");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "delete");

            doAnyTask.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }*/

      /*  @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(listener != null){
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION){

                    switch (menuItem.getItemId()){

                        case 1:
                            listener.onDoAnyTask(position);
                            return true;


                        case 2:
                            listener.onDelete(position);
                            return true;



                    }
                }

            }


            return false;
        }*/
    }

   /* public interface OnItemClickListener{
        void OnItemClick(int position);
        void onDoAnyTask(int position);
        void onDelete(int position);

    }


    public  void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;

    }*/


}



