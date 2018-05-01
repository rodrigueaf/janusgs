package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.controller.BaseEntityController;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.util.DefaultMP;
import com.gt.gestionsoi.util.Response;
import com.gt.gestionsoi.util.ResponseBuilder;
import com.gt.gestionsoi.entity.PrincipeValeur;
import com.gt.gestionsoi.filtreform.PrincipeValeurFormulaireDeFiltre;
import com.gt.gestionsoi.service.IPrincipeValeurService;
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
 * Contrôleur de gestion des PrincipeValeur
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@RestController
@Api(tags = {"principes-valeurs"})
public class PrincipeValeurController extends BaseEntityController<PrincipeValeur, Integer> {

    public PrincipeValeurController(IPrincipeValeurService principeValeurService) {
        super(principeValeurService);
    }

    /**
     * POST /principes-valeurs : Créer une valeur utilisateur.
     *
     * @param principeValeur Le valeur à créer
     * @return le ResponseEntity avec le status 201 (Created) et le nouveau
     * valeur dans le corps, ou le status 500 (Bad Request) si le nom du valeur
     * déjà utilisés
     * @throws URISyntaxException si le Location URI syntax est incorrect
     */
    @PostMapping(UrlConstants.PrincipeValeur.VALEUR_RACINE)
    
    @ApiOperation(value = "Créer une valeur")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "PrincipeValeur enregistré avec succès")
            ,
            @ApiResponse(code = 500, message = "Si le libelle du valeur déjà utilisés", response = Response.class)})
    public ResponseEntity<Response> ajouter(@Valid @RequestBody PrincipeValeur principeValeur)
            throws CustomException {
        return super.create(principeValeur);
    }

    /**
     * PUT /principes-valeurs : Modifier une valeur.
     *
     * @param principeValeur Le principeValeur à modifier
     * @return le ResponseEntity avec le status 200 (OK) et le principeValeur modifié ,
     * ou avec le status 500 (Bad Request) si le nom du principeValeur existe déjà, ou
     * le status 500 (Internal Server Error) si le principeValeur ne peut pas être
     * modifié
     */
    @PutMapping(UrlConstants.PrincipeValeur.VALEUR_RACINE)
    
    @ApiOperation(value = "Modifier une valeur utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PrincipeValeur.class)
            ,
            @ApiResponse(code = 500, message = "Si le libellé du principeValeur est déjà utilisé", response = Response.class)
            ,
            @ApiResponse(code = 500, message = "Si le principeValeur ne peut pas être modifié", response = Response.class)})
    public ResponseEntity<Response> modifier(
            @ApiParam(value = "Le principeValeur à modifier", required = true)
            @Valid @RequestBody PrincipeValeur principeValeur)
            throws CustomException {
        return super.update(principeValeur.getIdentifiant(), principeValeur);
    }

    /**
     * GET /principes-valeurs : Retourne la liste des principeValeurs
     *
     * @param pageable les informations de pagination
     * @return le ResponseEntity avec le status 200 (OK) et la liste des principeValeurs
     */
    @GetMapping(UrlConstants.PrincipeValeur.VALEUR_RACINE)
    
    @ApiOperation(value = "Retourner la liste des principeValeurs",
            response = PrincipeValeur.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")}
    )
    public ResponseEntity<Response> recupererLaListeDesPrincipeValeurs(
            @ApiParam(value = "Information de pagination (page & size)") Pageable pageable) {

        return super.readAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    /**
     * GET /principes-valeurs/{valeurId} : Retourne le principeValeur à partir de son identifiant.
     *
     * @param valeurId L'identifiant du principeValeur
     * @return le ResponseEntity avec le status 200 (OK) et le principeValeur, ou avec
     * le status 500
     */
    @GetMapping(UrlConstants.PrincipeValeur.VALEUR_ID)
    @ApiOperation(value = "Retourner le principeValeur à partir de son identifiant",
            response = PrincipeValeur.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> recupererUnPrincipeValeur(
            @ApiParam(value = "L'identifiant de la principeValeur", required = true)
            @PathVariable Integer valeurId) {
        return super.readOne(valeurId);
    }

    /**
     * DELETE /principes-valeurs/:valeurId : Supprime le principeValeur à partir de son identifiant.
     *
     * @param valeurId L'identifiant du principeValeur à supprimer
     * @return Le ResponseEntity avec le status 200 (OK)
     */
    @DeleteMapping(UrlConstants.PrincipeValeur.VALEUR_ID)
    
    @ApiOperation(value = "Supprimer le principeValeur à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> supprimerUnePrincipeValeur(
            @ApiParam(value = "Le nom du principeValeur", required = true)
            @PathVariable Integer valeurId) throws CustomException {

        return super.delete(valeurId);
    }

    /**
     * GET /principes-valeurs/search
     * <p>
     * Rechercher des principeValeurs
     *
     * @param principeValeurFormulaireDeFiltre : L'information de filtre
     * @return ResponseEntity
     */
    
    @RequestMapping(value = UrlConstants.PrincipeValeur.VALEUR_RECHERCHE, method = RequestMethod.POST)
    @ApiOperation(value = "Rechercher des principeValeurs",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<Response> rechercher(
            @ApiParam(value = "L'information de filtre")
            @RequestBody PrincipeValeurFormulaireDeFiltre principeValeurFormulaireDeFiltre) {

        Pageable pageable = null;
        Page<PrincipeValeur> principeValeurFind;

        if (principeValeurFormulaireDeFiltre.getPage() != null && principeValeurFormulaireDeFiltre.getSize() != null) {
            pageable = new PageRequest(principeValeurFormulaireDeFiltre.getPage(),
                    principeValeurFormulaireDeFiltre.getSize(), Sort.Direction.ASC, "identifiant");
        }

        if (principeValeurFormulaireDeFiltre.getPrincipeValeur() == null) {
            principeValeurFind = new PageImpl<>(Collections.emptyList(), new PageRequest(1, 5), 0);
        } else {
            principeValeurFind = service.findAllByCriteres(principeValeurFormulaireDeFiltre.getCriteres(), pageable);
        }

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(principeValeurFind)
                .buildI18n(), HttpStatus.OK);
    }

}
