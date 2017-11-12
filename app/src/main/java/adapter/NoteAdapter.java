package adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import helper.CircleTransform;
import helper.FlipAnimator;
import model.Note;
import luongvo.com.madara.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder>{
    private Context mContext;
    private List<Note> notes;
    private NoteAdapterListener listener;
    private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView author, title, snippet, iconText, timeStamp;
        public ImageView iconImp, imgProfile;
        public LinearLayout noteContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;

        public MyViewHolder(View view){
            super(view);

            // INITIALIZE ALL VIEWS HERE
            author = view.findViewById(R.id.note_author);
            title =  view.findViewById(R.id.note_title);
            snippet = view.findViewById(R.id.note_snippet);
            iconText = view.findViewById(R.id.icon_text);

            iconImp = view.findViewById(R.id.icon_star);
            imgProfile = view.findViewById(R.id.icon_profile);

            iconBack = view.findViewById(R.id.icon_back);
            iconFront = view.findViewById(R.id.icon_front);

            noteContainer = view.findViewById(R.id.note_container);
            iconContainer = view.findViewById(R.id.icon_container);

            view.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public NoteAdapter(Context mContext, List<Note> notes, NoteAdapterListener listener){
        this.mContext = mContext;
        this.notes = notes;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_note, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = notes.get(position);

        Log.v("TAG", "Before setText 1");
        // displaying note's text_view data
        holder.author.setText(note.getAuthor());
        Log.v("TAG", "Before setText 2");
        holder.title.setText(note.getTitle());
        Log.v("TAG", "Before setText 3");
        holder.snippet.setText(note.getSnippet());

        holder.iconText.setText(note.getTitle().substring(0,1));

        Log.v("TAG", "After setText's");

        holder.itemView.setActivated(selectedItems.get(position,false));

        applyIconAnimation(holder, position);

        applyClickEvents(holder, position);

        applyProfilePicture(holder, notes.get(position));

        applyReadStatus(holder,notes.get(position));

    }

    private void applyIconAnimation(MyViewHolder holder, int position) {
        if(selectedItems.get(position, false)){

            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);
            if(currentSelectedIndex == position){
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        }else{
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if((reverseAllAnimations && animationItemsIndex.get(position, false))
                    || (currentSelectedIndex == position)){
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false);
            }

        }
    }

    private void applyClickEvents(final MyViewHolder holder, final int position) {
        holder.iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.iconImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconImportantClicked(position);
            }
        });

        holder.noteContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNoteRowClicked(position);

            }
        });

        holder.noteContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    private void applyReadStatus(MyViewHolder holder, Note note){
        if(note.isRead()){
            holder.author.setTypeface(null, Typeface.NORMAL);
            holder.title.setTypeface(null, Typeface.NORMAL);
            holder.title.setTextColor(ContextCompat.getColor(mContext, R.color.message));
            holder.snippet.setTypeface(null, Typeface.NORMAL);
        }else{
            holder.author.setTypeface(null, Typeface.BOLD);
            holder.title.setTypeface(null, Typeface.BOLD);
            holder.title.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
            holder.snippet.setTypeface(null, Typeface.BOLD);
        }
    }

    private void applyProfilePicture(MyViewHolder holder, Note note) {
        if (!TextUtils.isEmpty(note.getPicture())) {
            Glide.with(mContext).load(note.getPicture())
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgProfile);
            holder.imgProfile.setColorFilter(null);
            holder.iconText.setVisibility(View.GONE);
        } else {
            holder.imgProfile.setImageResource(R.drawable.bg_circle);
            holder.imgProfile.setColorFilter(note.getColor());
            holder.iconText.setVisibility(View.VISIBLE);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    // Sometimes the icon will be flipped because older view is reused, reset the axis to be sure
    private void resetIconYAxis(View view) {
        if(view.getRotation()!=0){
            view.setRotation(0);
        }
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for(int i=0; i< selectedItems.size();i++){
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void toggleSelection(int pos){
        currentSelectedIndex = pos;
        if(selectedItems.get(pos, false)){
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        }else{
            selectedItems.put(pos,true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelection(){
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void removeData(int position){
        notes.remove(position);
        resetCurrentIndex();
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getID();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public interface NoteAdapterListener {

        void onIconClicked(int position);
        void onIconImportantClicked(int position);
        void onNoteRowClicked(int position);
        void onRowLongClicked(int position);
    }
}
