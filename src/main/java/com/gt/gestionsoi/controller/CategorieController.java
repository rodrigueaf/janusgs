package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.entity.Categorie;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.filtreform.CategorieFormulaireDeFiltre;
import com.gt.gestionsoi.service.ICategorieService;
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
 * Contrôleur de gestion des Categorie
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@RestController
@Api(tags = {"categories"})
public class CategorieController extends BaseEntityController<Categorie, Integer> {

    public CategorieController(ICategorieService categorieService) {
        super(categorieService);
    }

    @RequestMapping(value = UrlConstants.Categorie.CATEGORIE_RACINE + "/liste/{id}", method = RequestMethod.GET)
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
                .data(((ICategorieService) service).recupererLaListeVersionnee(ints))
                .buildI18n(), HttpStatus.OK);
    }

    /**
     * POST /categories : Créer une catégorie.
     *
     * @param categorie Le catégorie à créer
     * @return le ResponseEntity avec le status 201 (Created) et la nouvelle
     * catégorie dans le corps, ou le status 500 (Bad Request) si le libellé de la catégorie est
     * déjà utilisé
     * @throws URISyntaxException si le Location URI syntax est incorrect
     */
    @PostMapping(UrlConstants.Categorie.CATEGORIE_RACINE)
    @ApiOperation(value = "Créer une catégorie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Categorie enregistrée avec succès")
            ,
            @ApiResponse(code = 500, message = "Si le libelle de la catégorie est déjà utilisé", response = Response.class)})
    public ResponseEntity<Response> ajouter(@Valid @RequestBody Categorie categorie)
            throws CustomException {
        return super.create(categorie);
    }

    /**
     * PUT /categories : Modifier une catégorie.
     *
     * @param categorie La catégorie à modifier
     * @return le ResponseEntity avec le status 200 (OK) et le categorie modifiée ,
     * ou avec le status 500 (Bad Request) si le libellé de la categorie existe déjà, ou
     * le status 500 (Internal Server Error) si le categorie ne peut pas être
     * modifiée
     */
    @PutMapping(UrlConstants.Categorie.CATEGORIE_RACINE)
    @ApiOperation(value = "Modifier une catégorie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Categorie.class)
            ,
            @ApiResponse(code = 500, message = "Si le libellé du categorie est déjà utilisé", response = Response.class)
            ,
            @ApiResponse(code = 500, message = "Si la categorie ne peut pas être modifiée", response = Response.class)})
    public ResponseEntity<Response> modifier(
            @ApiParam(value = "La catégorie à modifier", required = true)
            @Valid @RequestBody Categorie categorie)
            throws CustomException {
        return super.update(categorie.getIdentifiant(), categorie);
    }

    /**
     * GET /categories : Retourne la liste des categories
     *
     * @param pageable les informations de pagination
     * @return le ResponseEntity avec le status 200 (OK) et la liste des categories
     */
    @GetMapping(UrlConstants.Categorie.CATEGORIE_RACINE)
    @ApiOperation(value = "Retourner la liste des categories",
            response = Categorie.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")}
    )
    public ResponseEntity<Response> recupererLaListeDesCategories(
            @ApiParam(value = "Information de pagination (page & size)") Pageable pageable) {

        return super.readAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    /**
     * GET /categories/{categorieId} : Retourne le categorie à partir de son identifiant.
     *
     * @param categorieId L'identifiant du categorie
     * @return le ResponseEntity avec le status 200 (OK) et le categorie, ou avec
     * le status 500
     */
    @GetMapping(UrlConstants.Categorie.CATEGORIE_ID)
    @ApiOperation(value = "Retourner le categorie à partir de son identifiant",
            response = Categorie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> recupererUnCategorie(
            @ApiParam(value = "L'identifiant de la categorie", required = true)
            @PathVariable Integer categorieId) {
        return super.readOne(categorieId);
    }

    /**
     * DELETE /categories/:categorieId : Supprime le categorie à partir de son identifiant.
     *
     * @param categorieId L'identifiant du categorie à supprimer
     * @return Le ResponseEntity avec le status 200 (OK)
     */
    @DeleteMapping(UrlConstants.Categorie.CATEGORIE_ID)
    @ApiOperation(value = "Supprimer le categorie à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> supprimerUneCategorie(
            @ApiParam(value = "Le nom du categorie", required = true)
            @PathVariable Integer categorieId) throws CustomException {

        return new ResponseEntity<>(ResponseBuilder.success()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(((ICategorieService) service).supprimer(categorieId))
                .buildI18n(), HttpStatus.OK);
    }

    /**
     * GET /categories/search
     * <p>
     * Rechercher des categories
     *
     * @param categorieFormulaireDeFiltre : L'information de filtre
     * @return ResponseEntity
     */
    @RequestMapping(value = UrlConstants.Categorie.CATEGORIE_RECHERCHE, method = RequestMethod.POST)
    @ApiOperation(value = "Rechercher des categories",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<Response> rechercher(
            @ApiParam(value = "L'information de filtre")
            @RequestBody CategorieFormulaireDeFiltre categorieFormulaireDeFiltre) {

        Pageable pageable = null;
        Page<Categorie> categorieFind;

        if (categorieFormulaireDeFiltre.getPage() != null && categorieFormulaireDeFiltre.getSize() != null) {
            pageable = new PageRequest(categorieFormulaireDeFiltre.getPage(),
                    categorieFormulaireDeFiltre.getSize(), Sort.Direction.ASC, "identifiant");
        }

        if (categorieFormulaireDeFiltre.getCategorie() == null) {
            categorieFind = new PageImpl<>(Collections.emptyList(), new PageRequest(1, 5), 0);
        } else {
            categorieFind = service.findAllByCriteres(categorieFormulaireDeFiltre.getCriteres(), pageable);
        }

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(categorieFind)
                .buildI18n(), HttpStatus.OK);
    }

}
