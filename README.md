# URL Shortner API 

API to shorten Urls. Can be used to create, retrieve, and updated shortened Urls. Shortened Urls can be used to redirect users to associated "real Urls".

## Commands
**Retrieve** - Returns all urls associated with current user as JSON.
* Action - GET
* Headers - userId (required)
* Path - /urls

**Retrieve by ID** - Returns id specific url associated with current user as JSON.
* Action - GET
* Headers - userId (required)
* Path - /urls/{id}

**Create** - Creates a new tiny url for specific user. Location of new resource is returned in header.
* Action - POST
* Headers - userId (required)
* Content-Type - application/json
* Path - /urls
* Body - TinyUrl (realUrl requried)

**Update** - Updates an existing tiny url by id. 
* Action - PUT
* Headers - userId (required)
* Content-Type - application/json
* Path - /urls/{id}
* Body - TinyUrl (realUrl requried)


## Objects
### TinyUrl
Model for shortened URL
#### Fields
* id (String)
* userId (String) - Might not want to store here. In the future could have a structure to link and normalize the relationship
* baseUrl (String) - For deployment convenience should be configured via deployment script/file
* tinyUrlSuffix (String)
* realUrl (String) - Must be formatted as a URL during PUT and POST (Need more robust validation right now it only ensures protocol is present)
* createdDate (Date)

### InMemoryCache (Authored by crunchify)
LRUCache wrapper used to store a maximum number of tinyUrl to URL mapping. Also has optional TTL functionality to remove stale entries using a daemon thread.
#### Constructor
```public InMemoryCache(long timeToLive, final long timerInterval, int maxItems)``` 
#### Fields
* timeToLive (long)
* cacheMap (LRU)


## Limitations
### Scalability
Currently the API is a single application. However, load could be unbalanced for certain calls. For example redirecting the shortened Url to the real Url will probably experience must more load than the CRUD portion of the API. In this case, services could be split out with CRUD handled on one and redirect on another. Could spin up multiple instances of application and have it load balanced for user calls. Additionally, if we see user access is spread accross the country, we could have instances in different availability zones. Tradeoffs would be flexibly load balancing vs network latency, increased storage needs, data orchestration, and deployment coordination.

### Performance
Currently the API only has a cache for the redirect all other operations pull from source. A cache for retrieve operations could also be included.  Additionally, a cache could be put at the method level instead to cached calls.


## Future Enhancements

### Features
* URL expiration
  * Could be simply based on first accessed time
  * Could be based on formula: 
    * (MinTime+1/(DeltaTimeFirstHit+1)-1) + ((NumHitsPerTimePeriod - RequiredHits)/RequiredHits) + 1
    * Allows for Minimum amount of time it has to exist after first hit
    * Accounts for number of hits during a certain time period
    * If it falls below zero it would get queued up for deletion

* Tailoring TTL for cached objects
* Scaling out the system
* Additional Unit tests
* Integration tests
* Improve Error Response

### Security
Considerations should include:
* Limits on call frequencey to prevent DOS attacks
* Limits on payload size
* More robust input validation

## Random Design Notes

Allowed Url chars = (0-9), (A-Z, a-z), ($–_.+!*‘(),)
10 + 26 + 26 + 11 = 73 possible characters
Url Suffix Size = n  
Math.pow(73, n) = total combinations 
n = 5 -> total combinations = 2,073,071,593 
will want to store only suffix in DB char(5) 5 btyes approx 10 GB of space for all suffix not including surrounding data
2,073,071,593 / 1000 urls per hour generated = 2,073,071 hours of url generation
2,073,071 / 24 hours in a day / 365 days in a year = 236 years of url generation before it runs out
needs to be balanced with db space and url creation collision.
Max runtime for site maybe 20 years -> 20 / 142 collision rate at end of life will be 8% might be acceptable 
n = 6 -> DB max size approx 900 GB, 17,275 years of generation, collision rate = .1%, seems like overkill