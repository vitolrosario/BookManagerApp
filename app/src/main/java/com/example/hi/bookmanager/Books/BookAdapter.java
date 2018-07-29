package com.example.hi.bookmanager.Books;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hi.bookmanager.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Item> mBook;
    private Activity mActivity;
    private Context mContext;
    private static RecyclerViewClickListener mItemListener;
    // Pass in the contact array into the constructor
    public BookAdapter(List<Item> books, Activity activity/*, Context context*/, RecyclerViewClickListener itemListener) {
        mBook = books;
        mActivity = activity;
        mItemListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView bookTextView;
        public TextView authorTextView;
        public ImageView bookImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);

            bookTextView = (TextView) itemView.findViewById(R.id.book_name);
            authorTextView = (TextView) itemView.findViewById(R.id.book_author);
            bookImageView = (ImageView) itemView.findViewById(R.id.book_image);

        }

        @Override
        public void onClick(View v) {
            Item book = mBook.get(this.getLayoutPosition());
            mItemListener.recyclerViewListClicked(v, book);
        }
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View BookView = inflater.inflate(R.layout.item_book, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(BookView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder viewHolder, int position) {
        Item book = mBook.get(position);

        TextView bookText = viewHolder.bookTextView;
        bookText.setText(book.getVolumeInfo().getTitle());

        TextView authorText = viewHolder.authorTextView;
        String authors = "Unknown";
        List<String> authorList = new ArrayList<String>();

        authorList = book.getVolumeInfo().getAuthors();

        if (authorList != null && authorList.size() > 0)
        {
            authors = TextUtils.join(", ", book.getVolumeInfo().getAuthors());
        }

        authorText.setText("By: " + authors);

        ImageView bookImage = viewHolder.bookImageView;

        if (book.getVolumeInfo().getImageLinks() != null)
            Picasso.get().load(book.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(bookImage);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mBook.size();
    }


}