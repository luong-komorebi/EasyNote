package luongvo.com.madara.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.dift.ui.SwipeToAction;
import luongvo.com.madara.R;
import luongvo.com.madara.model.Note;
import luongvo.com.madara.utils.LeadingMargin;

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
        NoteViewHolder vhNote = (NoteViewHolder) holder;

        vhNote.txtNoteTitle.setText(note.getTitle());
        vhNote.txtNoteTimeStamp.setText(note.getTimeStamp());

        //TODO: Set the actual description ( if any ) instead of content
        SpannableString ssNoteDescription = new SpannableString(note.getContent());

        // call measure() so that getMeasuredWidth returns actual width ( otherwise it returns 0 )
        vhNote.txtNoteTimeStamp.measure(0,0);

        ssNoteDescription       // push the 1st line of noteDescription
                                // to the left of noteTimeStamp ( + 10 as padding )
                                // while other lines of note Description use
                                // all the spaces below noteTimeStamp
                .setSpan(new LeadingMargin(1, vhNote.txtNoteTimeStamp.getMeasuredWidth() + 10 )
                            , 0
                            , ssNoteDescription.length(), 0);
        vhNote.txtNoteDescription.setText(ssNoteDescription);

        //TODO: Decide wether to set and set the actual reminder date
        vhNote.txtNoteReminder.setText("ReminderDate");

        //TODO: load note's desired icon here
        vhNote.imgNoteIcon.setImageResource(R.drawable.ic_note);

        vhNote.data = note;
    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }


    public class NoteViewHolder extends SwipeToAction.ViewHolder<Note> {
        private TextView txtNoteTitle;
        private TextView txtNoteTimeStamp;
        private TextView txtNoteDescription;
        private TextView txtNoteReminder;
        private ImageView imgNoteIcon;

        private NoteViewHolder(View itemView) {
            super(itemView);

            txtNoteTitle = itemView.findViewById(R.id.noteTitle);
            txtNoteTimeStamp = itemView.findViewById(R.id.noteTimeStamp);
            txtNoteDescription = itemView.findViewById(R.id.noteDescription);
            txtNoteReminder = itemView.findViewById(R.id.noteReminder);
            imgNoteIcon =  itemView.findViewById(R.id.noteIcon);
        }
    }
}
