package luongvo.com.madara.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import luongvo.com.madara.R;
import luongvo.com.madara.model.Notebook;

/**
 * Created by VVLv on 11/7/2017.
 */

public class NotebooksAdapter extends RecyclerView.Adapter<NotebooksAdapter.NotebookViewHolder> {

    private Context context;
    private ArrayList<Notebook> arrNotebooks;

    public NotebooksAdapter(Context context, ArrayList<Notebook> arrNotebooks) {
        this.context = context;
        this.arrNotebooks = arrNotebooks;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notebook, parent, false);
        return (new NotebookViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(final NotebookViewHolder notebookViewHolder, int position) {
        Notebook notebookResources = arrNotebooks.get(position);

        // TODO: notebookResources - onBindViewHolder - please notice the binding process
        /*
         * Add resources to notebookViewHolder's elements
         */
        notebookViewHolder.txtNotebookTitle.setText(notebookResources.getTitle());
        Picasso.with(context).load(notebookResources.getCoverId()).into(notebookViewHolder.imgNotebookCover);

        /*
         * Add events to notebookViewHolder's elements
         */
        notebookViewHolder.layoutNotebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: show Notes in chosen Notebook
            }
        });
        notebookViewHolder.layoutNotebook.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toggleNotebookProperties(notebookViewHolder);
                return true;
            }
        });
        notebookViewHolder.btnNotebookInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: show Info of Notebook
            }
        });
        notebookViewHolder.btnNotebookLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: lock Notebook

            }
        });
        notebookViewHolder.btnNotebookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Delete Notebook

            }
        });
    }

    private void toggleNotebookProperties(NotebookViewHolder notebookViewHolder) {
        LinearLayout layoutNotebook = notebookViewHolder.layoutNotebook;
        /*
         * Constants to adjust rotating process
         */
        float rotationYValue = 25f;
        float translationXValue = 50f;
        float scaleXValue = 0.75f;
        float scaleYValue = 0.95f;
        if (layoutNotebook.getRotationY() == 0) {
            /*
             * Open Properties
             */
            layoutNotebook.animate().scaleX(scaleXValue).start();
            layoutNotebook.animate().scaleY(scaleYValue).start();
            layoutNotebook.animate().translationXBy(-translationXValue).start();
            layoutNotebook.animate().rotationY(rotationYValue).start();
        } else {
            /*
             * Close Properties
             */
            layoutNotebook.animate().scaleX(1f).start();
            layoutNotebook.animate().scaleY(1f).start();
            layoutNotebook.animate().translationXBy(translationXValue).start();
            layoutNotebook.animate().rotationY(0f).start();
        }
    }


    @Override
    public int getItemCount() {
        return arrNotebooks.size();
    }



    public class NotebookViewHolder extends RecyclerView.ViewHolder {
        public ImageView btnNotebookInfo, btnNotebookLock, btnNotebookDelete;
        public ImageView imgNotebookCover;
        public TextView txtNotebookTitle;
        public LinearLayout layoutNotebook;


        public NotebookViewHolder(View itemView) {
            super(itemView);
            btnNotebookInfo = itemView.findViewById(R.id.btnNotebookInfo);
            btnNotebookLock = itemView.findViewById(R.id.btnNotebookLock);
            btnNotebookDelete = itemView.findViewById(R.id.btnNotebookDelete);
            imgNotebookCover = itemView.findViewById(R.id.imgNotebookCover);
            txtNotebookTitle = itemView.findViewById(R.id.txtNotebookTitle);
            layoutNotebook = itemView.findViewById(R.id.layoutNotebook);
        }
    }
}
