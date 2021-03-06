package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.entity.Version;
import com.gt.gestionsoi.repository.VersionRepository;
import com.gt.gestionsoi.service.IVersionService;
import com.gt.gestionsoi.util.DefaultMP;
import com.gt.gestionsoi.util.Response;
import com.gt.gestionsoi.util.ResponseBuilder;
import com.gt.gestionsoi.util.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class VersionController extends BaseEntityController<Version, Integer> {

    private VersionRepository versionRepository;

    public VersionController(IVersionService service) {
        super(service);
    }

    @GetMapping(value = UrlConstants.Version.VERSION_RACINE + "/version/{id}")
    public ResponseEntity<Response> selectionner(@PathVariable Integer id) {
        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(versionRepository.findOne(id))
                .buildI18n(), HttpStatus.OK);
    }

    @GetMapping(value = UrlConstants.Version.VERSION_ID)
    public ResponseEntity<Response> selectionnerListe(@PathVariable String id) {
        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(versionRepository.selectionnerListe(id.split("&")[0],
                        Integer.valueOf(id.split("&")[1])))
                .buildI18n(), HttpStatus.OK);
    }

    @Autowired
    public void setVersionRepository(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }
}
