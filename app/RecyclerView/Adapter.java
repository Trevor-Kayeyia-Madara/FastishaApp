import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;  // Import for TextView

import androidx.annotation.NonNull;  // Import for NonNull
import androidx.recyclerview.widget.RecyclerView;  // Import for RecyclerView

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {

    private List<Receipt> receiptList;

    public ReceiptAdapter(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_item, parent, false);
        return new ReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        Receipt receipt = receiptList.get(position);
        holder.txtItem.setText(receipt.getItemType());
        holder.txtDescription.setText(receipt.getDescription());
        holder.txtFromLocation.setText(receipt.getFromLocation());
        holder.txtToLocation.setText(receipt.getToLocation());
        holder.txtPrice.setText("Ksh " + receipt.getPrice());
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public static class ReceiptViewHolder extends RecyclerView.ViewHolder {

        TextView txtItem, txtDescription, txtFromLocation, txtToLocation, txtPrice;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txtItem);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtFromLocation = itemView.findViewById(R.id.txtFromLocation);
            txtToLocation = itemView.findViewById(R.id.txtToLocation);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}
