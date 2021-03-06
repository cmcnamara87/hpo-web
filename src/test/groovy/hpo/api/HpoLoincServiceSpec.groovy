package hpo.api

import grails.testing.services.ServiceUnitTest
import hpo.api.util.Loinc2Hpo
import org.monarchinitiative.loinc2hpo.loinc.LoincEntry
import org.monarchinitiative.loinc2hpo.loinc.LoincId
import org.monarchinitiative.phenol.ontology.data.TermId
import spock.lang.Shared
import spock.lang.Specification

class HpoLoincServiceSpec extends Specification implements ServiceUnitTest<HpoLoincService> {

  @Shared
  Loinc2Hpo loinc2Hpo

  void setup(){
    service.hpoLoinc = loinc2Hpo
  }

  void "search loinc by hpo"() {

    given:
      TermId query = TermId.of("HP:0004363")
      Map<LoincId, LoincEntry> mockLoincEntryMap = new HashMap<>()
      Map<TermId, Set<LoincId>> mockReverseMap = new HashMap<>()

      mockLoincEntryMap.put(buildMockLoincId("38230-9"), buildMockLoincEntry(getOneLoincLine()))
      mockReverseMap.put(TermId.of("HP:0004363"), new HashSet<LoincId>(Arrays.asList(new LoincId("38230-9"))))
      service.hpoLoinc = Mock(Loinc2Hpo)
      service.hpoLoinc.getReverseAnnotationMap() >> mockReverseMap
      service.hpoLoinc.getLoincEntryMap() >> mockLoincEntryMap
    when:

      Set<LoincEntry> result = service.searchByHpo(query)
      service.hpoLoinc.getReverseAnnotationMap() == mockReverseMap
      service.hpoLoinc.getLoincEntryMap() == mockLoincEntryMap

    then:
      result.size() == 1
      result[0].getLongName() == "Calcium.ionized [Mass/volume] in Blood"

  }

  def buildMockLoincId(String id){
    return new LoincId(id)
  }

  def buildMockLoincEntry(String loincLine){
    return new  LoincEntry(loincLine)
  }

  def getOneLoincLine(){
    return '38230-9,Calcium.ionized,MCnc,Pt,Bld,Qn,,CHEM,1,Calcium.ionized [Mass/volume] in Blood,Ca-I Bld-mCnc,,ACTIVE,2.13,2.34'
  }
}
