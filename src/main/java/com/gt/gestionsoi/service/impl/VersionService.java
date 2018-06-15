/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.gestionsoi.service.impl;

import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.repository.VersionRepository;
import com.gt.gestionsoi.service.IVersionService;
import org.springframework.stereotype.Service;

/**
 * @author RODRIGUE
 */
@Service
public class VersionService extends BaseEntityService<Version, Integer> implements IVersionService {

    public VersionService(VersionRepository repository) {
        super(repository);
    }
}
