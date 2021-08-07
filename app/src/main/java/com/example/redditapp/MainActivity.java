/*
*
*
 */

package com.example.redditapp;

import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.redditapp.model.Feed;
import com.example.redditapp.model.entry.Entry;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // logt shortcut

    private static final String REDDIT_BASE_URL = "https://www.reddit.com/r/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrofit object generates implementation for Feed interface
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REDDIT_BASE_URL)

                // TODO
                // Deprecated. Change to JSON parsing
                // JSON faster and better for Android
                .addConverterFactory(SimpleXmlConverterFactory.create()) // For parsing XML
                .build();

        FeedAPI feedAPI = retrofit.create(FeedAPI.class);
        Call<Feed> call = feedAPI.getFeed();

        // Retrofit Callback interface communicates from a server or offline requests
        call.enqueue(new Callback<Feed>() {
            // This is on success for reading RSS feed
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                //Log.d(TAG, "onResponse: feed: " + response.body().toString()); // logs are seen in event log tool window
                Log.d(TAG, "onResponse: Server Response: " + response.toString()); // Prints HTTP server status code response

                List<Entry> entries = response.body().getEntries();

                Log.d(TAG, "onResponse: entries: " + entries);
//                Log.d(TAG, "onResponse: authors: " + entries.get(0).getAuthor());
//                Log.d(TAG, "onResponse: titles: " + entries.get(0).getTitle());
//                Log.d(TAG, "onResponse: updated: " + entries.get(0).getUpdated());

                // pulls Thread objects
                ArrayList<Thread> threadList = new ArrayList<>();

                // Extract from content tag
                for(Entry entry : entries){
                    // Extracting a href tags
                    ExtractXML extractXML = new ExtractXML("<a href=", entry.getContent());
                    List<String> extractContent = extractXML.extract();

                    // If entries have images, else nullpointer and add default image
                    ExtractXML extractXMLImage = new ExtractXML("<img src=", entry.getContent());
                    try {
                        // Have to replace amp; with empty string because of reddit encoding
                        //https://stackoverflow.com/questions/63611376/fetching-an-image-from-reddit-javascript-react-no-praw
                        extractContent.add(extractXMLImage.extract().get(0).replace("amp;",""));
                        //Log.d(TAG, "onResponse: Extract image: " + extractContent.get((extractContent.size()-1)));
                    } catch (NullPointerException e){
                        // When object is null, will input default image
                        extractContent.add(null);
                        Log.d(TAG, "onResponse: NullPointerException(image) " + e.getMessage());
                    }catch (IndexOutOfBoundsException e){  // If tag didn't exist at all
                        // When object is null, will input default image
                        extractContent.add(null);
                        Log.d(TAG, "onResponse: IndexOutOfBoundsException(image) " + e.getMessage());
                    }

                    int lastPos = extractContent.size() - 1;
                    Thread thread = new Thread(entry.getTitle(),entry.getAuthor().getName(),entry.getUpdated(), extractContent.get(0),extractContent.get(lastPos));
                    threadList.add(thread);

                }

                // Checking if data is correct
//                for(Thread thread : threadList){
//                    Log.d(TAG, "onResponse: " + "\n" +
//                            "Thread URL: " + thread.getURL() + "\n" +
//                            "Thumbnail URL: " + thread.getThumbnailURL() + "\n" +
//                            "Title: " + thread.getTitle() + "\n" +
//                            "Author: " + thread.getAuthor() + "\n" +
//                            "Date Updated: " + thread.getDateUpdated() + "\n"
//                    );
//                }

                // Creating listView
                ListView listView = (ListView) findViewById(R.id.listView);
                CustomListAdapter customListAdapter = new CustomListAdapter(MainActivity.this, R.layout.card_layout_main, threadList);
                listView.setAdapter(customListAdapter);

            }

            // This is on failure to read RSS feed
            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS feed: " + t.getMessage() );
                Toast.makeText(MainActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show(); // Toast msg is an alert for the user
            }
        });
    }
}