# musicAPI
### Welcome to my Backend Developer tech assignment!
This is a REST api with one single GET endpoint which returns a paginated list of jukeboxes that suppport a given setting id.

In order to test use the application, you must run `src/main/java/sebastien.cantin.musicAPI/MusicApiApplication` 
and use the url `http://localhost:8080/compatibleJukeboxes/{settingId}` that also supports the optional parameters `model`, `limit` and `offset`.

`model` filters the results by the given model name

`limit` specifies the maximum number of jukeboxes returned in the list

`offset` specifies the index that the list starts at, i.e. it skips that many jukeboxes at the beginning of the list

You can also clone the dockerized version of my program at `...`

In order to run the unit tests, run the file `src/test/java/sebastien.cantin.musicAPI/MusicApiApplicationTests`

