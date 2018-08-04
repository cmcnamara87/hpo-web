package hpo.api.term

class DbTermSynonym {

  DbTerm dbTerm
  String synonym

  static constraints = {
    synonym()
  }
  static mapping = {
    version false
  }

  static belongsTo = [dbTerm: DbTerm]

}
