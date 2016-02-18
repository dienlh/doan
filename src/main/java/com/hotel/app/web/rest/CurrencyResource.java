package com.hotel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotel.app.domain.Currency;
import com.hotel.app.service.CurrencyService;
import com.hotel.app.web.rest.util.HeaderUtil;
import com.hotel.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Currency.
 */
@RestController
@RequestMapping("/api")
public class CurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyResource.class);
        
    @Inject
    private CurrencyService currencyService;
    
    /**
     * POST  /currencys -> Create a new currency.
     */
    @RequestMapping(value = "/currencys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Currency> createCurrency(@Valid @RequestBody Currency currency) throws URISyntaxException {
        log.debug("REST request to save Currency : {}", currency);
        if (currency.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("currency", "idexists", "A new currency cannot already have an ID")).body(null);
        }
        Currency result = currencyService.save(currency);
        return ResponseEntity.created(new URI("/api/currencys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("currency", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /currencys -> Updates an existing currency.
     */
    @RequestMapping(value = "/currencys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Currency> updateCurrency(@Valid @RequestBody Currency currency) throws URISyntaxException {
        log.debug("REST request to update Currency : {}", currency);
        if (currency.getId() == null) {
            return createCurrency(currency);
        }
        Currency result = currencyService.save(currency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("currency", currency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /currencys -> get all the currencys.
     */
    @RequestMapping(value = "/currencys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Currency>> getAllCurrencys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Currencys");
        Page<Currency> page = currencyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/currencys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /currencys/:id -> get the "id" currency.
     */
    @RequestMapping(value = "/currencys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Currency> getCurrency(@PathVariable Long id) {
        log.debug("REST request to get Currency : {}", id);
        Currency currency = currencyService.findOne(id);
        return Optional.ofNullable(currency)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /currencys/:id -> delete the "id" currency.
     */
    @RequestMapping(value = "/currencys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        log.debug("REST request to delete Currency : {}", id);
        currencyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("currency", id.toString())).build();
    }
}
