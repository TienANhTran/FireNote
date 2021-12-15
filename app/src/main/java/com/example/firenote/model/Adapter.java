package com.example.firenote.model;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firenote.note.NoteDetails;
import com.example.firenote.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    private List<NoteProperty> listNoteProperty;
    private List<NoteProperty> listNotePropertyOld;

    public Adapter(List<NoteProperty> listNoteProperty) {
        this.listNoteProperty = listNoteProperty;
        listNotePropertyOld = new ArrayList<>(listNoteProperty);
    }

    int i;
    private ViewHolder holder;
    private int position;

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NoteProperty noteProperty = listNoteProperty.get(position);
        holder.noteTitle.setText(noteProperty.getTitle());
        holder.noteContent.setText(noteProperty.getContent());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(getRandomColor(),null));
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),NoteDetails.class);
                i.putExtra("title",noteProperty.getTitle());
                i.putExtra("content",noteProperty.getContent());
                i.putExtra("code",getRandomColor());
                v.getContext().startActivity(i);
            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.red);
        colorCode.add(R.color.greenlight);
        colorCode.add(R.color.notgreen);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return colorCode.get(number);
    }

    @Override
    public int getItemCount() {
        return  listNoteProperty.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView noteTitle,noteContent;
        View view;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            view = itemView;
            mCardView = itemView.findViewById(R.id.noteCard);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<NoteProperty> filterList = new ArrayList<>();
                String strSearch = constraint.toString();
                if(strSearch.isEmpty())
                {
                    filterList.addAll(listNotePropertyOld);
                }
                else
                {
                    for(NoteProperty note : listNotePropertyOld)
                    {
                        if(note.getTitle().toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            filterList.add(note);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listNoteProperty.clear();
                listNoteProperty.addAll((Collection<? extends NoteProperty>) results.values);
                notifyDataSetChanged();
            }
        };
    }

}
