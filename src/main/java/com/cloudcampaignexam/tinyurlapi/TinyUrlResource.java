package com.cloudcampaignexam.tinyurlapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.List;

import javax.validation.Valid;

import com.cloudcampaignexam.tinyurlapi.models.TinyUrl;
import com.cloudcampaignexam.tinyurlapi.utils.TinyUrlHelper;

@RestController
public class TinyUrlResource {

    @Autowired
    TinyUrlManagementService tinyUrlManagementService;

    @Autowired
    TinyUrlHelper tinyUrlHelper;

    private static final Logger logger = LoggerFactory.getLogger(TinyUrlResource.class);

    // Could Autowire Service call later if moving to micro service architecture
    // @Autowired
    // WebClient.Builder webClientBuilder;

    @RequestMapping("/urls")
    public List<TinyUrl> getAllUrls(@RequestHeader("userId") String userId) {
        // retrieve from service/DB
        return tinyUrlManagementService.findAll(userId.toLowerCase());

    }

    @RequestMapping("/urls/{id}")
    public TinyUrl getUrl(@RequestHeader("userId") String userId, @PathVariable("id") String id) throws Exception {
        // retrieve from service/DB
            return tinyUrlManagementService.findById(userId.toLowerCase(), id);
    }

    @PutMapping("/urls/{id}")
	public ResponseEntity<Object> updateUrl(@RequestHeader("userId") String userId, @PathVariable("id") String id,
			@Valid @RequestBody TinyUrl tinyUrl) throws Exception {
        //Need more robust Url validation
		tinyUrlManagementService.update(userId.toLowerCase(), id, tinyUrl);

		return ResponseEntity.ok("resource saved");
	}

    @PostMapping("/urls")
    public ResponseEntity<Object> createURL(@RequestHeader("userId") String userId, @Valid @RequestBody TinyUrl tinyUrl) {      
        //Need more robust Url validation
        TinyUrl createdTinyUrl = tinyUrlManagementService.save(userId.toLowerCase(), tinyUrl);

        //get location of the newly created resource
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTinyUrl.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping("{tinyUrlName}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable("tinyUrlName") String tinyUrlName) {
        try {
      
            TinyUrl tinyUrl = tinyUrlManagementService.findByName(tinyUrlName);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(tinyUrl.getRealUrl()));

            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);


        } catch (Exception e) {

            logger.error("Failed to redirect user via tinyUrl: {}", tinyUrlName);

            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);           
        }		
    }
}