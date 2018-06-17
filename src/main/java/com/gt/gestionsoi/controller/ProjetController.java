package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.entity.Projet;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.filtreform.ProjetFormulaireDeFiltre;
import com.gt.gestionsoi.service.IProjetService;
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
 * Contrôleur de gestion des Projet
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@RestController
@Api(tags = {"projets"})
public class ProjetController extends BaseEntityController<Projet, Integer> {

    public ProjetController(IProjetService projetService) {
        super(projetService);
    }

    @RequestMapping(value = UrlConstants.Projet.PROJET_RACINE + "/liste/{id}", method = RequestMethod.GET)
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
                .data(((IProjetService) service).recupererLaListeVersionnee(ints))
                .buildI18n(), HttpStatus.OK);
    }

    /**
     * POST /projet : Créer une projet utilisateur.
     *
     * @param projet Le projet à créer
     * @return le ResponseEntity avec le status 201 (Created) et le nouveau
     * projet dans le corps, ou le status 500 (Bad Request) si le nom du projet
     * déjà utilisés
     * @throws URISyntaxException si le Location URI syntax est incorrect
     */
    @PostMapping(UrlConstants.Projet.PROJET_RACINE)

    @ApiOperation(value = "Créer une projet")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Projet enregistré avec succès")
            ,
            @ApiResponse(code = 500, message = "Si le libelle du projet déjà utilisés", response = Response.class)})
    public ResponseEntity<Response> ajouter(@Valid @RequestBody Projet projet)
            throws CustomException {
        return super.create(projet);
    }

    /**
     * PUT /projet : Modifier une projet.
     *
     * @param projet Le projet à modifier
     * @return le ResponseEntity avec le status 200 (OK) et le projet modifié ,
     * ou avec le status 500 (Bad Request) si le nom du projet existe déjà, ou
     * le status 500 (Internal Server Error) si le projet ne peut pas être
     * modifié
     */
    @PutMapping(UrlConstants.Projet.PROJET_RACINE)

    @ApiOperation(value = "Modifier une projet utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Projet.class)
            ,
            @ApiResponse(code = 500, message = "Si le libellé du projet est déjà utilisé", response = Response.class)
            ,
            @ApiResponse(code = 500, message = "Si le projet ne peut pas être modifié", response = Response.class)})
    public ResponseEntity<Response> modifier(
            @ApiParam(value = "Le projet à modifier", required = true)
            @Valid @RequestBody Projet projet)
            throws CustomException {
        return super.update(projet.getIdentifiant(), projet);
    }

    /**
     * GET /projet : Retourne la liste des projet
     *
     * @param pageable les informations de pagination
     * @return le ResponseEntity avec le status 200 (OK) et la liste des projet
     */
    @GetMapping(UrlConstants.Projet.PROJET_RACINE)

    @ApiOperation(value = "Retourner la liste des projet",
            response = Projet.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")}
    )
    public ResponseEntity<Response> recupererLaListeDesProjets(
            @ApiParam(value = "Information de pagination (page & size)") Pageable pageable) {

        return super.readAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    /**
     * GET /projet/{projetId} : Retourne le projet à partir de son identifiant.
     *
     * @param projetId L'identifiant du projet
     * @return le ResponseEntity avec le status 200 (OK) et le projet, ou avec
     * le status 500
     */
    @GetMapping(UrlConstants.Projet.PROJET_ID)
    @ApiOperation(value = "Retourner le projet à partir de son identifiant",
            response = Projet.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> recupererUnProjet(
            @ApiParam(value = "L'identifiant de la projet", required = true)
            @PathVariable Integer projetId) {
        return super.readOne(projetId);
    }

    /**
     * DELETE /projet/:projetId : Supprime le projet à partir de son identifiant.
     *
     * @param projetId L'identifiant du projet à supprimer
     * @return Le ResponseEntity avec le status 200 (OK)
     */
    @DeleteMapping(UrlConstants.Projet.PROJET_ID)

    @ApiOperation(value = "Supprimer le projet à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> supprimerUnProjet(
            @ApiParam(value = "Le nom du projet", required = true)
            @PathVariable Integer projetId) throws CustomException {

        return new ResponseEntity<>(ResponseBuilder.success()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(((IProjetService) service).supprimer(projetId))
                .buildI18n(), HttpStatus.NO_CONTENT);
    }

    /**
     * GET /projet/search
     * <p>
     * Rechercher des projet
     *
     * @param projetFormulaireDeFiltre : L'information de filtre
     * @return ResponseEntity
     */

    @RequestMapping(value = UrlConstants.Projet.PROJET_RECHERCHE, method = RequestMethod.POST)
    @ApiOperation(value = "Rechercher des projet",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<Response> rechercher(
            @ApiParam(value = "L'information de filtre")
            @RequestBody ProjetFormulaireDeFiltre projetFormulaireDeFiltre) {

        Pageable pageable = null;
        Page<Projet> projetFind;

        if (projetFormulaireDeFiltre.getPage() != null && projetFormulaireDeFiltre.getSize() != null) {
            pageable = new PageRequest(projetFormulaireDeFiltre.getPage(),
                    projetFormulaireDeFiltre.getSize(), Sort.Direction.ASC, "identifiant");
        }

        if (projetFormulaireDeFiltre.getProjet() == null) {
            projetFind = new PageImpl<>(Collections.emptyList(), new PageRequest(1, 5), 0);
        } else {
            projetFind = service.findAllByCriteres(projetFormulaireDeFiltre.getCriteres(), pageable);
        }

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(projetFind)
                .buildI18n(), HttpStatus.OK);
    }

}
