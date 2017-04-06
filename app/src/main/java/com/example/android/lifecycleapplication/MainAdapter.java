package com.example.android.lifecycleapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {

    private TheApplication applicationContext;
    private String viewgroupId;
    private final Context mContext;
    private final ListItemClickListner mOnClickListener;

    // В этом приложении 5 View
    private static final int NUMBER_OF_VIEWS = 5;
    private static final int VIEW_MAIN_ID = 0;
    private static final int VIEW_APPLICATION_ID = 1;
    private static final int VIEW_ACTIVITY_ID = 2;
    private static final int VIEW_VIEWGROUP_ID = 3;
    private static final int VIEW_VIEW_ID = 4;

    public interface ListItemClickListner {
        void onListItemClick(String componentType);
    }

    public MainAdapter(Context context, ListItemClickListner listener) {
        mContext = context;
        mOnClickListener = listener;
        viewgroupId = context.getString(R.string.log_viewgroup_id);
        applicationContext = (TheApplication) context.getApplicationContext();
        applicationContext.addLifecycleCallback(viewgroupId, "Создание MainAdapter");

    }

    public class MainAdapterViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        public final TextView mHeaderTextView;
        public final TextView mDescriptionTextView;
        public final ImageView mLeftScrollable;
        public final ImageView mRightScrollable;
        public final NewButton mNewButton;

        MainAdapterViewHolder(View view) {

            super(view);

            mHeaderTextView = (TextView) view.findViewById(R.id.tv_item_header);
            mDescriptionTextView = (TextView) view.findViewById(R.id.tv_item_description);
            mLeftScrollable = (ImageView) view.findViewById(R.id.iv_left);
            mRightScrollable = (ImageView) view.findViewById(R.id.iv_right);
            mNewButton = (NewButton) view.findViewById(R.id.b_show_log);
            mNewButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.b_show_log) {
                String item_id;
                switch (getAdapterPosition()) {
                    case VIEW_APPLICATION_ID:
                        item_id = mContext.getString(R.string.log_application_id);
                        break;
                    case VIEW_ACTIVITY_ID:
                        item_id = mContext.getString(R.string.log_activity_id);
                        break;
                    case VIEW_VIEWGROUP_ID:
                        item_id = mContext.getString(R.string.log_viewgroup_id);
                        break;
                    case VIEW_VIEW_ID:
                        item_id = mContext.getString(R.string.log_view_id);
                        break;
                    default:
                        item_id = mContext.getString(R.string.log_all_id);
                        break;
                }
                mOnClickListener.onListItemClick(item_id);
            }
        }

    }

    @Override
    public MainAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.main_list_item, viewGroup, false);

        applicationContext.addLifecycleCallback(viewgroupId, "Создание ViewHolder");

        return new MainAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapterViewHolder mainAdapterViewHolder, int position) {
        String header = "";
        String description = "";
        switch (position) {
            case VIEW_MAIN_ID:
                header = mContext.getString(R.string.info_main_header);
                description = mContext.getString(R.string.info_main_description);
                mainAdapterViewHolder.mLeftScrollable.setVisibility(View.INVISIBLE);
                break;
            case VIEW_APPLICATION_ID:
                header = mContext.getString(R.string.info_application_header);
                description = mContext.getString(R.string.info_application_description);
                break;
            case VIEW_ACTIVITY_ID:
                header = mContext.getString(R.string.info_activity_header);
                description = mContext.getString(R.string.info_activity_description);
                break;
            case VIEW_VIEWGROUP_ID:
                header = mContext.getString(R.string.info_viewgroup_header);
                description = mContext.getString(R.string.info_viewgroup_description);
                break;
            case VIEW_VIEW_ID:
                header = mContext.getString(R.string.info_view_header);
                description = mContext.getString(R.string.info_view_description);
                mainAdapterViewHolder.mRightScrollable.setVisibility(View.INVISIBLE);
                break;
        }
        mainAdapterViewHolder.mHeaderTextView.setText(header);
        mainAdapterViewHolder.mDescriptionTextView.setText(description);

        applicationContext.addLifecycleCallback(viewgroupId, "Привязка ViewHolder к адаптеру, position: " + position);
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_VIEWS;
    }

}