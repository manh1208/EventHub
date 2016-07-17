package com.linhv.eventhub.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.custom.CustomImage;
import com.linhv.eventhub.model.ComponentItem;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ViewHolder> {
    private Context mContext;
    private List<ComponentItem> componentItems;

    public ComponentAdapter(Context mContext, List<ComponentItem> componentItems) {
        this.mContext = mContext;
        this.componentItems = componentItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_component_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ComponentItem componentItem= componentItems.get(position);
        if (componentItem.getUrl() != null && componentItem.getUrl().length() > 0) {
            String url = DataUtils.URL+componentItem.getUrl();
            Picasso.with(mContext).load(Uri.parse(url))
                    .placeholder(R.drawable.image_default_avatar)
                    .error(R.drawable.image_default_avatar)
                    .into(viewHolder.ivImage);
            viewHolder.ivImage.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivImage.setVisibility(View.GONE);
        }

        viewHolder.txtName.setText(componentItem.getName());
        viewHolder.txtDesc.setText(componentItem.getDescription());
        viewHolder.txtDetail.setText(componentItem.getDetail());

    }

    @Override
    public int getItemCount() {
        return componentItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtDesc;
        private TextView txtDetail;
        private CustomImage ivImage;

        public ViewHolder(View convertView) {
            super(convertView);
            ivImage = (CustomImage) convertView.findViewById(R.id.iv_item_image);
            txtName = (TextView) convertView.findViewById(R.id.txt_item_name);
            txtDesc = (TextView) convertView.findViewById(R.id.txt_item_description);
            txtDetail = (TextView) convertView.findViewById(R.id.txt_item_detail);
        }

    }

    public void setComponentItems(List<ComponentItem> componentItems) {
        this.componentItems = componentItems;
        notifyDataSetChanged();
    }
}
