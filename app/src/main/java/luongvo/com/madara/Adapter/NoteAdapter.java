package luongvo.com.madara.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import co.dift.ui.SwipeToAction;
import luongvo.com.madara.Model.Note;
import luongvo.com.madara.R;


public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Note> items;

    public class NoteViewHolder extends SwipeToAction.ViewHolder<Note>{

        public TextView titleView;
        public TextView authorView;
        public SimpleDraweeView imageView;

        public NoteViewHolder(View v) {
            super(v);
            titleView = (TextView) v.findViewById(R.id.title);
            authorView = (TextView) v.findViewById(R.id.author);
            imageView = (SimpleDraweeView) v.findViewById(R.id.image);
        }
    }
    public NoteAdapter(List<Note> items){
        this.items = items;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_view, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Note item = items.get(position);
        NoteViewHolder vh = (NoteViewHolder) holder;
        vh.titleView.setText(item.getTitle());
        vh.authorView.setText(item.getAuthor());
        vh.imageView.setImageURI(Uri.parse(item.getImageURL()));
        vh.data = item;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return -1;
    } // return 0 and it will cause a crash whenever the list size is reduced to 0
        // ( when the last item on the list is removed )
}
