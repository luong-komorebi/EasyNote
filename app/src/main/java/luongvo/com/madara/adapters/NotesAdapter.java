package luongvo.com.madara.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.flexbox.FlexboxLayout;
import com.pchmn.materialchips.ChipView;

import java.util.ArrayList;
import java.util.Arrays;

import luongvo.com.madara.R;
import luongvo.com.madara.libs.SwipeToAction;
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

        NoteViewHolder vhNote = (NoteViewHolder) holder;

        vhNote.txtNoteTitle.setText(note.getTitle());

        vhNote.txtNoteTimeStamp.setText(note.getTimeStamp());

        //TODO: Set the actual description ( if any ) instead of content
        vhNote.txtNoteDescription.setText(note.getContent());

        //TODO: Decide wether to set and set the actual reminder date
        vhNote.txtNoteReminder.setText(R.string.reminderDateStr);

        //TODO: replace with [BACK_END] function_call to return representative image of a note
        vhNote.imgNoteIcon.setImageResource(R.drawable.ic_note);

        loadTagsToFlexBox(vhNote.flexboxLayout, note);

        vhNote.data = note;
    }


    private void loadTagsToFlexBox(FlexboxLayout flexboxLayout, Note note){

        // TODO: Replace by actual ArrayList<TAG> tags
        ArrayList<String> tags = new ArrayList<>();

        //TODO: replace with the actual tags of the notes
        tags.addAll(Arrays.asList(context.getResources().getStringArray(R.array.tags_example)));

        if(tags.isEmpty()){
            return;
        }

        FlexboxLayout.LayoutParams chipParams =
                new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT);
        // distance ( margins ) between ChipViews
        chipParams.setMargins(context.getResources().getDimensionPixelSize(R.dimen.chip_left_margin),
                                 context.getResources().getDimensionPixelSize(R.dimen.chip_top_margin),
                                  context.getResources().getDimensionPixelSize(R.dimen.chip_right_margin),
                                   context.getResources().getDimensionPixelSize(R.dimen.chip_bottom_margin));

        for(String tagName: tags){
            ChipView chip = new ChipView(context);
            chip.setLayoutParams(chipParams);
            chip.setLabel(tagName);

            //TODO: Set individual tag's styling here
            chip.setLabelColor(context.getColor(R.color.chipLabelColor));
            chip.setChipBackgroundColor(context.getColor(R.color.chipBackgroundColor));

            chip.setOnChipClicked(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Handle click action on a tag
                }
            });
            chip.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //TODO: Handle long click action on a tag
                    return false;
                }
            });
            flexboxLayout.addView(chip);
        }
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
        private FlexboxLayout flexboxLayout;

        private ImageView imgNoteIcon;

        private NoteViewHolder(View itemView) {
            super(itemView);

            txtNoteTitle = itemView.findViewById(R.id.noteTitle);
            txtNoteTimeStamp = itemView.findViewById(R.id.noteTimeStamp);
            txtNoteDescription = itemView.findViewById(R.id.noteDescription);
            txtNoteReminder = itemView.findViewById(R.id.noteReminder);
            imgNoteIcon =  itemView.findViewById(R.id.noteIcon);
            flexboxLayout = itemView.findViewById(R.id.flexboxLayout);

        }
    }
}
