package com.gt.gestionsoi.controller;

import com.gt.gestionsoi.entity.Journal;
import com.gt.gestionsoi.exception.CustomException;
import com.gt.gestionsoi.filtreform.JournalFormulaireDeFiltre;
import com.gt.gestionsoi.service.IJournalService;
import com.gt.gestionsoi.util.*;
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
 * Contrôleur de gestion des Journal
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
@RestController
@Api(tags = {"journaux"})
public class JournalController extends BaseEntityController<Journal, Integer> {

    public JournalController(IJournalService journalService) {
        super(journalService);
    }

    @RequestMapping(value = UrlConstants.Journal.JOURNAL_RACINE + "/liste/{id}", method = RequestMethod.GET)
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
                .data(((IJournalService) service).recupererLaListeVersionnee(ints))
                .buildI18n(), HttpStatus.OK);
    }

    /**
     * POST /journaux : Créer une journal utilisateur.
     *
     * @param journal Le journal à créer
     * @return le ResponseEntity avec le status 201 (Created) et le nouveau
     * journal dans le corps, ou le status 500 (Bad Request) si le nom du journal
     * déjà utilisés
     * @throws URISyntaxException si le Location URI syntax est incorrect
     */
    @PostMapping(UrlConstants.Journal.JOURNAL_RACINE)
    @Secured(PermissionsConstants.PERMISSION_JOURNAL)
    @ApiOperation(value = "Créer une journal")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Journal enregistré avec succès")
            ,
            @ApiResponse(code = 500, message = "Si le libelle du journal déjà utilisés", response = Response.class)})
    public ResponseEntity<Response> ajouter(@Valid @RequestBody Journal journal)
            throws CustomException {
        return super.create(journal);
    }

    /**
     * PUT /journaux : Modifier une journal.
     *
     * @param journal Le journal à modifier
     * @return le ResponseEntity avec le status 200 (OK) et le journal modifié ,
     * ou avec le status 500 (Bad Request) si le nom du journal existe déjà, ou
     * le status 500 (Internal Server Error) si le journal ne peut pas être
     * modifié
     */
    @PutMapping(UrlConstants.Journal.JOURNAL_RACINE)

    @ApiOperation(value = "Modifier une journal utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Journal.class)
            ,
            @ApiResponse(code = 500, message = "Si le libellé du journal est déjà utilisé", response = Response.class)
            ,
            @ApiResponse(code = 500, message = "Si le journal ne peut pas être modifié", response = Response.class)})
    public ResponseEntity<Response> modifier(
            @ApiParam(value = "Le journal à modifier", required = true)
            @Valid @RequestBody Journal journal)
            throws CustomException {
        return super.update(journal.getIdentifiant(), journal);
    }

    /**
     * GET /journaux : Retourne la liste des journaux
     *
     * @param pageable les informations de pagination
     * @return le ResponseEntity avec le status 200 (OK) et la liste des journaux
     */
    @GetMapping(UrlConstants.Journal.JOURNAL_RACINE)

    @ApiOperation(value = "Retourner la liste des journaux",
            response = Journal.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")}
    )
    public ResponseEntity<Response> recupererLaListeDesJournals(
            @ApiParam(value = "Information de pagination (page & size)") Pageable pageable) {

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(((IJournalService) service).findAllByOrderByIdentifiantDesc(pageable))
                .buildI18n(), HttpStatus.OK);
    }

    /**
     * GET /journaux/{journalId} : Retourne le journal à partir de son identifiant.
     *
     * @param journalId L'identifiant du journal
     * @return le ResponseEntity avec le status 200 (OK) et le journal, ou avec
     * le status 500
     */
    @GetMapping(UrlConstants.Journal.JOURNAL_ID)
    @ApiOperation(value = "Retourner le journal à partir de son identifiant",
            response = Journal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> recupererUnJournal(
            @ApiParam(value = "L'identifiant de la journal", required = true)
            @PathVariable Integer journalId) {
        return super.readOne(journalId);
    }

    /**
     * DELETE /journaux/:journalId : Supprime le journal à partir de son identifiant.
     *
     * @param journalId L'identifiant du journal à supprimer
     * @return Le ResponseEntity avec le status 200 (OK)
     */
    @DeleteMapping(UrlConstants.Journal.JOURNAL_ID)

    @ApiOperation(value = "Supprimer le journal à partir de son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<Response> supprimerUnJournal(
            @ApiParam(value = "Le nom du journal", required = true)
            @PathVariable Integer journalId) throws CustomException {

        return super.delete(journalId);
    }

    /**
     * GET /journaux/search
     * <p>
     * Rechercher des journaux
     *
     * @param journalFormulaireDeFiltre : L'information de filtre
     * @return ResponseEntity
     */

    @RequestMapping(value = UrlConstants.Journal.JOURNAL_RECHERCHE, method = RequestMethod.POST)
    @ApiOperation(value = "Rechercher des journaux",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<Response> rechercher(
            @ApiParam(value = "L'information de filtre")
            @RequestBody JournalFormulaireDeFiltre journalFormulaireDeFiltre) {

        Pageable pageable = null;
        Page<Journal> journalFind;

        if (journalFormulaireDeFiltre.getPage() != null && journalFormulaireDeFiltre.getSize() != null) {
            pageable = new PageRequest(journalFormulaireDeFiltre.getPage(),
                    journalFormulaireDeFiltre.getSize(), Sort.Direction.DESC, "identifiant");
        }

        if (journalFormulaireDeFiltre.getJournal() == null) {
            journalFind = new PageImpl<>(Collections.emptyList(), new PageRequest(1, 5), 0);
        } else {
            journalFind = service.findAllByCriteres(journalFormulaireDeFiltre.getCriteres(), pageable);
        }

        return new ResponseEntity<>(ResponseBuilder.info()
                .code(null)
                .title(DefaultMP.TITLE_SUCCESS)
                .message(DefaultMP.MESSAGE_SUCCESS)
                .data(journalFind)
                .buildI18n(), HttpStatus.OK);
    }

}
