package hpo.api

import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode
import hpo.api.annotation.DbAnnotation
import hpo.api.disease.DbDisease
import hpo.api.model.AnnotationResult
import hpo.api.term.DbTerm
import hpo.api.util.HpoAssociationFactory
import org.monarchinitiative.phenol.annotations.formats.hpo.category.HpoCategory
import org.monarchinitiative.phenol.annotations.formats.hpo.category.HpoCategoryMap
import org.monarchinitiative.phenol.ontology.data.Ontology
import org.monarchinitiative.phenol.ontology.data.TermId
import org.monarchinitiative.phenol.ontology.data.Term

@GrailsCompileStatic(TypeCheckingMode.SKIP)
class HpoDiseaseDetailsService {

  Ontology hpoOntology
  HpoAssociationFactory hpoAssociationFactory

  Map searchDisease(String query) {
    Map resultMap = ["disease": '', "termAssoc": [], "geneAssoc": [], "catTerms":[[:]]]
    if (query) {
      DbDisease disease = getDisease(query)
      if (!disease) {
        return resultMap
      }
      resultMap.put("disease", disease)
      resultMap.put("termAssoc", getTermsAssociatedByDisease(disease))
      resultMap.put("geneAssoc", disease.dbGenes)
      resultMap.put("catTerms", getDiseaseCategoriesWithTerms(disease))
    }
    return resultMap
  }

  DbDisease getDisease(String query) {
    DbDisease.findByDiseaseId(query)
  }

  List<DbTerm> getTermsAssociatedByDisease(DbDisease disease){
    return DbAnnotation.findAllByDbDisease(disease).dbTerm
  }

  /**
   * Given a disease find HPO categories and related HPO terms. Return as a list
   * of maps [category : [ Terms ]]
   * @param disease
   * @return
   */
  @GrailsCompileStatic(TypeCheckingMode.SKIP)
  private List<Map> getDiseaseCategoriesWithTerms(DbDisease disease){

    HpoCategoryMap hpoCatMap = new HpoCategoryMap()

    Set<DbTerm> dbTermList  = getTermsAssociatedByDisease(disease)
    dbTermList.each {dbTerm ->
      final TermId termId = TermId.of(dbTerm.getOntologyId())
      hpoCatMap.addAnnotatedTerm(termId, hpoOntology)
    }

    final List<HpoCategory> hpoCatList = hpoCatMap.getActiveCategoryList()

    final List<Map> catList = hpoCatList.collect { cat ->
      Map categoryTermMap = [:]
      cat.getNumberOfAnnotations()
      List<AnnotationResult> catAnnotationResult = []
      List<TermId> termIdList = cat.getAnnotatingTermIds()

      termIdList.each { tId ->
        Term term = hpoOntology.getTermMap().get(tId)
        catAnnotationResult << buildAnnotationResult(disease, term)
      }

      categoryTermMap.put("catLabel", cat.getLabel())
      categoryTermMap.put("terms", catAnnotationResult)
      return categoryTermMap
    }

    return catList
  }

  AnnotationResult buildAnnotationResult(DbDisease disease, Term term){
    DbTerm dbTerm = DbTerm.findByOntologyId(term.getId().toString())
    DbAnnotation annotation = DbAnnotation.findWhere(dbTerm: dbTerm, dbDisease: disease)
    if(annotation == null){
      // Mode of Inheritance Term.
      return new AnnotationResult(dbTerm, "-", "-", disease.getDiseaseId())
    }
    return new AnnotationResult(annotation.getDbTerm(), annotation.getOnset(), annotation.getFrequency(), annotation.getSources())
  }

}
