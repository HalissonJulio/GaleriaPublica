package pereira.lopes.julio.halisson.galeirapublica;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

class ImageDataComparator extends DiffUtil.ItemCallback<ImageData> {

    @Override
    public boolean areItemsTheSame(@NonNull ImageData oldItem, @NonNull ImageData newItem) {
        // O ID é único.
        return oldItem.uri.equals(newItem.uri);
    }

    @Override
    public boolean areContentsTheSame(@NonNull ImageData oldItem, @NonNull ImageData newItem) {
        return oldItem.uri.equals(newItem.uri);
    }
}
