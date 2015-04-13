package com.hrca.hrvoje.companymanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Adapter of subjects. Views are of type SubjectView.
 *
 * @see com.hrca.hrvoje.companymanager.Subject
 * @see com.hrca.hrvoje.companymanager.SubjectView
 * Created by hrvoje on 13.04.15..
 */
public class SubjectGridAdapter extends BaseAdapter {

    /**
     * Application context.
     */
    protected Context context;

    /**
     * Currently handled subjects.
     */
    protected final ArrayList<Subject> subjects;

    /**
     * Create a new SubjectGridAdapter.
     *
     * @param context  Application context.
     * @param subjects Subjects to handle.
     */
    public SubjectGridAdapter(Context context, ArrayList<Subject> subjects) {
        this.context = context;
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return this.subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return this.subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubjectView subjectView;
        if (convertView == null) {
            // Initialize subjectView
            subjectView = new SubjectView(context);
            subjectView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            //cast
            subjectView = (SubjectView) convertView;
        }
        // Assign subject
        subjectView.setSubject(this.subjects.get(position));
        return subjectView;
    }
}
