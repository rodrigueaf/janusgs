package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.entity.Prevision;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.filtreform.PrevisionFormulaireDeFiltre;
import com.gt.gestionsoi.service.IPrevisionService;
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
 * Contrôleur de gestion des Prevision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@RestController
@Api(tags = {"previsions"})
public class PrevisionController extends BaseEntityController<Prevision, Integer> {

    public PrevisionController(IPrevisionService previsionService) {
        super(previsionService);
    }

    @RequestMapping(value = UrlConstants.Prevision.PREVISION_RACINE + "/liste/{id}", method = RequestMethod.GET)
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
                .data(((IPrevisionService) service).recupererLaListeVersionnee(ints))
                .buildI18n(), HttpStatus.OK);
    }

    /**
     * POST /previsions : Créer une prévision.
     *
     * @param prevision Le prévision à créer
     * @return le ResponseEntity avec le status 201 (Created) et la nouvelle
     * prévision dans le corps, ou le status 500 (Bad Request) si le libellé de la prévision est
     * déjà utilisé
     * @throws URISyntaxException si le Location URI syntax est incorrect
     */
    @PostMapping(UrlConstants.Prevision.PREVISION_RACINE)
    
    @ApiOperation(value = "Créer une prévision")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Prevision enregistrée avec succès")
            ,
            @ApiResponse(code = 500, message = "Si le libelle de la prévision est déjà utilisé", response = Response.class)})
    public ResponseEntity<Response> ajouter(@Valid @RequestBody Prevision prevision)
            throws CustomException {
        return super.create(prevision);
    }

    /**
     * PUT /previsions : Modifier une prévision.
     *
     * @param prevision La prévision à modifier
     * @return le ResponseEntity avec le status 200 (OK) et la prévision modifiée ,
     * ou avec le status 500 (Bad Request) si le libellé de la prevision existe déjà, ou
     * le status 500 (Internal Server Error) si la prévision ne peut pas être
     * modifiée
     */
    @PutMapping(UrlConstants.Prevision.PREVISION_RACINE)
    
    @ApiOperation(value = "Modifier une prévision")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Prevision.class)
            ,
            @ApiResponse(code = 500, message = "Si le libellé de la prévision est déjà utilisé", response = Response.class)
            ,
            @ApiResponse(code = 500, message = "Si la prevision ne peut pas être modifiée", response = Response.class)})
    public ResponseEntity<Response> modifier(
            @ApiParam(value = "La prévision à modifier", required = true)
            @Valid @RequestBody Prevision prevision)
            throws CustomException {
        return super.update(prevision.getIdentifiant(), prevision);
    }

    /**
     * GET /previsions : Retourne la liste des previsions
     *
     * @param pageable les informations de pagination
     * @return le ResponseEntity avec le status 200 (OK) et la liste des previsions
     */
    @GetMapping(UrlConstants.Prevision.PREVISION_RACINE)
    @ApiOperation(value = "Retourner la liste des previsions",
            response = Prevision.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")}
    )
    public ResponseEntity<Response> recupererLaListeDesPrevisions(
            @ApiParam(value = "Information de pagination (page & size)") Pageable pageable) {

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(((IPrevisionService) service).findAllByOrderByIdentifiantDesc(pageable))
                .buildI18n(), HttpStatus.OK);
    }

    /**
     * GET /previsions/{previsionId} : Retourne la prévision à partir de son identifiant.
     *
     * @param previsionId L'identifiant de la prévision
     * @return le ResponseEntity avec le status 200 (OK) et la prévision, ou avec
     * le status 500
     */
    @GetMapping(UrlConstants.Prevision.PREVISION_ID)
    @ApiOperation(value = "Retourner la prévision à partir de son identifiant",
            response = Prevision.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> recupererUnPrevision(
            @ApiParam(value = "L'identifiant de la prevision", required = true)
            @PathVariable Integer previsionId) {
        return super.readOne(previsionId);
    }

    /**
     * DELETE /previsions/:previsionId : Supprime la prévision à partir de son identifiant.
     *
     * @param previsionId L'identifiant de la prévision à supprimer
     * @return Le ResponseEntity avec le status 200 (OK)
     */
    @DeleteMapping(UrlConstants.Prevision.PREVISION_ID)
    
    @ApiOperation(value = "Supprimer la prévision à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> supprimerUnePrevision(
            @ApiParam(value = "Le nom de la prévision", required = true)
            @PathVariable Integer previsionId) throws CustomException {

        return super.delete(previsionId);
    }

    /**
     * GET /previsions/search
     * <p>
     * Rechercher des previsions
     *
     * @param previsionFormulaireDeFiltre : L'information de filtre
     * @return ResponseEntity
     */
    
    @RequestMapping(value = UrlConstants.Prevision.PREVISION_RECHERCHE, method = RequestMethod.POST)
    @ApiOperation(value = "Rechercher des previsions",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<Response> rechercher(
            @ApiParam(value = "L'information de filtre")
            @RequestBody PrevisionFormulaireDeFiltre previsionFormulaireDeFiltre) {

        Pageable pageable = null;
        Page<Prevision> previsionFind;

        if (previsionFormulaireDeFiltre.getPage() != null && previsionFormulaireDeFiltre.getSize() != null) {
            pageable = new PageRequest(previsionFormulaireDeFiltre.getPage(),
                    previsionFormulaireDeFiltre.getSize(), Sort.Direction.DESC, "identifiant");
        }

        if (previsionFormulaireDeFiltre.getPrevision() == null) {
            previsionFind = new PageImpl<>(Collections.emptyList(), new PageRequest(1, 5), 0);
        } else {
            previsionFind = service.findAllByCriteres(previsionFormulaireDeFiltre.getCriteres(), pageable);
        }

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(previsionFind)
                .buildI18n(), HttpStatus.OK);
    }

}
