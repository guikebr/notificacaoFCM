package guilherme.viewapp.com.pushnotifications;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{


    private List<Notifications> mNotifList;

    private FirebaseFirestore mFirestore;
    private Context context;

    public NotificationsAdapter(Context context, List<Notifications> mNotifList){
        this.mNotifList = mNotifList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notification,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        mFirestore = FirebaseFirestore.getInstance();

        String from_id = mNotifList.get(position).getFrom();

        holder.mNotifMessage.setText(mNotifList.get(position).getMessage());

        mFirestore.collection("Users").document(from_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.getString("name");
                String image = documentSnapshot.getString("image");

                holder.mNotifName.setText(name);

                RequestOptions placeholderOptions = new RequestOptions();
                placeholderOptions.placeholder(R.mipmap.ic_user_profile);

                Glide.with(context).setDefaultRequestOptions(placeholderOptions).load(image).into(holder.mNotifImage);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mNotifList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;

        private CircleImageView mNotifImage;
        private TextView mNotifName;
        private TextView mNotifMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            mNotifImage = (CircleImageView) mView.findViewById(R.id.notif_image);
            mNotifName = (TextView) mView.findViewById(R.id.notif_name);
            mNotifMessage = (TextView) mView.findViewById(R.id.notif_message);
        }
    }


}
