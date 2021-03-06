package hpo.api

class UrlMappings {

  //exclude the paths to:
  // 1) Tell grails to avoid routing for these static resources, but instead have direct access to these via http://<host>/app/<filename>
  // 2) Allow routing of /app/* to angular app (index.html), but ignore these paths
  static excludes = [
    '/app/*.html',
    '/app/*.ico',
    '/app/assets/*.png',
    '/app/assets/*.jpg',
    '/app/assets/*/*.png',
    '/app/assets/*/*.jpg',
    '/webjars/swagger-ui/3.20.9/swagger-ui-bundle.js',
    '/webjars/swagger-ui/3.20.9/swagger-ui-standalone-preset.js'
  ]

  static mappings = {
    delete "/$controller/$id(.$format)?"(action: "delete")
    get "/$controller(.$format)?"(action: "index")
    get "/$controller/$id(.$format)?"(action: "show")
    post "/$controller(.$format)?"(action: "save")
    put "/$controller/$id(.$format)?"(action: "update")
    patch "/$controller/$id(.$format)?"(action: "patch")

    "/"(uri: '/app/index.html')
    "/app/"(uri: '/app/index.html')
    "/app/*.js"(controller: 'contentTypeAsset', action:'setCharEncoding') {
     contentType = 'text/javascript; charset=UTF-8'
    }
   "/app/*.map"(controller: 'contentTypeAsset', action:'setCharEncoding') {
     contentType = 'application/json; charset=UTF-8'
   }
   "/app/assets/*.js"(controller: 'contentTypeAsset', action:'setCharEncoding') {
     contentType = 'text/javascript; charset=UTF-8'
   }
   "/app/*.css"(controller: 'contentTypeAsset', action:'setCharEncoding') {
     contentType = 'text/css; charset=UTF-8'
   }
   "/app/assets/*.css"(controller: 'contentTypeAsset', action:'setCharEncoding') {
     contentType = 'text/css; charset=UTF-8'
   }

    "/app/**"(uri: '/app/index.html')

    "/api/hpo/search"(controller: 'hpoSearch', action: 'searchAll')
    "/api/hpo/term/$id"(controller: 'hpoTerm', action: 'searchTerm')
    "/api/hpo/term/$id/genes"(controller: 'hpoTerm', action: 'searchGenesByTerm')
    "/api/hpo/term/$id/diseases"(controller: 'hpoTerm', action: 'searchDiseasesByTerm')
    "/api/hpo/term/$id/loinc"(controller: 'hpoTerm', action: 'searchLoincByTerm')
    "/api/hpo/term/intersecting"(controller: 'hpoTerm', action: 'searchIntersectingAssociations')

    "/api/hpo/gene/$id"(controller: 'hpoGeneDetails', action: 'searchGene')
    "/api/hpo/disease/$id"(controller: 'hpoDiseaseDetails', action: 'searchDisease')
    "/api/hpo/download/term"(controller: 'hpoExcel', action: 'downloadTermAnnotation')
    "/api/hpo/download/disease"(controller: 'hpoExcel', action: 'downloadDiseaseAnnotation')
    "/api/hpo/download/gene"(controller: 'hpoExcel', action: 'downloadGeneAnnotation')
    "/api/hpo/docs/$action?/$id?"(controller: "apiDoc", action: "getDocuments")

    "/api/maxo/search"(controller: "maxoSearch", action: 'searchMaxo')

    "500"(view: '/error')
    "404"(view: '/notFound')
  }
}

