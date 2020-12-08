package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momentos.R;

import java.util.ArrayList;

import entity.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private int _resource;
    private ArrayList<Service> _servicesList;

    public ServiceAdapter(ArrayList<Service> servicesList, int resource) {
        this._resource = resource;
        this._servicesList = servicesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(_resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = _servicesList.get(position);
        holder._inputTitle.setText(service.get_text());
    }

    @Override
    public int getItemCount() {
        return _servicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView _inputTitle;
        public View _view;

        public ViewHolder(View view) {
            super(view);

            this._view = view;
            this._inputTitle = (TextView) view.findViewById(R.id.inputTitle);
        }
    }
}
