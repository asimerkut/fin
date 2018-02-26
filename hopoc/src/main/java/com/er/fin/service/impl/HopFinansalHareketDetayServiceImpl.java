package com.er.fin.service.impl;

import com.er.fin.service.HopFinansalHareketDetayService;
import com.er.fin.domain.HopFinansalHareketDetay;
import com.er.fin.repository.HopFinansalHareketDetayRepository;
import com.er.fin.repository.search.HopFinansalHareketDetaySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.BeanUtils;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HopFinansalHareketDetay.
 */
@Service
@Transactional
public class HopFinansalHareketDetayServiceImpl implements HopFinansalHareketDetayService {

    private final Logger log = LoggerFactory.getLogger(HopFinansalHareketDetayServiceImpl.class);

    private final HopFinansalHareketDetayRepository hopFinansalHareketDetayRepository;

    private final HopFinansalHareketDetaySearchRepository hopFinansalHareketDetaySearchRepository;

    private void deleteKarsiDetay(Long id){
        hopFinansalHareketDetayRepository.deleteKarsiDetay(id);
        hopFinansalHareketDetayRepository.flush();
    }

    private void saveKarsiDetay(HopFinansalHareketDetay finansalHareketDetay){
        if (finansalHareketDetay.getIlgi()!=null){
            return;
        }
        deleteKarsiDetay(finansalHareketDetay.getId());
        HopFinansalHareketDetay karsi = new HopFinansalHareketDetay();
        BeanUtils.copyProperties(finansalHareketDetay, karsi);
        karsi.setId(null);
        karsi.setKod(finansalHareketDetay.getKod()+"#");
        karsi.setHesapYonu((-1)*finansalHareketDetay.getHesapYonu());
        karsi.setIlgi(finansalHareketDetay);
        karsi.setHesap(finansalHareketDetay.getKarsiHesap());
        karsi.setKarsiHesap(finansalHareketDetay.getHesap());
        karsi = hopFinansalHareketDetayRepository.save(karsi);
        System.out.println("KarsiId:"+karsi.getId());
    }

    public HopFinansalHareketDetayServiceImpl(HopFinansalHareketDetayRepository hopFinansalHareketDetayRepository, HopFinansalHareketDetaySearchRepository hopFinansalHareketDetaySearchRepository) {
        this.hopFinansalHareketDetayRepository = hopFinansalHareketDetayRepository;
        this.hopFinansalHareketDetaySearchRepository = hopFinansalHareketDetaySearchRepository;
    }

    /**
     * Save a hopFinansalHareketDetay.
     *
     * @param hopFinansalHareketDetay the entity to save
     * @return the persisted entity
     */
    @Override
    public HopFinansalHareketDetay save(HopFinansalHareketDetay hopFinansalHareketDetay) {
        log.debug("Request to save HopFinansalHareketDetay : {}", hopFinansalHareketDetay);
        HopFinansalHareketDetay result = hopFinansalHareketDetayRepository.save(hopFinansalHareketDetay);
        hopFinansalHareketDetaySearchRepository.save(result);
        saveKarsiDetay(result);
        return result;
    }

    /**
     * Get all the hopFinansalHareketDetays.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopFinansalHareketDetay> findAll() {
        log.debug("Request to get all HopFinansalHareketDetays");
        return hopFinansalHareketDetayRepository.findAll();
    }

    /**
     * Get one hopFinansalHareketDetay by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HopFinansalHareketDetay findOne(Long id) {
        log.debug("Request to get HopFinansalHareketDetay : {}", id);
        return hopFinansalHareketDetayRepository.findOne(id);
    }

    /**
     * Delete the hopFinansalHareketDetay by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HopFinansalHareketDetay : {}", id);
        deleteKarsiDetay(id);
        hopFinansalHareketDetayRepository.delete(id);
        hopFinansalHareketDetaySearchRepository.delete(id);
    }

    /**
     * Search for the hopFinansalHareketDetay corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HopFinansalHareketDetay> search(String query) {
        log.debug("Request to search HopFinansalHareketDetays for query {}", query);
        return StreamSupport
            .stream(hopFinansalHareketDetaySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
