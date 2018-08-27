package com.fj.mad06;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> listFile;

    public FileAdapter(Context context, ArrayList<String> listFile) {
        this.context = context;
        this.listFile = listFile;
    }

    public ArrayList<String> getListFile() {
        return listFile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_file, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.txtFile.setText(getListFile().get(i));

        viewHolder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SaveLocalActivity.class);
                intent.putExtra("KeyFileName", getListFile().get(i));
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListFile().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtFile;
        CardView cvItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFile = itemView.findViewById(R.id.txt_file_name);
            cvItem = itemView.findViewById(R.id.cv_item);
        }
    }
}
