package its.antiragg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintViewHolder> {
    private Context mCtx;
    public TextView options;
    private List<Complaint> complaintList;
    private FirebaseFirestore db;
    private static final String TAG = "ComplaintsAdapter";
    public ComplaintsAdapter(Context mCtx, List<Complaint> complaintList) {
        this.mCtx = mCtx;
        this.complaintList = complaintList;
    }
    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ComplaintViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_complaint, parent, false)
        );
    }

    class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView textViewcname, textViewphone, textViewmail, textViewvname, textViewdetails,options;
        public ComplaintViewHolder(View itemView) {
            super(itemView);
            textViewcname = itemView.findViewById(R.id.textview_cname);
            textViewphone = itemView.findViewById(R.id.textview_phone);
            textViewmail = itemView.findViewById(R.id.textview_mail);
            textViewvname = itemView.findViewById(R.id.textview_vname);
            textViewdetails = itemView.findViewById(R.id.textview_details);
            options = itemView.findViewById(R.id.textview_menu);
            db = FirebaseFirestore.getInstance();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ComplaintViewHolder holder, int position) {
        final Complaint complaint = complaintList.get(position);
        holder.textViewcname.setText(complaint.getComplainant_Name());
        holder.textViewphone.setText(complaint.getPhone_No());
        holder.textViewmail.setText(complaint.getE_Mail());
        holder.textViewvname.setText(complaint.getVictim_Name());
        holder.textViewdetails.setText(complaint.getComplaint_Details());
        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mCtx, holder.options);
                popupMenu.inflate(R.menu.complaints_processing_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final CollectionReference complaintsRef = db.collection("complaints");
                        switch (item.getItemId()){
                            case R.id.processing:

                                complaintsRef.whereEqualTo("id", complaint.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Map<Object, String> map = new HashMap<>();
                                                map.put("status", "Processing...");
                                                complaintsRef.document(document.getId()).set(map, SetOptions.merge());
                                            }
                                        }
                                    }
                                });

                                break;
                            case R.id.solved:
                                complaintsRef.whereEqualTo("id", complaint.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Map<Object, String> map = new HashMap<>();
                                                map.put("status", "Solved !!!");
                                                complaintsRef.document(document.getId()).set(map, SetOptions.merge());
                                            }
                                        }
                                    }
                                });

                                break;
                            default:
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return complaintList.size();
    }

}