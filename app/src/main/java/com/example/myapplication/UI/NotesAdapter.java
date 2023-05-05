package com.example.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Notes;
import com.example.myapplication.entities.Term;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter <NotesAdapter.NotesViewHolder> {
    class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView notesItemView;

        private NotesViewHolder(View itemView){
            super(itemView);
            notesItemView=itemView.findViewById(R.id.textviewnotename);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Notes current=mNotes.get(position);
                    Intent intent=new Intent(context, NotesDetails.class);
                    intent.putExtra("id", current.getNotesID());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("name", current.getNoteName());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Notes> mNotes;
    private final Context context;
    private final LayoutInflater mInflater;

    public NotesAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.note_list_item,parent,false);
        return new NotesAdapter.NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {
        if(mNotes!=null){
            Notes current=mNotes.get(position);
            int ID = current.getNotesID();
            holder.notesItemView.setText(String.valueOf(ID));
        }
        else {
            holder.notesItemView.setText("No Note");
        }

    }
    public void setNotes(List<Notes> notes){
        mNotes=notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mNotes!=null){
            return mNotes.size();}
        else return 0;
    }
}
