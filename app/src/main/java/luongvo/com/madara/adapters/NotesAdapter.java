package luongvo.com.madara.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import co.dift.ui.SwipeToAction;
import luongvo.com.madara.R;
import luongvo.com.madara.model.Note;

/**
 * Created by Thanh on 11/16/2017.
 */


public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Note> arrNotes;

    public NotesAdapter(Context context, ArrayList<Note> arrNotes) {
        this.context = context;
        this.arrNotes = arrNotes;
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
        return (new NoteViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Note note = arrNotes.get(position);

        /*
         * Display snippet and timeStamp of a note
         */
        NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
        noteViewHolder.txtNoteSnippet.setText(note.getSnippet(15));
        noteViewHolder.txtNoteTimeStamp.setText("Created on "+ note.getTimeStamp());
        noteViewHolder.data = note;
    }

    @Override
    public int getItemCount() { return arrNotes.size(); }


    public class NoteViewHolder extends SwipeToAction.ViewHolder<Note> {
        public TextView txtNoteSnippet;
        public TextView txtNoteTimeStamp;
        public SimpleDraweeView imgNoteIcon;
        public NoteViewHolder(View itemView) {
            super(itemView);

            txtNoteSnippet = itemView.findViewById(R.id.noteSnippet);
            txtNoteTimeStamp = itemView.findViewById(R.id.noteTimeStamp);
            imgNoteIcon =  itemView.findViewById(R.id.noteIcon);
        }
    }
}
