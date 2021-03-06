package hpo.api.functional.specs

import builders.dsl.spreadsheet.query.api.SpreadsheetCriteria
import builders.dsl.spreadsheet.query.api.SpreadsheetCriteriaResult
import builders.dsl.spreadsheet.query.poi.PoiSpreadsheetCriteria
import geb.spock.GebReportingSpec
import grails.testing.mixin.integration.Integration
import hpo.api.HpoSpecHelpers
import hpo.api.functional.pages.TermDetailsPage

@Integration
class HpoTermDetailsSpec extends  GebReportingSpec {

  /* If you change api url for term controller download change this */
  final API_DOWNLOAD_EXCEL_TERM_URL = "/api/hpo/download/term?identifier="

  def setup() {
  }


  def cleanup() {
  }

  def "genes can be downloaded as an excel file"() {

    when: 'clicking download association buttons'
    TermDetailsPage termDetailsPage = browser.to(TermDetailsPage)
    termDetailsPage.downloadAssociationButton.click()

    then: 'the dialog should open'
    waitFor{ termDetailsPage.downloadAssociationDialog }


    when: 'clicking disease association download'
    String identifier = termDetailsPage.getPageUrl().split("/").last()
    String association = termDetailsPage.downloadGeneAssociationButton.text().toLowerCase()
    String url = API_DOWNLOAD_EXCEL_TERM_URL+ identifier + "&association=" + association

    then:
    def excelBytes = downloadBytes(url)

    when: 'if we search for a row for a specific disease'
    ByteArrayInputStream bis = new ByteArrayInputStream(excelBytes)
    SpreadsheetCriteria query = PoiSpreadsheetCriteria.FACTORY.forStream(bis)
    SpreadsheetCriteriaResult result = HpoSpecHelpers.queryExcelSheet(query, 'CHD7')

    then: 'a row is found'
    result.cells.size() == 1
    result.cells.first().value.equals('CHD7')

  }

  void "test disease paging element" (){

    when:
    TermDetailsPage termDetailsPage = browser.to(TermDetailsPage)

    then:
    termDetailsPage.diseasePagingElement.text().startsWith("Displaying")

  }

  void "test gene paging element" (){

    when:
    TermDetailsPage termDetailsPage = browser.to(TermDetailsPage)

    then:
    termDetailsPage.diseasePagingElement.text().startsWith("Displaying")

  }

  void "test disease filter" (){

    when:
    TermDetailsPage termDetailsPage = browser.to(TermDetailsPage)

    then:
    termDetailsPage.diseasePagingElement.text().startsWith("Displaying")

    when:
    termDetailsPage.loadDiseaseAssociations()

    then:
    termDetailsPage.diseasePagingElement.text().startsWith("Displaying")

    when:
    termDetailsPage.loadAllDiseases()

    then:
    termDetailsPage.diseaseFilterElement.value('disability')
    assert termDetailsPage.diseasePagingRangeLabelElement.text().trim().equals("1 – 5 of 5")
  }


  def "diseases can be downloaded as an excel file"() {

    given: 'we go to the page'
    TermDetailsPage termDetailsPage = browser.to(TermDetailsPage)

    when: 'clicking export association button'
    waitFor{ termDetailsPage.downloadAssociationButton.displayed }
    termDetailsPage.downloadAssociationButton.click()

    then: 'the dialog should open'
    assert waitFor { termDetailsPage.downloadAssociationDialog.displayed }

    when: 'clicking disease association download'
    String identifier = termDetailsPage.getPageUrl().split("/").last()
    String association = termDetailsPage.downloadDiseaseAssociationButton.text().toLowerCase()
    String url = API_DOWNLOAD_EXCEL_TERM_URL + identifier + "&association=" + association

    then:
    def excelBytes = downloadBytes(url)

    when: 'if we search for a row for a specific disease'
    ByteArrayInputStream bis = new ByteArrayInputStream(excelBytes);
    SpreadsheetCriteria query = PoiSpreadsheetCriteria.FACTORY.forStream(bis)
    SpreadsheetCriteriaResult result = HpoSpecHelpers.queryExcelSheet(query, 'ORPHA:90796')

    then: 'a row is found'
    result.cells.size() == 1
    result.cells.first().value.equals('ORPHA:90796')
  }
}
