package com.example.fleetifytest.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fleetifytest.R;
import com.example.fleetifytest.core.source.response.ListAllComplaintResponse;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {

    private List<ListAllComplaintResponse.Complaint> complaintList;


    public ComplaintAdapter(List<ListAllComplaintResponse.Complaint> complaintList) {
        this.complaintList = complaintList;
    }


    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaints, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        ListAllComplaintResponse.Complaint complaint = complaintList.get(position);
        holder.userId.setText(complaint.getReportId());
        holder.vehicleName.setText(complaint.getVehicleName());
        holder.vehicleNumber.setText(complaint.getVehicleLicenseNumber());
        holder.reportBy.setText(complaint.getCreatedBy());
        holder.note.setText(complaint.getNote());
        holder.date.setText(complaint.getCreatedAt());
        holder.status.setText(complaint.getReportStatus());

        Glide.with(holder.itemView.getContext())
                .load(complaint.getPhoto())
                .centerCrop()
                .into(holder.photo);



    }

    @Override
    public int getItemCount() {
        return complaintList != null ? complaintList.size() : 0;
    }


    public void setComplaintList(List<ListAllComplaintResponse.Complaint> complaintList) {
        this.complaintList = complaintList;
        notifyDataSetChanged();
    }


    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView complaintTitle;
        TextView vehicleName;
        TextView reportBy;
        TextView userId;
        TextView note;
        TextView date;
        TextView vehicleNumber;
        TextView status;
        ImageView photo;

        public ComplaintViewHolder(View itemView) {
            super(itemView);
            complaintTitle = itemView.findViewById(R.id.tv_complaint_title);
            vehicleName = itemView.findViewById(R.id.tv_vehicle_name);
            reportBy = itemView.findViewById(R.id.tv_report_by);
            userId = itemView.findViewById(R.id.tv_user_id);
            note = itemView.findViewById(R.id.tv_note);
            date = itemView.findViewById(R.id.tv_date);
            vehicleNumber = itemView.findViewById(R.id.tv_vehicle_number);
            status = itemView.findViewById(R.id.tv_status);
            photo = itemView.findViewById(R.id.iv_photo);
        }
    }
}
