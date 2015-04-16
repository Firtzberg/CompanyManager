package com.hrca.hrvoje.companymanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Adapter of resources. Views are of type ResourceView.
 *
 * @see Resource
 * @see com.hrca.hrvoje.companymanager.ResourceView
 * Created by hrvoje on 13.04.15..
 */
public class ResourceGridAdapter extends BaseAdapter {

    /**
     * Application context.
     */
    protected Context context;

    /**
     * Currently handled resources.
     */
    protected final ArrayList<? extends Resource> resources;

    /**
     * Create a new ResourceGridAdapter.
     *
     * @param context   Application context.
     * @param resources Resources to handle.
     */
    public ResourceGridAdapter(Context context, ArrayList<? extends Resource> resources) {
        this.context = context;
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return this.resources.size();
    }

    @Override
    public Object getItem(int position) {
        return this.resources.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return this.resources.get(position).isAffordable(1.0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResourceView resourceView;
        if (convertView == null) {
            // Initialize resourceView
            resourceView = new ResourceView(context);
            resourceView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
        } else {
            //cast
            resourceView = (ResourceView) convertView;
        }
        // Assign resource
        resourceView.setResource(this.resources.get(position));
        return resourceView;
    }
}
