package luongvo.com.madara.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import luongvo.com.madara.QuickNoteEditorActivity;
import luongvo.com.madara.R;
import luongvo.com.madara.helper.ItemTouchHelperAdapter;
import luongvo.com.madara.model.Notebook;
import luongvo.com.madara.model.QuickNote;
import luongvo.com.madara.utils.Constants;


public class QuickNotesAdapter extends RecyclerView.Adapter<QuickNotesAdapter.QuickNoteViewHolder>
        implements ItemTouchHelperAdapter {

    private Context context;
    private List<QuickNote> arrQuickNotes;

    public QuickNotesAdapter(Context context, List<QuickNote> arrQuickNotes) {
        this.context = context;
        this.arrQuickNotes = arrQuickNotes;
    }

    @Override
    public QuickNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_quicknote, parent, false);
        return (new QuickNoteViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(final QuickNoteViewHolder quickNoteViewHolder, int position) {
        final QuickNote quickNoteResources = arrQuickNotes.get(position);

        // TODO: quickNoteResources - onBindViewHolder - please notice the binding process
        // Add resources to QuickNoteViewHolder's elements
        quickNoteViewHolder.txtQuickNoteTitle.setText(quickNoteResources.getTitle());
        quickNoteViewHolder.txtQuickNoteContent.setText(quickNoteResources.getContent());
        quickNoteViewHolder.txtQuickNoteContent.setMaxLines(quickNoteResources.getMaxLines());
        quickNoteViewHolder.layoutQuickNote
            .setCardBackgroundColor(context
            .getColor(context
            .getResources()
            .getIdentifier(quickNoteResources.getColor(), "color", context.getPackageName())));

        // Add events to QuickNoteViewHolder's elements
        quickNoteViewHolder.layoutQuickNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quickNoteEditor = new Intent(context, QuickNoteEditorActivity.class);
                quickNoteEditor.putExtra(Constants.intentQuickNoteId, quickNoteResources.getId());
                context.startActivity(quickNoteEditor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrQuickNotes.size();
    }

    @Override
    public void onItemDismiss(int position) {
        arrQuickNotes.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        // Re-arrange quickNotes in arrQuickNotes
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(arrQuickNotes, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(arrQuickNotes, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        // TODO: save new re-arranged arrQuickNotes to DB


        return true;
    }

    public class QuickNoteViewHolder extends RecyclerView.ViewHolder {
        public CardView layoutQuickNote;
        public TextView txtQuickNoteTitle, txtQuickNoteContent;


        public QuickNoteViewHolder(View itemView) {
            super(itemView);
            txtQuickNoteTitle = itemView.findViewById(R.id.txtQuickNoteTitle);
            txtQuickNoteContent = itemView.findViewById(R.id.txtQuickNoteContent);
            layoutQuickNote = itemView.findViewById(R.id.layoutQuickNote);
        }
    }
}
