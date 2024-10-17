package com.techelevator.service;

import com.techelevator.dao.BeerDao;
import com.techelevator.dao.BreweryDao;
import com.techelevator.dao.StyleDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.UnauthorizedUserException;
import com.techelevator.model.Beer;
import com.techelevator.model.Brewery;
import com.techelevator.model.Style;
import com.techelevator.model.dto.BeerRequestDto;
import com.techelevator.model.dto.BeerResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class BeerService {
    private BeerDao beerDao;
    private UserDao userDao;
    private BreweryDao breweryDao;
    private StyleDao styleDao;

    public BeerService(BeerDao beerDao, UserDao userDao, BreweryDao breweryDao, StyleDao styleDao) {
        this.beerDao = beerDao;
        this.userDao = userDao;
        this.breweryDao = breweryDao;
        this.styleDao = styleDao;
    }

    public BeerResponseDto add(BeerRequestDto beerDto, String name) {
        if (!isAuthBrewer(beerDto.getBreweryId(), name)) {
            throw new UnauthorizedUserException();
        }
        Beer newBeer = convertDtoToBeer(beerDto);
        return convertBeerToDto(beerDao.createBeer(newBeer));
    }

    public void delete(int id, String name) {;
        Beer beer = beerDao.getBeerById(id);

        if (isAuthBrewer(beer.getBreweryId(), name)) {
            beerDao.deleteBeerById(id);
        } else {
            throw new UnauthorizedUserException();
        }
    }

    public List<BeerResponseDto> listByBrewery(int id, String query, Double minAbv, Double maxAbv, String styleName) {
        Style style = null;
        if (styleName != null) {
            style = styleDao.getStyleByName(styleName);
        }
        if (query == null && minAbv == null && maxAbv == null && style == null) {
            return  convertListToDto( beerDao.getBeersByBreweryId( id ) );
        } else if (minAbv == null && maxAbv == null && style == null) {
            return convertListToDto( beerDao.getBeersByBreweryQuery( id, query ) );
        } else if (maxAbv == null && query == null && style == null) {
            return convertListToDto( beerDao.getBeersByBreweryMinAbv( id, minAbv ) );
        } else if (query == null && minAbv == null && style == null) {
            return convertListToDto( beerDao.getBeersByBreweryMaxAbv( id, maxAbv ) );
        } else if (query == null && minAbv == null && maxAbv == null) {
            return convertListToDto( beerDao.getBeersByBreweryStyle( id, style.getStyleId() ) );
        } else if (maxAbv == null && style == null) {
            return convertListToDto( beerDao.getBeersByBreweryQueryMinAbv( id, query, minAbv ) );
        } else if (minAbv == null && maxAbv == null) {
            return convertListToDto( beerDao.getBeersByBreweryQueryStyle( id, query, style.getStyleId() ) );
        } else if (minAbv == null && style == null) {
            return convertListToDto( beerDao.getBeersByBreweryQueryMaxAbv( id, query, maxAbv ) );
        } else if (query == null && maxAbv == null) {
            return convertListToDto( beerDao.getBeersByBreweryMinAbvStyle( id, minAbv, style.getStyleId() ) );
        } else if (query == null && style == null) {
            return convertListToDto( beerDao.getBeersByBreweryAbvRange( id, minAbv, maxAbv ) );
        } else if (maxAbv == null) {
            return convertListToDto( beerDao.getBeersByBreweryQueryMinAbvStyle( id, query, minAbv, style.getStyleId() ) );
        } else if (style == null) {
            return convertListToDto( beerDao.getBeersByBreweryQueryAbvRange( id, query, minAbv, maxAbv ) );
        } else if (minAbv == null) {
            return convertListToDto( beerDao.getBeersByBreweryQueryMaxAbvStyle( id, query, maxAbv, style.getStyleId() ) );
        } else if (query == null) {
            return convertListToDto( beerDao.getBeersByBreweryAbvRangeStyle( id, minAbv, maxAbv, style.getStyleId() ) );
        } else {
            return convertListToDto( beerDao.getBeersByBreweryQueryAbvRangeStyle( id, query, minAbv, maxAbv, style.getStyleId() ) );
        }
    }

    private boolean isAuthBrewer(int breweryId, String name) {
        Brewery brewery = breweryDao.getBreweryById(breweryId);
        return brewery.getBrewerName().equals(name);
    }

    private BeerResponseDto convertBeerToDto(Beer beer) {
        return new BeerResponseDto(
                beer.getBeerId(),
                beer.getBeerName(),
                beer.getBreweryId(),
                beer.getBreweryName(),
                beer.getStyleName(),
                beer.getStyleId(),
                beer.getDesc(),
                beer.getAbv(),
                beer.isSeasonal(),
                beer.getSeasonName(),
                beer.getImgUrl());
    }

    private Beer convertDtoToBeer(BeerRequestDto dto) {
        Beer beer = new Beer();
        beer.setBeerName(dto.getBeerName());
        beer.setBreweryId(dto.getBreweryId());
        beer.setStyleId(dto.getStyle());
        beer.setDesc(dto.getDesc());
        beer.setAbv(dto.getAbv());
        beer.setSeasonal(dto.isSeasonal());
        if (beer.isSeasonal()) {
            beer.setSeasonName(dto.getSeasonName());
        }
        beer.setImgUrl(dto.getImgUrl());
        return beer;
    }

    private List<BeerResponseDto> convertListToDto(List<Beer> beers) {
        List<BeerResponseDto> dto = new ArrayList<>();
        for (Beer beer : beers) {
            dto.add(convertBeerToDto(beer));
        }
        return dto;
    }

    public BeerResponseDto getBeer(int id) {
        return convertBeerToDto(beerDao.getBeerById(id));
    }

    public BeerResponseDto update(int id, BeerRequestDto dto, String name) {
        if (!isAuthBrewer(dto.getBreweryId(), name)) {
            throw new UnauthorizedUserException();
        }
        Beer beer = convertDtoToBeer(dto);
        beer.setBeerId(id);
        return convertBeerToDto(beerDao.updateBeer(beer));
    }

    public List<BeerResponseDto> list() {
        return convertListToDto(beerDao.getBeers());
    }
}
