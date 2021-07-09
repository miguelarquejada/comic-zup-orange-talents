package com.marb.zupcomics.client.marvel;

import com.marb.zupcomics.exception.ComicNotFoundException;
import com.marb.zupcomics.model.comic.Comic;
import com.marb.zupcomics.model.comic.ComicItem;
import com.marb.zupcomics.model.creator.Creator;
import com.marb.zupcomics.model.creator.CreatorItem;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Component
public class MarvelComicClient {

    private static final String PUBLIC_KEY = "759cfb14846959c95a0d11c2cd9fe0ef";
    private static final String PRIVATE_KEY = "6ae2ba0da8e641294571083cf9b53127874ddae4";

    @Autowired
    private MarvelComicFeignClient marvelComicFeignClient;

    public Comic getComicById(Long id) {
        long currentTimestamp = System.currentTimeMillis();
        String message = currentTimestamp +PRIVATE_KEY+PUBLIC_KEY;
        String hash = generateMd5Hash(message);

        ComicItem comicItem = null;
        try {
            comicItem = marvelComicFeignClient.getComicDataWrapper(id, currentTimestamp, PUBLIC_KEY, hash)
                                            .getData().getResults().get(0);
        } catch (FeignException.NotFound ex) {
            throw new ComicNotFoundException("We couldn't find this comic");
        }

        return generateComicFromComicItem(comicItem);
    }

    private DayOfWeek foundDiscountDayFromIsbn(String isbn) {
        char lastChar = isbn.charAt(isbn.length()-1);
        int lastNumberOfIsbn = Character.getNumericValue(lastChar);

        DayOfWeek dayOfWeek;
        switch (lastNumberOfIsbn) {
            case 0:
            case 1:
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case 2:
            case 3:
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case 4:
            case 5:
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case 6:
            case 7:
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case 8:
            case 9:
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + lastNumberOfIsbn);
        }

        return dayOfWeek;
    }

    private Comic generateComicFromComicItem(ComicItem comicItem) {
        Comic comic = new Comic();
        comic.setComicId(comicItem.getId());
        comic.setDescription(comicItem.getDescription());
        comic.setIsbn(comicItem.getIsbn());
        comic.setTitle(comicItem.getTitle());
        comic.setCreators(generatorCreatorListFromCreatorItemList(comicItem.getCreators().getItems()));
        comic.setPrice(comicItem.getPrices().get(0).getPrice());
        if(!comicItem.getIsbn().isEmpty())
            comic.setDiscount_day(foundDiscountDayFromIsbn(comicItem.getIsbn()));

        return comic;
    }

    private List<Creator> generatorCreatorListFromCreatorItemList(List<CreatorItem> creatorItemList) {
        List<Creator> creatorList = new ArrayList<>();
        for(CreatorItem creatorItem : creatorItemList)
            creatorList.add(generateCreatorFromCreatorItem(creatorItem));

        return creatorList;
    }

    private Creator generateCreatorFromCreatorItem(CreatorItem creatorItem) {
        Creator creator = new Creator();
        creator.setName(creatorItem.getName());
        return creator;
    }

    private String generateUrlGetComicById(Long id) {
        long currentTimestamp = System.currentTimeMillis();
        String message = currentTimestamp +PRIVATE_KEY+PUBLIC_KEY;
        String hash = generateMd5Hash(message);
        return "http://gateway.marvel.com/v1/public/comics/"+id+"?ts="+currentTimestamp+"&apikey="+PUBLIC_KEY+"&hash="+hash;
    }

    private String generateMd5Hash(String message) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(message.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toLowerCase();

        return myHash;
    }
}
