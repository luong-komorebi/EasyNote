package luongvo.com.madara.adapters;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.pchmn.materialchips.ChipView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import luongvo.com.madara.R;
import luongvo.com.madara.libs.SwipeToAction;
import luongvo.com.madara.model.Note;


/**
 * Created by Thanh on 11/16/2017.
 */


public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DISPLAY_TAGS_NUM = 5 ;
    private static final long ENTRANCE_DURATION = 800 ;
    private static final long EXIT_DURATION = 800 ;
    private Context context;
    private ArrayList<Note> arrNotes;
    private boolean expanded = false;
    private int ITEM_DELAY_TIME = 100;

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
        vhNote.flexboxLayout.setJustifyContent(JustifyContent.FLEX_START);

        // set WRAP_CONTENT for each chip
        final FlexboxLayout.LayoutParams chipParams =
                new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        // set margins between chips
        chipParams.setMargins(context.getResources().getDimensionPixelSize(R.dimen.chip_left_margin),
                context.getResources().getDimensionPixelSize(R.dimen.chip_top_margin),
                context.getResources().getDimensionPixelSize(R.dimen.chip_right_margin),
                context.getResources().getDimensionPixelSize(R.dimen.chip_bottom_margin));

        // Fix items on the right-end of layout
        vhNote.flexboxLayout.setFlexWrap(FlexWrap.WRAP);
        vhNote.flexboxLayout.setFlexDirection(FlexDirection.ROW_REVERSE);

        // Set values for a note
        vhNote.txtNoteTitle.setText(note.getTitle());
        vhNote.txtNoteTimeStamp.setText(note.getTimeStamp());
        vhNote.txtNoteDescription.setText(note.getContent());
        vhNote.imgNoteIcon.setImageResource(R.drawable.ic_note);
        loadReminderChipToFlexBox(vhNote.flexboxLayout, chipParams, note);

        // TODO: Replace by actual ArrayList<TAG> tags
        ArrayList<String> tags = new ArrayList<>();
        tags.addAll(Arrays.asList(context.getResources().getStringArray(R.array.tags_example)));

        // Load note's tags
        loadStringsToChipsToFlexBox(vhNote.flexboxLayout,chipParams, note, tags);

        vhNote.data = note;

    }

    // load a tag with clock icon if the note has reminderDate
    private void loadReminderChipToFlexBox(final FlexboxLayout flexboxLayout, final FlexboxLayout.LayoutParams chipParams,
                                              Note note){
        if(note.hasReminderDate()) {
            final ChipView reminderChip = setUpChipViews(chipParams, context.getDrawable(R.drawable.ic_reminder));
            reminderChip.setChipBackgroundColor(Color.YELLOW);
            reminderChip.setLabel("!");
            populateChipsToFlexBox(new ArrayList<>(Collections.singletonList(reminderChip)), flexboxLayout);
        }
    }


    private void loadStringsToChipsToFlexBox(final FlexboxLayout flexboxLayout, final FlexboxLayout.LayoutParams chipParams,
                                   Note note, ArrayList<String> tags){

        if(tags.isEmpty()){
            return;
        }

        final ArrayList<String> displayTags = new ArrayList<>(tags.subList(0,
                                                (tags.size() <= DISPLAY_TAGS_NUM) ? tags.size() : DISPLAY_TAGS_NUM));
        final ArrayList<String> hiddenTags = new ArrayList<>(tags.subList(DISPLAY_TAGS_NUM, tags.size()));
        ArrayList<ChipView> displayChips = new ArrayList<>();
        final ArrayList<ChipView> hiddenChips = new ArrayList<>();

        for(String tagName: displayTags){
            displayChips.add(setUpChipViews(chipParams, tagName));
        }
        for(String tagName: hiddenTags){
            hiddenChips.add(setUpChipViews(chipParams, tagName));
        }

        // Show and animate tags in displayTags
        populateChipsToFlexBox(displayChips, flexboxLayout);
        addExpandChip(flexboxLayout, chipParams, hiddenChips);
    }

    private void addExpandChip(final FlexboxLayout flexboxLayout, final FlexboxLayout.LayoutParams chipParams,
                               final ArrayList<ChipView> hiddenChips){

        final ChipView expandChip = setUpChipViews(chipParams, String.valueOf(hiddenChips.size()));
        expandChip.setChipBackgroundColor(Color.MAGENTA);

        // A Click on expand chip removes it from layout, populate chips in hidden tags, and add shrinkChip again
        expandChip.setOnChipClicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runExitAnimation(flexboxLayout, expandChip);
                flexboxLayout.removeView(expandChip);
                populateChipsToFlexBox(hiddenChips, flexboxLayout);
                addShrinkChip(flexboxLayout, chipParams, hiddenChips);
            }
        });

        runEnterAnimation(expandChip);
        flexboxLayout.addView(expandChip);
    }

    private void addShrinkChip(final FlexboxLayout flexboxLayout, final FlexboxLayout.LayoutParams chipParams,
                               final ArrayList<ChipView> hiddenChips){

        final ChipView shrinkChip = setUpChipViews(chipParams, context.getString(R.string.shrinkString));
        shrinkChip.setChipBackgroundColor(Color.MAGENTA);

        // A Click on shrink chip removes it from layout, remove hidden chips in hidden tags, and add expandChip
        shrinkChip.setOnChipClicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runExitAnimation(flexboxLayout, shrinkChip);
                removeChipsFromFlexBox(hiddenChips, flexboxLayout);
                flexboxLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addExpandChip(flexboxLayout, chipParams, hiddenChips);
                    }
                    /* Delay until ( all chips in hiddenChips have been removed AND flexBoxLayout width gets resized )
                    * NOTE: Layout width gets shrunk as twice as fast as the animation
                    * => ( time + time* 0.5 = 1.5 * time (millis) )
                    */
                },(hiddenChips.size()) * (int)( ITEM_DELAY_TIME * 1.5));
            }
        });

        runEnterAnimation(shrinkChip);
        flexboxLayout.addView(shrinkChip);
    }

    // Animation is delayed by stacking ( index * ITEM_TIME_DELAY )
    private void setUpEntryAnimationOfChipAtIndex(final ChipView chip, int index){

        // addView set the layout and draw again, therefore set INVISIBLE before drawing to avoid flickering
        chip.setVisibility(View.INVISIBLE);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                runEnterAnimation(chip);
            }
            // delay by ITEM_DELAY_TIME * i between each chip entry animation
        }, ITEM_DELAY_TIME *index);
    }

    // Animation is delayed by stacking ( index * ITEM_TIME_DELAY )
    private void setUpEscapeAnimationOfChipAtIndex(final FlexboxLayout flexboxLayout, final ChipView chip, int index){


        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runExitAnimation(flexboxLayout, chip);

            }
            // delay by 100*i between each chip entry animation
        }, ITEM_DELAY_TIME * index);
    }

    private void populateChipsToFlexBox(ArrayList<ChipView> displayChips,
                                       FlexboxLayout flexboxLayout){

        for(int i=0; i< displayChips.size(); i++){
            ChipView chip = displayChips.get(i);
            // Set up animation and add chip to layout
            setUpEntryAnimationOfChipAtIndex(chip, i);
            flexboxLayout.addView(chip);
        }
    }

    private void removeChipsFromFlexBox(ArrayList<ChipView> removeChips,
                                        FlexboxLayout flexboxLayout){

        // Remove from the bottom ( last item ) to the top ( first item ) but we still want delay increases normally
        for(int i = removeChips.size() - 1; i >=0 ; i--){
            ChipView chip = removeChips.get(i);
            setUpEscapeAnimationOfChipAtIndex(flexboxLayout, chip, (removeChips.size() - 1) - i );
            // DO NOT removeView here, otherwise animation will not be seen
        }
    }

    // Return a chip with text given params and a string
    private ChipView setUpChipViews(FlexboxLayout.LayoutParams chipParams, String tagName){
        final ChipView chip = new ChipView(context);
        chip.setLayoutParams(chipParams);
        chip.setLabel(tagName);

        //TODO: Set individual tag's styling here
        chip.setLabelColor(context.getColor(R.color.chipLabelColor));
        chip.setChipBackgroundColor(context.getColor(R.color.chipBackgroundColor));

        return chip;
    }

    // Return a chip with icon given params and a drawable
    private ChipView setUpChipViews(FlexboxLayout.LayoutParams chipParams, Drawable drawable){
        final ChipView chip = new ChipView(context);
        chip.setLayoutParams(chipParams);
        chip.setAvatarIcon(drawable);
        return chip;
    }

    private void runEnterAnimation(final View view){
        // get (height, width) of the screen
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        view.setTranslationX(metrics.widthPixels);
        view.animate()
                .translationX(0)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(ENTRANCE_DURATION)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        // Since View was set to INVISIBLE, reveal the view to animate
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .start();
    }

    // runExitAnimation needs parentView ( FlexBoyLayout ) in this case to removeView onAnimationEnd
    private void runExitAnimation(final FlexboxLayout flexboxLayout, final View view){
        // get (height, width) of the screen
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        view.setTranslationX(0);
        view.animate()
                .translationX(metrics.widthPixels)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(EXIT_DURATION)
                .setListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animator) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        // hide the view and remove it from the parent
                        view.setVisibility(View.GONE);
                        flexboxLayout.removeView(view);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }

                })
                .start();
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
        private ImageView imgNoteIcon;

        private NoteViewHolder(View itemView) {
            super(itemView);

            txtNoteTitle = itemView.findViewById(R.id.noteTitle);
            txtNoteTimeStamp = itemView.findViewById(R.id.noteTimeStamp);
            txtNoteDescription = itemView.findViewById(R.id.noteDescription);
            imgNoteIcon =  itemView.findViewById(R.id.noteIcon);
            flexboxLayout = itemView.findViewById(R.id.flexboxLayout);

        }
    }
}
