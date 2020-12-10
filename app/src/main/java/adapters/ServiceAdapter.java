package adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.momentos.R;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import entity.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private ArrayList<Service> _servicesList;
    private ServiceAdapter.OnItemClickListener _clickListener;

    public ServiceAdapter(ArrayList<Service> servicesList,
                          ServiceAdapter.OnItemClickListener clickListener) {
        this._servicesList = servicesList;
        this._clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_view, parent, false);
        return new ViewHolder(view, _clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = _servicesList.get(position);
        ArrayList<String> images = service.get_images();

        holder._inputTitle.setText(service.get_title());
        holder._inputText.setText(service.get_text());
        holder._inputDate.setText(service.get_onCreate().toString());
        if (images.size() > 0)
            holder._image.setImageURI(Uri.parse(images.get(0)));
    }

    @Override
    public int getItemCount() {
        return _servicesList.size();
    }

    public interface OnItemClickListener {
        void onServiceClick(int position);
    }

    private String getUd(int position) {
        if (position != RecyclerView.NO_POSITION) {
            return _servicesList.get(position).get_uid();
        } else {
            return null;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        private TextView _inputTitle, _inputText, _inputDate;
        private ImageView _image;
        public View _view;

        OnItemClickListener onItemClickListener;

        public ViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            this.onItemClickListener = onItemClickListener;
            this._view = view;
            this._inputTitle = (TextView) view.findViewById(R.id.inputTitle);
            this._inputText = (TextView) view.findViewById(R.id.inputText);
            this._inputDate = (TextView) view.findViewById(R.id.inputDate);
            this._image = (ImageView) view.findViewById(R.id.image);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onServiceClick(getAdapterPosition());
        }
    }
}

/*
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {
    private ArrayList<Service> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ServiceAdapter(ArrayList<Service> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_view, parent, false).findViewById(R.id.inputTitle);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position).get_title());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}*/
