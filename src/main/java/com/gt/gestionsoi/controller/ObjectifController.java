package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.controller.BaseEntityController;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.util.DefaultMP;
import com.gt.gestionsoi.util.Response;
import com.gt.gestionsoi.util.ResponseBuilder;
import com.gt.gestionsoi.entity.Objectif;
import com.gt.gestionsoi.filtreform.ObjectifFormulaireDeFiltre;
import com.gt.gestionsoi.service.IObjectifService;
import com.gt.gestionsoi.util.PermissionsConstants;
import com.gt.gestionsoi.util.UrlConstants;
import io.swagger.annotations.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * Contrôleur de gestion des Objectif
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@RestController
@Api(tags = {"objectifs"})
public class ObjectifController extends BaseEntityController<Objectif, Integer> {

    public ObjectifController(IObjectifService objectifService) {
        super(objectifService);
    }

    /**
     * POST /objectifs : Créer un objectif utilisateur.
     *
     * @param objectif L'objectif à créer
     * @return le ResponseEntity avec le status 201 (Created) et le nouveau
     * objectif dans le corps, ou le status 500 (Bad Request) si le nom du objectif
     * déjà utilisés
     * @throws URISyntaxException si le Location URI syntax est incorrect
     */
    @PostMapping(UrlConstants.Objectif.OBJECTIF_RACINE)
    
    @ApiOperation(value = "Créer un objectif")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Objectif enregistré avec succès")
            ,
            @ApiResponse(code = 500, message = "Si le libelle du objectif déjà utilisés", response = Response.class)})
    public ResponseEntity<Response> ajouter(@Valid @RequestBody Objectif objectif)
            throws CustomException {
        return super.create(objectif);
    }

    /**
     * PUT /objectifs : Modifier un objectif.
     *
     * @param objectif Le objectif à modifier
     * @return le ResponseEntity avec le status 200 (OK) et le objectif modifié ,
     * ou avec le status 500 (Bad Request) si le nom du objectif existe déjà, ou
     * le status 500 (Internal Server Error) si le objectif ne peut pas être
     * modifié
     */
    @PutMapping(UrlConstants.Objectif.OBJECTIF_RACINE)
    
    @ApiOperation(value = "Modifier un objectif utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Objectif.class)
            ,
            @ApiResponse(code = 500, message = "Si le libellé du objectif est déjà utilisé", response = Response.class)
            ,
            @ApiResponse(code = 500, message = "Si le objectif ne peut pas être modifié", response = Response.class)})
    public ResponseEntity<Response> modifier(
            @ApiParam(value = "Le objectif à modifier", required = true)
            @Valid @RequestBody Objectif objectif)
            throws CustomException {
        return super.update(objectif.getIdentifiant(), objectif);
    }

    /**
     * GET /objectifs : Retourne la liste des objectifs
     *
     * @param pageable les informations de pagination
     * @return le ResponseEntity avec le status 200 (OK) et la liste des objectifs
     */
    @GetMapping(UrlConstants.Objectif.OBJECTIF_RACINE)
    
    @ApiOperation(value = "Retourner la liste des objectifs",
            response = Objectif.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")}
    )
    public ResponseEntity<Response> recupererLaListeDesObjectifs(
            @ApiParam(value = "Information de pagination (page & size)") Pageable pageable) {

        return super.readAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    /**
     * GET /objectifs/{objectifId} : Retourne le objectif à partir de son identifiant.
     *
     * @param objectifId L'identifiant du objectif
     * @return le ResponseEntity avec le status 200 (OK) et le objectif, ou avec
     * le status 500
     */
    @GetMapping(UrlConstants.Objectif.OBJECTIF_ID)
    @ApiOperation(value = "Retourner le objectif à partir de son identifiant",
            response = Objectif.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> recupererUnObjectif(
            @ApiParam(value = "L'identifiant de la objectif", required = true)
            @PathVariable Integer objectifId) {
        return super.readOne(objectifId);
    }

    /**
     * DELETE /objectifs/:objectifId : Supprime le objectif à partir de son identifiant.
     *
     * @param objectifId L'identifiant du objectif à supprimer
     * @return Le ResponseEntity avec le status 200 (OK)
     */
    @DeleteMapping(UrlConstants.Objectif.OBJECTIF_ID)
    
    @ApiOperation(value = "Supprimer le objectif à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> supprimerUneObjectif(
            @ApiParam(value = "Le nom du objectif", required = true)
            @PathVariable Integer objectifId) throws CustomException {

        return super.delete(objectifId);
    }

    /**
     * GET /objectifs/search
     * <p>
     * Rechercher des objectifs
     *
     * @param objectifFormulaireDeFiltre : L'information de filtre
     * @return ResponseEntity
     */
    
    @RequestMapping(value = UrlConstants.Objectif.OBJECTIF_RECHERCHE, method = RequestMethod.POST)
    @ApiOperation(value = "Rechercher des objectifs",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<Response> rechercher(
            @ApiParam(value = "L'information de filtre")
            @RequestBody ObjectifFormulaireDeFiltre objectifFormulaireDeFiltre) {

        Pageable pageable = null;
        Page<Objectif> objectifFind;

        if (objectifFormulaireDeFiltre.getPage() != null && objectifFormulaireDeFiltre.getSize() != null) {
            pageable = new PageRequest(objectifFormulaireDeFiltre.getPage(),
                    objectifFormulaireDeFiltre.getSize(), Sort.Direction.ASC, "identifiant");
        }

        if (objectifFormulaireDeFiltre.getObjectif() == null) {
            objectifFind = new PageImpl<>(Collections.emptyList(), new PageRequest(1, 5), 0);
        } else {
            objectifFind = service.findAllByCriteres(objectifFormulaireDeFiltre.getCriteres(), pageable);
        }

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(objectifFind)
                .buildI18n(), HttpStatus.OK);
    }

}
