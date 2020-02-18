package com.cloudcampaignexam.tinyurlapi.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * TinyUrlHelper utils helper class for TinyUrl
 */
public class TinyUrlHelper {
    // Hardcode list for now find better way in the future
    private final ArrayList<Character> AVAILABLECHARS = new ArrayList<Character>(73);
    // Allowed Url chars (0-9), (A-Z, a-z), ($–_.+!*‘(),)
    // 10 + 26 + 26 + 11 = 73 possible characters
    // url suffixSize = n  
    // Math.pow(73, n) = total combinations 
    // n = 5 -> total combinations = 2,073,071,593 
    //will want to store only suffix in DB char(5) 20 btyes approx 40 GB of space for all entries
    //2,073,071,593 / 1000 urls per hour generated = 2,073,071 hours of url generation
    //2,073,071 / 24 hours in a day / 365 days in a year = 236 years of url generation before it runs out
    //needs to be balanced with db space and url creation collision.
    //Max runtime for site maybe 20 years -> 20 / 142 collision rate at end of life will be 8% might be acceptable 
    // n = 6 -> DB max size approx 300 GB, 17,275 years of generation, collision rate = .1%, seems like overkill

    public TinyUrlHelper(){
        String availablesAlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String availablesAlphaStringLower = availablesAlphaString.toLowerCase();
        String availablesNumString = "1234567890";
        String availablesSpecString = "$-_.+!*'(),";

        String availablesString = availablesAlphaString + availablesAlphaStringLower + availablesNumString + availablesSpecString;
        
        IntStream.range(0, availablesString.length())
				.forEach(index -> AVAILABLECHARS.add(availablesString.charAt(index)));
 
    };
    private String genTinyUrlSuffix(int suffixSize){

        StringBuilder suffix = new StringBuilder();
        Random rand = new Random();
        
        for (int i = 0; i < suffixSize; i++){
            suffix.append(AVAILABLECHARS.get(rand.nextInt(AVAILABLECHARS.size())).toString());
        }     
        return suffix.toString();

    }

    private String genTinyUrlId(){
 
        return UUID.randomUUID().toString(); 
    }

     /**
     * Generic to be used to check if object is not null and is not in set then it is unique and valid 
     * Maybe move to utils
     */
    public <T> boolean isUnique(T t, Collection<T> collection) {   
        return t != null && !collection.contains(t);
    }

    public String getUniqueID(String id, Collection<String> idCollection){
        //Will want to cap retries 
        while (!isUnique(id, idCollection)){
            id = genTinyUrlId();
        }
        return id;
    }

    public String getUniqueSuffix(String suffix, Collection<String> suffixCollection, int suffixSize){
        //Will want to cap retries
        while (!isUnique(suffix, suffixCollection)){
            suffix = genTinyUrlSuffix(suffixSize);
        }
        return suffix;
    }
}