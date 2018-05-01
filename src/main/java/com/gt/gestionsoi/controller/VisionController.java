package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.controller.BaseEntityController;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.util.DefaultMP;
import com.gt.gestionsoi.util.Response;
import com.gt.gestionsoi.util.ResponseBuilder;
import com.gt.gestionsoi.entity.Vision;
import com.gt.gestionsoi.filtreform.VisionFormulaireDeFiltre;
import com.gt.gestionsoi.service.IVisionService;
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
 * Contrôleur de gestion des Visions
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@RestController
@Api(tags = {"visions"})
public class VisionController extends BaseEntityController<Vision, Integer> {

    public VisionController(IVisionService visionService) {
        super(visionService);
    }

    /**
     * POST /visions : Créer une vision.
     *
     * @param vision La vision à créer
     * @return le ResponseEntity avec le status 201 (Created) et la nouvelle
     * vision dans le corps, ou le status 500 (Bad Request) si le libellé de la vision est
     * déjà utilisé
     * @throws URISyntaxException si le Location URI syntax est incorrect
     */
    @PostMapping(UrlConstants.Vision.VISION_RACINE)
    
    @ApiOperation(value = "Créer une vision")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Vision enregistrée avec succès")
            ,
            @ApiResponse(code = 500, message = "Si le libelle de la vision est déjà utilisé", response = Response.class)})
    public ResponseEntity<Response> ajouter(@Valid @RequestBody Vision vision)
            throws CustomException {
        return super.create(vision);
    }

    /**
     * PUT /visions : Modifier une vision.
     *
     * @param vision La vision à modifier
     * @return le ResponseEntity avec le status 200 (OK) et la vision modifiée ,
     * ou avec le status 500 (Bad Request) si le libellé de la vision existe déjà, ou
     * le status 500 (Internal Server Error) si la vision ne peut pas être
     * modifiée
     */
    @PutMapping(UrlConstants.Vision.VISION_RACINE)
    
    @ApiOperation(value = "Modifier une vision")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Vision.class)
            ,
            @ApiResponse(code = 500, message = "Si le libellé de la vision est déjà utilisé", response = Response.class)
            ,
            @ApiResponse(code = 500, message = "Si la vision ne peut pas être modifiée", response = Response.class)})
    public ResponseEntity<Response> modifier(
            @ApiParam(value = "La vision à modifier", required = true)
            @Valid @RequestBody Vision vision)
            throws CustomException {
        return super.update(vision.getIdentifiant(), vision);
    }

    /**
     * GET /visions : Retourne la liste des visions
     *
     * @param pageable les informations de pagination
     * @return le ResponseEntity avec le status 200 (OK) et la liste des visions
     */
    @GetMapping(UrlConstants.Vision.VISION_RACINE)
    
    @ApiOperation(value = "Retourner la liste des visions",
            response = Vision.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")}
    )
    public ResponseEntity<Response> recupererLaListeDesVisions(
            @ApiParam(value = "Information de pagination (page & size)") Pageable pageable) {

        return super.readAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    /**
     * GET /visions/{visionId} : Retourne la vision à partir de son identifiant.
     *
     * @param visionId L'identifiant de la vision
     * @return le ResponseEntity avec le status 200 (OK) et la vision, ou avec
     * le status 500
     */
    @GetMapping(UrlConstants.Vision.VISION_ID)
    @ApiOperation(value = "Retourner la vision à partir de son identifiant",
            response = Vision.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> recupererUnVision(
            @ApiParam(value = "L'identifiant de la vision", required = true)
            @PathVariable Integer visionId) {
        return super.readOne(visionId);
    }

    /**
     * DELETE /visions/:visionId : Supprime la vision à partir de son identifiant.
     *
     * @param visionId L'identifiant de la vision à supprimer
     * @return Le ResponseEntity avec le status 200 (OK)
     */
    @DeleteMapping(UrlConstants.Vision.VISION_ID)
    
    @ApiOperation(value = "Supprimer la vision à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> supprimerUneVision(
            @ApiParam(value = "Le nom de la vision", required = true)
            @PathVariable Integer visionId) throws CustomException {

        return super.delete(visionId);
    }

    /**
     * GET /visions/search
     * <p>
     * Rechercher des visions
     *
     * @param visionFormulaireDeFiltre : L'information de filtre
     * @return ResponseEntity
     */
    
    @RequestMapping(value = UrlConstants.Vision.VISION_RECHERCHE, method = RequestMethod.POST)
    @ApiOperation(value = "Rechercher des visions",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<Response> rechercher(
            @ApiParam(value = "L'information de filtre")
            @RequestBody VisionFormulaireDeFiltre visionFormulaireDeFiltre) {

        Pageable pageable = null;
        Page<Vision> visionFind;

        if (visionFormulaireDeFiltre.getPage() != null && visionFormulaireDeFiltre.getSize() != null) {
            pageable = new PageRequest(visionFormulaireDeFiltre.getPage(),
                    visionFormulaireDeFiltre.getSize(), Sort.Direction.ASC, "identifiant");
        }

        if (visionFormulaireDeFiltre.getVision() == null) {
            visionFind = new PageImpl<>(Collections.emptyList(), new PageRequest(1, 5), 0);
        } else {
            visionFind = service.findAllByCriteres(visionFormulaireDeFiltre.getCriteres(), pageable);
        }

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(visionFind)
                .buildI18n(), HttpStatus.OK);
    }

}
