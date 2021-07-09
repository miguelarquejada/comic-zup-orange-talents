package com.marb.zupcomics.client.marvel;

import com.marb.zupcomics.model.comic.ComicDataWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="marvel", url = "http://gateway.marvel.com/v1/public/comics")
public interface MarvelComicFeignClient {

    @GetMapping("/{comicId}?ts={timest}&apikey={apiKey}&hash={hash}")
    ComicDataWrapper getComicDataWrapper(@PathVariable Long comicId, @PathVariable long timest,
                                         @PathVariable String apiKey, @PathVariable String hash);
}
