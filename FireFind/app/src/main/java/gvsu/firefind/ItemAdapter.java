package gvsu.firefind;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by droidowl on 2/27/16.
 */
public class ItemAdapter extends ArrayAdapter<FireFindItem> {
    Context context;
    int id;
    List<FireFindItem> items;
    private Bitmap bitmap;
    ItemHolder holder = null;

    public ItemAdapter(Context context, int resource, List<FireFindItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.id = resource;
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(id, parent, false);
            holder = new ItemHolder();
            holder.nameText = (TextView)row.findViewById(R.id.textViewName);
            holder.descText = (TextView)row.findViewById(R.id.textViewDesc);
            holder.imageView = (ImageView)row.findViewById(R.id
                    .imageView);
            row.setTag(holder);
        } else {
            holder = (ItemHolder)row.getTag();
        }
        FireFindItem item = items.get(position);
        Map uploadResult = item.getUploadResult();
        holder.descText.setText(item.getDesc());
        holder.nameText.setText(item.getName());
        if (uploadResult == null)  {
            return row;
        }

        Picasso.with(context).load(uploadResult.get("url").toString()).fit()
                .memoryPolicy(MemoryPolicy.NO_CACHE).into
                (holder.imageView);

        return row;
    }


    static class ItemHolder {
        TextView nameText;
        TextView descText;
        ImageView imageView;
    }
}