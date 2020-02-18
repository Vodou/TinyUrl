package com.cloudcampaignexam.tinyurlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.cloudcampaignexam.tinyurlapi.models.TinyUrl;
import com.cloudcampaignexam.tinyurlapi.utils.InMemoryCache;
import com.cloudcampaignexam.tinyurlapi.utils.TinyUrlHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;

public class TinyUrlManagementService {

    @Autowired
    TinyUrlHelper tinyUrlHelper;

    @Autowired
    InMemoryCache<String,TinyUrl> tinyUrlCache;


    private static final Logger logger = LoggerFactory.getLogger(TinyUrlManagementService.class);
    
    private static final String BASEURL = "http://localhost:8080/";
    private static final int SUFFIXSIZE = 5;

    public TinyUrlManagementService(){};

    private static List<TinyUrl> tinyUrls = new ArrayList<>();
    static{
       tinyUrls.add(new TinyUrl("guid1","fakeuserid", BASEURL, "a34sd", "http://google.com"));
       tinyUrls.add(new TinyUrl("guid2","fakeuserid", BASEURL, "s34ad", "http://twitter.com")) ;
    }

	public TinyUrl save(String userId, TinyUrl tinyUrl) {

        try{
            String id;
            Set<String> idSet = getAllIds();
            id = tinyUrlHelper.getUniqueID(null, idSet);
            logger.info("New id generated: {}", id);

            String tinyUrlSuffix;
            Set<String> tinyUrlSet = getAllTinyUrlSuffixs();    
            tinyUrlSuffix = tinyUrlHelper.getUniqueSuffix(null, tinyUrlSet, SUFFIXSIZE);;
            logger.info("New suffix generated: {}",tinyUrlSuffix);
            
            TinyUrl createdTinyUrl = new TinyUrl(id, userId, BASEURL, tinyUrlSuffix, tinyUrl.getRealUrl());

            //Save to DB
            tinyUrls.add(createdTinyUrl);
            logger.info("Tiny URL created successfully", createdTinyUrl);

            return createdTinyUrl;
        }
        catch (Exception e)
        {
            logger.error("Failed to save URL for user: {}", userId);
            logger.error(e.toString());

            throw e;
        }
	}

	public List<TinyUrl> findAll(String userId) {
        // Probably don't have to worry about cache here this would be consumers of api
        // could build in later
        try {
            List<TinyUrl> tinyUrlsforUser = tinyUrls.stream()
                                                .filter(tu -> userId.equals(tu.getUserId()))
                                                .collect(Collectors.toList());


            return tinyUrlsforUser;

        } catch (Exception e) {
            logger.error("Failed to retrieve Urls for user: {}", userId);
            logger.error(e.toString());
            throw e;
        }
	}

	public TinyUrl findById(String userId, String id) throws Exception{
        //get url based on id
        try {
            TinyUrl tinyUrl = findAll(userId).stream()
                                            .filter(tu -> id.equals(tu.getId()))
                                            .findFirst()
                                            .orElseThrow(() -> new ServletRequestBindingException("No resource with id: "+id+" found."));
            return tinyUrl;

        } catch (Exception e) {
            logger.error("Failed to retrieve Url with ID: {} for user: {}", id, userId);
            logger.error(e.toString());
            throw e;
        }
	}

	public TinyUrl findByName(String tinyUrlName) {
        try {           
            logger.info("Checking cache for tinyUrlName: {}", tinyUrlName);
            TinyUrl tinyUrl = tinyUrlCache.get(tinyUrlName);

            if (tinyUrl != null){
                logger.info("Retrieved TinyURL object from cache");
            }
            else{
                //simulate DB slowness
                logger.info("Checking DB for tinyUrlName: {}", tinyUrlName);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {} 
                
                tinyUrl = tinyUrls.stream().filter(tu -> tinyUrlName.equals(tu.getTinyUrlSuffix()))
                                            .findFirst()
                                            .orElseThrow(() -> new RuntimeException());
                logger.info("Retrieved TinyURL object from DB");

                //Add to LRUMap cache. Will automatically handles Last Retrieved queue
                if(tinyUrl != null){
                    tinyUrlCache.put(tinyUrlName, tinyUrl);
                    logger.info("TinyURL object added to cache");
                }
            }
            return tinyUrl;
        } catch (Exception e) {
            logger.error("Failed to retrieve Redirect URL for URL: {}", tinyUrlName);
            throw e;
        }
    }

    public Set<String> getAllIds(){
        return tinyUrls.stream().map(tu -> tu.getId()).collect(Collectors.toSet());
    }

    public Set<String> getAllTinyUrlSuffixs(){
        return tinyUrls.stream().map(tu -> tu.getTinyUrlSuffix()).collect(Collectors.toSet());
    }

    public TinyUrl deleteByID(String userId, String id) throws Exception  {
        try {

            TinyUrl tinyUrl = findById(userId, id);

            if (tinyUrl != null && tinyUrls.remove(tinyUrl)) {
                //Remove from cache if value in cache
                tinyUrlCache.remove(tinyUrl.getTinyUrlSuffix());
                return tinyUrl;
            }
            // if removal didn't happen
            return null;

        } catch (Exception e) {
            logger.error("Failed to delete TinyUrl");
            throw e;
        }
	}

	public TinyUrl update(String userId, String id, TinyUrl tinyUrl) throws Exception {
        try{
            TinyUrl updatedTinyUrl = findById(userId, id);

            if (updatedTinyUrl != null){
                //Update in DB
                //This should be done in a transaction 
                //at the db level so we could rollback on failure
                deleteByID(userId, id);             
                updatedTinyUrl.setRealUrl(tinyUrl.getRealUrl());
                tinyUrls.add(updatedTinyUrl);
            }
                return updatedTinyUrl;
        }
        catch (Exception e)
        {
            logger.error("Failed to Update TinyURL");
            throw e;
        }
	}


}
