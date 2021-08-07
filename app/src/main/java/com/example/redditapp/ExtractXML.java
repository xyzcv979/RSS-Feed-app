/*
* ExtractXML.java
*
* Every entry in each webpage has a content tag that cannot be called by Entry.java
* This class parses that tag and outputs the information within
*
 */

package com.example.redditapp;

import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

// input tag and xml
// Output filtered XML
public class ExtractXML {
    
    private static final String TAG = "ExtractXML";

    private String tag;
    private String xml;

    public ExtractXML(String tag, String xml) {
        this.tag = tag;
        this.xml = xml;
    }

    // Grabs all href tags
    public List<String> extract(){
        List<String> result  = new ArrayList<>();

        String[] splitXML = xml.split(tag + "\"");  // Splits between "
        int count = splitXML.length;

        for(int i = 1; i<count; i++){
            String temp = splitXML[i];
            int index;
            index = temp.indexOf("\"");

            // For debugging
            //Log.d(TAG,"extract: index: " + index);
            //Log.d(TAG, "extract: parsed xml: " + temp);

            temp = temp.substring(0,index);

            //Log.d(TAG, "extract: substring: " + temp);

            result.add(temp);
        }

        return result;
    }
}
