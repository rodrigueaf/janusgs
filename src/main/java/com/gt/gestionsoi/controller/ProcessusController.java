package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.entity.Processus;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.filtreform.ProcessusFormulaireDeFiltre;
import com.gt.gestionsoi.service.IProcessusService;
import com.gt.gestionsoi.util.DefaultMP;
import com.gt.gestionsoi.util.Response;
import com.gt.gestionsoi.util.ResponseBuilder;
import com.gt.gestionsoi.util.UrlConstants;
import io.swagger.annotations.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * Contrôleur de gestion des Processus
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@RestController
@Api(tags = {"processus"})
public class ProcessusController extends BaseEntityController<Processus, Integer> {

    public ProcessusController(IProcessusService processusService) {
        super(processusService);
    }

    @RequestMapping(value = UrlConstants.Processus.PROCESSUS_RACINE + "/liste/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> selectionnerListe(@PathVariable String id) {
        String[] split = id.split("&");
        Integer[] ints = new Integer[split.length];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Integer.valueOf(split[i]);
        }
        return new ResponseEntity<>(ResponseBuilder.success()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(((IProcessusService) service).recupererLaListeVersionnee(ints))
                .buildI18n(), HttpStatus.OK);
    }

    /**
     * POST /processus : Créer une catégorie utilisateur.
     *
     * @param processus Le catégorie à créer
     * @return le ResponseEntity avec le status 201 (Created) et le nouveau
     * catégorie dans le corps, ou le status 500 (Bad Request) si le nom du catégorie
     * déjà utilisés
     * @throws URISyntaxException si le Location URI syntax est incorrect
     */
    @PostMapping(UrlConstants.Processus.PROCESSUS_RACINE)
    
    @ApiOperation(value = "Créer une catégorie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Processus enregistré avec succès")
            ,
            @ApiResponse(code = 500, message = "Si le libelle du catégorie déjà utilisés", response = Response.class)})
    public ResponseEntity<Response> ajouter(@Valid @RequestBody Processus processus)
            throws CustomException {
        return super.create(processus);
    }

    /**
     * PUT /processus : Modifier une catégorie.
     *
     * @param processus Le processus à modifier
     * @return le ResponseEntity avec le status 200 (OK) et le processus modifié ,
     * ou avec le status 500 (Bad Request) si le nom du processus existe déjà, ou
     * le status 500 (Internal Server Error) si le processus ne peut pas être
     * modifié
     */
    @PutMapping(UrlConstants.Processus.PROCESSUS_RACINE)
    
    @ApiOperation(value = "Modifier une catégorie utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Processus.class)
            ,
            @ApiResponse(code = 500, message = "Si le libellé du processus est déjà utilisé", response = Response.class)
            ,
            @ApiResponse(code = 500, message = "Si le processus ne peut pas être modifié", response = Response.class)})
    public ResponseEntity<Response> modifier(
            @ApiParam(value = "Le processus à modifier", required = true)
            @Valid @RequestBody Processus processus)
            throws CustomException {
        return super.update(processus.getIdentifiant(), processus);
    }

    /**
     * GET /processus : Retourne la liste des processus
     *
     * @param pageable les informations de pagination
     * @return le ResponseEntity avec le status 200 (OK) et la liste des processus
     */
    @GetMapping(UrlConstants.Processus.PROCESSUS_RACINE)
    
    @ApiOperation(value = "Retourner la liste des processus",
            response = Processus.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")}
    )
    public ResponseEntity<Response> recupererLaListeDesProcessuss(
            @ApiParam(value = "Information de pagination (page & size)") Pageable pageable) {

        return super.readAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    /**
     * GET /processus/{processusId} : Retourne le processus à partir de son identifiant.
     *
     * @param processusId L'identifiant du processus
     * @return le ResponseEntity avec le status 200 (OK) et le processus, ou avec
     * le status 500
     */
    @GetMapping(UrlConstants.Processus.PROCESSUS_ID)
    @ApiOperation(value = "Retourner le processus à partir de son identifiant",
            response = Processus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> recupererUnProcessus(
            @ApiParam(value = "L'identifiant de la processus", required = true)
            @PathVariable Integer processusId) {
        return super.readOne(processusId);
    }

    /**
     * DELETE /processus/:processusId : Supprime le processus à partir de son identifiant.
     *
     * @param processusId L'identifiant du processus à supprimer
     * @return Le ResponseEntity avec le status 200 (OK)
     */
    @DeleteMapping(UrlConstants.Processus.PROCESSUS_ID)
    
    @ApiOperation(value = "Supprimer le processus à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> supprimerUnProcessus(
            @ApiParam(value = "Le nom du processus", required = true)
            @PathVariable Integer processusId) throws CustomException {

        return new ResponseEntity<>(ResponseBuilder.success()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(((IProcessusService)service).supprimer(processusId))
                .buildI18n(), HttpStatus.NO_CONTENT);
    }

    /**
     * GET /processus/search
     * <p>
     * Rechercher des processus
     *
     * @param processusFormulaireDeFiltre : L'information de filtre
     * @return ResponseEntity
     */
    
    @RequestMapping(value = UrlConstants.Processus.PROCESSUS_RECHERCHE, method = RequestMethod.POST)
    @ApiOperation(value = "Rechercher des processus",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<Response> rechercher(
            @ApiParam(value = "L'information de filtre")
            @RequestBody ProcessusFormulaireDeFiltre processusFormulaireDeFiltre) {

        Pageable pageable = null;
        Page<Processus> processusFind;

        if (processusFormulaireDeFiltre.getPage() != null && processusFormulaireDeFiltre.getSize() != null) {
            pageable = new PageRequest(processusFormulaireDeFiltre.getPage(),
                    processusFormulaireDeFiltre.getSize(), Sort.Direction.ASC, "identifiant");
        }

        if (processusFormulaireDeFiltre.getProcessus() == null) {
            processusFind = new PageImpl<>(Collections.emptyList(), new PageRequest(1, 5), 0);
        } else {
            processusFind = service.findAllByCriteres(processusFormulaireDeFiltre.getCriteres(), pageable);
        }

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(processusFind)
                .buildI18n(), HttpStatus.OK);
    }

}
