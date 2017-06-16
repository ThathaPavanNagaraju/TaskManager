package com.taskmanager.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.taskmanager.Fragments.TasksFragment.OnListFragmentInteractionListener;
import com.taskmanager.Fragments.dummy.DummyContent.DummyItem;
import com.taskmanager.MainActivity;
import com.taskmanager.Models.Task;
import com.taskmanager.R;
import com.taskmanager.Utils.DatabaseHelper;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final TasksFragment.OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public TaskRecyclerViewAdapter(List<Task> items, TasksFragment.OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mTvName.setText(holder.mItem.getName());
        holder.mTVDescription.setText(holder.mItem.getDescription());
        holder.mCreatedDate.setText("Created On - "+holder.mItem.getCreatedDate());
        holder.mUpdatedDate.setText("Updated On - "+holder.mItem.getModifiedDate());
        holder.imgPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(mContext, holder.imgPopup);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.edit_task_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menu_update){
                            mListener.onUpdateTask(holder.mItem);
                        }
                        if(item.getItemId() == R.id.menu_delete){
                            new DatabaseHelper(mContext).deleteTask(holder.mItem);
                            mValues.remove(position);
                            notifyDataSetChanged();
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTvName;
        public final TextView mTVDescription;
        public final TextView mCreatedDate;
        public final TextView mUpdatedDate;
        private final ImageView imgPopup;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTvName = (TextView) view.findViewById(R.id.tvName);
            mTVDescription = (TextView) view.findViewById(R.id.tvDescription);
            mCreatedDate = (TextView) view.findViewById(R.id.tvCreatedDate);
            mUpdatedDate = (TextView)view.findViewById(R.id.tvUpdatedDate);
            imgPopup = (ImageView) view.findViewById(R.id.imgPopup);
        }
    }
}
