# musicAPI
## Welcome to my Backend Developer tech assignment!
This is a REST api with one single GET endpoint which returns a paginated list of jukeboxes that support a given setting id.

### Functionality

In order to use the application, you must run `src/main/java/sebastien.cantin.musicAPI/MusicApiApplication` or the docker conatiner
and use the url `http://localhost:8080/compatibleJukeboxes/{settingId}` that also supports the optional parameters `model`, `limit` and `offset`.

`model` filters the results by the given model name

`limit` specifies the maximum number of jukeboxes returned in the list

`offset` specifies the index that the list starts at, i.e. it skips that many jukeboxes at the beginning of the list

### Docker
You can also clone the dockerized version of my program at `https://hub.docker.com/repository/docker/seb8stien/music-api` or pull it using the command: `docker pull seb8stien/music-api:1.0.0` or run it immediately using the command `docker run -d -p local_port:8080 seb8stien/music-api:1.0.0` where `local_port` can be any port you wish to use, with 8080 usually being free.

If you did pull it first, in order to create a local container, you must first find the id of the image using the command `docker images` and finding the image id that corresponds to `seb8stien/music-api`

Then you must use the command `docker run -d -p local_port:8080 image_id` where you can replace `local_port` with the value 8080 or whatever other port you wish to use and `image_id` is the id found in the previous step

### Unit Tests
In order to run the unit tests, run the file `src/test/java/sebastien.cantin.musicAPI/MusicApiApplicationTests`

