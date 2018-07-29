package com.example.hi.bookmanager;

import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hi.bookmanager.Books.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Item book = (Item) getIntent().getSerializableExtra("detail");

        TextView book_name = (TextView) findViewById(R.id.det_book_name);
        book_name.setText(book.getVolumeInfo().getTitle());

        TextView book_author = (TextView) findViewById(R.id.det_book_author);
        String authors = "Unknown";
        List<String> authorList = new ArrayList<String>();

        authorList = book.getVolumeInfo().getAuthors();

        if (authorList != null && authorList.size() > 0)
        {
            authors = TextUtils.join(", ", book.getVolumeInfo().getAuthors());
        }

        book_author.setText("By: " + authors);

        ImageView book_image = (ImageView) findViewById(R.id.det_book_image);
        Picasso.get().load(book.getVolumeInfo().getImageLinks().getThumbnail()).into(book_image);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        if (book.getVolumeInfo().getAverageRating() != null)
            ratingBar.setRating((float)(double)book.getVolumeInfo().getAverageRating());

        TextView book_rating_count = (TextView) findViewById(R.id.det_book_rating_count);
        int count = 0;

        if (book.getVolumeInfo().getRatingsCount() != null)
            count = book.getVolumeInfo().getRatingsCount();

        book_rating_count.setText(count + " users rated this");

        TextView book_date = (TextView) findViewById(R.id.det_book_published_date);
        book_date.setText("Publishedd on " + book.getVolumeInfo().getPublishedDate());

        TextView book_description = (TextView) findViewById(R.id.det_book_description);
        book_description.setText(book.getVolumeInfo().getDescription());

        TextView book_sale_available = (TextView) findViewById(R.id.det_book_sale_available);
        LinearLayout book_sale_container = (LinearLayout) findViewById(R.id.det_book_sale_container);
        AppCompatButton btn_buy = (AppCompatButton) findViewById(R.id.det_book_btn_buy);
        LinearLayout book_price_container = (LinearLayout) findViewById(R.id.det_book_price_container);
        TextView book_list_price = (TextView) findViewById(R.id.det_book_listed_price);
        book_list_price.setPaintFlags(book_list_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView book_retail_price = (TextView) findViewById(R.id.det_book_retail_price);


        if (book.getSaleInfo().getSaleability().equals("NOT_FOR_SALE")) {
            book_sale_container.setBackgroundColor(getResources().getColor(R.color.colorSaleFalse));
            book_sale_available.setTextColor(getResources().getColor(R.color.colorBlack));
            book_sale_available.setText("This book is not available in " + book.getSaleInfo().getCountry());

            btn_buy.setVisibility(Button.GONE);
            book_price_container.setVisibility(LinearLayout.GONE);
        }
        else {
            book_sale_container.setBackgroundColor(getResources().getColor(R.color.colorSaleTrue));
            book_sale_available.setTextColor(getResources().getColor(R.color.colorBlack));
            book_sale_available.setText("This book is available in " + book.getSaleInfo().getCountry());
            book_list_price.setText(book.getSaleInfo().getListPrice().getCurrencyCode() + "$" + book.getSaleInfo().getListPrice().getAmount());
            book_retail_price.setText(book.getSaleInfo().getRetailPrice().getCurrencyCode() + "$" + book.getSaleInfo().getRetailPrice().getAmount());

            btn_buy.setVisibility(Button.VISIBLE);
            book_price_container.setVisibility(LinearLayout.VISIBLE);

            btn_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse(book.getSaleInfo().getBuyLink()));
                    startActivity(browser);
                }
            });

        }

        AppCompatButton btn_sample = (AppCompatButton) findViewById(R.id.det_book_btn_sample);
        if (book.getAccessInfo().getAccessViewStatus().equals("SAMPLE"))
        {
            btn_sample.setVisibility(Button.VISIBLE);
            btn_sample.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse(book.getAccessInfo().getWebReaderLink()));
                    startActivity(browser);
                }
            });

        }
        else
        {
            btn_sample.setVisibility(Button.GONE);
        }




    }
}
