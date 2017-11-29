package luongvo.com.madara.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
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
    private int NUMBER_OF_TAG_ON_A_LINE = 4;


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

        // Adjust flexBoxLayout's alignment
        vhNote.flexboxLayout.setJustifyContent(JustifyContent.FLEX_END);
        vhNote.flexboxLayout.setFlexWrap(FlexWrap.NOWRAP);
        vhNote.flexboxLayout.setFlexDirection(FlexDirection.ROW);

        // Set values for a note
        vhNote.txtNoteTitle.setText(note.getTitle());
        vhNote.txtNoteTimeStamp.setText(note.getTimeStamp());
        vhNote.txtNoteDescription.setText(note.getContent());
        vhNote.imgNoteIcon.setImageResource(R.drawable.ic_note);

        // Load reminder date only if it's not null ( DIFFERENT from !isEmpty() )
        //TODO: Replace with function call to retrieve the representative icon of a note
        if(note.getReminderTime()!=null){
            vhNote.imgReminder.setImageResource(R.drawable.ic_reminder);
        }

        // TODO: Replace by actual ArrayList<TAG> tags
        ArrayList<String> tags = new ArrayList<>();
        tags.addAll(Arrays.asList(context.getResources().getStringArray(R.array.tags_example)));

        // TODO: Replace tags with the strings from the actual TAG data type
        loadStringsToTextViewToFlexBox(tags, vhNote.flexboxLayout);

        vhNote.data = note;

    }
    private void loadStringsToTextViewToFlexBox(ArrayList<String> tags, FlexboxLayout flexboxLayout){

        FlexboxLayout.LayoutParams flexBoxParams
                = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                 ViewGroup.LayoutParams.WRAP_CONTENT);

        flexBoxParams.setMargins( context.getResources().getDimensionPixelSize(R.dimen.chip_left_margin),
                                  context.getResources().getDimensionPixelSize(R.dimen.chip_top_margin),
                                  context.getResources().getDimensionPixelSize(R.dimen.chip_right_margin),
                                  context.getResources().getDimensionPixelSize(R.dimen.chip_bottom_margin));

        for(int i=0; i < tags.size() ; i++) {
            String tagName = tags.get(i);
            TextView txtThisTag = new TextView(context);
            txtThisTag.setLayoutParams(flexBoxParams);

            if (i < NUMBER_OF_TAG_ON_A_LINE) {

                txtThisTag.setText(tagName);

            if(i == NUMBER_OF_TAG_ON_A_LINE){   // If it's overflow a line,display a single tag to tell how much is left
                                                // and break out of the loop
                txtThisTag.setText( (tags.size() - NUMBER_OF_TAG_ON_A_LINE)
                              + context.getString(R.string.plusSign));
                break;
            }
        }


            txtThisTag.setBackgroundColor(context.getColor(R.color.gray));
            txtThisTag.setTextColor(Color.BLACK);
            txtThisTag.setTextSize( TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.chip_text_size));

            txtThisTag.setPadding(  context.getResources().getDimensionPixelSize(R.dimen.chip_left_padding),
                                    context.getResources().getDimensionPixelSize(R.dimen.chip_top_padding),
                                    context.getResources().getDimensionPixelSize(R.dimen.chip_right_padding),
                                    context.getResources().getDimensionPixelSize(R.dimen.chip_bottom_padding));

            flexboxLayout.addView(txtThisTag);
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
        private FlexboxLayout flexboxLayout;
        private ImageView imgReminder;
        private ImageView imgNoteIcon;

        private NoteViewHolder(View itemView) {
            super(itemView);

            txtNoteTitle = itemView.findViewById(R.id.noteTitle);
            txtNoteTimeStamp = itemView.findViewById(R.id.noteTimeStamp);
            txtNoteDescription = itemView.findViewById(R.id.noteDescription);
            imgNoteIcon =  itemView.findViewById(R.id.noteIcon);
            imgReminder = itemView.findViewById(R.id.noteReminder);
            flexboxLayout = itemView.findViewById(R.id.flexboxLayout);

        }
    }
}
